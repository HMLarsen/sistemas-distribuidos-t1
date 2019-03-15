package com.furb.br;

import java.util.List;

public class ElectionManager {

	private List<Node> nodes;

	/**
	 * Create and add a new Process to the list
	 */
	public void createProcess() {
		System.out.println("createProcess");
	}

	/**
	 * Invoke some process to make a request to its coordinator
	 */
	public void invokeRequestCoordinator() {
		System.out.println("invokeRequestCoordinator");
	}

	/**
	 * Disable the coordinator
	 */
	public void disableCoordinator() {
		System.out.println("disableCoordinator");
	}

	/**
	 * Disable a random process (Cannot be the coordinator)
	 */
	public void disableProcess() {
		System.out.println("disableProcess");
	}

}