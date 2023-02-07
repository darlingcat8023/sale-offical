package com.example.sale.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * @author xiaowenrou
 * @date 2023/2/7
 */
@Data
@Accessors(chain = true)
@Table(value = "sale_mail")
public class MailEntity {

    @Id
    private Long id;

    @CreatedDate
    private LocalDateTime createdDate;

    private String address;

    private String sender;

    private String content;

}
