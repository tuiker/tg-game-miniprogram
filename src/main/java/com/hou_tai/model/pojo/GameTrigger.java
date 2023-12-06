package com.hou_tai.model.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.hou_tai.common.enums.TriggerTypeEnums;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("game_trigger")
@Schema(title = "游戏触发记录实体")
public class GameTrigger implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id ;

    /** 游戏ID */
    @TableField(value = "game_id")
    private Long gameId ;

    /** 类型 对应枚举类 {@link TriggerTypeEnums} */
    @TableField(value = "type")
    private Integer type ;

    /** 导流分类 1：热门推荐，2：大家都在玩，3：巴西电子 */
    @TableField(value = "category")
    private Integer category;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}