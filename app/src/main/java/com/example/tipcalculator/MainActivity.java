package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements TextWatcher, SeekBar.OnSeekBarChangeListener {
    private double billAmount, percent;
    private TextView amountTextView, percentTextView, tipTextView, totalTextView;
    private EditText amountEditText;
    private SeekBar percentSeekBar;

    NumberFormat currencyFormat, percentFormat;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast toast = Toast.makeText(getApplicationContext(), "Total is "+
                        totalTextView.getText().toString(), Toast.LENGTH_LONG);
                toast.show();
                return true;

            case R.id.item2:

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar); // connecting toolbar by finding view
        currencyFormat = NumberFormat.getCurrencyInstance();
        percentFormat = NumberFormat.getPercentInstance();
        // inserting value to the defined variables
        billAmount = 0.0;
        percent = 0.15;
        amountTextView = findViewById(R.id.amountTextView);
        tipTextView = findViewById(R.id.tipTextView);
        totalTextView = findViewById(R.id.totalTextView);
        percentTextView = findViewById(R.id.percentTextView);

        amountEditText = findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(this);

        percentSeekBar = findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(this);

        amountTextView.setText(currencyFormat.format(billAmount));
        percentTextView.setText(percentFormat.format(percent));
        calculate();
    }

    private void calculate(){
        double tip = billAmount * percent;
        double total = billAmount + tip;

        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
        percentTextView.setText(percentFormat.format(percent));
    }

    private void hideKeyboard() {
        // recognize if keyboard is used
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // hide the soft keyboard from the window
        imm.hideSoftInputFromWindow(amountTextView.getWindowToken(), 0);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        try {
            billAmount = Double.parseDouble(s.toString())/100.0;
            amountTextView.setText(currencyFormat.format(billAmount));
        }
        catch (NumberFormatException e) {
            billAmount = 0.0;
            amountTextView.setText(currencyFormat.format(billAmount));
        }
        calculate();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        percent = progress/100.0;
        calculate();
        hideKeyboard();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}