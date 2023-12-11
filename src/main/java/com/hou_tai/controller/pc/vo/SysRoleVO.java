package com.hou_tai.controller.pc.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(title = "角色信息", description = "角色信息对象")
public class SysRoleVO {

    @Schema(title = "角色id", description = "角色id", name = "id")
    private long id;

    @Schema(title = "角色名称", description = "角色名称", name = "roleName")
    private String roleName;

    @Schema(title = "角色编码", description = "角色编码", name = "roleCode")
    private String roleCode;

    @Schema(title = "角色描述", description = "角色描述", name = "roleDescribe")
    private String roleDescribe;
}
