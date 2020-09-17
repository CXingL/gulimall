package com.atguigu.aclservice.service;

import com.atguigu.aclservice.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cxing
 * @since 2020-09-14
 */
public interface RoleService extends IService<Role> {

    void removeRole(String id);
}
