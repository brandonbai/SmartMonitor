package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.pojo.Threshold;

/**
 * ThresholdMapper
 *
 * @author Feihu Ji
 * @since 2016年10月17日
 */
interface ThresholdMapper {

    List<Threshold> findAll(@Param("pageNum") int pageNum,
                            @Param("pageSize") int pageSize);

    void update(Threshold threshold);

    Threshold findOne(Integer id);

}
