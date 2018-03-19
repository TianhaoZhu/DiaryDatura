package zth.com.gezhi.action;

/**
 * Created by cym1497 on 2017/7/26.
 */

public interface RfidReaderEventListener {
    void onReaderStateChanged(ConnectionState state);

    void onReaderActionChanged(ActionState actionState);

    void onReaderReadTag(String type, String epc, String tid, String userData, float rssi);

    void onReaderResult(ResultCode var2, ActionState var3, String var4, String var5, float var6, float var7);
}
