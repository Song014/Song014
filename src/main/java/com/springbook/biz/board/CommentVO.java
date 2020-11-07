package com.springbook.biz.board;

import java.sql.Date;

public class CommentVO {
	private int comment_num;
	private int bno;
	private String id;
	private String content;
	private Date writedate;
	
	public int getComment_num() {
		return comment_num;
	}
	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getWritedate() {
		return writedate;
	}
	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}
	@Override
	public String toString() {
		return "CommentVO [comment_num=" + comment_num + ", bno=" + bno + ", id=" + id + ", content=" + content
				+ ", writedate=" + writedate + "]";
	}

	
}
