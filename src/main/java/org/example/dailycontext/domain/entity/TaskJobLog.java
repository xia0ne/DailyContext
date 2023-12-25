package org.example.dailycontext.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TaskJobLog {
	Date time;
	String jobName;
}
