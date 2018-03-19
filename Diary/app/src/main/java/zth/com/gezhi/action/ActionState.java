package zth.com.gezhi.action;

/**
 * Created by cym1497 on 2017/7/26.
 */

public enum ActionState {

    Unknown(' ', "Unknown"),
    Inventory6bMulti('b', "Inventory 6B Multi"),
    Inventory6bSingle('a', "Inventory 6B Single"),
    Inventory6cMulti('f', "Inventory 6C Multi"),
    Inventory6cSingle('e', "Inventory 6C Single"),
    Inventory6cSelect('d', "Inventory 6C Selection"),
    InventoryAnyMulti('k', "inventory Any Multi"),
    ReadMemory6c('r', "Read Memory 6C Single"),
    ReadMemory6cMulti('R', "Read Memory 6C Multi"),
    ReadMemory6cSingle('R', "Read Memory 6C Single"),
    WriteMemory6c('w', "Write Memory 6C"),
    WriteMemory6b('c', "Write Memory 6B"),
    Lock('l', "Lock Tag"),
    Kill('k', "Kill Tag"),
    BlockErase('E', "Block Erase"),
    BlockWrite('W', "Block Write"),
    Stop('3', "Stop Operation"),
    InventoryRailMulti('R', "Inventory Rail Multi"),
    InventoryAnyExSingle('K', "Inventory Any Ex Single"),
    ReadMemoryRail('R', "Read Memory Rail");

    private char mAction;
    private String mName;

    private ActionState(char action, String name) {
        this.mAction = action;
        this.mName = name;
    }

    public char getCode() {
        return this.mAction;
    }

    public String toString() {
        return this.mName;
    }

    public static ActionState valueOf(int action) {
        return valueOf((char)action);
    }

    public static ActionState valueOf(char action) {
        ActionState[] var4;
        int var3 = (var4 = values()).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            ActionState e = var4[var2];
            if(e.getCode() == action) {
                return e;
            }
        }

        return Stop;
    }
}
