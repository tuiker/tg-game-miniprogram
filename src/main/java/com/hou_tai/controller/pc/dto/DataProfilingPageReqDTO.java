package com.hou_tai.controller.pc.dto;

import com.hou_tai.model.base.PageDaoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "数据概况列表分页查询请求 DTO")
@Data
public class DataProfilingPageReqDTO extends PageDaoEntity {

    @Schema(description = "排序类型  1：按游戏添加时间降序，2：按导流次数降序", type = "Integer")
    private Integer orderType;

}
