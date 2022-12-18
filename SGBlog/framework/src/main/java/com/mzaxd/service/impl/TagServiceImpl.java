package com.mzaxd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.TagDto;
import com.mzaxd.domain.dto.TagListDto;
import com.mzaxd.domain.entity.Tag;
import com.mzaxd.domain.vo.PageVo;
import com.mzaxd.domain.vo.TagVo;
import com.mzaxd.domain.vo.WriteTagVo;
import com.mzaxd.mapper.TagMapper;
import com.mzaxd.service.TagService;
import com.mzaxd.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
* @author root
* @description 针对表【sg_tag(标签)】的数据库操作Service实现
* @createDate 2022-12-04 12:02:26
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        //封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(String ids) {
        String[] id = ids.split(",");
        getBaseMapper().deleteBatchIds(Arrays.asList(id));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateTag(TagDto tagDto) {
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagInfo(Integer id) {
        Tag tag = getBaseMapper().selectById(id);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult listAllTag() {
        List<Tag> list = list();
        List<WriteTagVo> writeTagVos = BeanCopyUtils.copyBeanList(list, WriteTagVo.class);
        return ResponseResult.okResult(writeTagVos);
    }
}




