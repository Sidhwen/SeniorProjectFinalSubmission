package com.NUWC_ETJ.displays;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.database.sqlite.SQLiteDatabase;

import com.NUWC_ETJ.refreshnetwork.DownloadReturn;
import com.NUWC_ETJ.refreshnetwork.RefreshFragment;
import com.NUWC_ETJ.refreshnetwork.Refresher;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class NUWC_refresh extends AppCompatActivity implements DownloadReturn {

    public com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper myOpenHelper = new com.NUWC_ETJ.SQLite.AssetDatabaseOpenHelper(this);

    SQLiteDatabase myDB;
    //Interface variables
    public Button refresh_db;
    public Button back_RDB;
    public TextView refresh_TV;
    private int cnt1 = 0;
    Bundle myBundle = new Bundle();

    //Web Variables
    private Boolean Downloading = false;
    private RefreshFragment myRefreshFragment;

    public void refreshInit(){

        myDB = myOpenHelper.openDatabase();

        //links activity vars to design/xml manifest
        refresh_db = (Button)findViewById(R.id.refresh_db);
        back_RDB = (Button)findViewById(R.id.back_RDB);
        refresh_TV = (TextView)findViewById(R.id.refresh_textview);
        myBundle.putString("ID", getIntent().getExtras().getString("ID"));
        myBundle.putString("PIN", getIntent().getExtras().getString("PIN"));


        refresh_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload();
                cnt1++;
                refresh_TV.setText("Database Refreshing...");
            }
        });

        back_RDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(NUWC_refresh.this, NUWC_MAIN_Activity.class);
                myIntent.putExtras(myBundle);
                startActivity(myIntent);
            }
        });



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuwc_refresh);
        String PIN = getIntent().getExtras().getString("PIN");
        String ID = getIntent().getExtras().getString("ID");
        String WLAN_MAC = "";
        try {
            WLAN_MAC = GetMacAddress(this);
        }
        catch (SocketException e) { System.out.println(e.getMessage()); }
        myRefreshFragment = RefreshFragment.getInstance(getFragmentManager(), "http://ec2-54-172-214-171.compute-1.amazonaws.com:8080/METJ/MainServlet?WLAN=" + WLAN_MAC + "&Pass=" + PIN);
        refreshInit();
    }

    private void startDownload() {
        if (!Downloading && myRefreshFragment != null)
        {
            myRefreshFragment.startDownload();
            Downloading = true;
        }
    }

    @Override
    public void updateFromDownload(String DownloadResult) throws IOException
    {


        if (DownloadResult != null && !DownloadResult.equals("timeout"))
        {
        Refresher myRefresher = new Refresher(myDB, DownloadResult);
        myRefresher.flush();
            try {
                myRefresher.enter(refresh_TV);
            }
            catch (Exception e) { System.out.println(DownloadResult); }
        refresh_TV.setText("Refresh Complete!");
        }
        else
        {
            refresh_TV.setText("Refresh Failed");
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

                break;
            case Progress.CONNECT_SUCCESS:

                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:

                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:

                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:

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
        return "02:00:00:00:00:00";
    }


}
