package com.android.storemanage.entity;

public class MessageEntity {
	private String id;
	private String messageImgname;
	private String messageSfnew;
	private String messageImgpath;
	private String hdTitle;
	public String getHdTitle() {
		return hdTitle;
	}
	public void setHdTitle(String hdTitle) {
		this.hdTitle = hdTitle;
	}
	private long messageOpptime=0;
	private String messageImgid;
	private String messagePubtime;
	private String messageImgsize;

	private String messageImgdomain;
	private String messageDetail;
	private long dbOpptime = 0;
	private String hdWebpath;
	private String hdDjj;
	private String hdLogo;
	private String puntimeStamp;
	public String getPuntimeStamp() {
		return puntimeStamp;
	}
	public void setPuntimeStamp(String puntimeStamp) {
		this.puntimeStamp = puntimeStamp;
	}
	public String getHdLogo() {
		return hdLogo;
	}
	public void setHdLogo(String hdLogo) {
		this.hdLogo = hdLogo;
	}
	public String getHdDjj() {
		return hdDjj;
	}
	public void setHdDjj(String hdDjj) {
		this.hdDjj = hdDjj;
	}
	public String getHdWebpath() {
		return hdWebpath;
	}
	public void setHdWebpath(String hdWebpath) {
		this.hdWebpath = hdWebpath;
	}
	
	/**
	 * @return the dbOpptime
	 */
	public long getDbOpptime() {
		return dbOpptime;
	}

	/**
	 * @param dbOpptime the dbOpptime to set
	 */
	public void setDbOpptime(long dbOpptime) {
		this.dbOpptime = dbOpptime;
	}

	public MessageEntity() {
		super();
	}

	public MessageEntity(String messageId, String messageImgname,
			String messageSfnew, String messageImgpath, String messageTitle,
			long messageOpptime, String messageImgid, String messagePubtime,
			String messageImgsize, String messageImgdomain, String messageDetail,String hdDjj,String hdLogo,String hdWebpath) {
		super();
		this.id = messageId;
		this.messageImgname = messageImgname;
		this.messageSfnew = messageSfnew;
		this.messageImgpath = messageImgpath;
		this.hdTitle = messageTitle;
		this.messageOpptime = messageOpptime;
		this.messageImgid = messageImgid;
		this.messagePubtime = messagePubtime;
		this.messageImgsize = messageImgsize;
		this.messageImgdomain = messageImgdomain;
		this.messageDetail = messageDetail;
		this.hdDjj = hdDjj;
		this.hdLogo = hdLogo;
		this.hdWebpath = hdWebpath;
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



	public long getMessageOpptime() {
		return messageOpptime;
	}

	public void setMessageOpptime(long messageOpptime) {
		this.messageOpptime = messageOpptime;
	}

	public String getMessageImgid() {
		return messageImgid;
	}

	public void setMessageImgid(String messageImgid) {
		this.messageImgid = messageImgid;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessagePubtime() {
		return messagePubtime;
	}
	public void setMessagePubtime(String messagePubtime) {
		this.messagePubtime = messagePubtime;
	}



}
