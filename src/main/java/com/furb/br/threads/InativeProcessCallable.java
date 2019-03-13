package com.furb.br.threads;

import java.util.List;
import java.util.concurrent.Callable;

public class InativeProcessCallable implements Callable<List<Process>>{
	
	private List<Process> processList;
	
	public InativeProcessCallable(List<Process> processList) {
		this.processList = processList;
	}
	public List<Process> call() throws Exception {
		return null;
	}

}
