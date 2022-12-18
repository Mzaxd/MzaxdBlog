package com.mzaxd.service;

import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.TagDto;
import com.mzaxd.domain.dto.TagListDto;
import com.mzaxd.domain.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author root
* @description 针对表【sg_tag(标签)】的数据库操作Service
* @createDate 2022-12-04 12:02:26
*/
public interface TagService extends IService<Tag> {

    /**
     * 分页返回文章标签
     * @author mzaxd
     * @date 12/7/22 1:45 AM
     * @param pageNum
     * @param pageSize
     * @param tagListDto
     * @return ResponseResult
     */
    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    /**
     * 添加Tag
     *
     * @author mzaxd
     * @date 12/7/22 2:21 AM
     * @param tagListDto
     * @return ResponseResult
     */
    ResponseResult addTag(TagListDto tagListDto);

    /**
     * 根据id删除Tag
     *
     * @author mzaxd
     * @date 12/7/22 2:26 AM
     * @param ids 字符串形式的多个数
     * @return ResponseResult
     */
    ResponseResult deleteTag(String ids);

    /**
     * 修改Tag
     *
     * @author mzaxd
     * @date 12/7/22 2:45 AM
     * @param id
     * @return ResponseResult
     */
    ResponseResult updateTag(TagDto id);

    /**
     * 获取标签信息
     *
     * @author mzaxd
     * @date 12/7/22 2:47 AM
     * @param id
     * @return ResponseResult
     */
    ResponseResult getTagInfo(Integer id);

    /**
     * 返回所有标签
     *
     * @author mzaxd
     * @date 12/7/22 4:04 AM
     * @return ResponseResult
     */
    ResponseResult listAllTag();
}
