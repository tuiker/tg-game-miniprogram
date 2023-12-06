package com.hou_tai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 游戏分类枚举类
 * @Author: yxf
 */
@AllArgsConstructor
@Getter
public enum CategoryEnums {

    HOT(1, "热门推荐"),

    EVERYONE_IS_PLAYING(2, "大家都在玩"),

    BRAZIL_ELECTRON(3,"巴西电子");

    private final Integer code;
    private final String name;

}
