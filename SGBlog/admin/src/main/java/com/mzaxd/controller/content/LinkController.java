package com.mzaxd.controller.content;

import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.entity.Link;
import com.mzaxd.service.LinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author root
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Resource
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult getLinkList(Integer pageNum, Integer pageSize, Link condition) {
        return linkService.getLinkListByPageAndCondition(pageNum, pageSize, condition);
    }

    @PostMapping()
    public ResponseResult addLink(@RequestBody Link link) {
        return linkService.addLink(link);
    }

    @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable("id") Long id) {
        return linkService.getLinkById(id);
    }

    @PutMapping()
    public ResponseResult updateLink(@RequestBody Link link) {
        return linkService.updateLink(link);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult deleteLinks(@PathVariable("ids") String ids) {
        return linkService.deleteLinks(ids);
    }
}
