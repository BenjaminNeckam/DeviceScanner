package a1301917.at.ac.univie.hci.devicescanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

// TODO: 24.08.2016 Bluetooth-Switch zeigt zu beginn immer off an, auch wenn angeschalten
public class MainActivity extends AppCompatActivity {
    private BluetoothAdapter mBluetoothAdapter;
    private Switch bluetoothSwitch;
    private ArrayList<String> bluetoothDeviceArray;
    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        String message = "Bluetooth off";
                        Context bluetoothContext = getApplicationContext();
                        Toast toast = Toast.makeText(bluetoothContext, message, Toast.LENGTH_LONG);
                        toast.show();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        String message1 = "Bluetooth turning off";
                        Context bluetoothContext1 = getApplicationContext();
                        Toast toast1 = Toast.makeText(bluetoothContext1, message1, Toast.LENGTH_LONG);
                        toast1.show();
                        break;
                    case BluetoothAdapter.STATE_ON:
                        String message2 = "Bluetooth on";
                        Context bluetoothContext2 = getApplicationContext();
                        Toast toast2 = Toast.makeText(bluetoothContext2, message2, Toast.LENGTH_LONG);
                        toast2.show();
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        String message3 = "Bluetooth turning on";
                        Context bluetoothContext3 = getApplicationContext();
                        Toast toast3 = Toast.makeText(bluetoothContext3, message3, Toast.LENGTH_LONG);
                        toast3.show();
                        break;
                }
            }
        }
    };
    private final BroadcastReceiver bluetoothDeviceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device.getName().equals(null)){
                    bluetoothDeviceArray.add("unknown" + "\n" + device.getAddress());
                }else{
                    bluetoothDeviceArray.add(device.getName() + "\n" + device.getAddress());
                }
            }
            for(String device:bluetoothDeviceArray){
                System.out.println("Device: " + device);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(bluetoothReceiver, filter);
        IntentFilter filterBD = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(bluetoothDeviceReceiver,filterBD);
        bluetoothSwitch = (Switch)findViewById(R.id.bluetoothOnOff);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
            String message = "Device does not support Bluetooth";
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.show();
            bluetoothSwitch.setChecked(false);
        /*}else{
            if(!mBluetoothAdapter.isEnabled()){
               Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
               startActivityForResult(enableBtIntent, getResources().getInteger(R.integer.REQUEST_ENABLE_BT));
            }
            if(mBluetoothAdapter.isEnabled()){
                bluetoothSwitch.setChecked(true);
            }else{
                bluetoothSwitch.setChecked(false);
            }*/
        }

    }


    public void onBluetoothSwitchClick(View view){
        if(mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.disable();
            bluetoothSwitch.setChecked(false);
        }else{
            mBluetoothAdapter.enable();
            bluetoothSwitch.setChecked(true);
        }
    }

    public void scanBluetoothOnClick(View view){
        mBluetoothAdapter.startDiscovery();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(bluetoothReceiver);
        unregisterReceiver(bluetoothDeviceReceiver);
    }
}
