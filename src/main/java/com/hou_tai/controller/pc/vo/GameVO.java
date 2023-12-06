package com.hou_tai.controller.pc.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @ClassName: GameVo
 * @Description: 游戏输出
 * @Author: Sam
 * @Date: 2023-11-04 11:52
 * @Version: 1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(title = "游戏输出")
public class GameVO {

    @Schema(title = "游戏ID")
    private Long id;

    @Schema(title = "游戏名称")
    private String gameName;

    @Schema(title = "游戏分类")
    private String gameCategory;

    @Schema(title = "游戏语言ID")
    private Integer languageId;

    @Schema(title = "游戏LOGO")
    private String gameLogo;

    @Schema(title = "游戏主图")
    private String gameMainLogo;

    @Schema(title = "游戏地址")
    private String gameUrl;

    @Schema(title = "创建用户ID")
    private Long createId;

    @Schema(title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(title = "更新用户ID")
    private Long updateId;

    @Schema(title = "游戏更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //前后端接收日期都可以改变格式
    @JSONField(format = "yyyy-MM-dd HH:mm:ss") //ResponseBody做了拦截，封装了一层导致原来JsonFormat无效，前端返回日期，要用JSONField来处理
    private LocalDateTime updateTime;

    @Schema(title = "游戏语言名称")
    private String languageName;

    @Schema(title = "创建人名称")
    private String creator;

}
