package com.mzaxd.controller.content;

import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.TagDto;
import com.mzaxd.domain.dto.TagListDto;
import com.mzaxd.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author root
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Resource
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @PostMapping()
    public ResponseResult addTag(@RequestBody TagListDto tagListDto) {
        return tagService.addTag(tagListDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") String ids) {
        return tagService.deleteTag(ids);
    }

    @GetMapping("/{id}")
    public ResponseResult getTagInfo(@PathVariable("id") Integer id) {
        return tagService.getTagInfo(id);
    }

    @PutMapping()
    public ResponseResult updateTag(@RequestBody TagDto tagDto){
        return tagService.updateTag(tagDto);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag() {
        return tagService.listAllTag();
    }
}
