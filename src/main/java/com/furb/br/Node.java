package com.furb.br;

import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(of = { "id" })
@EqualsAndHashCode(of = { "id" })
public class Node {

	private static final ElectionManager electionManagerInstance = ElectionManager.getInstance();
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private final Logger LOGGER = Logger.getLogger(ElectionManager.class.getName());
	private int id = SequenceStore.getAndIncrement();

	public Node requestCoordinator() {
		System.out.println(String.format("[%s] Processo %s fez uma request ao coordenador %s.", LocalDateTime.now(),
				this, electionManagerInstance.getCoordinator()));
		// If the coordinator is disabled, starts an election process.
		if (electionManagerInstance.isCoordinatorDisabled() && !electionManagerInstance.isInElection()) {
			System.out.println(String.format("[%s] Coordenador não respondeu, processo de Eleição iniciado!",
					LocalDateTime.now()));
			Node newCoordinator = startElection();
			System.out.println(String.format("[%s] Processo de eleição finalizado, %s é o novo coordenador.",
					LocalDateTime.now(), newCoordinator));
			return newCoordinator;
		} else {
			System.out.println(String.format("[%s] Coordenador respondeu com sucesso.", LocalDateTime.now()));
			return electionManagerInstance.getCoordinator();
		}
	}

	private Node startElection() {
		electionManagerInstance.isInElection = true;
		// 1) P envia mensagem de eleição para todos os processos com IDs maiores
		// 2) Se ninguém responde, P vence eleição e torna-se coordenador
		// 3) Se algum processo com ID maior responde, ele desiste.
		return getCoordinator(this);
	}

	private Node getCoordinator(Node actualNode) {
		var aheadNodes = electionManagerInstance.getSortedList().stream().filter(n -> n.id > actualNode.id)
				.collect(Collectors.toList());
		// TODO: "send" a request to the others nodes, if someone responds, jump to the next node
		if (aheadNodes.isEmpty()) {
			return this;
		}
		return getCoordinator(aheadNodes.get(0));
	}

}
