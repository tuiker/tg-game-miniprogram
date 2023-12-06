package com.hou_tai.controller.pc.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: GaoLu
 * @Date: 2023-11-08 17:31
 * @Description: 游戏概括返回对象
 */
@Data
@Schema(title = "游戏概括返回对象")
public class GameGeneralizeVO {
    @Schema(title = "游戏ID", description = "游戏ID", name = "id")
    private Long id;
    @Schema(title = "游戏分类", description = "游戏分类", name = "gameCategory")
    private String gameCategory;
    @Schema(title = "游戏名称", description = "游戏名称", name = "gameName")
    private String gameName;
    @Schema(title = "游戏语言名称", description = "游戏语言名称", name = "languageName")
    private String languageName;
    @Schema(title = "游戏LOGO", description = "游戏LOGO", name = "gameLogo")
    private String gameLogo;
    @Schema(title = "导流次数", description = "导流次数", name = "openNum")
    private Integer openNum;


}
