package com.hou_tai.controller.pc.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(title = "游戏修改请求 DTO")
public class GameUpdateReqDTO implements Serializable {

    @Schema(title = "游戏ID")
    private Long Id;

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
}
