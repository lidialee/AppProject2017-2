package com.example.lidia.appproject2017_2.Class;

public class Comment {
    private String comment;
    private String writer;
    private String time;
    private String commentUid;
    private String storeUid;
    private String writerUid;
    private String writerImage;


    public Comment() {}

    public Comment(String comment, String writer, String time, String commentUid, String storeUid, String writerUid,String writerImage) {
        this.comment = comment;
        this.writer = writer;
        this.time = time;
        this.commentUid = commentUid;
        this.storeUid = storeUid;
        this.writerUid = writerUid;
        this.writerImage = writerImage;
    }

    public String getWriterImage() {
        return writerImage;
    }

    public void setWriterImage(String writerImage) {
        this.writerImage = writerImage;
    }

    public String getCommentUid() {
        return commentUid;
    }

    public void setCommentUid(String commentUid) {
        this.commentUid = commentUid;
    }

    public String getStoreUid() {
        return storeUid;
    }

    public void setStoreUid(String storeUid) {
        this.storeUid = storeUid;
    }

    public String getWriterUid() {
        return writerUid;
    }

    public void setWriterUid(String writerUid) {
        this.writerUid = writerUid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static Comment newComment(String comment, String writer, String time, String commentUid, String storeUid, String writerUid,String writerImage) {
        return new Comment(comment,writer,time,commentUid,storeUid,writerUid,writerImage);
    }
}
