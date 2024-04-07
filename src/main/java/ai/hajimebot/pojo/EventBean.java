package ai.hajimebot.pojo;

import cn.hutool.core.annotation.Alias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventBean {
    /**
     * Event Type
     */
    private String kind;

    /**
     * Device ID
     */
    @Alias("node_id")
    private String nodeId;

    /**
     * Event Content
     */
    private Map<String, Object> content;
}
