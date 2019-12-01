package com.android.guessnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    private TextView tvInfo;
    private EditText etInput;
    private Button bControl;
    private int number;
    private boolean isOver;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = findViewById(R.id.textView);
        etInput = findViewById(R.id.editText);
        bControl = findViewById(R.id.button);
        builder = new AlertDialog.Builder(MainActivity.this);

        newGame();
    }

    private void newGame()
    {
        Random r = new Random();
        int low = 1;
        int high = 10; // 200, 10 to test
        number = r.nextInt(high - low) + low;
        isOver = false;
    }

    public void enterButton(View view) {
        if (etInput.getText().toString().equals(""))
        {
            Log.i(LOG_TAG, String.format("Empty input"));

            builder.setTitle(R.string.error)
                    .setMessage(R.string.empty)
                    .setCancelable(false)
                    .setNegativeButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else {

            try {
                int temp = Integer.parseInt(etInput.getText().toString());

                if (temp < 1 || temp > 10) {
                    builder.setTitle(R.string.error)
                            .setMessage(R.string.value_borders)
                            .setCancelable(false)
                            .setNegativeButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                    etInput.setText("");
                } else {
                    if (temp > number) {
                        tvInfo.setText(getResources().getString(R.string.ahead));
                    } else if (temp < number) {
                        tvInfo.setText(getResources().getString(R.string.behind));
                    } else if (temp == number) {
                        tvInfo.setText(getResources().getString(R.string.hit));
                        isOver = true;
                        newGame();
                        etInput.setText("");
                    }
                }
            } catch (NumberFormatException e) {
                builder.setTitle(R.string.error)
                        .setMessage(R.string.value_not_int)
                        .setCancelable(false)
                        .setNegativeButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();

                etInput.setText("");
            }
        }

    }
}
