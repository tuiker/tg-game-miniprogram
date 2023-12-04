package com.hou_tai.controller.mobile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hou_tai.common.enums.CountryLanguageEnum;
import com.hou_tai.common.enums.HomeEnums;
import com.hou_tai.common.enums.ResultCode;
import com.hou_tai.common.response.ResponseData;
import com.hou_tai.common.response.ResultVO;
import com.hou_tai.common.util.IPAdress;
import com.hou_tai.common.util.IPCountryUtil;
import com.hou_tai.controller.mobile.dto.MobileGameDto;
import com.hou_tai.controller.mobile.dto.MobileHomeGameDto;
import com.hou_tai.controller.pc.dto.PointDto;
import com.hou_tai.controller.mobile.vo.MobileGameHomeVo;
import com.hou_tai.controller.mobile.vo.MobileGameVo;
import com.hou_tai.service.IGameService;
import com.hou_tai.service.IGameTriggerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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
//
//        Enumeration<String> s = request.getHeaderNames();
//        while (s.hasMoreElements()) {
//            String name = s.nextElement();
//            //log.info("hasMoreElements请求头里的参数》》》》》》》》》》" + name + "对应值》》》》" + request.getHeader(name));
//        }

        Integer type = CountryLanguageEnum.getValue(conutry);
        log.info("获取的初始语言》》》》》》》》》" + type);
        if (null == type) {
            type = 1;
        }
        log.info("获取的语言》》》》》》》》》" + type);
        return ResponseData.success(type);
    }

    @Operation(summary = "新增埋点数据", description = "触发类型 2下载3打开")
    @PostMapping("/addPointNum")
    public ResultVO addPointNum(@RequestBody PointDto dto) {
        return gameTriggerService.insertByPoint(dto);
    }

    @Operation(summary = "类似游戏列表", description = "类似游戏,传gameId则排除此游戏")
    @PostMapping("/getPage")
    public ResultVO<Page<MobileGameHomeVo>> getPage(@RequestBody MobileGameDto dto) {
        return ResponseData.success(gameService.pageForMobile(dto));
    }

    @Operation(summary = "游戏首页-近期")
    @PostMapping("/getHomeGameByRecent")
    public ResultVO<Page<MobileGameHomeVo>> getHomeGameByRecent(HttpServletRequest request, @RequestBody MobileHomeGameDto dto) {
        Integer languageId = IPCountryUtil.ipToCountry(IPAdress.getIp(request));
        dto.setLanguageId(languageId);
        dto.setHomeType(HomeEnums.RECENT.getCode());
        return ResponseData.success(gameService.pageForHomeType(dto));
    }

    @Operation(summary = "游戏首页-广告推荐")
    @PostMapping("/getHomeGameByRecommend")
    public ResultVO<Page<MobileGameHomeVo>> getHomeGameByRecommend(HttpServletRequest request, @RequestBody MobileHomeGameDto dto) {
        Integer languageId = IPCountryUtil.ipToCountry(IPAdress.getIp(request));
        dto.setLanguageId(languageId);
        dto.setHomeType(HomeEnums.RECOMMEND.getCode());
        return ResponseData.success(gameService.pageForHomeType(dto));
    }

    @Operation(summary = "游戏首页-个性化")
    @PostMapping("/getHomeGameByPersonal")
    public ResultVO<Page<MobileGameHomeVo>> getHomeGameByPersonal(HttpServletRequest request, @RequestBody MobileHomeGameDto dto) {
        Integer languageId = IPCountryUtil.ipToCountry(IPAdress.getIp(request));
        dto.setLanguageId(languageId);
        dto.setHomeType(HomeEnums.PERSONAL.getCode());
        return ResponseData.success(gameService.pageForHomeType(dto));
    }


}
