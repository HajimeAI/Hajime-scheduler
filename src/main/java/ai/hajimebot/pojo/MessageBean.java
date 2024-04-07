package ai.hajimebot.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageBean<T> {

    /**
     * message type
     */
    private String msgType;
    /**
     * cmd
     */
    private String cmd;
    /**
     * message data
     */
    T data;
}
