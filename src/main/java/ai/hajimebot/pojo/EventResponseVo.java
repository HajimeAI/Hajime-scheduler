package ai.hajimebot.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventResponseVo<T> {

    /**
     * Event Type
     */
    private String kind;
    /**
     * Device ID
     */
    @JsonProperty("node_id")
    private String nodeId;
    /**
     * Event Content
     */
    private T content;

}
