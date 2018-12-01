package org.jifeihu.smartshed.dao;

import java.util.List;
import java.util.Map;

import org.jifeihu.smartshed.bean.Device;
import org.jifeihu.smartshed.bean.SensorValue;
import org.jifeihu.smartshed.bean.Threshold;


public interface Dao {
	
	// �����־��Ϣ
	void insertLog(String content);
	// ��ӻ�������
	void insertData(SensorValue sensorValue);
	// ��ȡ��ֵ
	List<Threshold> getThresholds();
	// �޸��豸״̬
	void setDeviceState(int sensorId, boolean state);
	// ��ȡ�豸��Ϣ
	Map<Integer,Device> getDeviceInfo();
}
