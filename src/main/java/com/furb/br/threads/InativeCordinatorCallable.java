package com.furb.br.threads;

import java.util.List;
import java.util.concurrent.Callable;

public class InativeCordinatorCallable implements Callable<List<Process>>{
	
	private List<Process> processList;
	
	public InativeCordinatorCallable(List<Process> processList) {
		this.processList = processList;
	}
	public List<Process> call() throws Exception {
		return null;
	}

}
