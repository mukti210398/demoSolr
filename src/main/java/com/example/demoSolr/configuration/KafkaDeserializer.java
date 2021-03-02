//package com.example.demoSolr.configuration;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.common.serialization.Deserializer;
//
//import java.io.IOException;
//import java.util.Map;
//
//public class KafkaDeserializer<T> implements Deserializer<T> {
//    private ObjectMapper mapper;
//
//    @Override
//    public void close() {
//    }
//    @Override
//    public void configure(final Map<String, ?> settings, final boolean isKey) {
//        mapper = new ObjectMapper();
//    }
//    @Override
//    public T deserialize(final String topic, final byte[] bytes) {
//        try {
//            return mapper.readValue(bytes, new TypeReference<T>() {
//            });
//        } catch (final IOException e) {
//            throw new IllegalArgumentException(e);
//        }
//    }
//}