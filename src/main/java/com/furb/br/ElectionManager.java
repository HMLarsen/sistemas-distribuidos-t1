package com.furb.br;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.Getter;

public class ElectionManager {

	private final Logger LOGGER = Logger.getLogger(ElectionManager.class.getName());
	private static ElectionManager INSTANCE;
	@Getter private List<Node> nodes = new ArrayList<>();
	@Getter private Node coordinator;
	@Getter public boolean isInElection;

	// Private default constructor for a singleton instance
	private ElectionManager() {}

	public static ElectionManager getInstance() {
		return INSTANCE == null ? INSTANCE = new ElectionManager() : INSTANCE;
	}

	/**
	 * Create and add a new Process to the list
	 */
	public void createProcess() {
		LOGGER.log(Level.INFO, "--- createProcess ---");
		var node = new Node();
		addCoordinatorIfFirst(node);
		nodes.add(node);
	}

	/**
	 * Invoke some process to make a request to its coordinator
	 */
	public void invokeRequestCoordinator() {
		LOGGER.log(Level.INFO, "--- invokeRequestCoordinator ---");
		int randomIndex = getRandomIndex();
		if (randomIndex != -1) {
			nodes.get(randomIndex).requestCoordinator();
		}
	}

	/**
	 * Disable the coordinator. First, the method removes the node from the list of
	 * the process, then, sets the object to null
	 */
	public void disableCoordinator() {
		LOGGER.log(Level.INFO, "--- disableCoordinator ---");
		nodes.remove(coordinator);
		coordinator = null;
	}

	/**
	 * Disable a random process (Cannot be the coordinator)
	 */
	public void disableProcess() {
		LOGGER.log(Level.INFO, "--- disableProcess ---");
		var index = getRandomIndex();
		while (isCoordinator(nodes.get(index))) {
			index = getRandomIndex();
		}
		nodes.remove(index);
	}

	private void addCoordinatorIfFirst(Node node) {
		if (nodes.isEmpty()) {
			coordinator = node;
		}
	}

	private int getRandomIndex() {
		if (nodes.isEmpty())
			return -1;
		int randomNum = ThreadLocalRandom.current().nextInt(0, nodes.size());
		return randomNum;
	}

	private boolean isCoordinator(Node node) {
		return coordinator.equals(node) ? true : false;
	}

}