package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import com.github.brandonbai.smartmonitor.pojo.Log;
import tk.mybatis.mapper.common.Mapper;

/**
 * LogMapper
 *
 * @author brandonbai
 * @since 2016年10月17日
 */
public interface LogMapper extends Mapper<Log> {

    List<Log> findLog();

    List<Log> findLogByDate(String date);

    void addLog(Log log);

}
