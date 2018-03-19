package zth.com.gezhi.action;

/**
 * Created by yff on 2017/8/9.
 */

public enum ResultCode {

    NoError(0, "No error"),
    OtherError(1, "Other error"),
    Undefined(2, "Undefined"),
    MemoryOverrun(3, "Memory overrun"),
    MemoryLocked(4, "Memory locked"),
    InsufficientPower(11, "Insufficient power"),
    NonSpecificError(15, "Non-Specific error"),
    InvalidResponse('\ue001', "Invalid response"),
    InOperation('\ue002', "In operation"),
    OutOfRange('\ue003', "Out of range"),
    NotConnected('\ue004', "Disconnected"),
    InvalidParameter('\ue010', "Invalidate parameter"),
    InvalidInstance('\ue100', "Invalid instance"),
    FailSendControlPacket('\uee00', "Failed to send control packet"),
    FailReceivePacket('\uee01', "Failed to receive packet"),
    InvalidControlResponse('\uee02', "Invalidate control response packet"),
    UnknownControlResponse('\uee0f', "Unknown control response"),
    InvalidRegisterParameter('\uee10', "Invalidate register parameter"),
    InvalidRegisterResponse('\uee11', "Invalidate register response"),
    UnknownRegisterResponse('\uee12', "Unknown register response"),
    FailSendRegisterPacket('\uee11', "Failed to send register packet"),
    NotSupported('\uef00', "Not Supported"),
    Timeout('\uefff', "Timeout"),
    HandleMismatch('\uf001', "Handle mismatch"),
    CRCError('\uf002', "CRC error on tag response"),
    NoTagReply('\uf003', "No tag reply"),
    InvalidPassword('\uf004', "Invalid password"),
    ZeroKillPassword('\uf005', "Zero kill password"),
    TagLost('\uf006', "Tag lost"),
    CommandFormatError('\uf007', "Command format error"),
    ReadCountInvalid('\uf008', "Read count invalid"),
    OutOfRetries('\uf009', "Out of retries"),
    ParamError('\ufffb', "Parameter error"),
    Busy('￼', "Busy"),
    InvalidCommand('�', "Invalid command"),
    LowBattery('\ufffe', "Low battery"),
    OperationFailed('\uffff', "Operation failed");

    private final int mCode;
    private final String mName;

    private ResultCode(int code, String name) {
        this.mCode = code;
        this.mName = name;
    }

    public int getCode() {
        return this.mCode;
    }

    public String toString() {
        return this.mName;
    }

    public static ResultCode valueOf(int code) {
        if(code != 0 && code != '\uf000') {
            ResultCode[] var4;
            int var3 = (var4 = values()).length;

            for(int var2 = 0; var2 < var3; ++var2) {
                ResultCode item = var4[var2];
                if(item.getCode() == code) {
                    return item;
                }
            }

            return NonSpecificError;
        } else {
            return NoError;
        }
    }
}
