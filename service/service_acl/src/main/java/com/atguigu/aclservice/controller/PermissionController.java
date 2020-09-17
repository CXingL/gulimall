package com.atguigu.aclservice.controller;


import com.atguigu.aclservice.entity.Permission;
import com.atguigu.aclservice.entity.dto.PermissionDto;
import com.atguigu.aclservice.service.PermissionService;
import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author cxing
 * @since 2020-09-14
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/aclservice/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("add")
    public R addPermission(@RequestBody Permission permission) {
        permissionService.save(permission);
        return R.ok();
    }

    @PutMapping("update")
    public R updatePermission(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return R.ok();
    }

    @GetMapping("getList")
    public R getPermissionList() {
        List<PermissionDto> permissionDtoList = permissionService.getPermissionList();
        return R.ok().data("item", permissionDtoList);
    }

    @DeleteMapping("delete/{id}")
    public R deletePermission(@PathVariable Long id) {
        permissionService.removePermissionAndChildren(id);
        return R.ok();
    }

    /**
     * 给角色分配权限
     */
    @PostMapping("/doAssign")
    public R doAssign(String roleId, String[] permissionId) {
        permissionService.doAssign(roleId, permissionId);
        return R.ok();
    }

    /**
     * 根据角色获取权限列表
     */
    @GetMapping("toAssign/{id}")
    public R toAssign(@PathVariable Long id) {
        List<PermissionDto> list = permissionService.toAssign(id);
        return R.ok().data("item", list);
    }

}

