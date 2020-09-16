package com.atguigu.aclservice.service;

import com.atguigu.aclservice.entity.Permission;
import com.atguigu.aclservice.entity.dto.PermissionDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author cxing
 * @since 2020-09-14
 */
public interface PermissionService extends IService<Permission> {

    List<PermissionDto> getPermissionList();

    void removePermissionAndChildren(Long id);

    void doAssign(String roleId, String... permissionId);

    List<PermissionDto> toAssign(Long id);

    List<String> selectPermissionValueByUserId(String id);
}
