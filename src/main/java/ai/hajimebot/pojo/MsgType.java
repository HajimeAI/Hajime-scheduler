package ai.hajimebot.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MsgType {

    PING(1,"ping"),
    REGISTER(2,"register");

    private final int type;
    private final String name;
}
