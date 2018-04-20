package edu.illinois.cs.cs125.final_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                Log.d(TAG, "Open file button clicked");
                EditText translateInput = (EditText)findViewById(R.id.Translate_Input);
                EditText numberInput = (EditText)findViewById(R.id.Translation_Number);
                String stringNumber = numberInput.getText().toString();
                int translateNumber = Integer.parseInt(stringNumber);
                TextView translateOutput = findViewById(R.id.Translate_Output);
                translateOutput.setText(translate(translateInput.getText().toString(), translateNumber));
                translateOutput.setVisibility(View.VISIBLE);

            }
        });


    }

    private String translate(String userInput, int userNumber) {
        return userInput + userNumber;
    }

}
