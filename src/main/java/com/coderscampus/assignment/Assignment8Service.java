package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Assignment8Service {

	private ExecutorService cachedService = Executors.newCachedThreadPool();

	private List<CompletableFuture<List<Integer>>> numbers = new ArrayList<>();
	private List<Integer> combinedList = new ArrayList<>();
	private Map<Integer, AtomicInteger> numberCounts = new ConcurrentHashMap<>();

	int numIterations = 1000; // We have 1,000,000 numbers we can get 1,000 at a time, so we need 1,000
								// iterations

	Assignment8 assignment = new Assignment8();

	public void collectAllNumbers() {
		for (int i = 0; i < numIterations; i++) {
			CompletableFuture<List<Integer>> futuresList = CompletableFuture.supplyAsync(() -> assignment.getNumbers(),
					cachedService);
			numbers.add(futuresList);
		}

		while (numbers.stream().filter(CompletableFuture::isDone).count() < numIterations) {
		}

		cachedService.shutdown();

	}

	public void countDistinctNumbers() {
		combinedList = numbers.stream().map(CompletableFuture::join).flatMap(List::stream).toList();

		for (Integer number : combinedList) {
			numberCounts.putIfAbsent(number, new AtomicInteger(0));
			numberCounts.get(number).incrementAndGet();
		}
	}

	public void displayDistinctNumbers() {
		for (int i = 0; i < 15; i++) {
			Integer count = numberCounts.getOrDefault(i, new AtomicInteger(0)).get();
			System.out.println(i + "=" + count);
		}
	}

}
