package zth.com.gezhi.action;

/**
 * Created by cym1497 on 2017/7/26.
 */

public enum ConnectionState {
    Disconnected(0),
    Listen(1),
    Connecting(2),
    Connected(3);

    private final int state;

    private ConnectionState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    public static ConnectionState valueOf(int state) {
        ConnectionState[] items = values();
        ConnectionState[] var5 = items;
        int var4 = items.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            ConnectionState item = var5[var3];
            if(item.getState() == state) {
                return item;
            }
        }

        return Disconnected;
    }
}
