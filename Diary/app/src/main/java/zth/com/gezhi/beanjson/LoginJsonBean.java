package zth.com.gezhi.beanjson;

import java.io.Serializable;

/**
 * 登陆JsonBean
 * Created by Connie on 2017/9/25.
 */

public class LoginJsonBean {

    public String status;
    public String code;
    public String msg;
    public LoginData data;




    public static class LoginData implements Serializable {
        public String dname;
        public int dianId;
        private int id;
        private String password;
        private String name;
        private int clientId;
        private String cellphone;
        private String type;
        private String status;
        private String roleName;
        private int roleId;
        private String clientName;
        private Object nickname;
        private Object loginIp;
        private Object remark;
        private String attr01;
        private Object attr02;
        private Object attr03;
        private long createDate;
        private long lastUpdate;
        private String warehouse;
        private String warehouseName;
        private String warehouseId="1";//默认值为1
        private String tagBindFlag;//1 开启，0 关闭
        private int retrieveDigits=9;//检索位置

        public String getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(String warehouseId) {
            this.warehouseId = warehouseId;
        }

        public String getTagBindFlag() {
            return tagBindFlag;
        }

        public void setTagBindFlag(String tagBindFlag) {
            this.tagBindFlag = tagBindFlag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getClientId() {
            return clientId;
        }

        public void setClientId(int clientId) {
            this.clientId = clientId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public Object getNickname() {
            return nickname;
        }

        public void setNickname(Object nickname) {
            this.nickname = nickname;
        }

        public Object getLoginIp() {
            return loginIp;
        }

        public void setLoginIp(Object loginIp) {
            this.loginIp = loginIp;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getAttr01() {
            return attr01;
        }

        public void setAttr01(String attr01) {
            this.attr01 = attr01;
        }

        public Object getAttr02() {
            return attr02;
        }

        public void setAttr02(Object attr02) {
            this.attr02 = attr02;
        }

        public Object getAttr03() {
            return attr03;
        }

        public void setAttr03(Object attr03) {
            this.attr03 = attr03;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public long getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(long lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public String getWarehouse() {
            return warehouse;
        }

        public void setWarehouse(String warehouse) {
            this.warehouse = warehouse;
        }

        public String getWarehouseName() {
            return warehouseName;
        }

        public void setWarehouseName(String warehouseName) {
            this.warehouseName = warehouseName;
        }


        public int getRetrieveDigits() {
            return retrieveDigits;
        }

        public void setRetrieveDigits(int retrieveDigits) {
            this.retrieveDigits = retrieveDigits;
        }
    }
}
