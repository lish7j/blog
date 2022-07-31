package com.lssj.blog.config;

import org.elasticsearch.client.RestHighLevelClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;


import java.sql.Timestamp;

@Configuration
public class EsConfig extends AbstractElasticsearchConfiguration {

    @Override
    @Bean(destroyMethod = "close")
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchConverter elasticsearchConverter(SimpleElasticsearchMappingContext mappingContext) {
        DefaultConversionService defaultConversionService = new DefaultConversionService();
        defaultConversionService.addConverter(Jsr310Converters.DateToLocalDateTimeConverter.INSTANCE);
        defaultConversionService.addConverter(Jsr310Converters.StringToLocalDateTimeConverter.INSTANCE);
        defaultConversionService.addConverter(new TimestampToLong());
        defaultConversionService.addConverter(new LongToTimestamp());
        return new MappingElasticsearchConverter(mappingContext, defaultConversionService);
    }

    @WritingConverter
    static class TimestampToLong implements Converter<Timestamp, Long> {

        @Override
        public Long convert(Timestamp source) {
            return source.getTime();
        }
    }

    @ReadingConverter
    static class LongToTimestamp implements Converter<Long, Timestamp> {


        @Override
        public Timestamp convert(Long source) {
            return new Timestamp(source);
        }
    }
}

