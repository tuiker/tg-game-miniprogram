package com.hou_tai.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hou_tai.common.enums.CategoryEnums;
import com.hou_tai.common.enums.LanguageTypeEnum;
import com.hou_tai.common.constant.CommonNum;
import com.hou_tai.model.dao.GameMapper;
import com.hou_tai.model.dao.GameTriggerMapper;
import com.hou_tai.model.pojo.Game;
import com.hou_tai.controller.pc.vo.DataBoardVo;
import com.hou_tai.controller.pc.vo.GameGeneralizeVo;
import com.hou_tai.controller.pc.vo.GameVO;
import com.hou_tai.service.IDataOverviewService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @Author: Sam
 * @Date:2023-10-18 13:41
 */
@Service
@Slf4j
public class DataOverviewServiceImpl implements IDataOverviewService {
    @Resource
    private GameTriggerMapper gameTriggerMapper;
    @Resource
    private GameMapper gameMapper;

    @Override
    public DataBoardVo getAllStates(Long gameId) {
        DataBoardVo dataBoardVo = new DataBoardVo();
        long start = System.currentTimeMillis();
        try {
            CountDownLatch countDownLatch = setDataBoard(gameId, dataBoardVo);
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("总耗时ms---------------------------"+(start - System.currentTimeMillis()));
        return dataBoardVo;
    }

    /**
     * 获取并设置首页数据看板数据
     * @param gameId 游戏ID 筛选折线图数据
     * @param dataBoardVo 数据看板VO
     * @return 计数器
     */
    private CountDownLatch setDataBoard(Long gameId, DataBoardVo dataBoardVo) {
        CountDownLatch countDownLatch = new CountDownLatch(4);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        threadPoolExecutor.submit(() -> {
            //根据分类分组查询总导流量
            List<HashMap<String, Object>> sumGroupCount = gameTriggerMapper.getCountByDays(CommonNum.THREE, null);
            for (HashMap<String, Object> map : sumGroupCount){
                if(Objects.equals(map.get("category"), CategoryEnums.HOT.getCode())){//热门推荐
                    dataBoardVo.setHotSum(Integer.valueOf(map.get("num").toString()));
                }else if(Objects.equals(map.get("category"), CategoryEnums.EVERYONE_IS_PLAYING.getCode())){//大家都在玩
                    dataBoardVo.setEveryOneIsPlayingSum(Integer.valueOf(map.get("num").toString()));
                }else if(Objects.equals(map.get("category"), CategoryEnums.BRAZIL_ELECTRON.getCode())){//巴西电子
                    dataBoardVo.setBrazilElectronSum(Integer.valueOf(map.get("num").toString()));
                }
            }
            countDownLatch.countDown();
        });
        threadPoolExecutor.submit(() -> {
            //根据分类分组查询今天的导流量
            List<HashMap<String, Object>> todayGroupCount = gameTriggerMapper.getCountByDays(CommonNum.THREE, 0);
            for (HashMap<String, Object> map : todayGroupCount){
                if(Objects.equals(map.get("category"), CategoryEnums.HOT.getCode())){//热门推荐
                    dataBoardVo.setTodayHot(Integer.valueOf(map.get("num").toString()));
                }else if(Objects.equals(map.get("category"), CategoryEnums.EVERYONE_IS_PLAYING.getCode())){//大家都在玩
                    dataBoardVo.setTodayEveryOneIsPlaying(Integer.valueOf(map.get("num").toString()));
                }else if(Objects.equals(map.get("category"), CategoryEnums.BRAZIL_ELECTRON.getCode())){//巴西电子
                    dataBoardVo.setTodayBrazilElectron(Integer.valueOf(map.get("num").toString()));
                }
            }
            countDownLatch.countDown();
        });
        threadPoolExecutor.submit(() -> {
            //根据分类分组查询昨天的导流量
            List<HashMap<String, Object>> yesterdayGroupCount = gameTriggerMapper.getCountByDays(CommonNum.THREE, -1);
            for (HashMap<String, Object> map : yesterdayGroupCount){
                if(Objects.equals(map.get("category"), CategoryEnums.HOT.getCode())){//热门推荐
                    dataBoardVo.setYesterdayHot(Integer.valueOf(map.get("num").toString()));
                }else if(Objects.equals(map.get("category"), CategoryEnums.EVERYONE_IS_PLAYING.getCode())){//大家都在玩
                    dataBoardVo.setYesterdayEveryOneIsPlaying(Integer.valueOf(map.get("num").toString()));
                }else if(Objects.equals(map.get("category"), CategoryEnums.BRAZIL_ELECTRON.getCode())){//巴西电子
                    dataBoardVo.setYesterdayBrazilElectron(Integer.valueOf(map.get("num").toString()));
                }
            }
            countDownLatch.countDown();
        });
        threadPoolExecutor.submit(() -> {
            //查询过去七天内的导流折线图数据
            dataBoardVo.setHotDataOfTime(gameTriggerMapper.getNumForSevenDay(gameId, CommonNum.THREE, CategoryEnums.HOT.getCode()));
            dataBoardVo.setEveryOneIsPlayingDataOfTime(gameTriggerMapper.getNumForSevenDay(gameId, CommonNum.THREE, CategoryEnums.EVERYONE_IS_PLAYING.getCode()));
            dataBoardVo.setBrazilElectronDataOfTime(gameTriggerMapper.getNumForSevenDay(gameId, CommonNum.THREE, CategoryEnums.BRAZIL_ELECTRON.getCode()));
            countDownLatch.countDown();
        });
        return countDownLatch;
    }

    /**
     * 获取数据概况表格数据
     */
    @Override
    public List<GameGeneralizeVo> getDataProfilingTableData(Integer orderType) {
        return gameTriggerMapper.getCountAll(CommonNum.THREE, orderType);
    }


}
