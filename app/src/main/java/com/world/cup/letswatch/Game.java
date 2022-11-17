package com.world.cup.letswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.material.button.MaterialButton;
import com.startapp.sdk.ads.banner.Mrec;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Game extends AppCompatActivity {

    private String title, embed, url, badge, date;
    private JSONObject competition;
    private TextView mTitle, mCompetition;
    private CircleImageView mImageView;
    private WebView mWebView;
    private ProgressDialog progressDialog;

    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    private NativeAd native_Ads;
    private MaterialButton moreVideos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        // Start loading ads here...
        loadmrec(R.id.startionative3);


        moreVideos =findViewById(R.id.morev);

        moreVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(Game.this, HighlightsGames.class);
                startActivity(intent);
                // and show interstitial ad
                StartAppAd.showAd(Game.this);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading video data...");
        progressDialog.setCancelable(false);

        title = getIntent().getExtras().getString("title");
        embed = getIntent().getExtras().getString("embed");
        url = getIntent().getExtras().getString("url");
        badge = getIntent().getExtras().getString("badge");
        date = getIntent().getExtras().getString("date");
        try {

            competition = new JSONObject(Objects.requireNonNull(getIntent().getExtras().getString("competition")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mTitle = findViewById(R.id.tv_title);
        mCompetition = findViewById(R.id.tv_competition);
        mImageView = findViewById(R.id.image_main);
        mWebView = findViewById(R.id.webview);

        try {
            mCompetition.setText(competition.getString("name"));
            mTitle.setText(title);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String vdUrl = "<html><body>" + embed + "</body></html>";
        mWebView.requestFocus();
        mWebView.getSettings().setLightTouchEnabled(true);
        mWebView.setBackgroundColor(getResources().getColor(R.color.blueis));
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setGeolocationEnabled(true);
        mWebView.setSoundEffectsEnabled(true);
        mWebView.loadData(vdUrl, "text/html", "UTF-8");
        mWebView.setBackgroundColor(Color.TRANSPARENT);
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }
        });
        //setting values
        @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
        RequestOptions options = new RequestOptions()
                .skipMemoryCache(true);

        Glide.with(getApplicationContext()).applyDefaultRequestOptions(options)
                .load(badge)
                .into(mImageView);

//        mRecyclerView = findViewById(R.id.recycler_main);
//
//        final LinearLayoutManager layoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }
    void loadmrec(int id){
        RelativeLayout mainLayout = (RelativeLayout)findViewById(id);
        //mainLayout.setVisibility(View.GONE);
// Define StartApp Mrec
        Mrec startAppMrec = new Mrec(this);
        RelativeLayout.LayoutParams mrecParameters =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        mrecParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mrecParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

// Add to main Layout
        mainLayout.addView(startAppMrec, mrecParameters);

    }
    @Override
    public void onBackPressed() {
        StartAppAd.onBackPressed(this);
        super.onBackPressed();
    }

}