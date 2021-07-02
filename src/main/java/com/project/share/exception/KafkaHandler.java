//package com.project.share.exception;
//
//
//import com.project.share.controller.ProjectController;
//import org.apache.kafka.clients.consumer.Consumer;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.common.TopicPartition;
//import org.apache.kafka.common.errors.SerializationException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.listener.ErrorHandler;
//import org.springframework.kafka.listener.MessageListenerContainer;
//
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class KafkaHandler implements ErrorHandler {
//    static final Logger log = LoggerFactory.getLogger(ProjectController.class);
//
//    private void seekSerializeException(Exception e, Consumer<?, ?> consumer) {
//        String p = ".*partition (.*) at offset ([0-9]*).*";
//        Pattern r = Pattern.compile(p);
//        Matcher m = r.matcher(e.getMessage());
//        if (m.find()) {
//            int idx = m.group(1).lastIndexOf("-");
//            String topics = m.group(1).substring(0, idx);
//            int partition = Integer.parseInt(m.group(1).substring(idx));
//            int offset = Integer.parseInt(m.group(2));
//            TopicPartition topicPartition = new TopicPartition(topics, partition);
//            consumer.seek(topicPartition, (offset + 1));
//            log.info("Skipped message with offset {} from partition {}", offset, partition);
//        }
//    }
//    @Override
//    public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord) {
//        log.error("Error in process with Exception {} and the record is {}", e, consumerRecord);
//    }
//
//    @Override
//    public void handle(Exception thrownException, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {
//        if (thrownException instanceof SerializationException)
//            seekSerializeException(thrownException, consumer);
//    }
//
//    @Override
//    public void clearThreadState() {
//
//    }
//
//    @Override
//    public boolean isAckAfterHandle() {
//        return false;
//    }
//
//    @Override
//    public void setAckAfterHandle(boolean ack) {
//
//    }
//
//    @Override
//    public void handle(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer, MessageListenerContainer container) {
//        log.error("Error in process with Exception {} and the records are {}", thrownException, records);
//
//        if (thrownException instanceof SerializationException)
//            seekSerializeException(thrownException, consumer);
//    }
//}
