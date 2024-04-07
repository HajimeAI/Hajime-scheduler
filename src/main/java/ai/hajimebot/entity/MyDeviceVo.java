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
@Table(value = "my_device")
public class MyDeviceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key ID
     */
    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("Primary key ID")
    private Long id;

    /**
     * User ID
     */
    @ApiModelProperty("User ID")
    private Long uid;

    /**
     * Device ID
     */
    @ApiModelProperty("Device ID")
    private Long nid;

    /**
     * Device Identifier
     */
    @ApiModelProperty("Device Identifier")
    private String imei;

    /**
     * CreateTime
     */
    @ApiModelProperty("CreateTime")
    @Column(onInsertValue = "now()")
    private Timestamp createTime;

}
