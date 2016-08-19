package a1301917.at.ac.univie.hci.devicescanner;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

// TODO: 19.08.2016 App fragt zweimal wegen Aktivierung, jedoch kann nur einmal gewaehlt werden
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
            String message = "Device does not support Bluetooth";
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.show();
        }else{
            if(!mBluetoothAdapter.isEnabled()){
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, getResources().getInteger(R.integer.REQUEST_ENABLE_BT));
            }
        }
    }
}
