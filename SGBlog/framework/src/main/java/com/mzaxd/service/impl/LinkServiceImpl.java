package com.mzaxd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzaxd.constans.SystemConstants;
import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.entity.Link;
import com.mzaxd.domain.vo.LinkVo;
import com.mzaxd.domain.vo.PageVo;
import com.mzaxd.mapper.LinkMapper;
import com.mzaxd.service.LinkService;
import com.mzaxd.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
* @author root
* @description 针对表【sg_link(友链)】的数据库操作Service实现
* @createDate 2022-11-26 11:19:12
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService{

    @Resource
    private LinkMapper linkMapper;

    /**
     * 获取全部友链
     *
     * @return ResponseResult<?>
     * @author mzaxd
     * @date 11/26/22 11:26 AM
     */
    @Override
    public ResponseResult<List<?>> getAllLink() {
        //查询所有审核通过的友链
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //转换成Vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装结果返回
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult getLinkListByPageAndCondition(Integer pageNum, Integer pageSize, Link condition) {
        //设置条件
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(condition.getName()), Link::getName, condition.getName());
        queryWrapper.eq(StringUtils.hasText(condition.getStatus()), Link::getStatus, condition.getStatus());
        //按条件查询
        Page<Link> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        //封装返回
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addLink(Link link) {
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLinkById(Long id) {
        Link link = linkMapper.selectById(id);
        return ResponseResult.okResult(link);
    }

    @Override
    public ResponseResult updateLink(Link link) {
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLinks(String ids) {
        String[] id = ids.split(",");
        getBaseMapper().deleteBatchIds(Arrays.asList(id));
        return ResponseResult.okResult();
    }
}




