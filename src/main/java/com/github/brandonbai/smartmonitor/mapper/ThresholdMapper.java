package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.exception.MsgException;
import com.github.brandonbai.smartmonitor.pojo.Threshold;

/**
 * 
 * ThresholdMapper 
 * @author Feihu Ji
 * @sine 2016年10月17日
 *
 */
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
