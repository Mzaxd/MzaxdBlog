package com.mzaxd.controller.content;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.UpdateCategoryStatusDto;
import com.mzaxd.domain.entity.Category;
import com.mzaxd.domain.vo.ExcelCategoryVo;
import com.mzaxd.enums.AppHttpCodeEnum;
import com.mzaxd.service.CategoryService;
import com.mzaxd.utils.BeanCopyUtils;
import com.mzaxd.utils.WebUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author root
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        return categoryService.listAllCategory();
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, "导出错误");
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    @GetMapping("/list")
    public ResponseResult getCategoryList(Integer pageNum, Integer pageSize, Category condition) {
        return categoryService.getCategoryListByPageAndCondition(pageNum, pageSize, condition);
    }

    @PostMapping()
    public ResponseResult addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable("id") Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping()
    public ResponseResult updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody UpdateCategoryStatusDto updateCategoryStatusDto) {
        return categoryService.changeStatus(updateCategoryStatusDto);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult deleteUsers(@PathVariable("ids") String ids) {
        return categoryService.deleteCategorys(ids);
    }
}
