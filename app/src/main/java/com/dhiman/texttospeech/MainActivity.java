package com.dhiman.texttospeech;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Environment;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.UnityBanners;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.IUnityBannerListener;
import com.unity3d.services.banners.UnityBanners;

public class MainActivity extends AppCompatActivity  {

    private TextToSpeech mTTS;
    private String GameID = "4280555";
    private boolean testMode = false;
    private String bannerAdPlacement = "b";
    private EditText mEditText;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;
    private TextView text1;
    String speakTextTxt;
    private ImageView iv_mic;

    HashMap myHashRender = new HashMap();
    String tempDestFile ;
    //private TextView tv_Speech_to_text;
    private EditText edit_text;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;


    String exStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    //your path here!
    File appTmpPath = new File(exStoragePath + "/Download/");
//String tempFilename = "tmpaudio.wav";




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        if (item.getTitle().equals("About")) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("About");

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setIcon(R.drawable.ic_launcher_text);
        getSupportActionBar().setTitle(" TTS  AND  STT To mp3");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tex = findViewById(R.id.text);
        tex.setSelected(true);

        UnityAds.initialize(MainActivity.this, GameID, testMode);
        IUnityAdsListener unityAdsListener = new IUnityAdsListener() {
            @Override
            public void onUnityAdsReady(String s) {
//                Toast.makeText(CategoryActivity.this, "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnityAdsStart(String s) {
                //  Toast.makeText(CategoryActivity.this, "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
                //   Toast.makeText(CategoryActivity.this, "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
                Toast.makeText(MainActivity.this, unityAdsError.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        UnityAds.setListener(unityAdsListener);


        if (UnityAds.isInitialized()) {

            UnityBanners.loadBanner(MainActivity.this, bannerAdPlacement);

        }
        else {







                            UnityBanners.loadBanner(MainActivity.this, bannerAdPlacement);

                    }


        IUnityBannerListener iUnityBannerListener = new IUnityBannerListener() {
            @Override
            public void onUnityBannerLoaded(String s, View view) {
                ((ViewGroup) findViewById(R.id.bannerAdLayout)).removeView(view);
                ((ViewGroup) findViewById(R.id.bannerAdLayout)).addView(view);

            }

            @Override
            public void onUnityBannerUnloaded(String s) {

            }

            @Override
            public void onUnityBannerShow(String s) {
                UnityBanners.loadBanner(MainActivity.this, bannerAdPlacement);
            }

            @Override
            public void onUnityBannerClick(String s) {

            }

            @Override
            public void onUnityBannerHide(String s) {

            }

            @Override
            public void onUnityBannerError(String s) {

            }
        };

        UnityBanners.setBannerListener(iUnityBannerListener);


        iv_mic = findViewById(R.id.iv_mic);
        // here

        edit_text = findViewById(R.id.edit_text);

        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                       Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                }
                catch (Exception e) {
                    Toast
                            .makeText(MainActivity.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                //here

                edit_text.setText(
                        Objects.requireNonNull(result).get(0));
            }
        }

        mButtonSpeak = findViewById(R.id.button_speak);




        text1 = findViewById(R.id.text1);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v == text1) {
                    PopupMenu p1 = new PopupMenu(MainActivity.this, text1);
                    Menu m = p1.getMenu();
                    m.add("English");
                    m.add("German");
                    m.add("ITALIAN");
                    m.add("CHINESE");
                    m.add("KOREAN");
                    m.add("TAIWAN");
                    m.add("JAPANESE");
                    p1.show();

                    p1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getTitle().equals("English")) {
                                int result = mTTS.setLanguage(Locale.ENGLISH);
                                Snackbar.make(MainActivity.this, v, "English Selected", Snackbar.LENGTH_LONG).show();

                                if (result == TextToSpeech.LANG_MISSING_DATA
                                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Log.e("TTS", "Language not supported");
                                } else {
                                    mButtonSpeak.setEnabled(true);
                                }

                            } else {

                                if (item.getTitle().equals("German")) {
                                    int result = mTTS.setLanguage(Locale.GERMAN);

                                    Snackbar.make(MainActivity.this, v, "German Selected", Snackbar.LENGTH_LONG).show();
                                    if (result == TextToSpeech.LANG_MISSING_DATA
                                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                        Log.e("TTS", "Language not supported");
                                    } else {
                                        mButtonSpeak.setEnabled(true);
                                    }

                                } else if (item.getTitle().equals("ITALIAN")) {
                                    int result = mTTS.setLanguage(Locale.ITALIAN);

                                    Snackbar.make(MainActivity.this, v, "ITALIAN Selected", Snackbar.LENGTH_LONG).show();
                                    if (result == TextToSpeech.LANG_MISSING_DATA
                                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                        Log.e("TTS", "Language not supported");
                                    } else {
                                        mButtonSpeak.setEnabled(true);
                                    }

                                } else if (item.getTitle().equals("CHINESE")) {
                                    int result = mTTS.setLanguage(Locale.CHINESE);

                                    Snackbar.make(MainActivity.this, v, "CHINESE Selected", Snackbar.LENGTH_LONG).show();
                                    if (result == TextToSpeech.LANG_MISSING_DATA
                                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                        Log.e("TTS", "Language not supported");
                                    } else {
                                        mButtonSpeak.setEnabled(true);
                                    }
                                } else if (item.getTitle().equals("KOREAN")) {
                                    int result = mTTS.setLanguage(Locale.KOREAN);

                                    Snackbar.make(MainActivity.this, v, "KOREAN Selected", Snackbar.LENGTH_LONG).show();
                                    if (result == TextToSpeech.LANG_MISSING_DATA
                                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                        Log.e("TTS", "Language not supported");
                                    } else {
                                        mButtonSpeak.setEnabled(true);
                                    }
                                } else if (item.getTitle().equals("TAIWAN")) {
                                    int result = mTTS.setLanguage(Locale.TAIWAN);

                                    Snackbar.make(MainActivity.this, v, "TAIWAN Selected", Snackbar.LENGTH_LONG).show();
                                    if (result == TextToSpeech.LANG_MISSING_DATA
                                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                        Log.e("TTS", "Language not supported");
                                    } else {
                                        mButtonSpeak.setEnabled(true);
                                    }
                                } else if (item.getTitle().equals("JAPANESE")) {
                                    int result = mTTS.setLanguage(Locale.JAPANESE);

                                    Snackbar.make(MainActivity.this, v, "JAPANESE Selected", Snackbar.LENGTH_LONG).show();
                                    if (result == TextToSpeech.LANG_MISSING_DATA
                                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                        Log.e("TTS", "Language not supported");
                                    } else {
                                        mButtonSpeak.setEnabled(true);
                                    }
                                }
                                Log.e("TTS", "Initialization failed");
                            }


                            return false;
                        }


                    });
                }

            }


        });








        mTTS = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {







//                        if (status == TextToSpeech.SUCCESS) {
//
//
//                            int result = mTTS.setLanguage(Locale.ENGLISH);
//
//                            if (result == TextToSpeech.LANG_MISSING_DATA
//                                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                                Log.e("TTS", "Language not supported");
//                            } else {
//                                mButtonSpeak.setEnabled(true);
//                            }
//                        } else {
//                            Log.e("TTS", "Initialization failed");
//                        }
            }
        });


        mEditText = findViewById(R.id.edit_text);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
}

    class MySpeech implements TextToSpeech.OnInitListener
    {

        String tts;

        public MySpeech(String tts)
        {
            this.tts = tts;
            mTTS = new TextToSpeech(MainActivity.this, this);
        }

        @Override
        public void onInit(int status)
        {
            String editText1 = mEditText.getText().toString();
            Log.v("log", "initi");
            int i = mTTS.synthesizeToFile(speakTextTxt, myHashRender, tempDestFile);
            if(i == TextToSpeech.SUCCESS)
            {

                Toast toast = Toast.makeText(MainActivity.this, "Saved "+editText1,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
            System.out.println("Result : " + i);
        }
    }

    private void speak() {
        String text = mEditText.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);

        speakTextTxt = " "+text;
        HashMap myHashRender = new HashMap();
        myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, speakTextTxt);


        Log.d("MainActivity", "exStoragePath : "+exStoragePath);

        boolean isDirectoryCreated = appTmpPath.mkdirs();
        Log.d("MainActivity", "directory "+appTmpPath+" is created : "+isDirectoryCreated);

        tempDestFile = appTmpPath.getAbsolutePath() + File.separator + text +".mp3";
        Log.d("MainActivity", "tempDestFile : "+tempDestFile);

        Toast.makeText(this, "converted to audio mp3 check downloads folder ", Toast.LENGTH_SHORT).show();



new MySpeech(speakTextTxt);
          //addRecordingToMediaLibrary();



    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }


}