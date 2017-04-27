package com.NUWC_ETJ.refreshnetwork;

import android.net.NetworkInfo;

import java.io.IOException;

/**
 * Created by Sidhwen on 3/13/2017.
 */

public interface DownloadReturn <String> {
    interface Progress {
        int ERROR = -1;
        int CONNECT_SUCCESS = 0;
        int GET_INPUT_STREAM_SUCCESS = 1;
        int PROCESS_INPUT_STREAM_IN_PROGRESS = 2;
        int PROCESS_INPUT_STREAM_SUCCESS = 3;
    }

    void updateFromDownload(java.lang.String DownloadResult) throws IOException;

    NetworkInfo getActiveNetworkInfo();

    void onProgressUpdate(int progressCode, int percentComplete);

    void finishDownloading();
}

