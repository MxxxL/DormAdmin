package com.kaiyu.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * LocalDate配置序列化及转化器
 * @author mxxxl
 * @date 2021/6/15
 */
@Configuration
public class LocalDateConfig{

    private static final String PATTERN = "yyyy-MM-dd";

    @Bean
    public LocalDateSerializer localDateSerializer(){
        return new LocalDateSerializer(DateTimeFormatter.ofPattern(PATTERN));
    }

    @Bean
    public LocalDateDeserializer localDateDeserializer(){
        return new LocalDateDeserializer(DateTimeFormatter.ofPattern(PATTERN));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(){
        return builder -> {
            builder.serializerByType(LocalDate.class,localDateSerializer());
            builder.deserializerByType(LocalDate.class,localDateDeserializer());
            builder.simpleDateFormat(PATTERN);
        };
    }

    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return LocalDate.parse(source, DateTimeFormatter.ofPattern(PATTERN));
            }
        };
    }
}

