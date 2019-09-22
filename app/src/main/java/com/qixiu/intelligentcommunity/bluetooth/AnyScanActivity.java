package com.qixiu.intelligentcommunity.bluetooth;


import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.data.ScanResult;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.permission.PermissionCollectConstants;


import java.util.ArrayList;
import java.util.List;

public class AnyScanActivity extends Activity implements View.OnClickListener {
    // Debugging
    private static final String TAG = "AnyScanActivity";
    private static final boolean D = true;
    private Button btn_start, btn_stop;
    private ImageView img_loading;
    private Animation operatingAnim;
    private ResultAdapter mResultAdapter;
    private ProgressDialog progressDialog;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private BluetoothServiceBle mBluetoothService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //权限

        setContentView(R.layout.activity_any_scan);
        if (!hasPermission(PermissionCollectConstants.bluetoothPermissons)) {
            hasRequse(0, PermissionCollectConstants.bluetoothPermissons);
        }
        initView();
    }

    //检查权限
    public boolean hasPermission(String... permission) {
        for (String permissiom : permission) {
            if (ActivityCompat.checkSelfPermission(this, permissiom) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    //添加权限
    public void hasRequse(int code, String... permission) {
        ActivityCompat.requestPermissions(this, permission, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBluetoothService != null)
            unbindService();
    }

    private void initView() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("搜索设备");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(this);
        img_loading = (ImageView) findViewById(R.id.img_loading);
        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        operatingAnim.setInterpolator(new LinearInterpolator());
        progressDialog = new ProgressDialog(this);

        mResultAdapter = new ResultAdapter(this);
        ListView listView_device = (ListView) findViewById(R.id.list_device);
        listView_device.setAdapter(mResultAdapter);
        listView_device.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (mBluetoothService != null) {
//                    mBluetoothService.cancelScan();
//                    mBluetoothService.connectDevice(mResultAdapter.getItem(position));
                ScanResult item = mResultAdapter.getItem(position);
                String address = item.getDevice().getAddress();

                // Create the result Intent and include the MAC address
                Intent intent = new Intent();
                intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

                // Set result and finish this Activity
                setResult(Activity.RESULT_OK, intent);
                finish();

//                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                if (mBluetoothService == null) {
                    bindService();
                } else {
                    mBluetoothService.scanDevice();
                }
                break;

            case R.id.btn_stop:
                if (mBluetoothService != null) {
                    mBluetoothService.cancelScan();
                }
                break;
        }
    }

    private class ResultAdapter extends BaseAdapter {

        private Context context;
        private List<ScanResult> scanResultList;

        ResultAdapter(Context context) {
            this.context = context;
            scanResultList = new ArrayList<>();
        }

        void addResult(ScanResult result) {
            scanResultList.add(result);
        }

        void clear() {
            scanResultList.clear();
        }

        @Override
        public int getCount() {
            return scanResultList.size();
        }

        @Override
        public ScanResult getItem(int position) {
            if (position > scanResultList.size())
                return null;
            return scanResultList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ResultAdapter.ViewHolder holder;
            if (convertView != null) {
                holder = (ResultAdapter.ViewHolder) convertView.getTag();
            } else {
                convertView = View.inflate(context, R.layout.adapter_scan_result, null);
                holder = new ResultAdapter.ViewHolder();
                convertView.setTag(holder);
                holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
                holder.txt_mac = (TextView) convertView.findViewById(R.id.txt_mac);
                holder.txt_rssi = (TextView) convertView.findViewById(R.id.txt_rssi);
            }
            ScanResult result = scanResultList.get(position);
            BluetoothDevice device = result.getDevice();
            String name = device.getName();
            String mac = device.getAddress();

            int rssi = result.getRssi();
            holder.txt_name.setText(name);
            holder.txt_mac.setText(mac);
            holder.txt_rssi.setText(String.valueOf(rssi));
            return convertView;
        }

        class ViewHolder {
            TextView txt_name;
            TextView txt_mac;
            TextView txt_rssi;
        }
    }

    private void bindService() {
        Intent bindIntent = new Intent(this, BluetoothServiceBle.class);
        this.bindService(bindIntent, mFhrSCon, Context.BIND_AUTO_CREATE);
    }

    private void unbindService() {
        this.unbindService(mFhrSCon);
    }

    private ServiceConnection mFhrSCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBluetoothService = ((BluetoothServiceBle.BluetoothBinder) service).getService();
            mBluetoothService.setScanCallback(callback);
            mBluetoothService.scanDevice();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBluetoothService = null;
        }
    };

    private BluetoothServiceBle.Callback callback = new BluetoothServiceBle.Callback() {
        @Override
        public void onStartScan() {
            mResultAdapter.clear();
            mResultAdapter.notifyDataSetChanged();
            img_loading.startAnimation(operatingAnim);
            btn_start.setEnabled(false);
            btn_stop.setVisibility(View.VISIBLE);
        }

        @Override
        public void onScanning(ScanResult result) {
            mResultAdapter.addResult(result);
            mResultAdapter.notifyDataSetChanged();
        }

        @Override
        public void onScanComplete() {
            img_loading.clearAnimation();
            btn_start.setEnabled(true);
            btn_stop.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onConnecting() {
            progressDialog.show();
        }

        @Override
        public void onConnectFail() {
            img_loading.clearAnimation();
            btn_start.setEnabled(true);
            btn_stop.setVisibility(View.INVISIBLE);
            progressDialog.dismiss();
            Toast.makeText(AnyScanActivity.this, "连接失败", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onDisConnected() {
            progressDialog.dismiss();
            mResultAdapter.clear();
            mResultAdapter.notifyDataSetChanged();
            img_loading.clearAnimation();
            btn_start.setEnabled(true);
            btn_stop.setVisibility(View.INVISIBLE);
            Toast.makeText(AnyScanActivity.this, "连接断开", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServicesDiscovered() {
            progressDialog.dismiss();
            //  startActivity(new Intent(AnyScanActivity.this, OperationActivity.class));
        }
    };

}
