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
@Table(value = "node")
public class NodeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key ID
     */
    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("Primary key ID")
    private Long id;

    /**
     * name
     */
    @ApiModelProperty("name")
    private String name;

    /**
     * desc
     */
    @ApiModelProperty("desc")
    private String desc;

    /**
     * healthy
     */
    @ApiModelProperty("healthy")
    private Long healthy;

    /**
     * indexed ID
     */
    @ApiModelProperty("indexed ID")
    private String type;


    @ApiModelProperty("device type")
    @Column(value = "device_type")
    private String deviceType;

    /**
     * Device Identifier
     */
    @ApiModelProperty("Device Identifier")
    private String imei;

    /**
     * ip address
     */
    @ApiModelProperty("ip address")
    private String ip;

    /**
     * CreateTime
     */
    @ApiModelProperty("CreateTime")
    @Column(onInsertValue = "now()")
    private Timestamp createTime;

    /**
     * updateTime
     */
    @ApiModelProperty("updateTime")
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private Timestamp updateTime;

    /**
     * detail
     */
    @ApiModelProperty("detail")
    private String detail;

}
