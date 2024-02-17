package com.coderscampus.assignment;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Assignment8Application {
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		Assignment8 assignment = new Assignment8();

		CompletableFuture<Map<Integer, Integer>> resultFuture = assignment.countDistinctNumbers();

		Map<Integer, Integer> numberCountMap = resultFuture.join();
		
		numberCountMap.forEach((key, value) -> System.out.println(key + "=" + value));
		
		
		assignment.shutdownExecutor();
	}
}
