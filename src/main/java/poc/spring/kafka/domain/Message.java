package poc.spring.kafka.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Type {

        EMAIL("email"),
        SMS("sms"),
        MMS("mms");

        private String topicKey;

        Type(String topicKey){
            this.topicKey = topicKey;
        }

        public String getTopicKey(){
            return topicKey;
        }
    }

    private Type type;

    private String from;

    private String to;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate reception;

    private Message(){
        this.reception = LocalDate.now();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getReception() {
        return reception;
    }
}
