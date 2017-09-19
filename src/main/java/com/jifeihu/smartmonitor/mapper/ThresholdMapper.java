package com.jifeihu.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jifeihu.smartmonitor.exception.MsgException;
import com.jifeihu.smartmonitor.pojo.Threshold;

public interface ThresholdMapper {
	/**
	 * ��ȡȫ����ֵ
	 * @return ��ֵ���󼯺�
	 */
	public List<Threshold> findAll(@Param("pageNum") int pageNum, 
            @Param("pageSize") int pageSize);
	
	/**
	 * �޸���ֵ
	 * @param list ��ֵ���󼯺�
	 * @throws MsgException
	 */
	public void update(Threshold threshold);

	public Threshold findOne(Integer id);
	
}
