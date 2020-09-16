package poc.spring.kafka.service.spec;

public interface MessageConsumer {

    void consume(String message, String topic);
}
