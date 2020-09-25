package poc.spring.kafka.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poc.spring.kafka.domain.Message;
import poc.spring.kafka.service.spec.MessageDAO;
import poc.spring.kafka.service.spec.MessageProducer;

import java.util.List;

@RestController
@RequestMapping("api/messages/")
public class MessageController {

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private MessageDAO dao;

    @GetMapping("/{topic}")
    public ResponseEntity<List<Message>> getMessageByTopic(@PathVariable("topic") String topic) {
        List<Message> messages = dao.getMessageByTopic(topic);
        return ResponseEntity.ok(messages);
    }

    @PostMapping(value = "/publish")
    public ResponseEntity<Message> publish(@RequestBody Message message) {

        try {
            messageProducer.send(message);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
