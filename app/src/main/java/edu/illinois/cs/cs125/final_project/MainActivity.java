package edu.illinois.cs.cs125.final_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

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
                EditText translateInput = (EditText)findViewById(R.id.Translate_Input);
                EditText numberInput = (EditText)findViewById(R.id.Translation_Number);
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

    private String translate(String userInput, int userNumber) {
        if (userNumber > 50) {
            userNumber = 50;
        }
        return userInput + userNumber;
    }

    private String percentCorrect(String userInput, String translateOutput) {
        char[] userChars = userInput.toCharArray();
        char[] translatedChars = translateOutput.toCharArray();
        char[] fixedUser = new char [Math.max(userChars.length, translatedChars.length)];
        char[] fixedTranslated = new char [Math.max(userChars.length, translatedChars.length)];
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
        for (int i = 0; i < fixedUser.length; i++) {
            Log.d(TAG, "User " + fixedUser[i]);
            Log.d(TAG, "Translated " + fixedTranslated[i]);
            if (fixedUser[i] == fixedTranslated[i]) {
                countCorrect++;
            }
        }
        double percentCorrect = (double)countCorrect / fixedUser.length * 100;
        DecimalFormat percent = new DecimalFormat("###.##");
        return percent.format(percentCorrect) + "% Match";
    }

}
