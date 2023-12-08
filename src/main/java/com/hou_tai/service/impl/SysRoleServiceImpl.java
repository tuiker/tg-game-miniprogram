package com.hou_tai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hou_tai.common.response.ResultVO;
import com.hou_tai.controller.pc.dto.SysRoleAddReqDTO;
import com.hou_tai.controller.pc.dto.SysRoleUpdateReqDTO;
import com.hou_tai.model.dao.SysRoleMapper;
import com.hou_tai.model.pojo.SysRole;
import com.hou_tai.model.pojo.UserInfo;
import com.hou_tai.service.ISysRoleMenuService;
import com.hou_tai.service.ISysRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 角色 Service接口实现
 * @Author yxf
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Resource
    private ISysRoleMenuService sysRoleMenuService;

    /**
     * 添加角色
     * @param reqDTO
     * @return
     */
    @Transactional
    @Override
    public ResultVO<Boolean> addSysRole(SysRoleAddReqDTO reqDTO) {
        if(checkRoleCodeIsExists(reqDTO.getRoleCode(), null)){
            return ResultVO.success("该角色编码已被占用", false);
        }
        //保存角色
        SysRole sysRole = BeanUtil.copyProperties(reqDTO, SysRole.class);
        baseMapper.insert(sysRole);
        //保存角色权限关联信息
        sysRoleMenuService.saveRoleMenu(reqDTO.getMenuIds(), sysRole.getId());
        return ResultVO.success(true);
    }

    /**
     * 修改角色
     * @param reqDTO
     * @return
     */
    @Transactional
    @Override
    public ResultVO<Boolean> updateSysRole(SysRoleUpdateReqDTO reqDTO) {
        if(checkRoleCodeIsExists(reqDTO.getRoleCode(), reqDTO.getId())){
            return ResultVO.success("该角色编码已被占用", false);
        }
        //修改角色
        SysRole sysRole = BeanUtil.copyProperties(reqDTO, SysRole.class);
        this.updateById(sysRole);
        //修改角色权限关联信息
        sysRoleMenuService.updateRoleMenu(reqDTO.getMenuIds(), reqDTO.getId());
        return ResultVO.success(true);
    }

    /**
     * 根据角色ID删除角色
     * @param roleId 角色ID
     * @return
     */
    @Override
    public Boolean deleteRoleById(Integer roleId) {
        //删除角色
        this.removeById(roleId);
        //删除角色权限关联信息
        sysRoleMenuService.deleteByRoleId(roleId);
        return true;
    }

    /**
     * 校验角色编码是否已存在
     * @param roleCode 角色编码
     * @return true：已存在， false：不存在
     */
    private boolean checkRoleCodeIsExists(String roleCode, Integer roleId){
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getRoleCode, roleCode);
        if(null != roleId){
            queryWrapper.ne(SysRole::getId, roleId);
        }
        long count = this.count(queryWrapper);
        return count > 0;
    }
}
