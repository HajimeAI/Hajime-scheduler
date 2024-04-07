package ai.hajimebot.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;

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
@Table(value = "user")
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key ID
     */
    @Id(keyType = KeyType.Auto)
    @ApiModelProperty("Primary key ID")
    private Long id;

    /**
     * username
     */
    @ApiModelProperty("username")
    private String username;

    /**
     * password
     */
    @ApiModelProperty("password")
    private String password;

    /**
     * role
     */
    @ApiModelProperty("role")
    private String role;

    /**
     * status
     */
    @ApiModelProperty("status")
    private String status;

}
