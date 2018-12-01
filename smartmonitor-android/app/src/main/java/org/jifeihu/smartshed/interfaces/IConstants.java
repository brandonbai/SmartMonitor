package org.jifeihu.smartshed.interfaces;

/**
 * 常量集
 *
 * Created by Administrator on 2016/11/21.
 */

public interface IConstants {

    String SHARED_PREFERENCE_USER = "sharedPreference_password";
    String SHARED_PREFERENCE_NOTIFICATION = "sharedPreference_notification";
    String SHARED_PREFERENCE_NETWORK = "sharedPreference_network";
    String KEY_IP = "ip";
    String KEY_PORT = "port";

    String KEY_USER_INFO = "key_user_info";
    String KEY_SENSOR_ID = "key_sensor_id";
    String KEY_SENSOR_NAME = "key_sensor_name";
    String KEY_SENSOR_UNIT = "key_sensor_unit";
    String KEY_THRESHOLD_MAX = "max";
    String KEY_THRESHOLD_MIN = "min";
    String KEY_DEVICE_ID = "key_device_id";
    String KEY_COMMAND = "command";
    String KEY_DEVICE_NAME = "key_device_name";
    String KEY_TIME_FIRST = "ft";
    String KEY_TIME_LAST = "lt";
    String KEY_URI_IMAGE = "imageUrl";

    // web资源
    String URL_LOG = "log/list";
    String URL_LOGIN = "/user/login";
    String URL_LOGOUT = "/user/logout";
    String URL_CHANGE_PWD = "/user/changepwd";
    String URL_DEVICE = "device/list/1";
    String URL_CONTROL = "device/control";
    String URL_COMMAND = "device/commands/";
    String URL_SENSOR = "sysinfo/sensor/1";
    String URL_SENSOR_VALUE = "sysinfo/sensorValue/";
    String URL_THRESHOLD_UPDATE = "threshold/update";
    String URI_INSTRUCTION = "instruction.html";

    //token
    String KEY_TOKEN = "token";
    // 通知栏报警设置
    String KEY_STATE = "notification_state";
    String KEY_SOUND = "notification_sound";
    String KEY_VIBRATE = "notification_shake";

    String REGEX_IP = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
    String REGEX_PORT = "\\d{4,5}";

}
