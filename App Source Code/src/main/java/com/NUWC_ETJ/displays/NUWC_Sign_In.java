package com.NUWC_ETJ.displays;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.NUWC_ETJ.refreshnetwork.DownloadReturn;
import com.NUWC_ETJ.refreshnetwork.RefreshFragment;
import com.NUWC_ETJ.refreshnetwork.Refresher;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;


public class NUWC_Sign_In extends AppCompatActivity implements DownloadReturn {

    public com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper myOpenHelper = new com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper(this);

    SQLiteDatabase myDB;


    private Button Sign_In;
    private TextView Sign_In_Text;
    private EditText PIN_Text;

    private String ID;
    private String PIN;
    private String WLAN_MAC;

    //Web Variables
    private Boolean Downloading = false;
    private RefreshFragment myRefreshFragment;


    public void init2(){

        myDB = myOpenHelper.openDatabase();


        try {
            WLAN_MAC = GetMacAddress(this);
        }
        catch (SocketException e) { System.out.println(e.getMessage()); }
        System.out.println("WLAN_MAC : " + WLAN_MAC);



        Sign_In_Text = (TextView) findViewById(R.id.Sign_In_Text);

        PIN_Text = (EditText) findViewById(R.id.PIN_TEXT);


        Sign_In = (Button) findViewById(R.id.Sign_In);
        Sign_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PIN = PIN_Text.getText().toString();
                Sign_In_Text.setText("Logging in...");
                System.out.println("WLAN: " + WLAN_MAC);
                myRefreshFragment.updateURL("http://ec2-54-172-214-171.compute-1.amazonaws.com:8080/METJ/MainServlet?WLAN="+WLAN_MAC+"&Pass=" + PIN);
                startDownload();

            }
            });


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuwc_sign_in);

        myRefreshFragment = RefreshFragment.getInstance(getFragmentManager(), "http://ec2-54-172-214-171.compute-1.amazonaws.com:8080/METJ/MainServlet?DOD_EDI_PI=null&Pass=null");

        init2();
    }


    private void startDownload() {
        if (!Downloading && myRefreshFragment != null)
        {
            System.out.println("Starting Download");
            myRefreshFragment.startDownload();
            Downloading = true;
        }
    }

    @Override
    public void updateFromDownload(String DownloadResult) throws IOException
    {

        Intent myIntent = new Intent(NUWC_Sign_In.this, NUWC_MAIN_Activity.class);
        Bundle myBundle = new Bundle();

        if (DownloadResult.trim().equals("connect timed out"))
        {
            Sign_In_Text.setText("Attempting Offline Access");
            System.out.println("Initiating Offline Access");
            String QueryTable = "LOGIN";
            String QuerySelection = "PIN = '" + PIN + "' AND WLAN = '" + WLAN_MAC + "'";
            String[] QueryColumns = {"DOD_EDI_PI"};
            Cursor myCursor = myDB.query(QueryTable, QueryColumns, QuerySelection, null, null, null, null);

            if (myCursor.getCount() > 0)
            {
                myCursor.moveToFirst();
                ID = myCursor.getString(0);
                myBundle.putString("ID", ID);
                myBundle.putString("PIN", PIN);
                myIntent.putExtras(myBundle);
                Sign_In_Text.setText("Entering Main Activity");

                startActivity(myIntent);
            }
        }

        else if(DownloadResult.trim().equals("WLAN_NOT_FOUND")){
            myIntent = new Intent(NUWC_Sign_In.this, WLAN_change.class);
            startActivity(myIntent);
        }

        else if(DownloadResult.trim().equals("INVALID_WLAN_PIN")) {
            Sign_In_Text.setText("Incorrect PIN, please try again.");
        }

        else if(DownloadResult.trim().contains("HTTP error code")){
            Sign_In_Text.setText(("There has been a problem with the server.\n\nContact the System Administrator."));
        }

        else if(DownloadResult.trim().equals("timeout")) {
            Sign_In_Text.setText("Something timed out. Please try again later.");
        }

        else if(DownloadResult == null){
            Sign_In_Text.setText("Something went wrong, please try again.");
        }

        else {
            System.out.println("initiating online access");
            Sign_In_Text.setText("Refreshing the Database.");

            Refresher myRefresher = new Refresher(myDB, DownloadResult);
            Sign_In_Text.setText("Download Done");
            myRefresher.flush();
            try {
                ID = myRefresher.enter(Sign_In_Text);
            }
            catch (Exception e) { System.out.println(DownloadResult); }
            myBundle.putString("ID", ID);
            myBundle.putString("PIN", PIN);
            myIntent.putExtras(myBundle);
            Sign_In_Text.setText("Entering Main Activity");
            myDB.execSQL("DELETE FROM LOGIN");
            myDB.execSQL("VACUUM");
            myDB.execSQL("INSERT INTO LOGIN (DOD_EDI_PI, PIN, WLAN) VALUES ('" + ID + "','" + PIN + "','" + WLAN_MAC + "')");

            startActivity(myIntent);
        }
        //handle updates from after download finishes
    }


    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch(progressCode) {
            // You can add UI behavior for progress updates here.
            case Progress.ERROR:
                System.out.println("error");

                Sign_In_Text.setText("Logging in...ERROR");
                break;
            case Progress.CONNECT_SUCCESS:
                System.out.println("connect success");
                Sign_In_Text.setText("Logging in...Connected");
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                System.out.println("input stream success");

                Sign_In_Text.setText("Logging in...Info Recieved");
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                System.out.println("input stream in progress");

                Sign_In_Text.setText("Logging in...Info Processed...Storing");
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                System.out.println("input stream processed");

                Sign_In_Text.setText("Logging in...Info Processed...Storing");
                break;
        }
    }

    @Override
    public void finishDownloading() {
        Downloading = false;
        if (myRefreshFragment != null) {
            myRefreshFragment.cancelDownload();
        }
    }

    public String GetMacAddress(Context context) throws SocketException
    {


            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface nif : interfaces)
            {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        System.out.println("Network Interface Not Found : Returning Default");
    return "02:00:00:00:00:00";
}

    private byte[] getHash(String toBeHashed){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        digest.reset();
        return digest.digest(toBeHashed.getBytes());
    }

    private String binary2Hex(byte[] data) {
        return String.format("%0" + (data.length*2) + "X", new java.math.BigInteger(1, data));
    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Exit out of the Mobile ETJ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int whichButton){
                        moveTaskToBack(true);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null).show();
    }

}
