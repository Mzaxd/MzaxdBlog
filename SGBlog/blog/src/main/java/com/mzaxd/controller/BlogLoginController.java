package com.mzaxd.controller;


import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.entity.User;
import com.mzaxd.enums.AppHttpCodeEnum;
import com.mzaxd.exception.SystemException;
import com.mzaxd.service.BlogLoginService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author root
 */
@RestController
public class BlogLoginController {

    @Resource
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult<?> login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())) {
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult<?> logout() {
        return blogLoginService.logout();
    }
}
