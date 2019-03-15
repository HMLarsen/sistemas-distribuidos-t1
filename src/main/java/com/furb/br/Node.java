package com.furb.br;

import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class Node {

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private final Logger LOGGER = Logger.getLogger(ElectionManager.class.getName());
	private int id = SequenceStore.getAndIncrement();

	public void requestCoordinator() {
		/*
		 * Invoke a check request to the coordinator, to see if it's running (not null)
		 */
		LOGGER.log(Level.INFO, "--- Requested the coordinator ---");

		// If the coordinator is disabled, starts an election process.
		if (isCoordinatorDisabled()) {
			startElection();
		}
	}

	private void startElection() {
		/*
		 * 1) P envia mensagem de eleição para todos os processos com IDs maiores 
		 * 2) Se ninguém responde, P vence eleição e torna-se coordenador 
		 * 3) Se algum processo com ID maior responde, ele desiste.
		 */
	}

	private boolean isCoordinatorDisabled() {
		return ElectionManager.getInstance().getCoordinator() == null;
	}

}
