package com.android.storemanage.entity;

public class MessageEntity {
	private String messageId;
	private String messageImgname;
	private String messageSfnew;
	private String messageImgpath;
	private String messageTitle;
	private String messageOpptime;
	private String messageImgid;
	private String messagePubdate;
	private String messageImgsize;

	private String messageImgdomain;
	private String messageDetail;

	public MessageEntity() {
		super();
	}

	public MessageEntity(String messageId, String messageImgname,
			String messageSfnew, String messageImgpath, String messageTitle,
			String messageOpptime, String messageImgid, String messagePubdate,
			String messageImgsize, String messageImgdomain, String messageDetail) {
		super();
		this.messageId = messageId;
		this.messageImgname = messageImgname;
		this.messageSfnew = messageSfnew;
		this.messageImgpath = messageImgpath;
		this.messageTitle = messageTitle;
		this.messageOpptime = messageOpptime;
		this.messageImgid = messageImgid;
		this.messagePubdate = messagePubdate;
		this.messageImgsize = messageImgsize;
		this.messageImgdomain = messageImgdomain;
		this.messageDetail = messageDetail;
	}

	public String getMessageImgname() {
		return messageImgname;
	}

	public void setMessageImgname(String messageImgname) {
		this.messageImgname = messageImgname;
	}

	public String getMessageSfnew() {
		return messageSfnew;
	}

	public void setMessageSfnew(String messageSfnew) {
		this.messageSfnew = messageSfnew;
	}

	public String getMessageImgpath() {
		return messageImgpath;
	}

	public void setMessageImgpath(String messageImgpath) {
		this.messageImgpath = messageImgpath;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageOpptime() {
		return messageOpptime;
	}

	public void setMessageOpptime(String messageOpptime) {
		this.messageOpptime = messageOpptime;
	}

	public String getMessageImgid() {
		return messageImgid;
	}

	public void setMessageImgid(String messageImgid) {
		this.messageImgid = messageImgid;
	}

	public String getMessagePubdate() {
		return messagePubdate;
	}

	public void setMessagePubdate(String messagePubdate) {
		this.messagePubdate = messagePubdate;
	}

	public String getMessageImgsize() {
		return messageImgsize;
	}

	public void setMessageImgsize(String messageImgsize) {
		this.messageImgsize = messageImgsize;
	}

	public String getMessageImgdomain() {
		return messageImgdomain;
	}

	public void setMessageImgdomain(String messageImgdomain) {
		this.messageImgdomain = messageImgdomain;
	}

	public String getMessageDetail() {
		return messageDetail;
	}

	public void setMessageDetail(String messageDetail) {
		this.messageDetail = messageDetail;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

}
