package com.mzaxd.controller;

import com.mzaxd.domain.ResponseResult;
import com.mzaxd.service.LinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author root
 */
@RestController
@RequestMapping("/link")
public class LinkController {
    
    @Resource
    private LinkService linkService;
    
    @GetMapping("/getAllLink")
    public ResponseResult<List<?>> getAllLink(){
        return linkService.getAllLink();
    }
}
