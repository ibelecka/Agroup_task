package com.book.wordCounter;

import java.io.*;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.*;

public class App {
	public static void main( String[] args ) {
		try {  
			File file = new File("book.txt");     
			FileReader fileReader = new FileReader(file);    
			BufferedReader bufferReader = new BufferedReader(fileReader);    
			Map<String, Integer> counterWord = new HashMap<>();
			String line;  
			while ((line=bufferReader.readLine())!=null)  
			{  
				if (!line.isEmpty())
				{								
					String[] words = line.split("\\s+"); //split line into separate words
					for (String word : words ) {
						word.replaceAll("[^a-zA-Z]", "");						
						counterWord.compute(word, (k, v) -> v == null ? 1 : v + 1); } //+1 if word exists in map
				}
			}  
			fileReader.close();         
	        Comparator<Entry<String, Integer>> valueComparator  = new Comparator<Entry<String,Integer>>() {	            
		         @Override
		         public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
			             Integer v1 = e1.getValue();
			             Integer v2 = e2.getValue();
			             return v2.compareTo(v1);
			         }
			};			     
		    Set<Entry<String, Integer>> entries = counterWord.entrySet();
		    List<Entry<String, Integer>> listOfEntries = new ArrayList<Entry<String, Integer>>(entries);
		    Collections.sort(listOfEntries, valueComparator);		   
		    LinkedHashMap<String, Integer> sortedByValue1 = new LinkedHashMap<String, Integer>(listOfEntries.size());    
		    	for(Entry<String, Integer> entry : listOfEntries){
			        sortedByValue1.put(entry.getKey(), entry.getValue());
		    	}
			System.out.println("Ordered word counter without streams: " + Arrays.asList(sortedByValue1));
			System.out.println();			
	        Map<String, Integer> sortedByValue2 = new LinkedHashMap<>();
	        counterWord.entrySet().stream()
	                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
	                .forEachOrdered(x -> sortedByValue2.put(x.getKey(), x.getValue()));
	        System.out.println("Ordered word counter with streams: " + sortedByValue2);			
		}  
		catch(IOException e) {  
			e.printStackTrace();  
		}  
    }
}

