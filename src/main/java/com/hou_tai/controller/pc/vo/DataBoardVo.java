package com.hou_tai.controller.pc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Sam
 * @Date: 2023-10-21
 * @Description: 游戏广告返回类
 */
@Data
@Schema(title = "数据概览看板出参")
public class DataBoardVo implements Serializable {

    @Schema(title = "热门推荐导流总数", description = "热门推荐导流总数", name = "hotSum")
    private Integer hotSum = 0;
    @Schema(title = "大家都在玩导流总数", description = "大家都在玩导流总数", name = "everyOneIsPlayingSum")
    private Integer everyOneIsPlayingSum = 0;
    @Schema(title = "巴西电子导流总数", description = "巴西电子导流总数", name = "brazilElectronSum")
    private Integer brazilElectronSum = 0;

    @Schema(title = "今日热门推荐导流次数", description = "今日热门推荐导流次数", name = "todayHot")
    private Integer todayHot = 0;
    @Schema(title = "昨日热门推荐导流次数", description = "昨日热门推荐导流次数", name = "yesterdayHot")
    private Integer yesterdayHot = 0;

    @Schema(title = "今日大家都在玩导流次数", description = "今日大家都在玩导流次数", name = "todayEveryOneIsPlaying")
    private Integer todayEveryOneIsPlaying = 0;
    @Schema(title = "昨日大家都在玩导流次数", description = "昨日大家都在玩导流次数", name = "yesterdayEveryOneIsPlaying")
    private Integer yesterdayEveryOneIsPlaying = 0;

    @Schema(title = "今日巴西电子导流次数", description = "今日巴西电子导流次数", name = "todayBrazilElectron")
    private Integer todayBrazilElectron = 0;
    @Schema(title = "昨日巴西电子导流次数", description = "昨日巴西电子导流次数", name = "yesterdayBrazilElectron")
    private Integer yesterdayBrazilElectron = 0;


    @Schema(title = "过去七日内热门推荐导流折线图数据", description = "过去七日内热门推荐导流折线图数据", name = "hotDataOfTime")
    private List<DataOfTimeVo> hotDataOfTime;
    @Schema(title = "过去七日内大家都在玩导流折线图数据", description = "过去七日内大家都在玩导流折线图数据", name = "everyOneIsPlayingDataOfTime")
    private List<DataOfTimeVo> everyOneIsPlayingDataOfTime;
    @Schema(title = "过去七日内巴西电子导流折线图数据", description = "过去七日内巴西电子导流折线图数据", name = "brazilElectronDataOfTime")
    private List<DataOfTimeVo> brazilElectronDataOfTime;

}
