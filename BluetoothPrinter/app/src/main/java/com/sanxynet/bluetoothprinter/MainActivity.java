package com.sanxynet.bluetoothprinter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends Activity implements Runnable {

    protected static final String TAG = "MainActivity";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    Button mScan, mPrint;

    WebView webView;


    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;
    TextView stat;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stat = findViewById(R.id.bpstatus);
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://soft14.bdtask.com/bhojon23_latest/appv1/posorderdueinvoice/667");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        mScan = findViewById(R.id.Scan);
        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {

                if (mScan.getText().equals("Connect")) {
                    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter == null) {
                        Toast.makeText(MainActivity.this, "Bluetooth is Not Available", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!mBluetoothAdapter.isEnabled()) {
                            Intent enableBtIntent = new Intent(
                                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent,
                                    REQUEST_ENABLE_BT);
                        } else {
                            ListPairedDevices();
                            Intent connectIntent = new Intent(MainActivity.this,
                                    DeviceListActivity.class);
                            startActivityForResult(connectIntent,
                                    REQUEST_CONNECT_DEVICE);

                        }
                    }

                } else if (mScan.getText().equals("Disconnect")) {
                    if (mBluetoothAdapter != null)
                        mBluetoothAdapter.disable();
                    stat.setText("");
                    stat.setText("Disconnected");
                    stat.setTextColor(Color.rgb(199, 59, 59));
                    mPrint.setEnabled(false);
                    mScan.setEnabled(true);
                    mScan.setText("Connect");
                }
            }
        });


        mPrint = findViewById(R.id.mPrint);
        mPrint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {

                printFunction();

            }
        });

    }

    public void printFunction() {

        Thread t = new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {
                try {
                    OutputStream os = mBluetoothSocket.getOutputStream();
//                    String header = "";
//                    String he = "";
//                    String blank = "";
//                    String header2 = "";
//                    String BILL = "";
//                    String vio = "";
//                    String header3 = "";
//                    String mvdtail = "";
//                    String header4 = "";
//                    String offname = "";
//                    String time = "";
//                    String copy = "";
//                    String checktop_status = "";

//                    blank = "\n\n";
//                    he = "      EFULLTECH NIGERIA\n";
//                    he = he + "********************************\n\n";
//
//                    header = "FULL NAME:\n";
//                    BILL = fullName.getText().toString() + "\n";
//                    BILL = BILL
//                            + "================================\n";
//                    header2 = "COMPANY'S NAME:\n";
//                    vio = companyName.getText().toString() + "\n";
//                    vio = vio
//                            + "================================\n";
//                    header3 = "AGE:\n";
//                    mvdtail = age.getText().toString() + "\n";
//                    mvdtail = mvdtail
//                            + "================================\n";
//
//                    header4 = "AGENT DETAILS:\n";
//                    offname = agent_detail.getText().toString() + "\n";
//                    offname = offname
//                            + "--------------------------------\n";
//                    time = formattedDate + "\n\n";
//                    copy = "-Customer's Copy\n\n\n\n\n\n\n\n\n";


//                    os.write(blank.getBytes());
//                    os.write(he.getBytes());
//                    os.write(header.getBytes());
//                    os.write(BILL.getBytes());
//                    os.write(header2.getBytes());
//                    os.write(vio.getBytes());
//                    os.write(header3.getBytes());
//                    os.write(mvdtail.getBytes());
//                    os.write(header4.getBytes());
//                    os.write(offname.getBytes());
//                    os.write(checktop_status.getBytes());
//                    os.write(time.getBytes());
//                    os.write(copy.getBytes());

                    String address = "98 Green Road, Farmgate, Dhaka-1215";
                    String hot_line = "Hot Line: +8801676079239";
                    String line = "--------------------------------";
                    String status = "Walking";
                    String date ="Date:18/02/2021 12:44:37";
                    String bar ="Q Item       Size   Price  Total";
                    String item = "1 Idli-sumbal  1:2 400 Tk 400 Tk";
                    String sub_total = "  Subtotal                400 Tk";
                    String vat = "  Vat(10%)                 40 Tk";
                    String discount = "  Discount(0%)              0 Tk";
                    String sd = "  SD(4%)                   15 Tk";
                    String grand = "  Grand Total             455 Tk";
                    String due = "  Total Due               455 Tk";
                    String receipt_no = " Receipt No: 0667 | Order No.: 667 ";
                    String powered_by = " Powered By: BDTASK, www.bdtask.com";
                    String newline = "\n";

                    String img = String.valueOf(R.drawable.bhojon);

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bhojon);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] bitMapData = stream.toByteArray();

                    os.write(bitMapData);
                    os.write(newline.getBytes());

                    os.write(address.getBytes());
                    os.write(newline.getBytes());

                    os.write(hot_line.getBytes());
                    os.write(newline.getBytes());

                    os.write(line.getBytes());
                    os.write(newline.getBytes());

                    os.write(status.getBytes());
                    os.write(newline.getBytes());

                    os.write(date.getBytes());
                    os.write(newline.getBytes());

                    os.write(bar.getBytes());
                    os.write(newline.getBytes());

                    os.write(item.getBytes());
                    os.write(newline.getBytes());

                    os.write(line.getBytes());
                    os.write(newline.getBytes());

                    os.write(sub_total.getBytes());
                    os.write(newline.getBytes());


                    os.write(line.getBytes());
                    os.write(newline.getBytes());

                    os.write(vat.getBytes());
                    os.write(newline.getBytes());

                    os.write(discount.getBytes());
                    os.write(newline.getBytes());

                    os.write(line.getBytes());
                    os.write(newline.getBytes());

                    os.write(sd.getBytes());
                    os.write(newline.getBytes());

                    os.write(line.getBytes());
                    os.write(newline.getBytes());

                    os.write(grand.getBytes());
                    os.write(newline.getBytes());

                    os.write(line.getBytes());
                    os.write(newline.getBytes());

                    os.write(due.getBytes());
                    os.write(newline.getBytes());

                    os.write(line.getBytes());
                    os.write(newline.getBytes());

                    os.write(receipt_no.getBytes());
                    os.write(newline.getBytes());

                    os.write(powered_by.getBytes());
                    os.write(newline.getBytes());
                    os.write(newline.getBytes());
                    os.write(newline.getBytes());


                    // Setting height
                    int gs = 29;
                    os.write(intToByteArray(gs));
                    int h = 150;
                    os.write(intToByteArray(h));
                    int n = 170;
                    os.write(intToByteArray(n));

                    // Setting Width
                    int gs_width = 29;
                    os.write(intToByteArray(gs_width));
                    int w = 119;
                    os.write(intToByteArray(w));
                    int n_width = 2;
                    os.write(intToByteArray(n_width));


                } catch (Exception e) {
                    Log.e("PrintActivity", "Exe ", e);
                }
            }
        };
        t.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /* Terminate bluetooth connection and close all sockets opened */
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
    }


    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (mResultCode == Activity.RESULT_OK) {
                    Bundle mExtra = mDataIntent.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");
                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                    mBluetoothDevice = mBluetoothAdapter
                            .getRemoteDevice(mDeviceAddress);
                    mBluetoothConnectProgressDialog = ProgressDialog.show(this,
                            "Connecting...", mBluetoothDevice.getName() + " : "
                                    + mBluetoothDevice.getAddress(), true, false);
                    Thread mBlutoothConnectThread = new Thread(this);
                    mBlutoothConnectThread.start();
                    // pairToDevice(mBluetoothDevice); This method is replaced by
                    // progress dialog with thread
                }
                break;

            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
                    ListPairedDevices();
                    Intent connectIntent = new Intent(MainActivity.this,
                            DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(MainActivity.this, "Not connected to any device", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void ListPairedDevices() {
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
            }
        }
    }

    public void run() {
        try {
            mBluetoothSocket = mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
            return;
        }
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();

            stat.setText("");
            stat.setText("Connected");
            stat.setTextColor(Color.rgb(97, 170, 74));
            mPrint.setEnabled(true);
            mScan.setText("Disconnect");


        }
    };

    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
    }

}
