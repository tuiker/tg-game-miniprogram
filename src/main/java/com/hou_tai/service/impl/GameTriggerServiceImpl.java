package com.hou_tai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hou_tai.model.dao.GameMapper;
import com.hou_tai.model.dao.GameTriggerMapper;
import com.hou_tai.controller.mobile.dto.PointDTO;
import com.hou_tai.model.pojo.GameTrigger;
import com.hou_tai.common.response.ResultVO;
import com.hou_tai.service.IGameTriggerService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @ClassName: GameServiceImpl
 * @Description: 游戏实现
 * @Author: Sam
 * @Date: 2023-11-04 11:39
 * @Version: 1.0
 **/
@Service
@AllArgsConstructor
public class GameTriggerServiceImpl extends ServiceImpl<GameTriggerMapper, GameTrigger> implements IGameTriggerService {

    @Resource
    private GameMapper gameMapper;


    @Override
    public ResultVO<String> insertByPoint(PointDTO dto) {
        insert(GameTrigger.builder()
                .gameId(dto.getGameId())
                .type(dto.getTriggerType())
                .category(dto.getCategory())
                .createTime(LocalDateTime.now())
                .build()
        );
        return ResultVO.success("ok");
    }

    /**
     * 新增数据
     *
     * @param gameTrigger 实例对象
     * @return 实例对象
     */
    public GameTrigger insert(GameTrigger gameTrigger) {
        this.save(gameTrigger);
        return gameTrigger;
    }

}
