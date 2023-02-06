package com.example.sale.utils;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

/**
 * @author xiaowenrou
 * @date 2023/2/6
 */
public abstract class NonBlockingFiles {

    public static boolean exists(Path path, LinkOption... options) {
        return Files.exists(path, options);
    }

    @SneakyThrows
    public static Path createDirectory(Path dir, FileAttribute<?>... attrs) {
        return Files.createDirectory(dir, attrs);
    }

    @SneakyThrows
    public static Path write(Path path, byte[] bytes, OpenOption... options) {
        return Files.write(path, bytes, options);
    }

}
