package com.practice.ui;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class demo2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String data = "Hammer, Drill, Hammer , Saw, Drill, Hammer, Drill123, Saw";
		System.out.println(Arrays.stream(data.split(","))
				.map(String::trim)
				.filter(s -> !s.matches(".*\\d+.*"))
				.collect(Collectors.groupingBy(Function.identity(),Collectors.counting())));
		
		
		

	}

}
