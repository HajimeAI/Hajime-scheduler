package ai.hajimebot.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.sql.Timestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  entity
 *
 * @author xc.deng
 * @since 1.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("")
@Table(value = "event")
public class EventVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key ID
     */
    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("Primary key ID")
    private Long id;

    /**
     * Event Type
     */
    @ApiModelProperty("Event Type")
    private String kind;

    /**
     * Device ID
     */
    @ApiModelProperty("Device ID")
    private String nodeId;

    /**
     * Event Content
     */
    @ApiModelProperty("Event Content")
    private String content;

    /**
     * CreateTime
     */
    @ApiModelProperty("CreateTime")
    @Column(onInsertValue = "now()")
    private Timestamp createTime;

}
