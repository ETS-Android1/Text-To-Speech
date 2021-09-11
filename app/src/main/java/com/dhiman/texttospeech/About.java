package com.dhiman.texttospeech;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.view.View;




import android.os.Bundle;


import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.IUnityBannerListener;
import com.unity3d.services.banners.UnityBanners;

public class About extends AppCompatActivity {




    TextView privacy;

    private String GameID = "4280555";
    private boolean testMode = false;
    private String bannerAdPlacement = "vc";
    private String interstitialAdPlacement = "n";

    private Button showInterstitialBtn, showBannerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
privacy = findViewById(R.id.privacy);

        TextView txt = findViewById(R.id.text);
        txt.setSelected(true);

        privacy.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             TextView txt=  (TextView) findViewById(R.id.privacy); //txt is object of TextView
             txt.setMovementMethod(LinkMovementMethod.getInstance());
         }
     });

        UnityAds.initialize(About.this, GameID, testMode);
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
                Toast.makeText(About.this, unityAdsError.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        UnityAds.setListener(unityAdsListener);

        if (UnityAds.isInitialized()) {
            UnityAds.load(interstitialAdPlacement);
            UnityBanners.loadBanner(About.this, bannerAdPlacement);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DisplayInterstitialAd();
                }
            }, 5000);
        } else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    UnityAds.load(interstitialAdPlacement);


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            DisplayInterstitialAd();
                            UnityBanners.loadBanner(About.this, bannerAdPlacement);
                        }
                    }, 5000);
                }
            }, 5000);
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
                UnityBanners.loadBanner(About.this, bannerAdPlacement);
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

//        showInterstitialBtn = findViewById(R.id.showInterstitialAdBtn);
//       showBannerBtn = findViewById(R.id.showBannerAdBtn);
        UnityAds.load(interstitialAdPlacement);
        DisplayInterstitialAd();
//        showInterstitialBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UnityAds.load(interstitialAdPlacement);
//                DisplayInterstitialAd();
//
//            }
//        });


//
//        showBannerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UnityBanners.loadBanner(CategoryActivity.this, bannerAdPlacement);
//            }
//        });

    }


    private void DisplayInterstitialAd() {
        if (UnityAds.isReady(interstitialAdPlacement)) {
            UnityAds.show(About.this, interstitialAdPlacement);
        }


    }
}

