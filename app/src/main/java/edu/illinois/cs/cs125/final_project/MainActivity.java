package edu.illinois.cs.cs125.final_project;

import android.content.Intent;
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
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static int translateNumber = 1;

    private static RequestQueue requestQueue;

    public static double[] percents = new double[10];

    public static int percentsIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        final Button openFile = findViewById(R.id.Update_Button);
        openFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Update button clicked");
                EditText translateInput = (EditText) findViewById(R.id.Translate_Input);
                Tasks.text = translateInput.getText().toString();
                EditText numberInput = (EditText) findViewById(R.id.Translation_Number);
                String stringNumber = numberInput.getText().toString();
                Log.d(TAG, stringNumber);
                try {
                    translateNumber = Integer.parseInt(stringNumber);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new Tasks.TranslateTask(MainActivity.this, requestQueue).execute();
                setText();
            }
        });

        final Button grapher = (Button)findViewById(R.id.graph);

        grapher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AccuracyGrapher.class));
            }
        });


    }

    public  void setText() {
        EditText translateInput = (EditText) findViewById(R.id.Translate_Input);
        String output = Tasks.finalOutput;
        TextView translateOutput = findViewById(R.id.Translate_Output);
        TextView percentCorrect = findViewById(R.id.Percent_Correct);
        translateOutput.setText(output);
        DecimalFormat percent = new DecimalFormat("###.##");
        percentCorrect.setText(percent.format(percentCorrect(translateInput.getText().toString(), output)) + "% Match");
        translateOutput.setVisibility(View.VISIBLE);
        percentCorrect.setVisibility(View.VISIBLE);
    }

    /**
     * Function that takes as input the user's input and the output of the translate function and
     * determines how similar the input and output are, then rounds to 2 decimal places.
     *
     * @param userInput       The input the user enters into the app.
     * @param translateOutput The output of the translate function
     * @return A string of the percentage match of the two inputs.
     */
    public double percentCorrect(String userInput, String translateOutput) {
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
            //Log.d(TAG, "User " + fixedUser[i]);
            //Log.d(TAG, "Translated " + fixedTranslated[i]);
            if (fixedUser[i] == fixedTranslated[i]) {
                /*
                if (fixedUser[i] == ' ') {
                    continue;
                } else {
                    countCorrect++;
                }
                */
                countCorrect++;
            }
        }
        //Computing the percent and making it look nice.
        double percentCorrect = (double) countCorrect / fixedUser.length * 100;
        DecimalFormat percent = new DecimalFormat("###.##");
        percents[percentsIndex] = percentCorrect;
        Log.d(TAG, Arrays.toString(percents));
        percentsIndex++;
        return percentCorrect;
    }

}
