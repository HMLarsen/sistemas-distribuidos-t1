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

	private final ElectionManager electionManagerInstance = ElectionManager.getInstance();
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private final Logger LOGGER = Logger.getLogger(ElectionManager.class.getName());
	private int id = SequenceStore.getAndIncrement();
	private boolean active;

	public Node requestCoordinator() {
		System.out.println(String.format("[%s] Processo %s fez uma request ao coordenador %s.", LocalDateTime.now(),
				this, electionManagerInstance.getCoordinator()));
		// If the coordinator is disabled, starts an election process.
		if (ElectionManagerUtils.isCoordinatorDisabled() && !electionManagerInstance.isInElection()) {
			System.out.println(String.format("[%s] Coordenador n�o respondeu, processo de Elei��o iniciado!",
					LocalDateTime.now()));
			Node newCoordinator = startElection();
			System.out.println(String.format("[%s] Processo de elei��o finalizado, %s � o novo coordenador.",
					LocalDateTime.now(), newCoordinator));
			return newCoordinator;
		} else {
			System.out.println(String.format("[%s] Coordenador respondeu com sucesso.", LocalDateTime.now()));
			return electionManagerInstance.getCoordinator();
		}
	}

	private Node startElection() {
		electionManagerInstance.isInElection = true;
		// 1) P envia mensagem de elei��o para todos os processos com IDs maiores
		// 2) Se ningu�m responde, P vence elei��o e torna-se coordenador
		// 3) Se algum processo com ID maior responde, ele desiste.
		return getCoordinator(this);
	}

	public boolean sendMessage() {
		return active;
	};

	private Node getCoordinator(Node actualNode) {
		var aheadNodes = ElectionManagerUtils.getSortedList().stream().filter(n -> n.id > actualNode.id)
				.collect(Collectors.toList());
		var collected = aheadNodes.stream().map(n -> {
			return n.sendMessage();
		}).collect(Collectors.toList());
		if (collected.isEmpty()) {
			return this;
		}
		return getCoordinator(aheadNodes.get(0));
	}

}
