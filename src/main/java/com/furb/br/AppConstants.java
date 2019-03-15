package com.furb.br;

import java.util.concurrent.TimeUnit;

public interface AppConstants {

	public static final long CREATE_PROCESS_INTERVAL = TimeUnit.SECONDS.toMillis(30);
	public static final String CREATE_PROCESS_TIMER = "CREATE_PROCESS_TIMER";
	public static final String CREATE_PROCESS_METHOD = "createProcess";
	
	public static final long INVOKE_REQUEST_CORDINATOR_INTERVAL = TimeUnit.SECONDS.toMillis(25);
	public static final String INVOKE_REQUEST_CORDINATOR_TIMER = "INVOKE_REQUEST_CORDINATOR_TIMER";
	public static final String INVOKE_REQUEST_CORDINATOR_METHOD = "invokeRequestCoordinator";
	

	public static final long DISABLE_CORDINATOR_INTERVAL = TimeUnit.SECONDS.toMillis(100);
	public static final String DISABLE_CORDINATOR_TIMER = "DISABLE_CORDINATOR_TIMER";
	public static final String DISABLE_CORDINATOR_METHOD = "disableCoordinator";

	public static final long DISABLE_PROCESS_INTERVAL = TimeUnit.SECONDS.toMillis(80);
	public static final String DISABLE_PROCESS_TIMER = "DISABLE_PROCESS_TIMER";
	public static final String DISABLE_PROCESS_METHOD = "disableProcess";

}
