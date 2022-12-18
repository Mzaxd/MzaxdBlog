package com.mzaxd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.entity.User;

/**
 * @author root
 */
public interface LoginService extends IService<User> {

    /**
     * 后台登录接口
     *
     * @author mzaxd
     * @date 12/4/22 1:29 PM
     * @param user
     * @return ResponseResult
     */
    ResponseResult login(User user);

    /**
     * 后台用户退出登陆
     *
     * @author mzaxd
     * @date 12/6/22 2:07 PM
     * @return ResponseResult
     */
    ResponseResult logout();
}
