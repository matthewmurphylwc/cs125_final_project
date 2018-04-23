package edu.illinois.cs.cs125.final_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DecimalFormat;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button openFile = findViewById(R.id.Update_Button);
        openFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Update button clicked");
                EditText translateInput = (EditText) findViewById(R.id.Translate_Input);
                EditText numberInput = (EditText) findViewById(R.id.Translation_Number);
                String stringNumber = numberInput.getText().toString();
                int translateNumber = Integer.parseInt(stringNumber);
                TextView translateOutput = findViewById(R.id.Translate_Output);
                TextView percentCorrect = findViewById(R.id.Percent_Correct);
                translateOutput.setText(translate(translateInput.getText().toString(), translateNumber));
                percentCorrect.setText(percentCorrect(translateInput.getText().toString(), translate(translateInput.getText().toString(), translateNumber)));
                translateOutput.setVisibility(View.VISIBLE);
                percentCorrect.setVisibility(View.VISIBLE);

            }
        });


    }

    /**
     * IDK man

    public void startAPICall() {
        try {
            final JSONObject jsonBody = new JSONObject("{\"INPUT\":\"hello world\"}");
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    " HTTP://API.SHOUTCLOUD.IO/V1/SHOUT",
                    jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d(TAG, response.toString());
                            TextView jsonView = findViewById(R.id.jsonResult);
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            String jsonString = gson.toJson(response);
                            jsonView.setText(jsonString);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, error.toString());
                    Toast.makeText(getApplicationContext(), "Problem accessing API",
                            Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     */


    /**
     * Base function for doing translations.
     *
     * @param userInput  Whatever the user types in.
     * @param userNumber How many translations the user wants to iterate through (should be limited)
     * @return The string, in English, after it has been translated through a series of languages.
     */
    private String translate(String userInput, int userNumber) {
        /*Not sure if 50 is the right number but we should have a cap on the number of translations
        the user can iterate through in one attempt both so we don't burn our API key's limit and so
        the app doesn't take years to respond to a request.
        */
        if (userNumber > 50) {
            userNumber = 50;
        }

        return userInput + userNumber;
    }

    /**
     * Function that takes as input the user's input and the output of the translate function and
     * determines how similar the input and output are, then rounds to 2 decimal places.
     *
     * @param userInput       The input the user enters into the app.
     * @param translateOutput The output of the translate function
     * @return A string of the percentage match of the two inputs.
     */
    private String percentCorrect(String userInput, String translateOutput) {
        //Set up for calculating percent correct
        char[] userChars = userInput.toCharArray();
        char[] translatedChars = translateOutput.toCharArray();
        char[] fixedUser = new char[Math.max(userChars.length, translatedChars.length)];
        char[] fixedTranslated = new char[Math.max(userChars.length, translatedChars.length)];
        for (int i = 0; i < fixedUser.length; i++) {
            if (i < userChars.length) {
                fixedUser[i] = userChars[i];
            } else {
                fixedUser[i] = ' ';
            }
            if (i < translatedChars.length) {
                fixedTranslated[i] = translatedChars[i];
            } else {
                fixedTranslated[i] = ' ';
            }
        }
        int countCorrect = 0;
        //Loop that actually compares the two character arrays.
        for (int i = 0; i < fixedUser.length; i++) {
            Log.d(TAG, "User " + fixedUser[i]);
            Log.d(TAG, "Translated " + fixedTranslated[i]);
            if (fixedUser[i] == fixedTranslated[i]) {
                countCorrect++;
            }
        }
        //Computing the percent and making it look nice.
        double percentCorrect = (double) countCorrect / fixedUser.length * 100;
        DecimalFormat percent = new DecimalFormat("###.##");
        return percent.format(percentCorrect) + "% Match";
    }

}
