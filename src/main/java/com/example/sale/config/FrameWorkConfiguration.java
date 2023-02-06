package com.example.sale.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xiaowenrou
 * @date 2023/2/6
 */
@Configuration(proxyBeanMethods = false)
public class FrameWorkConfiguration {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Bean
    public Validator validator() {
        var validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure().messageInterpolator(new ParameterMessageInterpolator())
                .failFast(true).buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        var implicitRecordAI = new JacksonAnnotationIntrospector() {
            @Override
            public PropertyName findNameForDeserialization(Annotated a) {
                var nameForDeserialization = super.findNameForDeserialization(a);
                if (PropertyName.USE_DEFAULT.equals(nameForDeserialization) && a instanceof AnnotatedParameter && ((AnnotatedParameter) a).getDeclaringClass().isRecord()) {
                    var str = findImplicitPropertyName((AnnotatedParameter) a);
                    if (str != null && !str.isEmpty()) {
                        return PropertyName.construct(str);
                    }
                }
                return nameForDeserialization;
            }

            @Override
            public String findImplicitPropertyName(AnnotatedMember m) {
                if (m.getDeclaringClass().isRecord()) {
                    if (m instanceof AnnotatedParameter parameter) {
                        return m.getDeclaringClass().getRecordComponents()[parameter.getIndex()].getName();
                    }
                    for (var recordComponent : m.getDeclaringClass().getRecordComponents()) {
                        if (recordComponent.getName().equals(m.getName())) {
                            return m.getName();
                        }
                    }
                }
                return super.findImplicitPropertyName(m);
            }
        };
        // 配置日期类型序列化和反序列化
        var timeModule = new JavaTimeModule();

        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));

        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DATE_FORMATTER));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER));

        timeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(TIME_FORMATTER));
        timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(TIME_FORMATTER));

        return builder.createXmlMapper(false)
                .annotationIntrospector(implicitRecordAI)
                .modules(timeModule)
                .failOnEmptyBeans(false)
                .failOnUnknownProperties(false)
                .build();
    }

}
