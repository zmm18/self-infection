package com.zmm.framework.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import top.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>新闻表 </p>
 * @Name SysNews
 * @Author 900045
 * @Created by 2019/7/5 0005
 */
@TableName("sys_news")
public class SysNews extends BaseModel {

	/**
	 * 新闻标题
	 */
	@TableField(value = "news_title")
	private String newsTitle;
	/**
	 * 新闻类型
	 */
	@TableField(value = "news_type")
	private String newsType;
	/**
	 * 发布时间
	 */
	@TableField(value = "send_time")
	private Date sendTime;
	/**
	 * 作者
	 */
	@TableField(value = "author_")
	private String author;
	/**
	 * 编辑
	 */
	@TableField(value = "editor_")
	private String editor;
	/**
	 * Tag标签
	 */
	@TableField(value = "tags_")
	private String tags;
	/**
	 * 关键字
	 */
	@TableField(value = "keys_")
	private String keys;
	/**
	 * 内容
	 */
	@TableField(value = "content_")
	private String content;
	/**
	 * 阅读次数
	 */
	@TableField(value = "reader_times")
	private Integer readerTimes;
	/**
	 * 发布状态
	 */
	@TableField(value = "status_")
	private String status;

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getReaderTimes() {
		return readerTimes;
	}

	public void setReaderTimes(Integer readerTimes) {
		this.readerTimes = readerTimes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	protected Serializable pkVal() {
		return getId();
	}
}
