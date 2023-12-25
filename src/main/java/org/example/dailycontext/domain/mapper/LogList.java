package org.example.dailycontext.domain.mapper;

import lombok.Data;
import org.example.dailycontext.domain.entity.TaskJobLog;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class LogList {
	List<TaskJobLog> taskJobLogs = new ArrayList<>();

	public List<TaskJobLog> getList(){

		return taskJobLogs;
	}

	public void addLog(TaskJobLog taskJobLog) {
		taskJobLogs.add(taskJobLog);
	}
}
