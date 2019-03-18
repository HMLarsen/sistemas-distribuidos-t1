package com.furb.br;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utils class for {@link ElectionManager} singleton.
 */
public class ElectionManagerUtils {

	private static final ElectionManager electionManagerInstance = ElectionManager.getInstance();

	public static List<Node> getSortedList() {
		// Copy to a new List to don't modify the original one.
		List<Node> sortedList = new ArrayList<>(electionManagerInstance.getNodes());
		sortedList.sort(Comparator.comparing(Node::getId));
		return sortedList;
	}

	public static boolean isCoordinatorDisabled() {
		return electionManagerInstance.getCoordinator() == null;
	}

	public static int getNormalProcessRandomIndex() {
		var index = getRandomIndex();
		if (index != -1 && electionManagerInstance.getNodes().size() > 1) {
			while (index != -1 && isCoordinator(electionManagerInstance.getNodes().get(index))) {
				index = getRandomIndex();
			}
			return index;
		} else {
			return -1;
		}
	}

	private static int getRandomIndex() {
		if (electionManagerInstance.getNodes().isEmpty())
			return -1;
		int randomNum = ThreadLocalRandom.current().nextInt(0, electionManagerInstance.getNodes().size());
		return randomNum;
	}

	private static boolean isCoordinator(Node node) {
		return electionManagerInstance.getCoordinator() != null && electionManagerInstance.getCoordinator().equals(node)
				? true
				: false;
	}

}
