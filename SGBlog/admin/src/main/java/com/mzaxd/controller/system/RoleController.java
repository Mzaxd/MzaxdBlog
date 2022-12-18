package com.mzaxd.controller.system;

import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.AddRoleDto;
import com.mzaxd.domain.dto.RoleChangeStatusDto;
import com.mzaxd.domain.dto.RoleDto;
import com.mzaxd.domain.dto.UpdateRoleDto;
import com.mzaxd.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author root
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult getRoleList(Integer pageNum, Integer pageSize, RoleDto roleDto) {
        return roleService.getRoleList(pageNum, pageSize, roleDto);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleChangeStatusDto roleChangeDto) {
        return roleService.changeStatus(roleChangeDto);
    }

    @PostMapping()
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto) {
        return roleService.addRole(addRoleDto);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult deleteRole(@PathVariable("ids") String ids){
        return roleService.deleteRole(ids);
    }

    @GetMapping("/listAllRole")
    public ResponseResult listAllRole() {
        return roleService.listAllRole();
    }
    
    @PutMapping()
    public ResponseResult updateRole(@RequestBody UpdateRoleDto updateRoleDto){
        return roleService.updateRole(updateRoleDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getRoleInfo(@PathVariable("id") Long id) {
        return roleService.getRoleInfo(id);
    }
}
