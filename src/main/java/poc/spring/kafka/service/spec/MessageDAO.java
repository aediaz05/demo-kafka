package poc.spring.kafka.service.spec;

import poc.spring.kafka.domain.Message;

import java.util.List;

public interface MessageDAO {

    void save(String topic, Message message);

    List<Message> getMessageByTopic(String topic);
}
