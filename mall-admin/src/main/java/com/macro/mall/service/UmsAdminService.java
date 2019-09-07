package com.macro.mall.service;

import com.macro.mall.dto.UmsAdminLoginParam;
import com.macro.mall.model.UmsAdmin;
import com.macro.mall.model.UmsPermission;

import java.util.List;

public interface UmsAdminService {

    /**
     * 登录用户
     * @param param
     * @return
     */
    String login(UmsAdminLoginParam param);

    /**
     * 根据用户名得到用户信息
     * @param username
     * @return
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 根据用户编号获取权限列表
     * @param id
     * @return
     */
    List<UmsPermission> getPermissionList(Long id);

    /**
     * 刷新token
     * @param token
     * @return
     */
    String refreshToken(String token);
}
