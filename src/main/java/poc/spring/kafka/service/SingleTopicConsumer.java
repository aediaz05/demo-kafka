package poc.spring.kafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import poc.spring.kafka.domain.Message;
import poc.spring.kafka.service.spec.MessageConsumer;
import poc.spring.kafka.service.spec.MessageDAO;

import java.io.IOException;

@Service
public class SingleTopicConsumer implements MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(SingleTopicConsumer.class);

    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private ObjectMapper mapper;

    @Override
    @KafkaListener(topics = { "email" })
    public void consume(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){
        try {

            Message msg = mapper.readValue(message, Message.class);
            messageDAO.save(topic, msg);

        }catch(IOException e){
            logger.error(e.getLocalizedMessage());
        }
    }
}
