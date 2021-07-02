//package com.project.share.config;
//
//import com.project.share.exception.KafkaHandler;
//import com.project.share.model.ChatMessage;
//import com.project.share.model.Project;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.*;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableKafka
//public class KafkaConfig {
//    private final String BOOTSTRAP_SERVER = "localhost:9092";
//
//    // Producer Configuration
//    @Bean
//    public Map<String, Object> producerConfiguration() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
//        config.put(ProducerConfig.ACKS_CONFIG, "all");
//        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//
//        return config;
//    }
//
//    @Bean
//    public ProducerFactory<String, Object> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfiguration());
//    }
//
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    // Consumer Configuration
//    // Messaging
//    @Bean
//    public ConsumerFactory<String, ChatMessage> consumerFactoryMessaging() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "groupMessage");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(ChatMessage.class));
//    }
//
//    @Bean(name = "listenerFactoryMessaging")
//    public ConcurrentKafkaListenerContainerFactory<String, ChatMessage> kafkaListenerContainerFactoryMessaging() {
//        ConcurrentKafkaListenerContainerFactory<String, ChatMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactoryMessaging());
//        factory.setErrorHandler(new KafkaHandler());
//        return factory;
//    }
//
//    // Redis
//    public ConsumerFactory<String, ChatMessage> consumerFactoryRedis() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "groupRedis");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(ChatMessage.class));
//    }
//
//    @Bean(name = "listenerFactoryRedis")
//    public ConcurrentKafkaListenerContainerFactory<String, ChatMessage> kafkaListenerContainerFactoryRedis() {
//        ConcurrentKafkaListenerContainerFactory<String, ChatMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactoryRedis());
//        factory.setErrorHandler(new KafkaHandler());
//        return factory;
//    }
//
//    // Elastic Search
//    public ConsumerFactory<String, Project> consumerFactoryES() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "groupES");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(Project.class));
//    }
//
//    @Bean(name = "listenerFactoryES")
//    public ConcurrentKafkaListenerContainerFactory<String, Project> kafkaListenerContainerFactoryES() {
//        ConcurrentKafkaListenerContainerFactory<String, Project> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactoryES());
//        factory.setErrorHandler(new KafkaHandler());
//        return factory;
//    }
//
//    // RediSearch
//    public ConsumerFactory<String, Project> consumerFactoryRediSearch() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "groupRedis");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(Project.class));
//    }
//
//    @Bean(name = "listenerFactoryRediSearch")
//    public ConcurrentKafkaListenerContainerFactory<String, Project> kafkaListenerContainerFactoryRediSearch() {
//        ConcurrentKafkaListenerContainerFactory<String, Project> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactoryRediSearch());
//        factory.setErrorHandler(new KafkaHandler());
//        return factory;
//    }
//}
