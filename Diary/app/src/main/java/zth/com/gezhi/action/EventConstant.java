package zth.com.gezhi.action;

/**
 * Created by cym1497 on 2017/7/25.
 */

public interface EventConstant {
     String PORT_NUMBER = "115200";
     String PORT_NAME = "/dev/ttyS4";

     int CONNECTION_EVENT = 100;
     int DISCONNECTION_EVENT = 101;
     int READ_TAG_EVENT = 102;

     int CONNECT_SUCCESS_STATE = 0;
     int CONNECT_FAILURE_STATE = 1;
     int CONNECT_DOING_STATE = 2;
}
