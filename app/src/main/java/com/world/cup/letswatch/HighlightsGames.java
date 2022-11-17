package com.world.cup.letswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighlightsGames extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GameListAdapter matchesAdapters;
    private List<GameModel> matchModelList;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    private NativeAd native_Ads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highlights_games);



        getData();
        recyclerView =findViewById(R.id.recycler_main);
        matchModelList=new ArrayList<>();
        matchesAdapters=new GameListAdapter(this,matchModelList);

        final LinearLayoutManager layoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void getData() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ApiUrl.base_url, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.i("res", "[" + response + "]");
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);

                        String title = jsonObject.getString("title");
                        String embed = jsonObject.getString("embed");
                        String url = jsonObject.getString("url");
                        String badge = jsonObject.getString("thumbnail");
                        String date = jsonObject.getString("date");
                        JSONArray videos = jsonObject.getJSONArray("videos");
                        JSONObject competition = jsonObject.getJSONObject("competition");

                        //setting data to list
                        GameModel matchModel = new GameModel(competition, videos,title, embed, url, badge, date);
                        matchModelList.add(matchModel);

                        //setting adatpte to recycler
                        recyclerView.setAdapter(matchesAdapters);


                    }
                    matchesAdapters.notifyDataSetChanged();
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();

                }

            }
        }, new Response.ErrorListener() {
            /**
             * Callback method that an error has been occurred with the provided error code and optional
             * user-readable message.
             *
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();

            }

        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("x-rapidapi-key", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                headers.put("x-rapidapi-host", "free-football-soccer-videos.p.rapidapi.com");
                return headers;
            }

        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //adding the string request to request queue
        requestQueue.add(jsonArrayRequest);

    }
    @Override
    public void onBackPressed() {
        StartAppAd.onBackPressed(this);
        super.onBackPressed();
    }
}