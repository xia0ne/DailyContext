package org.example.dailycontext.service;

import org.example.dailycontext.domain.entity.TaskJobLog;

import java.util.List;

public interface LogService {
	public void addLog(TaskJobLog taskJobLog);

	public List<TaskJobLog> getList();
}
