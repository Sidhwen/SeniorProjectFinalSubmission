package com.NUWC_ETJ.displays;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import java.util.Collections;
import java.util.List;

public class WLAN_change extends AppCompatActivity implements DownloadReturn {

    private Button enterButton;
    private EditText ID_Text;
    private EditText Pin_Text;
    private TextView Change_Text;
    private TextView Debug_Text;
    private String PIN;
    private String ID;
    private String WLAN_MAC;

    private RefreshFragment myRefreshFragment;
    private Boolean Downloading = false;

    public void initializeWLANChange() {
        enterButton = (Button) findViewById(R.id.Enter);
        ID_Text = (EditText) findViewById(R.id.ID_Text);
        Pin_Text = (EditText) findViewById(R.id.PIN_TEXT);
        Change_Text = (TextView) findViewById(R.id.WLAN_Change_Text);
        Debug_Text = (TextView) findViewById(R.id.Debug_box);
        try {
            WLAN_MAC = GetMacAddress(this);
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        }

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PIN = Pin_Text.getText().toString();
                ID = ID_Text.getText().toString();
                Change_Text.setText("Sending information to server.");

                //After user has entered their information send it to the ChangeServlet to be used
                myRefreshFragment.updateURL("http://ec2-54-172-214-171.compute-1.amazonaws.com:8080/METJ/changeServlet?ID=" + ID + "&Pass=" + PIN + "&WLAN=" + WLAN_MAC);
                //Debug_Text.setText("http://ec2-54-172-214-171.compute-1.amazonaws.com:8080/METJ/changeServlet?ID=" + ID + "&Pass=" + PIN + "&WLAN=" + WLAN_MAC);
                startDownload();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wlan_change);

        myRefreshFragment = RefreshFragment.getInstance(getFragmentManager(), "http://ec2-54-172-214-171.compute-1.amazonaws.com:8080/METJ/changeServlet?ID=null&Pass=null&WLAN=null");

        initializeWLANChange();
    }

    //Function to get the device's WLAN_MAC address. Same as in NUWC_Sign_In.java
    public String GetMacAddress(Context context) throws SocketException {
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

    private void startDownload() {
        if (!Downloading && myRefreshFragment != null)
        {
            System.out.println("Starting Download");
            myRefreshFragment.startDownload();
            Downloading = true;
        }
    }

    @Override
    public void updateFromDownload(String DownloadResult) throws IOException {
        Intent myIntent = new Intent(this, NUWC_Sign_In.class);

        if(DownloadResult.trim().equals("SUCCESS")){
            Change_Text.setText("Successfully changed WLAN, going to Sign In page.");
            try{
                enterButton.setClickable(false);
                wait(500);
            }
            catch(Exception e){}
            startActivity(myIntent);
        }
        else if(DownloadResult.trim().equals("FAILURE")){
            Change_Text.setText("WLAN registration failed. Please try again.");
            //Empty the text inputs so the user can try again
            Pin_Text.setText("");
            ID_Text.setText("");
        }
        else if(DownloadResult.trim().contains("HTTP error code")){
            Change_Text.setText("Something went wrong with the server, please contact the system administrator.");
        }
        else if(DownloadResult.trim().equals("timeout") || DownloadResult.trim().equals("connect timed out")) {
            Change_Text.setText("Connection Timed Out");
        }
        else if(DownloadResult == null) {
            Change_Text.setText("Null result\n");
        }else if(DownloadResult.trim().equals("")) {
            Change_Text.setText("Empty String");
        }else{
            Change_Text.setText("Something went wrong, please try again.\n" + DownloadResult);
        }
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
                Change_Text.setText("ERROR");
                break;
            case Progress.CONNECT_SUCCESS:
                Change_Text.setText("Connected to server");
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                Change_Text.setText("Info Recieved");
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                Change_Text.setText("Info Processed...Storing");
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                Change_Text.setText("Info Processed...Storing");
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
}
