package ai.hajimebot.global;

import lombok.*;
import org.springframework.context.ApplicationEvent;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogEvent {

    private String name;

    private String type;

    private String content;

//    public LogEvent(Long id,String name) {
//        super(id);
//        this.name = name;
//    }
}
