package com.mzaxd.service;

import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.UpdateCategoryStatusDto;
import com.mzaxd.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzaxd.domain.vo.CategoryVo;
import com.mzaxd.domain.vo.WriteCategoryVo;

import java.util.List;

/**
* @author Mzaxd
* @description 针对表【sg_category(分类表)】的数据库操作Service
* @createDate 2022-11-22 16:07:21
*/
public interface CategoryService extends IService<Category> {

    /**
     * 返回分类信息
     *
     * @author mzaxd
     * @date 2022/11/22 16:21
     * @return ResponseResult<List<CategoryVo>>
     */
    ResponseResult<List<CategoryVo>> getCategoryList();

    /**
     * 返回所有分类信息
     *
     * @author mzaxd
     * @date 12/7/22 3:54 AM
     * @return ResponseResult
     */
    ResponseResult<List<WriteCategoryVo>> listAllCategory();

    /**
     * 条件和分页查询所有分类
     *
     * @author mzaxd
     * @date 12/18/22 2:05 AM
     * @param pageNum
     * @param pageSize
     * @param condition
     * @return ResponseResult
     */
    ResponseResult getCategoryListByPageAndCondition(Integer pageNum, Integer pageSize, Category condition);

    /**
     * 新增分类
     *
     * @author mzaxd
     * @date 12/18/22 2:19 AM
     * @param category
     * @return ResponseResult
     */
    ResponseResult addCategory(Category category);

    /**
     * 通过id回显分类信息
     *
     * @author mzaxd
     * @date 12/18/22 2:22 AM
     * @param id
     * @return ResponseResult
     */
    ResponseResult getCategoryById(Long id);

    /**
     * 修改更新分类
     *
     * @author mzaxd
     * @date 12/18/22 2:36 AM
     * @param category
     * @return ResponseResult
     */
    ResponseResult updateCategory(Category category);

    /**
     * 改变分类状态
     *
     * @author mzaxd
     * @date 12/18/22 3:45 AM
     * @param updateCategoryStatusDto
     * @return ResponseResult
     */
    ResponseResult changeStatus(UpdateCategoryStatusDto updateCategoryStatusDto);

    /**
     * 批量删除分类
     * 
     * @author mzaxd
     * @date 12/18/22 3:52 AM
     * @param ids
     * @return ResponseResult
     */
    ResponseResult deleteCategorys(String ids);
}
