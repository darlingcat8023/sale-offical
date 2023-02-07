package com.example.sale.handler;

import com.example.sale.dao.MailRepository;
import com.example.sale.dao.entity.MailEntity;
import com.example.sale.model.MailSendRequest;
import com.example.sale.utils.ValidatorUtils;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author xiaowenrou
 * @date 2023/2/7
 */
@Component
@AllArgsConstructor
public class SaleMailHandler {

    private final MailRepository mailRepository;

    private final Validator validator;

    private final JavaMailSender mailSender;

    private final ITemplateEngine engine;

    private final Environment environment;


    public Mono<ServerResponse> sendMail(ServerRequest request) {
        var mono = request.bodyToMono(MailSendRequest.class).doOnNext(req -> ValidatorUtils.valid(this.validator, req))
                .flatMap(req -> this.mailRepository.save(req.convert()).doOnSuccess(ent -> this.doSend(req))).thenReturn("success");
        return ServerResponse.ok().body(mono, String.class);
    }

    @SneakyThrows
    private void doSend(MailSendRequest request) {
        var message = new MimeMessageHelper(this.mailSender.createMimeMessage(), true);
        message.setTo(Objects.requireNonNull(this.environment.getProperty("targetMail")));
        message.setFrom(request.address());
        Context ctx = new Context();
        ctx.setVariable("sender", request.sender());
        ctx.setVariable("address", request.address());
        ctx.setVariable("content", request.content());
        ctx.setVariable("current", LocalDateTime.now());
        ctx.setVariable("location", request.location());
        message.setText(this.engine.process("mail", ctx), true);
        this.mailSender.send(message.getMimeMessage());
    }

    public Mono<ServerResponse> pageMail(ServerRequest request) {
        var pageable = PageRequest.of(request.queryParam("page").map(Integer::parseInt).orElse(0), 10);
        var sort = Sort.by("id").ascending();
        var entity = new MailEntity();
        request.queryParam("address").ifPresent(entity::setAddress);
        var matcher = ExampleMatcher.matching().withMatcher("address", ExampleMatcher.GenericPropertyMatchers.contains()).withIgnoreNullValues();
        var ret = this.mailRepository.findBy(Example.of(entity, matcher), fluent -> fluent.sortBy(sort).page(pageable));
        var type = new ParameterizedTypeReference<Page<MailEntity>>() {};
        return ServerResponse.ok().body(ret, type);
    }

}
