package com.coderscampus.assignment;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Assignment8Application {
	public static void main(String[] args) {

		Assignment8 assignment = new Assignment8();

		ExecutorService executor = Executors.newFixedThreadPool(4);

		CompletableFuture<Map<Integer, Integer>> resultFuture = assignment.countDistinctNumbers();

		try {
			Map<Integer, Integer> numberCountMap = resultFuture.get();
			numberCountMap.forEach((key, value) -> System.out.println(key + "=" + value));
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
		}
	}
}
