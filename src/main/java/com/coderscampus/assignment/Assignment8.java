package com.coderscampus.assignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Assignment8 {
	private List<Integer> numbers = null;
	private AtomicInteger i = new AtomicInteger(0);

	public Assignment8() {
		try {
			// Make sure you download the output.txt file for Assignment 8
			// and place the file in the root of your Java project
			numbers = Files.readAllLines(Paths.get("output.txt")).stream().map(n -> Integer.parseInt(n))
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will return the numbers that you'll need to process from the list
	 * of Integers. However, it can only return 1000 records at a time. You will
	 * need to call this method 1,000 times in order to retrieve all 1,000,000
	 * numbers from the list
	 * 
	 * @return Integers from the parsed txt file, 1,000 numbers at a time
	 */
	public List<Integer> getNumbers() {
		int start, end;
		synchronized (i) {
			start = i.get();
			end = i.addAndGet(1000);

			System.out.println("Starting to fetch records " + start + " to " + (end));
			
		}
		// force thread to pause for half a second to simulate actual Http / API traffic
		// delay
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		List<Integer> newList = new ArrayList<>();
		IntStream.range(start, end).forEach(n -> {
			newList.add(numbers.get(n));
		});
		System.out.println("Done Fetching records " + start + " to " + (end));
		return newList;
	}

	// This method will count distinct numbers stored in a HashMap that have been
	// returned from the getNumbers method

	private int numOfIterations = 1000; // We know that we have 1,000,000 numbers to return but only 1,000 at a time. So
										// we need 1,000 iterations of the method to return all of the numbers.

	public CompletableFuture<Map<Integer, Integer>> countDistinctNumbers() {
		return CompletableFuture.supplyAsync(() -> {
			Map<Integer, Integer> numberCountMap = new HashMap<>();
			for (int i = 0; i < numOfIterations; i++) {
				List<Integer> numbersList = getNumbers();
				for (Integer number : numbersList) {
					numberCountMap.put(number, numberCountMap.getOrDefault(number, 0) + 1);
				}
			}
			return numberCountMap;
		});
	}

}