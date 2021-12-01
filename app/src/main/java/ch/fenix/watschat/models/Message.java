package ch.fenix.watschat.models;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Message {
    private LocalDateTime sentAt;
    private String message;
    private String sender;
}
