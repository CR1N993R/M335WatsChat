package ch.fenix.watschat.models;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocketMsg {
    public SocketMsg(String tel, Message message) {
        this.tel = tel;
        this.message = message;
    }

    public SocketMsg(String tel) {
        this.tel = tel;
    }

    private Message message;
    private List<Message> messages;
    private String tel;
}
