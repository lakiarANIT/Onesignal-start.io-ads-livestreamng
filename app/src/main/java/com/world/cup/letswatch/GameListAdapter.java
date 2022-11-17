package com.world.cup.letswatch;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder>{
    private Context context;
    private List<GameModel> matchModelList;

    public GameListAdapter(Context context, List<GameModel> matchModelList) {
        this.context = context;
        this.matchModelList = matchModelList;
    }

    @NonNull
    @Override
    public GameListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list2, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);    }


    @Override
    public void onBindViewHolder(@NonNull GameListAdapter.ViewHolder holder, int position) {

        final String title = matchModelList.get(position).getTitle();
        final String embed = matchModelList.get(position).getEmbed();
        final String url = matchModelList.get(position).getUrl();
        final String badge = matchModelList.get(position).getThumbnail();
        final String date = matchModelList.get(position).getDate();
        final JSONObject competition = matchModelList.get(position).getCompetition();
        final JSONArray videos = matchModelList.get(position).getVideos();

        holder.settingValues(badge, title);

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.passValues(title,embed,url,badge,date,competition,videos);



            }
        });

    }


    @Override
    public int getItemCount() {
        return matchModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTitle;
        private ImageView mWebView;
        private ProgressBar progressBar;
        private ConstraintLayout mLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.tv_title);
            mWebView = itemView.findViewById(R.id.webview);
            mLinearLayout = itemView.findViewById(R.id.mycond);
        }

        private void settingValues(String bage,String title){
            mTitle.setText(title);
            Picasso.get().load(bage).into(mWebView);


        }

        private void passValues(String matchTitle, String embed, String url, String badge, String date, JSONObject competition, JSONArray videos){

            Intent intent = new Intent(context, Game.class);
            intent.putExtra("title", matchTitle);
            intent.putExtra("url", url);
            intent.putExtra("badge", badge);
            intent.putExtra("date", date);
            intent.putExtra("videos", videos.toString());
            intent.putExtra("competition", competition.toString());
            intent.putExtra("embed", embed);
            if (Build.VERSION.SDK_INT > 20) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context);
                context.startActivity(intent, options.toBundle());
            } else {
                context.startActivity(intent);
            }
        }
    }
}
