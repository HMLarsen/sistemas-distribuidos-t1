package com.furb.br;

import lombok.Data;

/**
 * Singleton instance to storage the next process ID
 */
@Data
public class SequenceStore {

	private static volatile int actualID = 0;

	public static int getAndIncrement() {
		return actualID++;
	}
}
