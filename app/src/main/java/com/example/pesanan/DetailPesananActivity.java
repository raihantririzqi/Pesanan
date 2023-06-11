package com.example.pesanan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailPesananActivity extends AppCompatActivity {
    private TextView textUp,textDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);
        textUp = findViewById(R.id.Up);
        textDown = findViewById(R.id.Down);
        EditText editText = findViewById(R.id.editTextTextPersonName2);
        String values = editText.getText().toString();
        textUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valueString = editText.getText().toString();
                int value = Integer.parseInt(valueString);
                int newValue = value + 1;
                editText.setText(String.valueOf(newValue));
            }
        });
        textDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ValueStr = editText.getText().toString();
                int value = Integer.parseInt(ValueStr);
                int newValue = value - 1;
                editText.setText(String.valueOf(newValue));
            }
        });
    }
}