package com.example.sale.handler;

import com.example.sale.dao.AdminRepository;
import com.example.sale.model.LoginRequest;
import com.example.sale.model.ModifyPasswordRequest;
import com.example.sale.utils.ValidatorUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Validator;

/**
 * @author xiaowenrou
 * @date 2023/2/27
 */
@Component
@AllArgsConstructor
public class AdminHandler {

    private final Validator validator;

    private final AdminRepository adminRepository;

    public Mono<ServerResponse> login(ServerRequest request) {
        var mono = request.bodyToMono(LoginRequest.class).doOnNext(req -> ValidatorUtils.valid(this.validator, req))
                .flatMap(req -> this.adminRepository.findByUserNameAndPassword(req.userName(), req.password()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException("用户名或密码错误")))).thenReturn("success");
        return ServerResponse.ok().body(mono, String.class);
    }

    public Mono<ServerResponse> modifyPassword(ServerRequest request) {
        var mono = request.bodyToMono(ModifyPasswordRequest.class).doOnNext(req -> ValidatorUtils.valid(this.validator, req))
                .flatMap(req -> this.adminRepository.findByUserNameAndPassword(req.userName(), req.password()).switchIfEmpty(Mono.error(new RuntimeException("用户名或密码错误"))).flatMap(ent -> {
                    ent.setPassword(req.newPassword());
                    return this.adminRepository.save(ent);
                })).thenReturn("success");
        return ServerResponse.ok().body(mono, String.class);
    }

}
