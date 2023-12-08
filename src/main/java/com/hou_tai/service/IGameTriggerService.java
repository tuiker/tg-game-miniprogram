package com.hou_tai.service;

import com.hou_tai.controller.mobile.dto.PointDTO;
import com.hou_tai.model.pojo.GameTrigger;
import com.hou_tai.common.response.ResultVO;

/**
 * @InterfaceName: GameService
 * @Description: 游戏方法
 * @Author: Sam
 * @Date: 2023-11-04 11:37
 * @Version: 1.0
 **/
public interface IGameTriggerService {

    ResultVO<String> insertByPoint(PointDTO dto);
    GameTrigger insert(GameTrigger gameTrigger);
}
