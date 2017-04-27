package com.NUWC_ETJ.refreshnetwork;

/**
 * Created by Sidhwen on 3/13/2017.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class RefreshFragment extends Fragment {

    private String myURLString;
    private DownloadReturn myDownloadReturn;
    private DownloadTask myDownloadTask;

    public static RefreshFragment getInstance (FragmentManager fragmentManager, String URL)
    {
        RefreshFragment myRefreshFragment = new RefreshFragment();
        Bundle args = new Bundle();
        args.putString("UrlKey", URL);
        myRefreshFragment.setArguments(args);
        fragmentManager.beginTransaction().add(myRefreshFragment, "RefreshFragment").commit();
        return myRefreshFragment;
    }

    public void updateURL(String newURL)
    {
        myURLString = newURL;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        myURLString = getArguments().getString("UrlKey");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Host Activity will handle callbacks from task.
        myDownloadReturn = (DownloadReturn) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Clear reference to host Activity to avoid memory leak.
        myDownloadReturn = null;
    }

    @Override
    public void onDestroy() {
        // Cancel task when Fragment is destroyed.
        cancelDownload();
        super.onDestroy();
    }

    /**
     * Start non-blocking execution of DownloadTask.
     */
    public void startDownload() {
        cancelDownload();
        myDownloadTask = new DownloadTask(myDownloadReturn);
        myDownloadTask.execute(myURLString);
    }

    /**
     * Cancel (and interrupt if necessary) any ongoing DownloadTask execution.
     */
    public void cancelDownload() {
        if (myDownloadTask != null) {
            myDownloadTask.cancel(true);
        }
    }

    private class DownloadTask extends AsyncTask<String, Void, DownloadTask.Result> {

        private DownloadReturn<String> myReturn;

        DownloadTask(DownloadReturn<String> callback) {
            setCallback(callback);
        }

        void setCallback(DownloadReturn<String> callback) {
            myReturn = callback;
        }

        class Result {
            public String myResultValue;
            public Exception myException;

            public Result(String resultValue) {
                myResultValue = resultValue;
            }

            public Result(Exception exception) {
                myException = exception;
            }
        }

        @Override
        protected void onPreExecute() {
            if (myReturn != null) {
                NetworkInfo networkInfo = myReturn.getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnected() ||
                        (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                                && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                    // If no connectivity, cancel task and update Callback with null data.
                    try {
                        myReturn.updateFromDownload("Connection Error");
                    }
                    catch (IOException e) {}
                    cancel(true);
                }
            }
        }

        /**
         * Defines work to perform on the background thread.
         */
        @Override
        protected DownloadTask.Result doInBackground(String... urls) {
            Result result = null;
            if (!isCancelled() && urls != null && urls.length > 0) {
                String urlString = urls[0];
                try {
                    URL url = new URL(urlString);
                    String resultString = downloadUrl(url);
                        result = new Result(resultString);
                } catch (Exception e) {
                    result = new Result(e);
                }
            }
            return result;
        }

        /**
         * Updates the DownloadCallback with the result.
         */
        @Override
        protected void onPostExecute(Result result) {
            if (result != null && myReturn != null) {
                try {
                    if (result.myException != null) {

                        myReturn.updateFromDownload("Connection Error");
                    } else if (result.myResultValue != null) {

                        myReturn.updateFromDownload(result.myResultValue);
                    }
                }
                catch (IOException e) { System.out.println("I/O Exception : " + e.getMessage()); }
                myReturn.finishDownloading();
            }

        }

        /**
         * Override to add special behavior for cancelled AsyncTask.
         */
        @Override
        protected void onCancelled(Result result) {
        }


        private String downloadUrl(URL url) throws IOException {
            InputStream stream = null;
            HttpURLConnection connection = null;
            String result = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                // Timeout for reading InputStream arbitrarily set to 2000ms.
                connection.setReadTimeout(2000);
                // Timeout for connection.connect() arbitrarily set to 2000ms.
                connection.setConnectTimeout(2000);
                // For this use case, set HTTP method to GET.
                connection.setRequestMethod("GET");
                // Already true by default but setting just in case; needs to be true since this request
                // is carrying an input (response) body.
                connection.setDoInput(true);
                // Open communications link (network traffic occurs here).
                System.out.println("Attempting to connect");
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }

                // Retrieve the response body as an InputStream.
                stream = connection.getInputStream();
                if (stream != null) {
                    System.out.println("Reading Stream");

                    // Converts Stream to String with max length of 100000.
                    result = readStream(stream, 100000);
                }

            } finally {
                // Close Stream and disconnect HTTPS connection.
                if (stream != null) {
                    stream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }
        private String readStream(InputStream stream, int maxLength) throws IOException {
            String result = null;
            // Read InputStream using the UTF-8 charset.
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            // Create temporary buffer to hold Stream data with specified max length.
            char[] buffer = new char[maxLength];
            // Populate temporary buffer with Stream data.
            int numChars = 0;
            int readSize = 0;
            while (numChars < maxLength && readSize != -1) {
                numChars += readSize;
                int pct = (100 * numChars) / maxLength;
                readSize = reader.read(buffer, numChars, buffer.length - numChars);
            }
            if (numChars != -1) {
                // The stream was not empty.
                // Create String that is actual length of response body if actual length was less than
                // max length.
                numChars = Math.min(numChars, maxLength);
                result = new String(buffer, 0, numChars);
                System.out.println("Stream not empty");
            }


            return result;
        }
    }

}
