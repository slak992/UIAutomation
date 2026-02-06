package com.practice.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Practice {

	public static void main(String[] args) throws IOException  {
		
//		Path filePath = Path.of(System.getProperty("user.dir")+"\\src\\test\\java\\com\\practice\\ui\\test.txt");
//		List<String> contents =Files.readAllLines(filePath);
//		Long count =contents.stream().flatMap(line -> Arrays.stream(line.split("\\s"))).filter(word -> word.equals("sreelakshmi")).count();
//		System.out.println(count);
		
//		Path filePath = Path.of(System.getProperty("user.dir")+"\\src\\test\\java\\com\\practice\\ui\\test.txt");
//		String fileContent = new String(Files.readAllBytes(filePath));
//		System.out.println(fileContent);
//		
//		Long count = Arrays.stream(fileContent.split("\\s")).filter(word -> word.equals("sreelakshmir")).count();
//		System.out.println(count);
		
//		String s = "Sree";
//		String reverseString="";
//		for(int i=s.length()-1;i>=0;i--)
//		{
//			reverseString+=s.charAt(i);
//		}
//		System.out.println(reverseString);
		
//		int[] numArr = {10, 20, 35, 50, 50, 44};
//		
//		List<Integer> arrList = Arrays.stream(numArr).boxed().collect(Collectors.toList());
//		
//		Set<Integer> listSet = new LinkedHashSet<>(arrList);
//		System.out.println(listSet);
//		String checkString = "malayalakm";
//		
//		String reverseString = "";
//		
//		for(int i=checkString.length()-1;i>=0;i--)
//		{
//			reverseString+=checkString.charAt(i);
//		}
//		if(checkString.equals(reverseString))
//		{
//			System.out.println("Palindrome");
//		}
//		else
//		{
//			System.out.println("Not Palindrome");
//		}
		String checkString = "stress";
		Map<Character,Integer> cmpa = new LinkedHashMap<>();
		for(int i=0;i<checkString.length();i++)
		{
			if(cmpa.containsKey(checkString.charAt(i)))
			{
				cmpa.put(checkString.charAt(i), cmpa.get(checkString.charAt(i))+1);
			}
			else
			{
				cmpa.put(checkString.charAt(i), 1);
			}
			
			
		}
//		System.out.println(cmpa);
//		for(Map.Entry<Character, Integer> entry : cmpa.entrySet())
//		{
//			if(entry.getValue()==1)
//			{
//				System.out.println(entry.getKey());
//				break;
//			}
//		}
		
		int[] nums = {1,2,3,4,5};
		int sumnum =Arrays.stream(nums).boxed().filter(x -> x%2==0).map(x->x*x).reduce(0, Integer::sum);
		System.out.println(sumnum);
	}

}
