package com.atguigu.aclservice.service.impl;

import com.atguigu.aclservice.entity.Role;
import com.atguigu.aclservice.entity.RolePermission;
import com.atguigu.aclservice.entity.UserRole;
import com.atguigu.aclservice.mapper.RoleMapper;
import com.atguigu.aclservice.mapper.RolePermissionMapper;
import com.atguigu.aclservice.mapper.UserMapper;
import com.atguigu.aclservice.mapper.UserRoleMapper;
import com.atguigu.aclservice.service.RoleService;
import com.atguigu.aclservice.service.UserService;
import com.atguigu.servicebase.exceptionhandler.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cxing
 * @since 2020-09-14
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    UserRoleMapper userRoleMapper;

    @Resource
    RolePermissionMapper rolePermissionMapper;

    @Override
    @Transactional
    public void removeRole(String id) {
        // 确认是否有用户绑定该角色
        Integer count = userRoleMapper.selectCount(new QueryWrapper<UserRole>().eq("role_id", id));
        if (count > 0) throw new GuliException(20001, "有用户绑定了该角色");

        // 删除 role 和 rolePermission
        rolePermissionMapper.delete(new QueryWrapper<RolePermission>().eq("role_id", id));
        baseMapper.deleteById(id);
    }
}
