package com.hou_tai.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hou_tai.common.vo.PageResult;
import com.hou_tai.controller.mobile.dto.GamePageReqDTO;
import com.hou_tai.controller.mobile.vo.MobileGameVO;
import com.hou_tai.controller.pc.dto.GameAddReqDTO;
import com.hou_tai.controller.pc.dto.GameDto;
import com.hou_tai.controller.pc.dto.GameUpdateReqDTO;
import com.hou_tai.model.pojo.Game;
import com.hou_tai.controller.pc.vo.GameVO;

import java.util.List;

/**
 * @InterfaceName: GameService
 * @Description: 游戏方法
 * @Author: Sam
 * @Date: 2023-11-04 11:37
 * @Version: 1.0
 **/
public interface IGameService {

    /**
     * 分页查询游戏
     * @param reqDTO
     * @return
     */
    PageResult<MobileGameVO> pageGameList(GamePageReqDTO reqDTO);

    /**
     * 分页查询
     *
     * @param dto 筛选条件
     * @return
     */
    Page<GameVO> paginQuery(GameDto dto);
    /**
     * 新增数据
     *
     * @param gameAddReqDTO 实例对象
     * @return 实例对象
     */
    Game insert(GameAddReqDTO gameAddReqDTO);
    /**
     * 更新数据
     *
     * @param reqDTO 实例对象
     * @return 实例对象
     */
    Boolean update(GameUpdateReqDTO reqDTO);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * @Author Sam
     * @Description 获取所有游戏
     * @Date 9:48 2023/11/7
     * @Param  * @param 
     * @return List<Map<String,Object>>
     **/
    List<GameVO> listByGame();
    
    /**
     * @Author Sam
     * @Description 根据ID查询游戏
     * @Date 16:02 2023/11/8
     * @Param  * @param id
     * @return GameVo
     **/
    GameVO getVoById(Long id);
    
}
