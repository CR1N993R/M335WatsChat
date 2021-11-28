package ch.fenix.watschat.models;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocketMsg {
    private Message message;
    private List<Message> messages;
    private String tel;
}
