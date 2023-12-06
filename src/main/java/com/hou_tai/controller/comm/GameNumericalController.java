package com.hou_tai.controller.comm;


import com.hou_tai.model.pojo.Language;
import com.hou_tai.common.response.ResultVO;
import com.hou_tai.controller.pc.vo.GameVO;
import com.hou_tai.service.IGameService;
import com.hou_tai.service.ILanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Author: Sam
 * @Date: 2023-10-18 13:41
 * @Description: 游戏各项数值管理
 */
@RestController
@RequestMapping("/comm/numerical")
@Tag(name = "游戏各项数值")
@Slf4j
public class GameNumericalController {

    @Resource
    private ILanguageService languageService;

    @Resource
    private IGameService gameService;


    /**
     * 全查询
     *
     * @return 查询结果
     */
    @Operation(summary = "所有语言")
    @GetMapping("/listByLanguage")
    @ApiResponse(responseCode = "200", description = "查找成功")
    public ResultVO<List<Language>> listByLanguage() {
        List<Language> list = languageService.listByLanguage();
        return ResultVO.success(list);
    }

    /**
     * @Description 所有游戏
     * @Author Sam
     * @Date 2023年11月7日
     **/
    @Operation(summary = "所有游戏")
    @GetMapping("/listByGame")
    public ResultVO<List<GameVO>> listByGame() {
        List<GameVO> list = gameService.listByGame();
        return ResultVO.success(list);
    }


}
