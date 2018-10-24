package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.pojo.Threshold;
import tk.mybatis.mapper.common.Mapper;

/**
 * ThresholdMapper
 *
 * @author brandonbai
 * @since 2016年10月17日
 */
public interface ThresholdMapper extends Mapper<Threshold> {

    List<Threshold> findAll(@Param("pageNum") int pageNum,
                            @Param("pageSize") int pageSize);

    void updateThreshold(Threshold threshold);

    Threshold findOne(Integer id);

    void insertThreshold(Threshold threshold);
}
