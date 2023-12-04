package com.hou_tai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hou_tai.common.constant.CommonNum;
import com.hou_tai.controller.mobile.dto.MobileGameDto;
import com.hou_tai.controller.mobile.dto.MobileHomeGameDto;
import com.hou_tai.controller.pc.dto.GameAddReqDTO;
import com.hou_tai.controller.pc.dto.GameDto;
import com.hou_tai.controller.pc.dto.GameUpdateReqDTO;
import com.hou_tai.model.dao.GameMapper;
import com.hou_tai.model.pojo.*;
import com.hou_tai.controller.pc.vo.GameVo;
import com.hou_tai.controller.mobile.vo.MobileGameHomeVo;
import com.hou_tai.controller.mobile.vo.MobileGameVo;
import com.hou_tai.service.*;
import com.hou_tai.common.util.SystemNumUtil;
import jakarta.annotation.Resource;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
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


    @Value("${lanBo.fall.path:}")
    private String fallPath;

    public Game queryById(Long id) {
        return this.getById(id);
    }

    public GameVo getVoById(Long id) {
        GameVo gameVo = this.baseMapper.selectJoinOne(GameVo.class, new MPJLambdaWrapper<Game>()
                .selectAll(Game.class)
                .select("l.language_name")
                .leftJoin(Language.class, "l", Language::getId, Game::getLanguageId)
                .eq(Game::getId, id));
        return gameVo;
    }

    /**
     * 分页查询
     *
     * @param dto 筛选条件
     * @return
     */
    public Page<GameVo> paginQuery(GameDto dto) {
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

        Page<GameVo> pagin = this.baseMapper.selectJoinPage(new Page<>(dto.getPage(), dto.getPageSize()),
                GameVo.class, mpjLambdaWrapper);
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
        LambdaUpdateWrapper<Game> wrapper = new LambdaUpdateWrapper<Game>();
        wrapper.set(StrUtil.isNotBlank(reqDTO.getGameName()), Game::getGameName, reqDTO.getGameName())
                .set(StrUtil.isNotBlank(reqDTO.getGameLogo()), Game::getGameLogo, reqDTO.getGameLogo())
                .set(StrUtil.isNotBlank(reqDTO.getGameMainLogo()), Game::getGameMainLogo, reqDTO.getGameMainLogo())
                .set(Game::getGameUrl,  reqDTO.getGameUrl())
                //.set(game.getUpdateId() > 0, Game::getUpdateId, game.getUpdateId())
                .set(Game::getUpdateTime, LocalDateTime.now());

        //2. 设置主键，并更新
        wrapper.eq(Game::getId, reqDTO.getId());
        this.update(wrapper);

        //3. 更新成功了，查询最最对象返回
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

    public List<GameVo> listByGame() {
        List<GameVo> volist = Arrays.asList();
        List<Game> list = this.list(new LambdaQueryWrapper<Game>().select(Game::getId, Game::getGameName));
        if (CollectionUtil.isNotEmpty(list)) {
            volist = BeanUtil.copyToList(list, GameVo.class);
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
        return Long.valueOf(gameId);
    }

    public Page<MobileGameHomeVo> pageForMobile(MobileGameDto dto) {
        Page<MobileGameHomeVo> pagin = this.baseMapper.selectJoinPage(
                new Page<>(dto.getPage(), dto.getPageSize()),
                MobileGameHomeVo.class,
                new MPJLambdaWrapper<Game>()
                        .select(Game::getGameName, Game::getGameLogo, Game::getGameMainLogo)
                        .select("l.language_name")
                        .leftJoin(Language.class, "l", Language::getId, Game::getLanguageId)
                        .orderByDesc(Game::getCreateTime)
                        .notIn(dto.getGameId() != null, Game::getId, dto.getGameId()
                        )
        );
        //3. 返回结果
        return pagin;
    }

    public Page<MobileGameHomeVo> pageForMobileHome(MobileHomeGameDto dto) {
        Page<MobileGameHomeVo> pagin = this.baseMapper.selectJoinPage(
                new Page<>(dto.getPage(), dto.getPageSize()), MobileGameHomeVo.class,
                new MPJLambdaWrapper<Game>()
                        .select(Game::getGameName, Game::getGameLogo, Game::getGameMainLogo)
                        .select("l.language_name")
                        .leftJoin(Language.class, "l", Language::getId, Game::getLanguageId)
                        .orderByDesc(Game::getCreateTime)
                        .eq(dto.getLanguageId() != null && dto.getLanguageId() > 0, Game::getLanguageId, dto.getLanguageId())
        );
        //3. 返回结果
        return pagin;
    }

    public Page<MobileGameHomeVo> pageForHomeType(MobileHomeGameDto dto) {
        //具体类型的实现在此处
        return pageForMobileHome(dto);
    }

    @Value("${spring.profiles.active:}")
    private String active;

    @Value("${lanBo.mobile.path:}")
    private String mobilePath;

    @Value("${lanBo.file.path:}")
    private String filePath;

}
