package com.hou_tai.controller.pc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hou_tai.controller.pc.dto.GameAddReqDTO;
import com.hou_tai.controller.pc.dto.GameDTO;
import com.hou_tai.controller.pc.dto.GameUpdateReqDTO;
import com.hou_tai.model.pojo.Game;
import com.hou_tai.common.response.ResultVO;
import com.hou_tai.controller.pc.vo.GameVO;
import com.hou_tai.service.IGameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: GameController
 * @Description: 后台游戏
 * @Author: Sam
 * @Date: 2023-11-06 10:10
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/pc/game")
@Tag(name = "后台端游戏控制层")
@Slf4j
public class GameController {

    @Resource
    private IGameService gameService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Operation(summary = "通过ID查询单条数据")
    @Parameter(name = "id", description = "游戏ID", required = true)
    @GetMapping("getVoById")
    public ResultVO<GameVO> getVoById(@RequestParam("id") Long id){
        return ResultVO.success(gameService.getVoById(id));
    }

    /**
     * 分页查询
     *
     * @param dto 筛选条件
     * @return 查询结果
     */
    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public ResultVO<Page<GameVO>> paginQuery(@ParameterObject GameDTO dto){
        //1.分页参数
        Page<GameVO> page = gameService.paginQuery(dto);
        return ResultVO.success(page);
    }

    /**
     * 新增数据
     *
     * @param gameAddReqDTO 实例对象
     * @return 实例对象
     */
    @Operation(summary = "添加游戏")
    @PostMapping("/add")
    public ResultVO<Game> add(@RequestBody GameAddReqDTO gameAddReqDTO){
        return ResultVO.success(gameService.insert(gameAddReqDTO));
    }

    /**
     * 更新数据
     *
     * @param reqDTO 实例对象
     * @return 实例对象
     */
    @Operation(summary = "更新数据")
    @PostMapping("/update")
    public ResultVO<Boolean> update(@RequestBody GameUpdateReqDTO reqDTO){
        return ResultVO.success(gameService.update(reqDTO));
    }

    /**
     * 通过主键删除数据
     * @param id 主键
     * @return 是否成功
     */
    @Operation(summary = "通过主键删除数据")
    @DeleteMapping("/deleteById")
    public ResultVO<Boolean> deleteById(Long id){
        return ResultVO.success(gameService.deleteById(id));
    }



}
