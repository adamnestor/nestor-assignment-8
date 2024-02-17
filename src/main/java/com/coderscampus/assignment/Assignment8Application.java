package com.coderscampus.assignment;

public class Assignment8Application {
	public static void main(String[] args) {

		Assignment8Service a8 = new Assignment8Service();

		a8.collectAllNumbers();
		a8.countDistinctNumbers();
		a8.displayDistinctNumbers();
	}
}