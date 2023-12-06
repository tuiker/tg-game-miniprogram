package com.hou_tai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hou_tai.common.constant.CommonNum;
import com.hou_tai.common.util.SecurityUtils;
import com.hou_tai.common.vo.PageResult;
import com.hou_tai.controller.mobile.dto.GamePageReqDTO;
import com.hou_tai.controller.mobile.vo.MobileGameVO;
import com.hou_tai.controller.pc.dto.GameAddReqDTO;
import com.hou_tai.controller.pc.dto.GameDTO;
import com.hou_tai.controller.pc.dto.GameUpdateReqDTO;
import com.hou_tai.model.dao.GameMapper;
import com.hou_tai.model.pojo.*;
import com.hou_tai.controller.pc.vo.GameVO;
import com.hou_tai.service.*;
import com.hou_tai.common.util.SystemNumUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: GameServiceImpl
 * @Description: 游戏实现
 * @Author: Sam
 * @Date: 2023-11-04 11:39
 * @Version: 1.0
 **/
@Service
public class GameServiceImpl extends ServiceImpl<GameMapper, Game> implements IGameService {

    @Resource
    IGameTriggerService gameTriggerService;

    /**
     * @Author Sam
     * @Description 根据ID查询游戏
     * @Date 16:02 2023/11/8
     * @Param  * @param id
     * @return GameVo
     **/
    public GameVO getVoById(Long id) {
        GameVO gameVo = this.baseMapper.selectJoinOne(GameVO.class, new MPJLambdaWrapper<Game>()
                .selectAll(Game.class)
                .select("l.language_name")
                .leftJoin(Language.class, "l", Language::getId, Game::getLanguageId)
                .eq(Game::getId, id));
        return gameVo;
    }

    /**
     * 分页查询游戏
     * @param reqDTO
     * @return
     */
    @Override
    public PageResult<MobileGameVO> pageGameList(GamePageReqDTO reqDTO) {
        QueryWrapper<Game> gameQueryWrapper = new QueryWrapper<>();
        if(null != reqDTO.getGameCategory()){
            gameQueryWrapper.apply("FIND_IN_SET(" + reqDTO.getGameCategory() + ", game_category)");
        }
        Page<Game> page = this.page(new Page<>(reqDTO.getPage(), reqDTO.getPageSize()), gameQueryWrapper);

        return new PageResult<>(BeanUtil.copyToList(page.getRecords(), MobileGameVO.class), page.getTotal());
    }

    /**
     * 分页查询
     *
     * @param dto 筛选条件
     * @return
     */
    public Page<GameVO> paginQuery(GameDTO dto) {
        MPJLambdaWrapper<Game> mpjLambdaWrapper = new MPJLambdaWrapper<Game>()
                .selectAll(Game.class)
                .select("l.language_name, u.user_name AS creator")
                .leftJoin(UserInfo.class, "u", UserInfo::getId, Game::getCreateId)
                .leftJoin(Language.class, "l", Language::getId, Game::getLanguageId);

        if(null != dto.getGameId()){
            mpjLambdaWrapper.eq(Game::getId, dto.getGameId());
        }
        if(StrUtil.isNotBlank(dto.getGameName())){
            mpjLambdaWrapper.like(Game::getGameName, dto.getGameName());
        }
        mpjLambdaWrapper.orderByDesc(Game::getUpdateTime);

        Page<GameVO> pagin = this.baseMapper.selectJoinPage(new Page<>(dto.getPage(), dto.getPageSize()),
                GameVO.class, mpjLambdaWrapper);
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param gameAddReqDTO 实例对象
     * @return 实例对象
     */
    @Transactional
    public Game insert(GameAddReqDTO gameAddReqDTO) {
        //转换对象
        Game game = BeanUtil.copyProperties(gameAddReqDTO, Game.class);
        long gameId = getGameId();
        game.setId(gameId);
        game.setCreateId(SecurityUtils.getLoginUserId());
        game.setCreateTime(LocalDateTime.now());
        game.setUpdateTime(LocalDateTime.now());

        this.save(game);
        return game;
    }

    /**
     * 更新数据
     *
     * @param reqDTO 实例对象
     * @return 实例对象
     */
    @Transactional
    public Boolean update(GameUpdateReqDTO reqDTO) {
        Game game = BeanUtil.copyProperties(reqDTO, Game.class);
        game.setUpdateId(SecurityUtils.getLoginUserId());
        game.setUpdateTime(LocalDateTime.now());
        this.updateById(game);
        return true;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Transactional
    public boolean deleteById(Long id) {
        this.removeById(id);
        //游戏是逻辑删除，那么此处的游戏APK扩展信息就不删了
        //删除游戏APK扩展信息
        //gameApkService.deleteByGameId(id);
        return true;
    }

    public List<GameVO> listByGame() {
        List<GameVO> volist = Arrays.asList();
        List<Game> list = this.list(new LambdaQueryWrapper<Game>().select(Game::getId, Game::getGameName));
        if (CollectionUtil.isNotEmpty(list)) {
            volist = BeanUtil.copyToList(list, GameVO.class);
        }
        return volist;
    }

    /**
     * @Description 生成游戏ID
     * @Author GaoLu
     * @Date 2023/11/7
     * @Return
     **/
    private long getGameId() {
        String num = SystemNumUtil.getRandomNumberByNum(CommonNum.FOUR);
        long allNum = this.count();
        String gameId = num + allNum;
        return Long.parseLong(gameId);
    }

}
