/*
 * Copyright (c) 2015.
 * 湖南球谱科技有限公司版权所有
 * Hunan Qiupu Technology Co., Ltd. all rights reserved.
 */

package com.qixiu.intelligentcommunity.widget.imagepager;

public class PhotoSaveBean {
	private int position;
	private String path;
	
	public PhotoSaveBean(int position, String path){
		this.position = position;
		this.path = path;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	
	

}