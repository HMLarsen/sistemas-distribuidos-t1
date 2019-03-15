package com.furb.br;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node {

	private int id;
	private boolean isCordinator;

	public Node(int id) {
		super();
		this.id = id;
	}
}
