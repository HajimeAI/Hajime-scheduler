package ai.hajimebot.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NodeBean {
    /**
     * Device ID
     */
    String imei;

    /**
     *
     */
    String type;

    String content;

}
