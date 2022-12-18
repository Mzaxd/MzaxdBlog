package com.mzaxd.controller.system;

import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.AddUserDto;
import com.mzaxd.domain.dto.UpdateUserRoleDto;
import com.mzaxd.domain.dto.UpdateUserStatusDto;
import com.mzaxd.domain.dto.UserListDto;
import com.mzaxd.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author root
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, UserListDto userListDto) {
        return userService.getUserList(pageNum, pageSize, userListDto);
    }

    @PostMapping()
    public ResponseResult addUser(@RequestBody AddUserDto addUserDto) {
        return userService.addUser(addUserDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getUserInfoById(@PathVariable("id") Long id) {
        return userService.getUserInfoById(id);
    }

    @PutMapping()
    public ResponseResult updateUser(@RequestBody UpdateUserRoleDto updateUserRoleDto) {
        return userService.updateUserRole(updateUserRoleDto);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody UpdateUserStatusDto updateUserStatusDto) {
        return userService.changeStatus(updateUserStatusDto);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult deleteUsers(@PathVariable("ids") String ids) {
        return userService.deleteUsers(ids);
    }

}
