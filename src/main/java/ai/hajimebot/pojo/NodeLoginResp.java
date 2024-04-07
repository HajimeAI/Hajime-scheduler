package ai.hajimebot.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NodeLoginResp {
    /**
     * Primary key ID
     */
    private Long id;

    /**
     * p2p cmd
     */
    private String cmd;


    /**
     * healthy
     */
    private Long healthy;

    /**
     * indexed id
     */
    private String type;

    /**
     * Device Identifier
     */
    private String imei;

    /**
     * ip address
     */
    private String ip;

}
