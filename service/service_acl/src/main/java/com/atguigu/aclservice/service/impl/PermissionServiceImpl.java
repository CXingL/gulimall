package com.atguigu.aclservice.service.impl;

import com.atguigu.aclservice.entity.Permission;
import com.atguigu.aclservice.entity.RolePermission;
import com.atguigu.aclservice.entity.User;
import com.atguigu.aclservice.entity.dto.PermissionDto;
import com.atguigu.aclservice.mapper.PermissionMapper;
import com.atguigu.aclservice.mapper.RolePermissionMapper;
import com.atguigu.aclservice.mapper.UserMapper;
import com.atguigu.aclservice.service.PermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author cxing
 * @since 2020-09-14
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    RolePermissionMapper rolePermissionMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public List<PermissionDto> getPermissionList() {
        List<Permission> permissionList = baseMapper.selectList(new QueryWrapper<Permission>().eq("status", 1));
        return build(permissionList);
    }

    @Override
    public void removePermissionAndChildren(Long id) {
        List<Long> list = getChildrenIds(id);
        baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public void doAssign(String roleId, String... permissionId) {
        if (rolePermissionMapper.selectCount(new QueryWrapper<RolePermission>().eq("ole_id", roleId)) > 0) {
            rolePermissionMapper.delete(new QueryWrapper<RolePermission>().eq("ole_id", roleId));
        }

        for (String id : permissionId) {
            if (StringUtils.isEmpty(id)) continue;

            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(id);
            rolePermissionMapper.insert(rolePermission);
        }
    }

    @Override
    public List<PermissionDto> toAssign(Long id) {
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(new QueryWrapper<RolePermission>().eq("role_id", id));
        List<String> permissionIds = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissionList) {
            permissionIds.add(rolePermission.getPermissionId());
        }
        List<Permission> permissions = baseMapper.selectBatchIds(permissionIds);
//        List<PermissionDto> permissionDtos = new ArrayList<>();
//        permissions.forEach(item -> {
//            PermissionDto permissionDto = new PermissionDto();
//            BeanUtils.copyProperties(item, permissionDto);
//            permissionDtos.add(permissionDto);
//        });
        return build(permissions);
    }

    @Override
    public List<String> selectPermissionValueByUserId(String id) {
        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    private boolean isSysAdmin(String id) {
        User user = userMapper.selectById(id);

        return null != user && "admin".equals(user.getUsername());
    }

    //========================递归查询所有菜单================================================
    // 查找出顶层菜单并封装数据
    private List<PermissionDto> build(List<Permission> permissionList) {
        ArrayList<PermissionDto> permissionDtos = new ArrayList<>();
        for (Permission permission:
             permissionList) {
            if ("0".equals(permission.getPid())) {
                PermissionDto permissionDto = new PermissionDto();
                BeanUtils.copyProperties(permission, permissionDto);
                permissionDto.setLevel(1);
                permissionDtos.add(selectChildren(permissionDto, permissionList));
            }
        }
        return permissionDtos;
    }

    // 查询出 children list 添加后返回
    private PermissionDto selectChildren(PermissionDto permissionDto, List<Permission> permissionList) {
        permissionDto.setChildren(new ArrayList<>());

        for (Permission permission:
             permissionList) {
            if (permissionDto.getId().equals(permission.getPid())) {
                PermissionDto permissionDto1 = new PermissionDto();
                BeanUtils.copyProperties(permission, permissionDto1);
                permissionDto1.setLevel(permissionDto.getLevel() + 1);
                if (permissionDto.getChildren() == null) {
                    permissionDto.setChildren(new ArrayList<>());
                }
                permissionDto.getChildren().add(selectChildren(permissionDto1, permissionList));
            }
        }
        return permissionDto;
    }

    //========================递归查询子菜单 id================================================
    private List<Long> getChildrenIds(Long id) {
        List<Long> idList = new ArrayList<>();
        idList.add(id);
        selectChildrenPermissionById(id, idList);
        return idList;
    }

    private void selectChildrenPermissionById(Long id, List<Long> idList) {
        List<Permission> childrenIdList = baseMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
        childrenIdList.forEach(item -> {
            idList.add(Long.valueOf(item.getId()));
            selectChildrenPermissionById(Long.valueOf(item.getId()), idList);
        });
    }
}
