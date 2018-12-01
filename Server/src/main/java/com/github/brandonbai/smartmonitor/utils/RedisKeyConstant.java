package com.github.brandonbai.smartmonitor.utils;

/**
 * redis keys
 * @author brandonbai
 * @since 2018/10/24
 */
public interface RedisKeyConstant {

    /**
     * 传感器数量
     */
    String DASHBOARD_SENSOR_NUMBER = "dashboard_sensor_number";

    /**
     * 报警数量
     */
    String DASHBOARD_WARN_NUMBER = "dashboard_warn_number";

    /**
     * 消息数量
     */
    String DASHBOARD_MESSAGE_NUMBER = "dashboard_message_number";

    /**
     * 设备数量
     */
    String DASHBOARD_DEVICE_NUMBER = "dashboard_device_number";

    /**
     * 月正常数据
     */
    String DATA_NUMBER_MONTH_NORMAL = "data_number_month_normal";

    /**
     * 月异常数据
     */
    String DATA_NUMBER_MONTH_WARN = "data_number_month_warn";

}
