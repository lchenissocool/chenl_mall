package com.macro.mall.dao;

import com.macro.mall.model.UmsPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户与角色管理自定义dao
 */
public interface UmsAdminRoleRelationDao {


    /**
     * 根据用户编号获取用户权限
     */
    public List<UmsPermission> getPermission(@Param("adminId") Long id);
}
