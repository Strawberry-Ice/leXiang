package com.jfdh.controller.weichat.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaIdMessage {
	@XStreamAlias("MediaId")
	@XStreamCDATA
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

}
