package com.juster.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.juster.R;
import com.juster.data.api.database.user.model.GuidesDetail;
import com.juster.login.view.GuideDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by deepakj on 6/8/16.
 */
public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.SimpleRvViewHolder> {

    private List<GuidesDetail> guidesDetails;
    private AdapterView.OnItemClickListener onItemClickListener;
    private LayoutInflater layoutInflater;
    private String searchText;

    final Context mContext;
    private String mDefaulttimeString;

    public GuideAdapter(Context mContext , ArrayList<GuidesDetail> guidesDetails) {
        this.guidesDetails = guidesDetails;
        this.mContext = mContext;
        this.mDefaulttimeString = mContext.getString(R.string.no_claims_msg);
    }

    public void updateListData(String updatedSearch, ArrayList<GuidesDetail> guidesDetails) {
        this.guidesDetails = guidesDetails;
        this.searchText = updatedSearch;
        notifyDataSetChanged();
    }

    @Override
    public SimpleRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_row_guide, parent, false);
        return new SimpleRvViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SimpleRvViewHolder holder, int position) {
        holder.tv_name.setText(guidesDetails.get(position).getName());
        if(guidesDetails!=null && guidesDetails.size() > 0) {
            holder.tv_review.setText(guidesDetails.get(position).getRatingCount() + " Reviews");
            holder.tv_ratingvar.setRating(guidesDetails.get(position).getRating());
        }
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    @Override
    public int getItemCount() {
        return guidesDetails.size();
    }

    class SimpleRvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView tv_name;
        TextView tv_review;
        RatingBar tv_ratingvar;

        public SimpleRvViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.image);
            tv_name = (TextView) itemView.findViewById(R.id.tvname);
            tv_ratingvar = (RatingBar) itemView.findViewById(R.id.ratingbar);
            tv_review = (TextView) itemView.findViewById(R.id.feedback);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, GuideDetailActivity.class);
            if(guidesDetails !=null && guidesDetails.get(getAdapterPosition()) != null) {
                intent.putExtra("guide", guidesDetails.get(getAdapterPosition()));
            }
            mContext.startActivity(intent);
        }
    }
}
