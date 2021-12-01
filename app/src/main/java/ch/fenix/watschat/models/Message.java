package ch.fenix.watschat.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime sentAt;
    private String message;
    private String sender;
    private String receiverTel;
}
