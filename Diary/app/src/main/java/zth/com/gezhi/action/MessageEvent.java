package zth.com.gezhi.action;

import java.io.Serializable;

/**
 * Created by cym1497 on 2017/7/25.
 */

public class MessageEvent implements Serializable {
        public int type;
        public int msg;
        public Object obj;

        public MessageEvent(int type, int msg) {
            this.type = type;
            this.msg = msg;
        }

        public MessageEvent(int type, int msg, Object obj) {
            this.type = type;
            this.msg = msg;
            this.obj = obj;
        }
}
