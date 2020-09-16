package poc.spring.kafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;
import poc.spring.kafka.domain.Message;
import poc.spring.kafka.service.spec.MessageProducer;

import java.io.IOException;

@Service
public class KafkaProducer implements MessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(Message message) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonMessage = mapper.writeValueAsString(message);

            kafkaTemplate.send(message.getType().getTopicKey(), jsonMessage)
                    .addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            logger.error("Error sending message {}", throwable.getLocalizedMessage());
                        }

                        @Override
                        public void onSuccess(SendResult<String, String> stringStringSendResult) {
                            logger.info("Send message to {}", message.getType().getTopicKey());
                        }
                    });
        }catch (IOException e){
            logger.error(e.getLocalizedMessage());
            throw new UnsupportedOperationException(e);
        }
    }
}
