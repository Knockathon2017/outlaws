package com.juster.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.juster.R;

/**
 * Created by deepakj on 6/8/16.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.SimpleRvViewHolder> {

    private AdapterView.OnItemClickListener onItemClickListener;
    private LayoutInflater layoutInflater;
    private String searchText;

    private Integer[] mImageIdsAgra = {
            R.drawable.ic_taj_mahal,R.drawable.agra_temple,R.drawable.ic_buland_darwaza};

    private String[] mStringAgra = {"Taj Mahal", "Agra temple", "Buland Darwaza"};

    private Integer[] mImageIdsDelhi = {
            R.drawable.ic_india_gate, R.drawable.ic_humayun_tomp, R.drawable.ic_lotus_temp, R
            .drawable.ic_akshar_dam };

    private String[] mStringDelhi = {"India Gate", "Humayun Tomb", "Lotus Temple", "Akshar Dham"};

    final Context mContext;
    private String mDefaulttimeString;

    public GridAdapter(Context mContext , String updatedSearch) {
        this.mContext = mContext;
        this.searchText = updatedSearch;
        this.mDefaulttimeString = mContext.getString(R.string.no_claims_msg);
    }

    public void updateListData(String updatedSearch) {
        this.searchText = updatedSearch;
        notifyDataSetChanged();
    }

    @Override
    public SimpleRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_row_top_dest, parent, false);
        return new SimpleRvViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SimpleRvViewHolder holder, int position) {
        if(searchText.equalsIgnoreCase("agra")) {

            holder.image.setImageDrawable(ContextCompat.getDrawable(mContext, mImageIdsAgra[position]));
            holder.tv_view.setText(mStringAgra[position]);

        } else if(searchText.equalsIgnoreCase("delhi") || searchText.contains("del")) {

            holder.image.setImageDrawable(ContextCompat.getDrawable(mContext, mImageIdsDelhi[position]));
            holder.tv_view.setText(mStringDelhi[position]);

        }
    }

    @Override
    public int getItemCount() {
        if(searchText.isEmpty())
            return  0;
        else if(searchText.equalsIgnoreCase("agra"))
            return mImageIdsAgra.length;
        else if(searchText.equalsIgnoreCase("delhi"))
            return mImageIdsDelhi.length;
        else return  0;
    }

    class SimpleRvViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView tv_view;
        public SimpleRvViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            tv_view = (TextView) itemView.findViewById(R.id.destination_name);
        }
    }
}