package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import com.github.brandonbai.smartmonitor.pojo.Log;

/**
 * LogMapper
 *
 * @author Feihu Ji
 * @since 2016年10月17日
 */
public interface LogMapper {

    List<Log> findLog();

    List<Log> findLogByDate(String date);

    void addLog(Log log);

}
