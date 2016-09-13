package com.guma.desarrollo.salesumk.Activity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.lvrenyang.myprinter.WorkService;
import com.lvrenyang.myprinter.Global;
import com.guma.desarrollo.salesumk.R;



public class ListaBTActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    TextView tblth;
    private ProgressDialog dialog;
    private static ListView listView;
    public static final String ICON = "ICON";
    public static final String PRINTERNAME = "PRINTERNAME";
    public static final String PRINTERMAC = "PRINTERMAC";
    private static List<Map<String, Object>> boundedPrinters;
    private static Handler mHandler = null;
    private static String TAG = "ConnectBTMacActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_bt);
        dialog = new ProgressDialog(this);
        boundedPrinters = getBoundedPrinters();
        InitGlobalString();

        new Handler().postDelayed(new Runnable() {

            public void run() {
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                if (null != adapter) {
                    if (!adapter.isEnabled()) {
                        if (adapter.enable()) {
                            // while(!adapter.isEnabled());
                        } else {
                            finish();
                            return;
                        }
                    }
                }
            }

        }, 1000);


        listView = (ListView) findViewById(R.id.listViewSettingConnect);
        listView.setAdapter(new SimpleAdapter(this, boundedPrinters,
                R.layout.list_item_printer, new String[] { ICON,
                PRINTERNAME, PRINTERMAC }, new int[] {
                R.id.btListItemPrinterIcon, R.id.tvListItemPrinterName,
                R.id.tvListItemPrinterMac }));
        listView.setOnItemClickListener(this);

        mHandler = new MHandler(this);
        WorkService.addHandler(mHandler);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        WorkService.delHandler(mHandler);
        mHandler = null;
    }

    private List<Map<String, Object>> getBoundedPrinters() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            return list;
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(ICON, android.R.drawable.stat_sys_data_bluetooth);
                map.put(PRINTERNAME, device.getName());
                map.put(PRINTERMAC, device.getAddress());
                list.add(map);
            }
        }
        return list;
    }
    private void InitGlobalString() {
        Global.toast_success = getString(R.string.toast_success);
        Global.toast_fail = getString(R.string.toast_fail);
        Global.toast_notconnect = getString(R.string.toast_notconnect);
        Global.toast_usbpermit = getString(R.string.toast_usbpermit);
        if (null == WorkService.workThread) {
            Intent intent = new Intent(this, WorkService.class);
            startService(intent);
        }


    }
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,long id) {
        String address = (String) boundedPrinters.get(position).get(PRINTERMAC);
        dialog.setMessage(Global.toast_connecting + " " + address);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        WorkService.workThread.connectBt(address);

    }

    static class MHandler extends Handler {

        WeakReference<ListaBTActivity> mActivity;

        MHandler(ListaBTActivity activity) {
            mActivity = new WeakReference<ListaBTActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ListaBTActivity theActivity = mActivity.get();
            switch (msg.what) {
                case Global.MSG_WORKTHREAD_SEND_CONNECTBTRESULT: {
                    //Toast.makeText(theActivity,, Toast.LENGTH_SHORT).show();
                    theActivity.dialog.cancel();
                    theActivity.getIntent().putExtra("Resul", msg.arg1);
                    theActivity.finish();
                    break;
                }

            }
        }
    }
}
