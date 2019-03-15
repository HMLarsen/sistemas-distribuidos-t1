package com.furb.br;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TaskScheduler {

	private ElectionManager em = new ElectionManager();

	/**
	 * Creates all the tasks of the application
	 */
	public void scheduleAllTasks() {
		createTask(AppConstants.CREATE_PROCESS_TIMER, AppConstants.CREATE_PROCESS_INTERVAL, AppConstants.CREATE_PROCESS_METHOD);
		createTask(AppConstants.INVOKE_REQUEST_CORDINATOR_TIMER, AppConstants.INVOKE_REQUEST_CORDINATOR_INTERVAL,
				AppConstants.INVOKE_REQUEST_CORDINATOR_METHOD);
		createTask(AppConstants.DISABLE_CORDINATOR_TIMER, AppConstants.DISABLE_CORDINATOR_INTERVAL,
				AppConstants.DISABLE_CORDINATOR_METHOD);
		createTask(AppConstants.DISABLE_PROCESS_TIMER, AppConstants.DISABLE_PROCESS_INTERVAL, AppConstants.DISABLE_PROCESS_METHOD);
	}

	/**
	 * Creates a task
	 */
	private void createTask(String timerName, long jobInterval, String methodName) {
		Timer timer = new Timer(timerName);
		TimerTask task = new TimerTask() {
			public void run() {
				try {
					Method method = em.getClass().getMethod(methodName);
					method.invoke(em);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		// Converts from millis to nanos
		LocalDateTime localDate = LocalDateTime.now().plusNanos(TimeUnit.NANOSECONDS.convert(jobInterval, TimeUnit.MILLISECONDS));
		Date startDate = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
		timer.schedule(task, startDate, jobInterval);
	}

}
