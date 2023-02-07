package com.example.sale.handler;

import com.example.sale.utils.NonBlockingFiles;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author xiaowenrou
 * @date 2023/2/6
 */
@Component
@AllArgsConstructor
public class FileHandler {

    private final Environment environment;

    /**
     * 文件上传
     * @param request
     * @return
     */
    public Mono<ServerResponse> fileUpload(ServerRequest request) {
        var current = Path.of(DateTimeFormatter.ofPattern("yyyyMM").format(LocalDate.now()));
        var dict = Path.of(Objects.requireNonNull(this.environment.getProperty("filePath"))).resolve(current);
        if (!NonBlockingFiles.exists(dict)) {
            NonBlockingFiles.createDirectory(dict);
        }
        Function<FilePart, Mono<String>> function = part -> {
            var fn = UUID.randomUUID() + "." + FilenameUtils.getExtension(part.filename());
            return part.transferTo(dict.resolve(fn)).then(Mono.defer(() -> Mono.just(current.resolve(fn).toString())));
        };
        var uploaded = request.multipartData().map(m -> m.get("file"))
                .flatMapMany(Flux::fromIterable).cast(FilePart.class).flatMap(function);
        return ServerResponse.ok().body(uploaded, Path.class);
    }

}
