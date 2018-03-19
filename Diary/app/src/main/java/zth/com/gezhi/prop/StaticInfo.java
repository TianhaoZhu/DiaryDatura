package zth.com.gezhi.prop;

/**
 * Created by yff on 2017/4/26.
 * 功能描述：
 */
public interface StaticInfo {


    String CHAR_SET_NAME = "utf-8";

    String warehouseStore[] = new String[]{
            "166--深圳天利广场店","167--福永益田假日店","168--深圳星城广场店"
    };






    int  volume_init=7;

    int search_signle_power=5;
    int search_Power=30;
    int inventory_Power=30;
    int input_power=30;
    int bind_power=30;
    int allocation_power=30;
    int back_power=30;
    int bind_test_power=30;






    int POWER_INPUT=0;
    int POWER_ALLOCATION=1;
    int POWER_BACK=2;
    int POWER_SEARCH=3;
    int POWER_BIND=4;
    int POWER_INVENTORY=5;
    int POWER_BIND_TEST=6;




    String shareName="sysSetting.txt";



    String volumeMaxLabel="volumeMax";
    String volumeLabel="volume";

    String searchPowerLabel="searchPower";
    String inventoryPowerLabel="inventoryPower";
    String bindPowerLabel="bindPower";
    String allocationPowerLabel="allocationPower";
    String inputPowerLabel="inputPower";
    String backToWarehousePowerLabel="backPower";
    String bindPowerTestLabel="bindTestPower";


    String userShareName = "userinfo";
    String urlName="useUrl";


}
