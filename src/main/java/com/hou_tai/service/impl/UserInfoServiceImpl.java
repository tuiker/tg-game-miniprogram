package com.hou_tai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hou_tai.auth.entity.LoginUser;
import com.hou_tai.common.constant.CommonNum;
import com.hou_tai.common.enums.ResultCode;
import com.hou_tai.common.util.SecurityUtils;
import com.hou_tai.common.util.SystemNumUtil;
import com.hou_tai.common.vo.PageResult;
import com.hou_tai.controller.pc.dto.*;
import com.hou_tai.model.dao.UserInfoMapper;
import com.hou_tai.model.pojo.UserInfo;
import com.hou_tai.model.redis.LoginUserRedisDAO;
import com.hou_tai.common.response.ResultVO;
import com.hou_tai.controller.pc.vo.UserInfoVO;
import com.hou_tai.controller.pc.vo.UserLoginRespVO;
import com.hou_tai.service.IUserInfoService;
import com.hou_tai.common.util.MD5Utils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: GaoLu
 * @Date: 2023-10-18 14:08
 * @Description: 用户信息表 服务类
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private LoginUserRedisDAO loginUserRedisDAO;

    /**
     * 用户登录
     * @param reqDTO
     * @return
     */
    @Transactional
    @Override
    public ResultVO<UserLoginRespVO> loginUser(UserLoginReqDTO reqDTO) {
        UserInfo user = this.lambdaQuery().eq(UserInfo::getUserName, reqDTO.getUsername())
                .eq(UserInfo::getPassword, MD5Utils.MD5(reqDTO.getPassword())).one();
        if (null != user) {
            //登录成功，创建token，存入缓存
            String token = IdUtil.fastSimpleUUID();
            LoginUser loginUser = BeanUtil.copyProperties(user, LoginUser.class);
            loginUser.setToken(token);
            loginUserRedisDAO.set(token, loginUser);

            //修改最近登录时间
            UserInfo updateUser = new UserInfo();
            updateUser.setId(user.getId());
            updateUser.setRecentLoginTime(LocalDateTime.now());
            userInfoMapper.updateById(updateUser);

            //将用户的基本信息及token返回给前端
            UserLoginRespVO respVO = BeanUtil.copyProperties(user, UserLoginRespVO.class);
            respVO.setToken(token);
            return ResultVO.success(respVO);
        } else
            return ResultVO.error(ResultCode.ERROR_USER_OR_PASSWORD);
    }


    public UserInfoVO getUserInfoById(long id) {
        UserInfo userInfo = userInfoMapper.selectById(id);
        return BeanUtil.copyProperties(userInfo, UserInfoVO.class);
    }

    /**
     * 分页查询用户列表
     * @param reqDTO
     * @return
     */
    @Override
    public PageResult<UserInfoVO> pageList(UserPageReqDTO reqDTO) {
        if(StrUtil.isNotBlank(reqDTO.getUserName())){//模糊查询用户账号
            reqDTO.setUserName("%" + reqDTO.getUserName() + "%");
        }
        Page<UserInfoVO> page = userInfoMapper.pageList(new Page<>(reqDTO.getPage(), reqDTO.getPageSize()), reqDTO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    /**
     * 添加系统用户
     * @param reqDTO
     * @return
     */
    @Override
    public ResultVO<Boolean> addSysUser(SysUserAddReqDTO reqDTO) {
        if(checkIsExists(reqDTO.getUserName(), null)){
            return ResultVO.success("该用户账号已被占用", false);
        }

        UserInfo user = BeanUtil.copyProperties(reqDTO, UserInfo.class);
        //密码进行加密
        user.setPassword(MD5Utils.MD5(reqDTO.getPassword()));
        user.setId(getUserId());
        user.setCreateId(SecurityUtils.getLoginUserId());
        user.setCreateTime(LocalDateTime.now());
        user.setChannelId(CommonNum.ONE);
        this.save(user);

        return ResultVO.success(true);
    }

    /**
     * 修改系统用户
     * @param reqDTO
     * @return
     */
    @Override
    public ResultVO<Boolean> updateSysUser(SysUserUpdateReqDTO reqDTO) {
        if(checkIsExists(reqDTO.getUserName(), reqDTO.getId())){
            return ResultVO.success("该用户账号已被占用", false);
        }

        UserInfo user = BeanUtil.copyProperties(reqDTO, UserInfo.class);
        user.setUpdateId(SecurityUtils.getLoginUserId());
        user.setUpdateTime(LocalDateTime.now());
        this.updateById(user);

        return ResultVO.success(true);
    }

    /**
     * 修改系统用户密码
     * @param reqDTO
     * @return
     */
    @Override
    public ResultVO<Boolean> updateSysUserPassword(SysUserPasswordUpdateReqDTO reqDTO) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(reqDTO.getId());
        userInfo.setPassword(MD5Utils.MD5(reqDTO.getPassword()));
        this.updateById(userInfo);
        return ResultVO.success(true);
    }

    /**
     * 校验用户账号是否已存在
     * @param userName 用户账号
     * @return true：已存在， false：不存在
     */
    private boolean checkIsExists(String userName, Long userId){
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUserName, userName);
        if(null != userId){
            queryWrapper.ne(UserInfo::getId, userId);
        }
        List<UserInfo> list = this.list(queryWrapper);
        return CollectionUtil.isNotEmpty(list);
    }

    /**
     * @Description 生成用户ID 随机6位数+用户总数 拼接
     * @Author GaoLu
     * @Date 2023/11/6
     * @Return
     **/
    private long getUserId() {
        String num = SystemNumUtil.getRandomNumberByNum(CommonNum.SIX);
        long allUserNum = this.count();
        String allUserStr = String.valueOf(allUserNum);
        String userIdOne = num + allUserStr;
        return Long.parseLong(userIdOne);
    }

}
