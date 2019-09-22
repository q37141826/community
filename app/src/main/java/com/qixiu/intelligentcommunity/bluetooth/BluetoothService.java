package com.qixiu.intelligentcommunity.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.bluetooth.testpack.BleUtil;
import com.qixiu.intelligentcommunity.mvp.beans.bluetooth.BluetoothDevicePackaging;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Klasse, welche die Bluetooth-Funktionalität
 * bereitstellt
 */
public final class BluetoothService {

    /**
     * Das Logging-Tag
     */
    @NotNull
    private static final String TAG = "BluetoothService_mine";

    /**
     * UUID für SPP-Protokoll
     */

    @NotNull
    private static final UUID uuidSpp = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    /**
     * Der Anwendungskontext
     */
    private static Context applicationContext;

    /**
     * Gibt an, ob die Klasse initialisiert wurde
     */
    private static boolean initialized;

    /**
     * Der Intent-Filter für die Broadcasts
     */
    @NotNull
    private static final IntentFilter broadcastIntentFilter = new IntentFilter();

    /**
     * Der Broadcast-Receiver
     */
    private static BroadcastReceiver broadcastReceiver;

    /**
     * Keine Instanzen erlauben
     */
    private BluetoothService() {
    }

    /**
     * Der Bluetooth-Adapter
     */
    private static BluetoothAdapter btAdapter;

    /**
     * Der Event-Receiver
     */
    private static IBluetoothServiceEventReceiver eventReceiver;

    /**
     * Handler für den event receiver
     */
    @NotNull
    private final static Handler eventReceiverHandler = new Handler();

    /**
     * Das verbundene Gerät
     */
    @Nullable
    private static BluetoothDevice connectedDevice;

    /**
     * Der Socket des verbundenen Gerätes
     */
    @Nullable
    private static BluetoothSocket connectedSocket;

    /**
     * Der Ausgabestream
     */
    @Nullable
    private static BufferedOutputStream outputStream;

    /**
     * Der Eingabestream
     */
    @Nullable
    private static BufferedInputStream inputStream;

    /**
     * Initialisiert Bluetooth.
     * <p>
     * <p/>
     * <p>
     * Diese Methode muss vor allen anderen Methoden aufgerufen werden!
     *
     * @param applicationContext Der Anwendungskontext
     * @return <code>true</code>, wenn der Service erfolgreich initialisiert wurde (oder bereits initialisiert war)<br/>
     * <code>false</code>, wenn bei der Initialisierung ein Fehler auftrat
     */
    public static synchronized boolean initialize(@NotNull Context applicationContext, @NotNull IBluetoothServiceEventReceiver eventReceiver) {
        BluetoothService.eventReceiver = eventReceiver;

        if (initialized) return true;
        BluetoothService.applicationContext = applicationContext;

        // Lokalen Adapter besorgen
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            Toast.makeText(applicationContext, R.string.no_bluetooth_modem, Toast.LENGTH_LONG).show();
            ;
            return false;
        }

