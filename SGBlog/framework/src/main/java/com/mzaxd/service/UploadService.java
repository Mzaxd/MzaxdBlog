package com.mzaxd.service;

import com.mzaxd.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author root
 */
public interface UploadService {
    /**
     * 上传图片
     *
     * @author mzaxd
     * @date 11/27/22 10:20 AM
     * @param img 图片
     * @return ResponseResult
     */
    ResponseResult uploadImg(MultipartFile img);
}
