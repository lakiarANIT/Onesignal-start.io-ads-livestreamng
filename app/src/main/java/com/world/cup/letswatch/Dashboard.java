package com.world.cup.letswatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.startapp.sdk.ads.banner.Mrec;
import com.startapp.sdk.ads.nativead.NativeAdDetails;
import com.startapp.sdk.ads.nativead.StartAppNativeAd;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import org.jetbrains.annotations.NotNull;

public class Dashboard extends AppCompatActivity {
    //private InterstitialAd mInterstitialAd;
    //private AdView mAdView;
    private StartAppAd startAppAd;
    private StartAppNativeAd startAppNativeAd;
    private LinearLayout linear1, linear2, linear3, linear4;
    private NativeAd native_Ads;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        linear1 = findViewById(R.id.linear_live);
        linear2 = findViewById(R.id.linear_highlight);
        linear3 = findViewById(R.id.linear_score);
        linear4 = findViewById(R.id.linear_tv);


        loadmrec(R.id.startionative);
        loadmrec(R.id.startionative2);


        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start your next activity
                opening(0);
                // and show interstitial ad
                StartAppAd.showAd(Dashboard.this);
            }
        });
        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start your next activity
                opening(0);
                // and show interstitial ad
                StartAppAd.showAd(Dashboard.this);
            }
        });
        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start your next activity
                opening(1);
                // and show interstitial ad
                StartAppAd.showAd(Dashboard.this);
            }
        });
        linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start your next activity
                opening(3);
                // and show interstitial ad
                StartAppAd.showAd(Dashboard.this);
            }
        });

        linear4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start your next activity
                opening(1);
                // and show interstitial ad
                StartAppAd.showAd(Dashboard.this);
            }
        });
    }


    private void opening(int position) {
        switch (position) {
            case 0:
                final Intent intent = new Intent(Dashboard.this, MatchEvents.class);
                intent.putExtra("isSaving", false);
                if (Build.VERSION.SDK_INT > 20) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Dashboard.this);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
                break;
            case 1:
                final Intent intent1 = new Intent(Dashboard.this, HighlightsGames.class);
                intent1.putExtra("isSaving", false);
                if (Build.VERSION.SDK_INT > 20) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Dashboard.this);
                    startActivity(intent1, options.toBundle());
                } else {
                    startActivity(intent1);
                }
                break;

            default:
                final Intent intent4 = new Intent(Dashboard.this, Livescore.class);
                if (Build.VERSION.SDK_INT > 20) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Dashboard.this);
                    startActivity(intent4, options.toBundle());
                } else {
                    startActivity(intent4);
                }
                break;
        }
    }

    void loadmrec(int id) {
        RelativeLayout mainLayout = (RelativeLayout) findViewById(id);
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

    public void exit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // Setting Alert Dialog Title
        alertDialogBuilder.setTitle("RATE US");
        // Icon Of Alert Dialog
        alertDialogBuilder.setIcon(R.drawable.ic_baseline_star_rate_24);
        // Setting Alert Dialog Message
        alertDialogBuilder.setMessage("Thank for using our app, we will like you to take a moment to rate and review us");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("Rate Us", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String btnrate = "https://play.google.com/store/apps/details?id=" +getPackageName();
                Uri rateweb = Uri.parse(btnrate);

                Intent itrate = new Intent(Intent.ACTION_VIEW, rateweb);
                if (itrate.resolveActivity(getPackageManager()) !=null){
                    startActivity(itrate);
                }
            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        exit();

    }
}