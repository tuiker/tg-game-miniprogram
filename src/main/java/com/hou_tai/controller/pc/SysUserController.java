package com.hou_tai.controller.pc;

import com.hou_tai.common.response.ResultVO;
import com.hou_tai.common.vo.PageResult;
import com.hou_tai.controller.pc.dto.UserPageReqDTO;
import com.hou_tai.controller.pc.vo.UserInfoVO;
import com.hou_tai.service.IUserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/pc/sysUser")
@Tag(name = "admin端 - 后台用户控制层")
public class SysUserController {

    @Resource
    IUserInfoService userInfoService;

    @Operation(summary = "分页查询用户列表")
    @GetMapping("/pageList")
    public ResultVO<PageResult<UserInfoVO>> pageList(@ParameterObject UserPageReqDTO reqDTO){
        return ResultVO.success(userInfoService.pageList(reqDTO));
    }

}
