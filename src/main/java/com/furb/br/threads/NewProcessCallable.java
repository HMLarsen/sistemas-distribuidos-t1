package com.furb.br.threads;

import java.util.List;
import java.util.concurrent.Callable;

public class NewProcessCallable implements Callable<Process>{
	
	private List<Process> processList;
	
	public NewProcessCallable(List<Process> processList) {
		this.processList = processList;
	}
	
	public Process call() throws Exception {
		return null;
	}

}
