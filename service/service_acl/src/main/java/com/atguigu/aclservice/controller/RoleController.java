package com.atguigu.aclservice.controller;


import com.atguigu.aclservice.entity.Role;
import com.atguigu.aclservice.service.RoleService;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cxing
 * @since 2020-09-14
 */
@Api(tags = "角色相关")
@RestController
@RequestMapping("/aclservice/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("分页获取角色列表")
    @GetMapping("page/{page}/{limit}")
    public R pageRole(@PathVariable Long page,
                      @PathVariable Long limit) {
        Page<Role> rolePage = new Page<>(page, limit);
        IPage<Role> roles = roleService.page(rolePage, null);
        return R.ok().data("items", roles);
    }

    @PostMapping("add")
    public R addRole(@RequestBody Role role) {
        roleService.save(role);
        return R.ok();
    }

    @GetMapping("get/{id}")
    public R getRole(@PathVariable String id) {
        Role role = roleService.getById(id);
        return R.ok().data("role", role);
    }

    @PutMapping("update")
    public R updateRole(@RequestBody Role role) {
        roleService.updateById(role);
        return R.ok();
    }

    @DeleteMapping("delete/{id}")
    public R removeRole(@PathVariable String id) {
        roleService.removeRole(id);
        return R.ok();
    }
}

