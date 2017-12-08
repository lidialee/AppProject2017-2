package com.example.lidia.appproject2017_2.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lidia.appproject2017_2.Class.Comment;
import com.example.lidia.appproject2017_2.Holder.CommentHolder;
import com.example.lidia.appproject2017_2.R;

import java.util.List;


public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentHolder> {
    private List<Comment> CommentList;
    private Context context;

    public CommentRecyclerAdapter(List<Comment> CommentList, Context context) {
        this.CommentList = CommentList;
        this.context = context;
    }


    public void setCommentList(List<Comment> list){ this.CommentList = list; }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.comment_item_layout,parent,false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        final Comment Comment = CommentList.get(position);
        holder.bindData(Comment,context);
    }

    @Override
    public int getItemCount() {
        return CommentList.size();
    }
}
