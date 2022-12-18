package com.mzaxd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.entity.Link;

import java.util.List;

/**
* @author root
* @description 针对表【sg_link(友链)】的数据库操作Service
* @createDate 2022-11-26 11:19:12
*/
public interface LinkService extends IService<Link> {

    /**
     * 获取全部友链
     *
     * @author mzaxd
     * @date 11/26/22 11:26 AM
     * @return ResponseResult<?>
     */
    ResponseResult<List<?>> getAllLink();

    ResponseResult getLinkListByPageAndCondition(Integer pageNum, Integer pageSize, Link condition);

    ResponseResult addLink(Link link);

    ResponseResult getLinkById(Long id);

    ResponseResult updateLink(Link link);

    ResponseResult deleteLinks(String ids);
}
