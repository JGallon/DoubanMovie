package com.sorcererxw.doubanmovie.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sorcererxw.doubanmovie.R;
import com.sorcererxw.doubanmovie.data.SimpleMovieBean;
import com.sorcererxw.doubanmovie.ui.activities.MovieDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JGallon on 2017/6/11.
 */

public class UsRankingAdapter extends RecyclerView.Adapter<UsRankingAdapter.ViewHolder> {

    private Activity mActivity;
    private List<SimpleMovieBean> mList;

    public UsRankingAdapter(Activity context,
                            List<SimpleMovieBean> list) {
        mActivity = context;
        mList = list;
    }

    public void setData(List<SimpleMovieBean> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(mActivity).inflate(R.layout.item_ranking, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SimpleMovieBean movie = mList.get(position);
        int rank = position + 1;
        Glide.with(mActivity)
                .load(movie.getImageUrl())
                .transition(new DrawableTransitionOptions().crossFade(500))
                .into(holder.mPicture);

        holder.mTitle.setText(movie.getTitle());
        holder.mrank.setText(rank + "");
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, MovieDetailActivity.class);
            intent.putExtra("movie", movie);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, holder.mPicture,
                            mActivity.getString(R.string.transition_name_movie_image));
            ActivityCompat.startActivity(mActivity, intent, options.toBundle());
        });

        if (movie.getRating() > 0) {
            holder.mRatingBar.setVisibility(View.VISIBLE);
            holder.mRatingInfo.setText(String.valueOf(movie.getRating()));
            holder.mRatingBar.setRating((float) movie.getRating() / 2);
        } else {
            holder.mRatingInfo.setText("评价人数不足");
            holder.mRatingBar.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewe_ranking)
        TextView mrank;

        @BindView(R.id.linearLayout_item_ranking_item_container)
        LinearLayout mContainer;

        @BindView(R.id.imageView_item_ranking)
        ImageView mPicture;

        @BindView(R.id.title_item_ranking)
        TextView mTitle;

        @BindView(R.id.ratingBar_item_ranking)
        RatingBar mRatingBar;

        @BindView(R.id.textView_item_ranking_rating_info)
        TextView mRatingInfo;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
