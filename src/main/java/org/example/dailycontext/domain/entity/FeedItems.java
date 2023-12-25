package org.example.dailycontext.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FeedItems {
	private String title;
	private String url;
	private Date time;
}
