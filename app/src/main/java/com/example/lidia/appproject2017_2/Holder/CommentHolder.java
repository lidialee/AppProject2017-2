package com.example.lidia.appproject2017_2.Holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lidia.appproject2017_2.Class.Comment;
import com.example.lidia.appproject2017_2.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class CommentHolder extends RecyclerView.ViewHolder {
    private CircleImageView profileView;
    private TextView commentText;
    private TextView writer;
    private TextView time;

    public CommentHolder(View itemView) {
        super(itemView);
        commentText = itemView.findViewById(R.id.comment_textview);
        writer = itemView.findViewById(R.id.comment_writer);
        time = itemView.findViewById(R.id.comment_time);
        profileView =  itemView.findViewById(R.id.comment_profile_image);

    }

    public void bindData(Comment comment, Context context){
        commentText.setText(comment.getComment());
        writer.setText(comment.getWriter());
        time.setText(comment.getTime());
        String check = comment.getWriterImage();
        Glide.with(context).load(check).into(profileView);
    }
}
