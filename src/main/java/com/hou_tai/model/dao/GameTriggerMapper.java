package com.hou_tai.model.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hou_tai.controller.pc.vo.GameGeneralizeVo;
import com.hou_tai.model.pojo.GameTrigger;
import com.hou_tai.controller.pc.vo.DataOfTimeVo;

import java.util.HashMap;
import java.util.List;

public interface GameTriggerMapper extends BaseMapper<GameTrigger> {

    /**
     * 根据触发类型、天数（-1：当前日期减一天，1：当前日期加一天）统计记录数
     * @param type
     * @param days
     * @return
     */
    List<HashMap<String, Object>> getCountByDays(Integer type, Integer days);

    /**
     * @Description 获取近七日数据
     * @Author GaoLu
     * @Date 2023/11/8
     * @Return
     * @Param type
     **/
    List<DataOfTimeVo> getNumForSevenDay(Long gameId, int type, Integer category);

    /**
     * 根据触发类型统计游戏导流次数
     * @param type 触发类型
     * @return
     */
    List<GameGeneralizeVo> getCountAll(int type);
}