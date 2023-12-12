package com.hou_tai.controller.mobile.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @ClassName: MobileGameVo
 * @Description: 移动端游戏输出
 * @Author: Sam
 * @Date: 2023-11-04 11:52
 * @Version: 1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(title = "移动端游戏输出")
public class MobileGameVO {
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

    @Schema(title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(title = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
