package poc.spring.kafka.service.spec;

import poc.spring.kafka.domain.Message;

public interface MessageProducer {

    void send(Message message);
}
