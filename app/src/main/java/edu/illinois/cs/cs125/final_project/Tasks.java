package edu.illinois.cs.cs125.final_project;

import android.net.Uri;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.lang.ref.WeakReference;
import java.net.*;
import java.text.DecimalFormat;
import java.util.*;


public class Tasks {

    private static final String TAG = "Tasks";

    static String subscriptionKey = "3de8331d91a6458dbe657851e5c1beb2";

    static String host = "https://api.microsofttranslator.com";
    static String path = "/V2/Http.svc/Translate";

    static String target = "de";
    static String text = "Hello";

    static String translated = "";
    static String finalOutput = "";

    static class TranslateTask extends AsyncTask<String, Integer, Integer> {

        private WeakReference<MainActivity> activityReference;

        private RequestQueue requestQueue;


        TranslateTask(final MainActivity context, final RequestQueue setRequestQueue) {
            activityReference = new WeakReference<>(context);
            requestQueue = setRequestQueue;
        }

        @Override
        protected Integer doInBackground(final String... userInput) {
            if (MainActivity.translateNumber > 50) {
                MainActivity.translateNumber = 50;
            }
            String[] languages = new String[]{"af", "ar", "bn", "bs", "bg", "yue", "ca", "zh-Hans", "zh-Hant", "hr", "cs", "da", "nl", "et", "fj", "fil", "fi", "fr", "de", "el", "ht",
                    "he", "hi", "mww", "hu", "id", "it", "ja", "sw", "ko", "lv", "lt", "mg", "ms", "mt", "nb", "fa", "pl", "pt", "otq", "ro", "ru", "sm", "sr-Cryl", "sr-Latn",
                    "sk", "sl", "es", "sv", "ty", "ta", "th", "to", "tr", "uk", "ur", "vi", "cy", "yua", "af", "ar", "bn", "bs", "bg", "yue", "ca",
                    "zh-Hans", "zh-Hant", "hr", "cs", "da", "nl", "et", "fj", "fil", "fi", "fr", "de", "el", "ht", "sk", "sl", "es", "sv", "ty", "ta", "th",
                    "to", "tr", "uk", "ur", "vi", "cy", "yua", "af", "ar", "bn", "bs", "bg", "yue"};
            for (int i = 0; i < MainActivity.translateNumber; i++) {
                double random = Math.random() * 100;
                int languageSelector = (int) random;
                Log.d(TAG, "Random number was: " + Integer.toString(languageSelector));
                target = languages[languageSelector];
                try {
                    Log.d("Tasks", "Calling in loop");
                    try {
                        final MainActivity activity = activityReference.get();
                        if (activity == null || activity.isFinishing()) {
                            return 0;
                        }
                        String params = "?to=" + target + "&text=" + text;
                        String requestURL = Uri.parse(host + path + params).buildUpon().appendQueryParameter("Ocp-Apim-Subscription-Key", subscriptionKey).build().toString();
                        Log.d(TAG, "Using url: " + requestURL);
                        StringRequest stringRequest = new StringRequest(
                                Request.Method.GET, requestURL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(final String response) {
                                        // On success, clear the progress bar and call finishProcessImage
                                        Log.d(TAG, "Response: " + response);
                                        MainActivity activity = activityReference.get();
                                        if (activity == null || activity.isFinishing()) {
                                            return;
                                        }
                                        translated = response;
                                        String translatedText = Tasks.translated;
                                        String noXMLTranslation = translatedText.substring(translatedText.indexOf('>') + 1, translatedText.lastIndexOf('<'));
                                        Log.d(TAG, "No XML: " + noXMLTranslation);
                                        Tasks.text = noXMLTranslation;
                                        Tasks.finalOutput = noXMLTranslation;
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(final VolleyError error) {
                                // On failure just clear the progress bar
                                Log.w(TAG, "Error: " + error.toString());
                                NetworkResponse networkResponse = error.networkResponse;
                                if (networkResponse != null &&
                                        networkResponse.statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                                    Log.w(TAG, "Unauthorized request. "
                                            + "Make sure you added your API_KEY to app/secrets.properties");
                                }
                                MainActivity activity = activityReference.get();
                                if (activity == null || activity.isFinishing()) {
                                    return;
                                }
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() {
                                // Set up headers properly
                                Map<String, String> headers = new HashMap<>();
                                headers.put("Content-Type", "application/octet-stream");
                                headers.put("Ocp-Apim-Subscription-Key", subscriptionKey);
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                // Set the body content type properly for a binary upload
                                return "application/octet-stream";
                            }
                        };
                        requestQueue.add(stringRequest);
                        Thread.sleep(3000);
                    } catch (Exception e) {

                    }
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                    String translatedText = "Failed to Translate";
                    //break;
                }
                Log.d(TAG, "At this point the translation is: " + Tasks.finalOutput);
                //}
                Log.d(TAG, "This should only print once");

            }
            try {
                target = "en";
                Log.d("Tasks", "Calling English correctly");
                try {
                    final MainActivity activity = activityReference.get();
                    if (activity == null || activity.isFinishing()) {
                        return 0;
                    }
                    String params = "?to=" + target + "&text=" + text;
                    String requestURL = Uri.parse(host + path + params).buildUpon().appendQueryParameter("Ocp-Apim-Subscription-Key", subscriptionKey).build().toString();
                    Log.d(TAG, "Using url: " + requestURL);
                    StringRequest stringRequest = new StringRequest(
                            Request.Method.GET, requestURL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(final String response) {
                                    // On success, clear the progress bar and call finishProcessImage
                                    Log.d(TAG, "Response: " + response);
                                    MainActivity activity = activityReference.get();
                                    if (activity == null || activity.isFinishing()) {
                                        return;
                                    }
                                    translated = response;
                                    String translatedText = Tasks.translated;
                                    String noXMLTranslation = translatedText.substring(translatedText.indexOf('>') + 1, translatedText.lastIndexOf('<'));
                                    Log.d(TAG, "No XML: " + noXMLTranslation);
                                    Tasks.text = noXMLTranslation;
                                    Tasks.finalOutput = noXMLTranslation;
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(final VolleyError error) {
                            // On failure just clear the progress bar
                            Log.w(TAG, "Error: " + error.toString());
                            NetworkResponse networkResponse = error.networkResponse;
                            if (networkResponse != null &&
                                    networkResponse.statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                                Log.w(TAG, "Unauthorized request. "
                                        + "Make sure you added your API_KEY to app/secrets.properties");
                            }
                            MainActivity activity = activityReference.get();
                            if (activity == null || activity.isFinishing()) {
                                return;
                            }
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() {
                            // Set up headers properly
                            Map<String, String> headers = new HashMap<>();
                            headers.put("Content-Type", "application/octet-stream");
                            headers.put("Ocp-Apim-Subscription-Key", subscriptionKey);
                            return headers;
                        }

                        @Override
                        public String getBodyContentType() {
                            // Set the body content type properly for a binary upload
                            return "application/octet-stream";
                        }
                    };
                    requestQueue.add(stringRequest);
                    Thread.sleep(3000);
                } catch (Exception e) {

                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
                String translatedText = "Failed to Translate to English";
            }
            Log.d("Tasks", "The final output was: " + Tasks.finalOutput);
            return 0;
        }



            protected void onProgressUpdate (Integer...progress){
                //setProgressPercent(progress[0]);
            }

            protected void onPostExecute (Integer result) {
                MainActivity activity = activityReference.get();
                if (activity == null || activity.isFinishing()) {
                    return;
                }
                TextView translateOutput = activity.findViewById(R.id.Translate_Output);
                translateOutput.setText(finalOutput);
                translateOutput.setVisibility(View.VISIBLE);

                EditText translateInput = (EditText) activity.findViewById(R.id.Translate_Input);
                TextView percentCorrect = activity.findViewById(R.id.Percent_Correct);
                DecimalFormat percent = new DecimalFormat("###.##");
                percentCorrect.setText(percent.format(activity.percentCorrect(translateInput.getText().toString(), finalOutput)) + "% Match");
                percentCorrect.setVisibility(View.VISIBLE);
            }

    }
}
