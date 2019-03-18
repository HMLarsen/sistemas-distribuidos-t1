package com.furb.br;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
		var node = new Node();
		var addedCordinator = addCoordinatorIfFirst(node);
		nodes.add(node);
		System.out.println(String.format("[%s] Processo %s criado.", LocalDateTime.now(), node));

		if (addedCordinator)
			System.out.println(String.format("[%s] Processo %s virou coordenador.", LocalDateTime.now(), node));
	}

	/**
	 * Invoke some process to make a request to its coordinator
	 */
	public void invokeRequestCoordinator() {
		int randomIndex = ElectionManagerUtils.getNormalProcessRandomIndex();
		if (randomIndex != -1) {
			coordinator = nodes.get(randomIndex).requestCoordinator();
			isInElection = false;
		}
	}

	/**
	 * Disable the coordinator. First, the method removes the node from the list of
	 * nodes, then, sets the object to null
	 */
	public void disableCoordinator() {
		System.out.println(String.format("[%s] Processo %s não é mais coordenador.", LocalDateTime.now(), coordinator));
		nodes.remove(coordinator);
		coordinator = null;
	}

	/**
	 * Disable a random process (Cannot be the coordinator)
	 */
	public void disableProcess() {
		System.out.println(String.format("[%s] Inicio de remoção de processo do cluster.", LocalDateTime.now()));
		var index = ElectionManagerUtils.getNormalProcessRandomIndex();
		if (index != -1) {
			System.out.println(
					String.format("[%s] Processo %s removido do cluster.", LocalDateTime.now(), nodes.get(index)));
			nodes.remove(index);
		} else {
			System.out.println(String.format("[%s] Processos (não coordenadores) não existentes no cluster.", LocalDateTime.now()));
		}
	}

	private boolean addCoordinatorIfFirst(Node node) {
		if (nodes.isEmpty()) {
			coordinator = node;
			return true;
		}
		return false;
	}

}