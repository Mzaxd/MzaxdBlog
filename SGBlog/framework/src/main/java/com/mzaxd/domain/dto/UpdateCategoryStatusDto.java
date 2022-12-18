package com.mzaxd.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author root
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryStatusDto {

    private Long categoryId;

    private String status;
}
