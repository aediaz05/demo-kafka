package poc.spring.kafka.service;

import org.springframework.stereotype.Service;
import poc.spring.kafka.domain.Message;
import poc.spring.kafka.service.spec.MessageDAO;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
public class MessageMemory implements MessageDAO {

    private static ConcurrentMap<String, ConcurrentMap<Instant, Message>> receivedMessage = new ConcurrentHashMap<>();

    @Override
    public void save(String topic, Message message){

        ConcurrentMap<Instant, Message> msgByTopic =
                receivedMessage.computeIfAbsent(topic, k -> new ConcurrentHashMap<>());

        msgByTopic.putIfAbsent(Instant.now(), message);

    }

    @Override
    public List<Message> getMessageByTopic(String topic) {

        return receivedMessage.computeIfAbsent(topic, k -> new ConcurrentHashMap<>())
                .values().stream().collect(Collectors.toList());
    }
}