        // Erfolg.
        initialized = true;
        return true;
    }

    /**
     * Ermittelt, ob Bluetooth vorhanden ist.
     *
     * @return <code>true</code>, wenn Bluetooth prinzipiell vorhanden ist.
     */
    public static synchronized boolean bluetoothAvailable() {
        return btAdapter != null;
    }

    /**
     * Ermittelt, ob Bluetooth aktiviert ist
     *
     * @return <code>true</code>, wenn Bluetooth aktiviert ist
     */
    public static synchronized boolean bluetoothEnabled() {
        return btAdapter != null && btAdapter.isEnabled();
    }

    /**
     * Fordert das Aktivieren von Bluetooth an
     *
     * @return <code>false</code>, wenn Bluetooth schon aktiviert war
     */
    public static synchronized boolean requestEnableBluetooth(@NotNull Activity activity) {
        if (bluetoothEnabled()) return false;

        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableIntent, IntentRequestCodes.BT_REQUEST_ENABLE);
        return true;
    }

    /**
     * Registriert den Broadcast-Receiver
     *
     * @param activity Die Activity
     */
    public static synchronized void registerBroadcastReceiver(@NotNull final Activity activity) {
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    int currentState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                    int lastState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, -1);

                    Log.v(TAG, "Bluetooth state change received: " + lastState + " --> " + currentState);
                    switch (currentState) {
                        case BluetoothAdapter.STATE_TURNING_ON:
                            onBluetoothEnabling();
                            break;
                        case BluetoothAdapter.STATE_ON:
                            onBluetoothEnabled();
                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            onBluetoothDisabling();
                            break;
                        case BluetoothAdapter.STATE_OFF:
                            onBluetoothDisabled();
                            break;
                    }

                    if (currentState == BluetoothAdapter.STATE_ON)
                        onBluetoothEnabled();
                    if (currentState == BluetoothAdapter.STATE_TURNING_OFF)
                        onBluetoothDisabling();
                }
            };

            broadcastIntentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        }
        try{
            activity.registerReceiver(broadcastReceiver, broadcastIntentFilter);
        }catch (Exception e){

        }

    }

    /**
     * Deregistriert den Broadcast-Receiver
     *
     * @param activity Die Activity
     */
    public static synchronized void unregisterBroadcastReceiver(@NotNull Activity activity) {
        if (broadcastReceiver == null) return;
        try {
            activity.unregisterReceiver(broadcastReceiver);
        }catch (Exception e){

        }
    }

    /**
     * Wird gerufen, wenn Bluetooth aktiviert wird
     */
    private static synchronized void onBluetoothEnabling() {
        assert eventReceiver != null;
        eventReceiverHandler.post(new Runnable() {
            @Override
            public void run() {
                eventReceiver.bluetoothEnabling();
            }
        });
    }

    /**
     * Wird gerufen, wenn Bluetooth aktiviert wird
     */
    private static synchronized void onBluetoothEnabled() {
        assert eventReceiver != null;
        eventReceiverHandler.post(new Runnable() {
            @Override
            public void run() {
                eventReceiver.bluetoothEnabled();
            }
        });
    }

    /**
     * Wird gerufen, wenn Bluetooth aktiviert wurde
     */
    private static synchronized void onBluetoothDisabling() {
        assert eventReceiver != null;
        eventReceiverHandler.post(new Runnable() {
            @Override
            public void run() {
                eventReceiver.bluetoothDisabling();
            }
        });
    }

    /**
     * Wird gerufen, wenn Bluetooth aktiviert wurde
     */
    private static synchronized void onBluetoothDisabled() {
        assert eventReceiver != null;
        eventReceiverHandler.post(new Runnable() {
            @Override
            public void run() {
                eventReceiver.bluetoothDisabled();
            }
        });
    }

    /**
     * Verbindet mit dem angegeben Gerät
     *
     * @param macAddress Die MAC-Adresse
     */

    private static final int fixationByteSum = 20;

    public interface ConnectCallBack {
        void onSuccess(BluetoothDevice bluetoothDevice);

        void onFailed(BluetoothDevice bluetoothDevice, Exception e);

    }

    private static BluetoothAdapter.LeScanCallback mScanCb = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi,
                             byte[] scanRecord) {
            final BleUtil.BleAdvertisedData badata = BleUtil.parseAdertisedData(scanRecord);
            String deviceName = device.getName();
            if (deviceName == null) {
                deviceName = badata.getName();
            }
        }
    };


    public static synchronized void connectToDevice(@NotNull final BluetoothDevicePackaging bluetoothDevicePackaging, @NotNull final ConnectCallBack connectCallBack) {
        assert eventReceiver != null;

        // Alte Verbindung trennen
        disconnect();
        // Bezieht das Gerät
//        BluetoothDevice device = btAdapter.getRemoteDevice(bluetoothDevicePackaging.getBluetoothDevice().getAddress());
        BluetoothDevice device = bluetoothDevicePackaging.getBluetoothDevice();
        Log.i(TAG, "Bluetooth-Gerät ausgewählt: " + device.getName() + "; " + device.getAddress());

        connectedDevice = device;

        // Benachrichtigung senden
        final String deviceName = device.getName();
        if (TextUtils.isEmpty(deviceName)) {


            connectCallBack.onFailed(device, null);
            return;
        }
        eventReceiverHandler.post(new Runnable() {
            @Override
            public void run() {
                eventReceiver.connectedTo(deviceName == null ? "unnamed" : deviceName, bluetoothDevicePackaging.getBluetoothDevice().getAddress());
            }
        });

        // Socket aufbauen
        try {
//            BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuidSpp);
            Method m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
            BluetoothSocket socket = (BluetoothSocket) m.invoke(device, 1);

            connectedSocket = socket;
            if (socket == null) {
                Log.e(TAG, "Konnte Bluetooth-Socket nicht erzeugen!"); // TODO: An UI weitergeben!
                return;
            }

            // Wenn wir discovern - abbrechen
            if (btAdapter.isDiscovering()) btAdapter.cancelDiscovery();

            // Verbinden
            try {
                socket.connect();
                Log.i(TAG, "Connecting Socket to " + device.getName()); // TODO: An UI weitergeben!

            } catch (IOException e) {
                socket.close();//
                Log.e(TAG, "失败了更换连接"); // TODO: An UI weitergeben!
//                try {
//                    Method m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
//                    socket = (BluetoothSocket) m.invoke(device, 1);
//                    socket.connect();
//                } catch (Exception e1) {
//                    Log.e(TAG, "失败了关闭连接"); // TODO: An UI weitergeben!
//                    //如果更换了连接也无法传输成功，那么这个地方返回去重新来一下
//                    try {
//                        socket.close();
//                        connectCallBack.onFailed(device, e);
//                    } catch (IOException ie) {
//                        connectCallBack.onFailed(device, e);
//                    }
//                    connectToDevice(bluetoothDevicePackaging, connectCallBack);
//                    return;
//                }
            }

            // Ausgabestream besorgen
            try {
                InputStream realInputStream = socket.getInputStream();
                if (realInputStream == null) {
                    Log.e(TAG, "失败了无下文1"); // TODO: An UI weitergeben!
                    return;
                }
                inputStream = new BufferedInputStream(realInputStream);
            } catch (IOException e) {
                Log.e(TAG, "失败了无下文2"); // TODO: An UI weitergeben!
                return;
            }

            // Ausgabestream besorgen
            try {
                OutputStream realOutputStream = socket.getOutputStream();
                if (realOutputStream == null) {
                    Log.e(TAG, "失败了无下文3"); // TODO: An UI weitergeben!
                    return;
                }
//                String firstGoint = "ID5056C00001|WHJIBLKEY39383171";
//                String md5_1 = MD5Util.MD5(firstGoint);
//                String secondGoint = "OPEN0001|" + md5_1;
//                String s2 = "OPEN0001|" + MD5Util.MD5(secondGoint);
                //   s2 = toASi(s2);
                //  String s2 = "4F50454E303030317C4243433946383342414146333341343330314134383836323141413139383630";
                realOutputStream.write(sendSyncMessage(bluetoothDevicePackaging.getHardwareId()).getBytes());
                realOutputStream.flush();
                connectCallBack.onSuccess(device);
//                int ss = s2.length() / fixationByteSum;
//
//                int remainder = s2.length() % fixationByteSum;
//                if (remainder > 0) {
//                    ss += 1;
//                }
//                int initLocation = 0;
//                int slipToLocation = 0;
//                for (int i = 0; i < ss; i++) {
//
//                    if (remainder > 0 && i == ss - 1) {
//                        realOutputStream.write(s2.substring(initLocation, s2.length()).getBytes());
//                        return;
//                    }
//                    slipToLocation = initLocation + fixationByteSum;
//                    Log.e("LOGCAT", "initLocation==" + initLocation + "====slipToLocation" + slipToLocation);
//                    String substring = s2.substring(initLocation, slipToLocation);
//                    Log.e("LOGCAT", "substring==" + substring);
//                    realOutputStream.write(substring.getBytes());
//                    initLocation += fixationByteSum;
//
//                    try {
//                        Thread.sleep(15);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                outputStream = new BufferedOutputStream(realOutputStream);
            } catch (IOException e) {
                Log.e(TAG, "失败了重启连接"); // TODO: An UI weitergeben!
                connectToDevice(bluetoothDevicePackaging, connectCallBack);
                return;
            }

            // Sync senden
            //         sendSyncMessage();

        } catch (IOException e) {
            e.printStackTrace(); // TODO: An UI weitergeben!
            if (connectedSocket != null) {
                try {
                    connectedSocket.close();
                    connectedSocket = null;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            connectToDevice(bluetoothDevicePackaging, connectCallBack);
        } catch (NullPointerException e) {
            Log.e(TAG, "Nullreferenz-Ausnahmefehler!", e); // TODO: An UI weitergeben!
        }
        catch (Exception e){
            if (connectedSocket != null) {
                try {
                    connectedSocket.close();
                    connectedSocket = null;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            connectToDevice(bluetoothDevicePackaging, connectCallBack);
        }
    }

    /**
     * Trennt die Verbindung
     */

    public static synchronized void disconnect() {

        // Ausgabestream schließen
        if (outputStream != null) try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }
        outputStream = null;

        // Eingabestream schließen
        if (inputStream != null) try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }
        inputStream = null;

        // Eingabestream schließen
        if (connectedSocket != null) try {
            connectedSocket.getOutputStream().close();
            connectedSocket.getInputStream().close();
            connectedSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }
        connectedSocket = null;

        // Gerät freigeben
        connectedDevice = null;
    }

    /**
     * Sendet eine sync-Nachricht
     */
    private static synchronized String sendSyncMessage(String id) {


        // String md5_1 = "ID5056C00001|WHJIBLKEY39383171";
        String md5_1 = id + "|WHJIBLKEY39383171";
        String s = MD5Util.MD5(md5_1);
        String md5_2 = "OPEN0001|" + s;
        String s1 = MD5Util.MD5(md5_2);
        Log.e("LOGCAT", "OPEN0001|" + s1);
        return "OPEN0001|" + s1;

    }

    public static String toASi(String s) {//字符串转换为ASCII码

        StringBuffer stringBuffer = new StringBuffer();

        char[] chars = s.toCharArray(); //把字符中转换为字符数组
        for (int i = 0; i < chars.length; i++) {//输出结果

            stringBuffer.append((int) chars[i]);
        }
        return stringBuffer.toString();
    }

    public static String getAscii(String str) {
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c > 255) {
                sb.append("//u");
                j = (c >>> 8);
                tmp = Integer.toHexString(j);
                if (tmp.length() == 1)
                    sb.append("0");
                sb.append(tmp);
                j = (c & 0xFF);
                tmp = Integer.toHexString(j);
                if (tmp.length() == 1)
                    sb.append("0");
                sb.append(tmp);
            } else {
                sb.append(c);
            }

        }
        return (new String(sb));
    }

    /**
     * Gibt an, ob eine Bluetooth-Verbindung besteht
     *
     * @return <code>true</code>, wenn die Verbindung besteht
     */
    public static boolean isConnected() {
        return connectedSocket != null && outputStream != null;
    }

    /**
     * Sendet eine Nachricht an den Server
     *
     * @param message Die zu sendende Nachricht
     */
    public static synchronized void sendToTarget(@NotNull String message) {
        try {
            outputStream.write(message.getBytes());
            outputStream.write('\r');
            outputStream.write('\n');
            outputStream.flush();
        } catch (IOException e) {
        } catch (NullPointerException e) {
        }
    }

}
