package com.example.calculator2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {


    //values for doing the actual calculating
    private final int ADD = 0;
    private final int SUB = 1;
    private final int MULT = 2;
    private final int DIV = 3;
    private final int MOD = 4;
    private final int SIN = 5;
    private final int COS = 6;
    private final int TAN = 7;
    private final double PI = Math.PI;
    private double valueHolder = -1;
    private int operator = -1;
    private int operator2 = -1;


    private TextView calculatorText;
    private TextView calculatorTextSci;
    private TextView memoryBox;
    private ImageView gif;

    //keep track of when the "=" button is clicked, do not allow overwriting
    private boolean overwriteable = true;
    //keep track of diviceByZero errors, do not allow writing after divide by zero until cleared
    boolean writeable = true;
    //keep track of if a decimal has been clicked, to prevent double decimals in a num
    private boolean decimalClicked = false;
    //true for using radians, false for using degrees
    private boolean useRadians = true;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate");
        calculatorText = findViewById(R.id.my_textview);
        calculatorTextSci = findViewById(R.id.my_textviewSci);
        memoryBox = findViewById(R.id.memory_TextBox);
        gif = findViewById(R.id.gif);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(getApplicationContext(),"Try getting an answer of 7",Toast.LENGTH_LONG).show();
        }
        //Toast.makeText(getApplicationContext(),"Try getting an answer of 7",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    public void onButtonClick(View view) {


        if (!writeable) {
            if (view.getId() == R.id.AC) {
                calculatorText.setText("0");
            }
        } else {
            switch (view.getId()) {
                case R.id.my_textview:
                    break;
                case R.id.AC:
                    calculatorText.setText("0");
                    overwriteable = true;
                    decimalClicked = false;
                    break;
                case R.id.equals:
                    equation();
                    overwriteable = false;
                    decimalClicked = false;
                    break;
                case R.id.negation:
                    double val = Double.parseDouble(calculatorText.getText().toString());
                    val *= -1;
                    String text = ""+val;
                    int length = text.length();
                    if(text.substring(length-2,length).equals(".0")){
                        text=text.substring(0,length-2);
                    }
                    calculatorText.setText(text);
                    break;
                case R.id.percent:
                    valueHolder = Double.parseDouble(calculatorText.getText().toString());
                    calculatorText.setText("0");
                    operator = MOD;
                    overwriteable = true;
                    decimalClicked = false;
                    break;
                case R.id.division:
                    valueHolder = Double.parseDouble(calculatorText.getText().toString());
                    calculatorText.setText("0");
                    operator = DIV;
                    overwriteable = true;
                    decimalClicked = false;
                    break;
                case R.id.plus:
                    valueHolder = Double.parseDouble(calculatorText.getText().toString());
                    calculatorText.setText("0");
                    operator = ADD;
                    overwriteable = true;
                    decimalClicked = false;
                    break;
                case R.id.minus:
                    valueHolder = Double.parseDouble(calculatorText.getText().toString());
                    calculatorText.setText("0");
                    operator = SUB;
                    overwriteable = true;
                    decimalClicked = false;
                    break;
                case R.id.times:
                    valueHolder = Double.parseDouble(calculatorText.getText().toString());
                    calculatorText.setText("0");
                    operator = MULT;
                    overwriteable = true;
                    decimalClicked = false;
                    break;
                case R.id.decimal:
                    if (decimalClicked) {
                        //do nothing, to avoid double decimals
                    } else if (overwriteable) {
                        calculatorText.setText(calculatorText.getText().toString().trim() + ".");
                        decimalClicked = true;
                    }
                    decimalClicked = true;
                    break;
                default:
                    if (overwriteable) {
                        String text2 = calculatorText.getText().toString().trim();
                        if(text2.equals("0")){
                            text2="";
                        }
                        calculatorText.setText(text2 + ((Button) view).getText());
                    }
            }
        }
    }

    public void onButtonClickSci(View view) {
        gif.setVisibility(View.INVISIBLE);
        calculatorTextSci.setTextColor(getResources().getColor(R.color.text_color));
        if (!writeable) {
            if (view.getId() == R.id.AC) {
                calculatorTextSci.setText("0");
            }
        } else {
            switch (view.getId()) {
                case R.id.my_textviewSci:
                    break;
                case R.id.ACSci:
                    calculatorTextSci.setText("0");
                    overwriteable = true;
                    decimalClicked = false;
                    memoryBox.setText("");
                    break;
                case R.id.equalsSci:
                    equationSci();
                    overwriteable = false;
                    decimalClicked = false;
                    memoryBox.setText("");
                    break;
                case R.id.negationSci:
                    double val = Double.parseDouble(calculatorTextSci.getText().toString());
                    val *= -1;
                    String text = ""+val;
                    int length = text.length();
                    if(text.substring(length-2,length).equals(".0")){
                        text=text.substring(0,length-2);
                    }
                    calculatorTextSci.setText(text);
                    break;
                case R.id.percentSci:
                    valueHolder = Double.parseDouble(calculatorTextSci.getText().toString());
                    memoryBox.setText(""+valueHolder+"%");
                    calculatorTextSci.setText("0");
                    operator = MOD;
                    overwriteable = true;
                    decimalClicked = false;
                    break;
                case R.id.divisionSci:
                    valueHolder = Double.parseDouble(calculatorTextSci.getText().toString());
                    memoryBox.setText(valueHolder+getString(R.string.division_symbol));
                    calculatorTextSci.setText("0");
                    operator = DIV;
                    overwriteable = true;
                    decimalClicked = false;
                    break;
                case R.id.plusSci:
                    valueHolder = Double.parseDouble(calculatorTextSci.getText().toString());
                    calculatorTextSci.setText("0");
                    operator = ADD;
                    overwriteable = true;
                    decimalClicked = false;
                    memoryBox.setText(valueHolder+"+");
                    break;
                case R.id.minusSci:
                    valueHolder = Double.parseDouble(calculatorTextSci.getText().toString());
                    calculatorTextSci.setText("0");
                    operator = SUB;
                    overwriteable = true;
                    decimalClicked = false;
                    memoryBox.setText(valueHolder+"-");
                    break;
                case R.id.timesSci:
                    valueHolder = Double.parseDouble(calculatorTextSci.getText().toString());
                    calculatorTextSci.setText("0");
                    operator = MULT;
                    overwriteable = true;
                    decimalClicked = false;
                    memoryBox.setText(valueHolder+"x");
                    break;
                case R.id.decimalSci:
                    if (decimalClicked) {
                        //do nothing, to avoid double decimals
                    } else if (overwriteable) {
                        calculatorTextSci.setText(calculatorTextSci.getText().toString().trim() + ".");
                        decimalClicked = true;
                    }
                    decimalClicked = true;
                    break;
                case R.id.sinSci:
                    if(overwriteable){
                        memoryBox.setText(memoryBox.getText().toString()+"SIN(");
                        ((Button)findViewById(R.id.equalsSci)).setText(")");
                        operator2=SIN;
                    }
                    break;
                case R.id.cosSci:
                    if(overwriteable){
                        memoryBox.setText(memoryBox.getText().toString()+"COS(");
                        ((Button)findViewById(R.id.equalsSci)).setText(")");
                        operator2=COS;
                    }
                    break;
                case R.id.tanSci:
                    if(overwriteable){
                        memoryBox.setText(memoryBox.getText().toString()+"TAN(");
                        ((Button)findViewById(R.id.equalsSci)).setText(")");
                        operator2=TAN;
                    }
                    break;
                case R.id.PISci:
                    if(overwriteable){
                        calculatorTextSci.setText(""+PI);
                    }
                    break;
                case R.id.degRadSci:
                    useRadians = !useRadians;
                    if(useRadians){
                        ((TextView)findViewById(R.id.radiansTextBox)).setText("RAD");
                    }else{
                        ((TextView)findViewById(R.id.radiansTextBox)).setText("DEG");
                    }
                    break;
                default:
                    if (overwriteable) {
                        String text2 = calculatorTextSci.getText().toString().trim();
                        if (text2.equals("0")) {
                            text2 = "";
                        }
                        calculatorTextSci.setText(text2 + ((Button) view).getText());
                    }
            }
        }
    }

    public void equation(){
        double val = Double.parseDouble(calculatorText.getText().toString().trim());
        double answer = 0;
        boolean divideByZero = false;
        switch(operator){
            case MOD:
                answer = valueHolder % val;
                valueHolder = 0;
                break;
            case ADD:
                answer = valueHolder + val;
                valueHolder = 0;
                break;
            case SUB:
                answer = valueHolder - val;
                valueHolder = 0;
                break;
            case MULT:
                answer = valueHolder * val;
                valueHolder = 0;
                break;
            case DIV:
                if(val==0){
                    divideByZero = true;
                }else {
                    answer = valueHolder / val;
                }
                valueHolder = 0;
        }
        if(divideByZero) {
            calculatorText.setText("ERROR");
            writeable = false;
        }else{
            int roundTo = 10000000;
            answer = Math.round(answer * roundTo)*1.0 / roundTo;
            String newText = "" + answer;
            if(newText.substring(newText.length()-2,newText.length()).equals(".0")){
                newText = newText.substring(0,newText.length()-2);
            }
            calculatorText.setText(newText);
        }
    }

    public void equationSci(){
        double val = Double.parseDouble(calculatorTextSci.getText().toString().trim());
        if(operator2>0){
            //if the user is in degrees, convert to radians first
            if(!useRadians) {
                val = Math.toRadians(val);
            }
            switch(operator2){
                case SIN:
                    val=Math.sin(val);
                    break;
                case COS:
                    val=Math.cos(val);
                    break;
                case TAN:
                    val=Math.tan(val);
                    break;
                default:
                    //do nothing
            }
            int round = 10000000;
            val*=round;
            val=Math.round(val);
            val/=round;
            String newText = "" + val;
            if(newText.substring(newText.length()-2,newText.length()).equals(".0")){
                newText = newText.substring(0,newText.length()-2);
            }
            calculatorTextSci.setText(""+newText);
            ((Button)findViewById(R.id.equalsSci)).setText("=");
            operator2=-1;
        }
        double answer = 0;
        boolean divideByZero = false;
        boolean doNothing = false;
        switch(operator){
            case MOD:
                answer = valueHolder % val;
                valueHolder = 0;
                break;
            case ADD:
                answer = valueHolder + val;
                valueHolder = 0;
                break;
            case SUB:
                answer = valueHolder - val;
                valueHolder = 0;
                break;
            case MULT:
                answer = valueHolder * val;
                valueHolder = 0;
                break;
            case DIV:
                if(val==0){
                    divideByZero = true;
                }else {
                    answer = valueHolder / val;
                }
                valueHolder = 0;
            case -1:
                doNothing = true;
                //do nothing because no operator has been clicked
        }
        if(divideByZero) {
            calculatorTextSci.setText("ERROR");
            writeable = false;
        }else if(doNothing){
            //do nothing
        }else{
            String newText = "" + answer;
            if(newText.substring(newText.length()-2,newText.length()).equals(".0")){
                newText = newText.substring(0,newText.length()-2);
            }
            calculatorTextSci.setText(newText);
            operator = -1;
            ((Button)findViewById(R.id.equalsSci)).setText("=");
        }
        if(answer==7.0){
            Glide.with(this).asGif()
                    .load(getString(R.string.seven_gif))
                    .into(gif);

            gif.setVisibility(View.VISIBLE);
            gif.bringToFront();
            calculatorTextSci.setTextColor(getResources().getColor(R.color.header_bk_color));
        }else{
            gif.setVisibility(View.INVISIBLE);
        }
    }

}