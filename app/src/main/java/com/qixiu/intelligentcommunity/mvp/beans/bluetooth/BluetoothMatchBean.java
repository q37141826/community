package com.qixiu.intelligentcommunity.mvp.beans.bluetooth;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

/**
 * Created by Administrator on 2017/6/28 0028.
 */

public class BluetoothMatchBean extends BaseBean<BluetoothMatchBean.BluetoothMatchInfo> {




    public static class BluetoothMatchInfo {
        /**
         * id : 2
         * device : ID5056C00001
         * ssid : WHJ0000001
         */

        private String id;
        private String device;
        private String ssid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getSsid() {
            return ssid;
        }

        public void setSsid(String ssid) {
            this.ssid = ssid;
        }
    }
}
