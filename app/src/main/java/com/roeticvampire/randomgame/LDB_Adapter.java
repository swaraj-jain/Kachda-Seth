package com.roeticvampire.randomgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LDB_Adapter extends RecyclerView.Adapter<LDB_Adapter.ViewHolder>  {
    private List<LDB_User> mData = null;
    private  LayoutInflater mInflater;
    private Context context;

    public LDB_Adapter(List<LDB_User> mData, Context context) {
        this.mData = mData;
        this.mInflater =  LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.ldb_recyc_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  LDB_Adapter.ViewHolder holder, int position) {
        LDB_User currUser = mData.get(position);
        holder.NameView.setText(currUser.getName());
        Bitmap bitmap = ImageUtil.convert(currUser.getProfileImg());
        holder.profileView.setImageBitmap(bitmap);
        String s1=""+(position+1);
        holder.counter.setText(s1);
        s1=""+ currUser.getScore();
        holder.scoreView.setText(s1);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView NameView;
        TextView scoreView;
        ImageView profileView;
        TextView counter;
        ViewHolder(View itemView) {
            super(itemView);
            NameView = itemView.findViewById(R.id.nameView);
            scoreView=itemView.findViewById(R.id.ScoreView);
            profileView=itemView.findViewById(R.id.profileView);
            counter=itemView.findViewById(R.id.counterView);
        }


    }

    // convenience method for getting data at click position
    LDB_User getItem(int id) {
        return mData.get(id);
    }
}
