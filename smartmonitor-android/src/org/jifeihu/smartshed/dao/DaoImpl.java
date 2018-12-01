package org.jifeihu.smartshed.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jifeihu.smartshed.bean.Device;
import org.jifeihu.smartshed.bean.SensorValue;
import org.jifeihu.smartshed.bean.Threshold;
import org.jifeihu.smartshed.util.DBUtils;

public class DaoImpl implements Dao {
	
	@Override
	public void insertLog(String content) {
		try {
			DBUtils.update("INSERT INTO tb_loginfo VALUES(NULL,NOW(),?)", content);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertData(SensorValue sensorValue) {
		try {
			DBUtils.update("INSERT INTO tb_sensor_value VALUES(NULL,?,?,NOW())", sensorValue.getValue(), sensorValue.getSensorId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Threshold> getThresholds() {
		List<Threshold> threList = new ArrayList<Threshold>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT nameCN, max, min, sensorId FROM tb_threshold, tb_sensor " +
				"WHERE sensorId = tb_sensor.id ORDER BY tb_threshold.id";
		try {
			conn = DBUtils.getConn();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				threList.add(new Threshold(rs.getInt("sensorId"),
						rs.getString("nameCN"), rs.getInt("max"),rs.getInt("min")));
			}
			return threList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtils.close(conn, ps, rs);
		}
	}
	@Override
	public void setDeviceState(int sensorId, boolean state) {
		try {
			DBUtils.update("UPDATE tb_device SET state=? WHERE sensorId=?", state, sensorId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public Map<Integer,Device> getDeviceInfo() {
		Map<Integer,Device> list = new HashMap<Integer,Device>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT sensorId, state, tb_device.nameCN, commandOn, commandOff FROM tb_device, tb_sensor WHERE sensorId=tb_sensor.id";
		try {
			conn = DBUtils.getConn();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				list.put(rs.getInt("sensorId"), new Device(rs.getBoolean("state"), rs.getString("nameCN"), 
						rs.getString("commandOn"), rs.getString("commandOff")));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtils.close(conn, ps, rs);
		}
	}
}
