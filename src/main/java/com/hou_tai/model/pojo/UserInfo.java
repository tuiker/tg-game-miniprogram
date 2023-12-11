package com.hou_tai.model.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.nio.LongBuffer;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("user_info")
@Schema(title = "用户实体")
public class UserInfo {
    /**
     * 用户ID
     */
    @TableId
    private Long id ;
    /** 用户账号 */
    private String userName ;
    /** 用户头像 */
    private String userImg ;
    /** 用户密码 */
    private String password ;
    /** 最近登录时间 */
    private LocalDateTime recentLoginTime ;
    /** 创建人ID */
    private Long createId ;
    /** 创建时间 */
    private LocalDateTime createTime ;
    /** 修改人ID */
    private Long updateId ;
    /** 修改时间 */
    private LocalDateTime updateTime ;
    /** 角色ID */
    private Integer roleId ;
    /** 渠道ID */
    private Integer channelId ;

}