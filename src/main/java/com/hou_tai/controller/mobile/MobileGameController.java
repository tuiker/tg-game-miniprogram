package com.hou_tai.controller.mobile;

import com.hou_tai.common.enums.CountryLanguageEnum;
import com.hou_tai.common.response.ResultVO;
import com.hou_tai.common.vo.PageResult;
import com.hou_tai.controller.mobile.dto.GamePageReqDTO;
import com.hou_tai.controller.mobile.dto.PointDTO;
import com.hou_tai.controller.mobile.vo.MobileGameVO;
import com.hou_tai.service.IGameService;
import com.hou_tai.service.IGameTriggerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: GameController
 * @Description: 移动端游戏
 * @Author: Sam
 * @Date: 2023-11-04 11:18
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/mobile/game")
@Tag(name = "移动端游戏控制层")
@Slf4j
public class MobileGameController {

    @Resource
    IGameService gameService;

    @Resource
    IGameTriggerService gameTriggerService;


    @Operation(summary = "获取游戏语言", description = "获取游戏语言")
    @GetMapping("/getLanguageType")
    public ResultVO getLanguageType(HttpServletRequest request) {
        //根据用户ip获取语言id
        String conutry = request.getHeader("cf-ipcountry");
        log.info("当前地址客户城市》》》》》》》》》》》》》》" + conutry);
        String realIp = request.getHeader("x-real-ip");
        log.info("当前地址客户端IP》》》》》》》》》》》》》》" + realIp);
        Integer type = CountryLanguageEnum.getValue(conutry);
        log.info("获取的初始语言》》》》》》》》》" + type);
        if (null == type) {
            type = 1;
        }
        log.info("获取的语言》》》》》》》》》" + type);
        return ResultVO.success(type);
    }

    @Operation(summary = "新增埋点数据", description = "触发类型 2下载3打开")
    @PostMapping("/addPointNum")
    public ResultVO addPointNum(@RequestBody PointDTO dto) {
        return gameTriggerService.insertByPoint(dto);
    }

    @Operation(summary = "分页查询游戏")
    @GetMapping("/pageGameList")
    public ResultVO<PageResult<MobileGameVO>> pageGameList(@ParameterObject GamePageReqDTO reqDTO){
        return ResultVO.success(gameService.pageGameList(reqDTO));
    }


}
