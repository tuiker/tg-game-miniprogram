package com.hou_tai.model.dao;

import com.github.yulichang.base.MPJBaseMapper;
import com.hou_tai.controller.pc.vo.SysMenuRespVO;
import com.hou_tai.model.pojo.SysMenu;
import com.hou_tai.model.pojo.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper extends MPJBaseMapper<SysRole> {



}