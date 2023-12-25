package org.example.dailycontext.domain.entity;

import lombok.Data;

@Data
public class TaskJob {
	String jobName;
	String url;
	String title;
	String crontab;
	String sendTo;
}
