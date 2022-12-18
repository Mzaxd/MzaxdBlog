package com.mzaxd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.entity.User;

/**
 * @author root
 */
public interface BlogLoginService extends IService<User> {
    /**
     * 用户登录接口
     *
     * @author mzaxd
     * @date 11/27/22 12:50 AM
     * @param user 用户对象
     * @return ResponseResult
     */
    ResponseResult login(User user);

    /**
     * 注销接口
     *
     * @author mzaxd
     * @date 11/27/22 4:35 AM
     * @return ResponseResult<?>
     */
    ResponseResult<?> logout();
}
