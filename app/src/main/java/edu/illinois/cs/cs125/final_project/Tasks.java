package edu.illinois.cs.cs125.final_project;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class Tasks {

    private static final String TAG = "Tasks";

    static String subscriptionKey = "3de8331d91a6458dbe657851e5c1beb2";

    static String host = "https://api.microsofttranslator.com";
    static String path = "/V2/Http.svc/Translate";

    static String target = "fr";
    static String text = "Hello";

    static String translated = "";

    static class TranslateTask extends AsyncTask<String, Integer, Integer> {

        private WeakReference<MainActivity> activityReference;

        private RequestQueue requestQueue;


        TranslateTask(final MainActivity context, final RequestQueue setRequestQueue) {
            activityReference = new WeakReference<>(context);
            requestQueue = setRequestQueue;
        }

        @Override
        protected Integer doInBackground(final String... userInput){
            try {
                final MainActivity activity = activityReference.get();
                if (activity == null || activity.isFinishing()) {
                    return 0;
                }
                String encoded_query = URLEncoder.encode(text, "UTF-8");
                String params = "?to=" + target + "&text=" + text;
                Log.d(TAG, host + path + params);
                URL url = new URL(host + path + params);

                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                Log.d(TAG, subscriptionKey);
                try {
                    connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
                } catch (Exception e) {
                    Log.d(TAG, "Key/token failure");
                }
                connection.setDoOutput(true);

                StringBuilder response = new StringBuilder();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();
                String stringResponse = response.toString();
                translated = stringResponse;
                Log.d(TAG, translated);
                return 0;
            } catch (Exception e) {
                return 0;
            }
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Long result) {
            //showDialog("Downloaded " + result + " bytes");
        }
    }

}
