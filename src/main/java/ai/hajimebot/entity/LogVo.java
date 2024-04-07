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
@Table(value = "log")
public class LogVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key
     */
    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("Primary key")
    private Long id;

    /**
     * Log Type
     */
    @ApiModelProperty("Log Type")
    private String type;

    /**
     * Log Content
     */
    @ApiModelProperty("Log Content")
    private String content;

    /**
     * Operators
     */
    @ApiModelProperty("Operators")
    private String op;

    @ApiModelProperty("")
    @Column(onInsertValue = "now()")
    private Timestamp createTime;

}
