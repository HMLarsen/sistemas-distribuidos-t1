package com.furb.br;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import lombok.Getter;

public class ElectionManager {

	private static ElectionManager INSTANCE;
	@Getter
	private List<Node> nodes = new ArrayList<>();
	@Getter
	private Node coordinator;
	@Getter
	public boolean isInElection;

	// Private default constructor for a singleton instance
	private ElectionManager() {
	}

	public static ElectionManager getInstance() {
		return INSTANCE == null ? INSTANCE = new ElectionManager() : INSTANCE;
	}

	/**
	 * Create and add a new Process to the list
	 */
	public void createProcess() {
		System.out.println("createProcess");
		var node = new Node();
		if (addCoordinatorIfFirst(node))
			System.out.println(String.format("[%s] Processo %s virou coordenador.", LocalDateTime.now(), node));
		
		nodes.add(node);
		System.out.println(String.format("[%s] Processo %s criado.", LocalDateTime.now(), node));
	}

	/**
	 * Invoke some process to make a request to its coordinator
	 */
	public void invokeRequestCoordinator() {
		System.out.println("invokeRequestCoordinator");
		int randomIndex = getNormalProcessRandomIndex();
		if (randomIndex != -1) {
			coordinator = nodes.get(randomIndex).requestCoordinator();
			isInElection = false;
		}
	}

	/**
	 * Disable the coordinator. First, the method removes the node from the list of
	 * the process, then, sets the object to null
	 */
	public void disableCoordinator() {
		System.out.println("disableCoordinator");
		System.out.println(String.format("[%s] Processo %s não é mais coordenador.", LocalDateTime.now(), coordinator));
		nodes.remove(coordinator);
		coordinator = null;
	}

	/**
	 * Disable a random process (Cannot be the coordinator)
	 */
	public void disableProcess() {
		System.out.println("disableProcess");
		System.out.println(String.format("[%s] Inicio de remoção de processo do cluster.", LocalDateTime.now()));
		var index = getNormalProcessRandomIndex();
		if (index != -1) {
			System.out.println(
					String.format("[%s] Processo %s removido do cluster.", LocalDateTime.now(), nodes.get(index)));
			nodes.remove(index);
		} else {
			System.out.println(String.format("[%s] Processos normais não existentes no cluster.", LocalDateTime.now()));
		}
	}

	public List<Node> getSortedList() {
		// Copy to a new List do don't modify the original one.
		List<Node> sortedList = new ArrayList<>(nodes);
		sortedList.sort(Comparator.comparing(Node::getId));
		return sortedList;
	}

	public boolean isCoordinatorDisabled() {
		return getCoordinator() == null;
	}

	private int getNormalProcessRandomIndex() {
		var index = getRandomIndex();
		if (index != -1 && nodes.size() > 1) {
			while (index != -1 && isCoordinator(nodes.get(index))) {
				index = getRandomIndex();
			}
			return index;
		} else {
			return -1;
		}
	}

	private boolean addCoordinatorIfFirst(Node node) {
		if (nodes.isEmpty()) {
			coordinator = node;
			return true;
		}
		return false;
	}

	private int getRandomIndex() {
		if (nodes.isEmpty())
			return -1;
		int randomNum = ThreadLocalRandom.current().nextInt(0, nodes.size());
		return randomNum;
	}

	private boolean isCoordinator(Node node) {
		return coordinator != null && coordinator.equals(node) ? true : false;
	}

}