package com.hou_tai.controller.mobile.dto;

import com.hou_tai.model.base.PageDaoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "游戏分页请求 VO")
public class GamePageReqDTO extends PageDaoEntity {

    @Schema(title = "游戏分类", description = "游戏分类 1：热门推荐，2：大家都在玩，3：巴西电子", type = "Integer")
    private Integer gameCategory;

}
