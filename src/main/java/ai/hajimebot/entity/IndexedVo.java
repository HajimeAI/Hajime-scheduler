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
@Table(value = "indexed")
public class IndexedVo implements Serializable {

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
     * 0 disable 1 enable
     */
    @ApiModelProperty("0 disable 1 enable")
    private String enable;

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
     * 1 del 0 normal
     */
    @ApiModelProperty("1 del 0 normal")
    @Column(isLogicDelete = true)
    private Integer isDelete;

}
