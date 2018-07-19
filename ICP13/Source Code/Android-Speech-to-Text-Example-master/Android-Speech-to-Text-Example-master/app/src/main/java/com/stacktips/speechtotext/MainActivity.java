package com.stacktips.speechtotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    //private static final int REQ_CODE_SPEECH_START = 200;

    private final int CHECK_CODE = 0x1;
    private TextView mVoiceInputTv;
    private TextView mResponse;
    private ImageButton mSpeakBtn;
    //ArrayList<String> fullText = new ArrayList<>();
    private Speaker speaker;
    private CompoundButton.OnCheckedChangeListener toggleListener;
    String myName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SayHello();
        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);

        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });

        checkTTS();
    }

    private void checkTTS(){
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, CHECK_CODE);
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                        Reply(result.get(0));

                    //mVoiceInputTv.append(result.get(0));
                    appendColoredText(mVoiceInputTv,"\n"+result.get(0) +"\n",Color.BLUE);
                }
                break;
            }
            case CHECK_CODE: {
                if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                    speaker = new Speaker(this);
                    speaker.allow(true);
                }else {
                    Intent install = new Intent();
                    install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(install);
                }
            }

        }
    }
    public static void appendColoredText(TextView tv, String text, int color) {
        int start = tv.getText().length();
        tv.append(text);
        int end = tv.getText().length();

        Spannable spannableText = (Spannable) tv.getText();
        spannableText.setSpan(new ForegroundColorSpan(color), start, end, 0);
    }
    public void Reply(String input){

        if(input.contains("name")){
            myName = input.substring(11);
            input="name";
        }
        else if(input.contains("time")){
            input = "time";
        }
        else if(input.contains("medical")){
            input = "medical";
        }
        else if(input.contains("take")){
            input="take";
        }
        switch (input){
            case "hello": {
                speaker.speak("What is your name?");
                //mVoiceInputTv.("What is your name?")
                appendColoredText(mVoiceInputTv,"What is your name?",Color.RED);

                break;
            }
            case "name":{
                speaker.speak("Nice to meet you " + myName);
               // mVoiceInputTv.setText(myName);
                appendColoredText(mVoiceInputTv,"Nice to meet you " + myName,Color.RED);
                break;
            }
            case "take":{
                speaker.speak(myName + " I think you have fever. Please take this medicine.");
                appendColoredText(mVoiceInputTv,myName + " I think you have fever. Please take this medicine." + myName,Color.RED);
                break;
            }
            case "time":{
                SimpleDateFormat sdfDate =new SimpleDateFormat("HH:mm");
                Date now = new Date();String[] strDate = sdfDate.format(now).split(":");if(strDate[1].contains("00"))strDate[1] = "o'clock";
                speaker.speak("The time is " + sdfDate.format(now));
                appendColoredText(mVoiceInputTv,"The time is " + sdfDate.format(now),Color.RED);
                break;
            }
            case "medical":{
                speaker.speak("Thank you too "+ myName +" Take care");
                appendColoredText(mVoiceInputTv,"Thank you too "+ myName +" Take care" + myName,Color.RED);
                break;
            }
            default:{
                speaker.speak("I did not get that, play try again");
                appendColoredText(mVoiceInputTv,myName + "I did not get that, play try again" + myName,Color.RED);
                break;
            }

        }

        }
    }

