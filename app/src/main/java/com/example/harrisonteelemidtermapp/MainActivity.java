package com.example.harrisonteelemidtermapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.mariuszgromada.math.mxparser.*;

public class MainActivity extends AppCompatActivity {

    private EditText display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //soundcode
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(8)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else{
            soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
        }

        sound1 = soundPool.load(this,R.raw.charles,1);
        sound2 = soundPool.load(this,R.raw.rin,1);
        sound3 = soundPool.load(this,R.raw.olivia,1);
        sound4 = soundPool.load(this,R.raw.greg,1);
        sound5 = soundPool.load(this,R.raw.jacob,1);
        sound6 = soundPool.load(this,R.raw.lily,1);
        sound7 = soundPool.load(this,R.raw.richie,1);
        sound8 = soundPool.load(this,R.raw.wiggy,1);


        //displaycode
        display = findViewById(R.id.textView);
        display.setShowSoftInputOnFocus(false);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getString(R.string.display).equals(display.getText().toString())){
                    display.setText(" ");
                }
            }
        });
    }

    //sound code

    private SoundPool soundPool;
    private int sound1, sound2, sound3, sound4, sound5, sound6, sound7, sound8;

    public void playSound(View v){
        switch(v.getId()){
            case R.id.soundCharles:
                soundPool.play(sound1, 10, 10, 0, 0,1);
                break;
            case R.id.soundRin:
                soundPool.play(sound2, 1, 1, 0, 0,1);
                break;
            case R.id.soundOli:
                soundPool.play(sound3, 1, 1, 0, 0,1);
                break;
            case R.id.soundGreg:
                soundPool.play(sound4, 1, 1, 0, 0,1);
                break;
            case R.id.soundJacob:
                soundPool.play(sound5, 1, 1, 0, 0,1);
                break;
            case R.id.soundLily:
                soundPool.play(sound6, 1, 1, 0, 0,1);
                break;
            case R.id.soundRichie:
                soundPool.play(sound7, 1, 1, 0, 0,1);
                break;
            case R.id.soundWiggy:
                soundPool.play(sound8, 1, 1, 0, 0,1);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }


    //swipe function

    float x1;
    float x2;
    float y1;
    float y2;

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();;
                if(x1 < x2){
                    Intent i = new Intent(MainActivity.this, SwipeLeft.class);
                    startActivity(i);
                } else if(x1 > x2){
                    Intent i = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }



    //calc code

    private void updateText(String strToAdd){
        String oldStr = display.getText().toString();
        int cursorPos = display.getSelectionStart();
        String leftStr = oldStr.substring(0, cursorPos);
        String rightStr = oldStr.substring(cursorPos);
        if(getString(R.string.display).equals(display.getText().toString())){
            display.setText(strToAdd);
            display.setSelection(cursorPos + 1);
        } else{
            display.setText(String.format("%s%s%s", leftStr, strToAdd, rightStr));
            display.setSelection(cursorPos + 1);
        }
    }

    public void zeroButton(View view){
        updateText("0");
    }

    public void decimalButton(View view){
        updateText(".");
    }

    public void equalsButton(View view){
        String userExpression = display.getText().toString();

        userExpression = userExpression.replaceAll("÷", "/");
        userExpression = userExpression.replaceAll("×", "*");

        Expression expression = new Expression(userExpression);
        String answer = String.valueOf(expression.calculate());

        display.setText(answer);
        display.setSelection(answer.length());
    }

    public void additionButton(View view){
        updateText("+");
    }

    public void oneButton(View view){
        updateText("1");
    }

    public void twoButton(View view){
        updateText("2");
    }

    public void threeButton(View view){
        updateText("3");
    }

    public void subButton(View view){
        updateText("-");
    }

    public void fourButton(View view){
        updateText("4");
    }

    public void fiveButton(View view){
        updateText("5");
    }

    public void sixButton(View view){
        updateText("6");
    }

    public void multButton(View view){
        updateText("×");
    }

    public void sevenButton(View view){
        updateText("7");
    }

    public void eightButton(View view){
        updateText("8");
    }

    public void nineButton(View view){
        updateText("9");
    }

    public void divButton(View view){
        updateText("÷");
    }

    public void clearButton(View view){
        display.setText("");
    }

    public void expButton(View view){
        updateText("^");
    }

    public void parButton(View view){
        int cursorPos = display.getSelectionStart();
        int openPar = 0;
        int closePar = 0;
        int textLength = display.getText().length();

        for(int i = 0; i < cursorPos; i++){
            if(display.getText().toString().charAt(i) == '('){
                openPar += 1;
            }
            if(display.getText().toString().charAt(i) == ')'){
                closePar += 1;
            }
        }
        if(openPar == closePar || display.getText().toString().substring(textLength - 1, textLength).equals("(")){
            updateText("(");
        }
        else if(closePar < openPar && !display.getText().toString().substring(textLength - 1, textLength).equals("(")){
            updateText(")");
        } display.setSelection(cursorPos + 1);
    }

    public void backspaceButton(View view){
        int cursorPos = display.getSelectionStart();
        int textLength = display.getText().length();

        if(cursorPos != 0 && textLength != 0){
            SpannableStringBuilder selection = (SpannableStringBuilder) display.getText();
            selection.replace(cursorPos - 1, cursorPos, "");
            display.setText(selection);
            display.setSelection(cursorPos -1);
        }
    }

    public void plusMinus(View view){
        updateText("-");
    }

}