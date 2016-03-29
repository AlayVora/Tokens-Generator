import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.io.File;

public class HW1_1 {
	static HashMap<String, Integer> finalTokens = new HashMap<String, Integer>();
	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		//String path  = "C://Users//Alay//workspace//IRHomeWork1//Cranfield//Cranfield"; //Provide your own path
		
		String path = args[0];
		File dir = new File(path);
        File[] files = dir.listFiles();
        int noOfDocuments=0;
        for (File f : files) {
        	if (f.isFile()) {
        		BufferedReader inputStream = new BufferedReader(new FileReader(f));
        		 String line;
        		 while ((line = inputStream.readLine()) != null) {
        			 String words[] = line.split(" ");
        			 for (int i = 0; i < words.length; i++) {
        				 String word = words[i];
        				 
        				 word = word.toLowerCase();        //Convert Lower case
        				 word = removeNumbers(word);       //Remove Numbers
        				 word = removeTags(word);          //Remove SGML tags
        				 
        				 //Remove Special Characters
        				 word = removeSpecialChar('!', word); 
        				 word = removeSpecialChar('@', word);
        				 word = removeSpecialChar('#', word);
        				 word = removeSpecialChar('$', word);
                         word = removeSpecialChar('%', word);
                         word = removeSpecialChar('^', word);
                         word = removeSpecialChar('&', word);
                         word = removeSpecialChar('*', word);
                         word = removeSpecialChar('(', word);
                         word = removeSpecialChar(')', word);
        				 word = removeSpecialChar('_', word);
                         word = removeSpecialChar('{', word);
                         word = removeSpecialChar('}', word);
                         word = removeSpecialChar('[', word);
                         word = removeSpecialChar(']', word);
                         word = removeSpecialChar('|', word);
                         word = removeSpecialChar('\\',word);
                         word = removeSpecialChar('/', word);
                         word = removeSpecialChar('.', word);
                         word = removeSpecialChar('\'',word);
                         word = removeSpecialChar('+', word);
                         word = removeSpecialChar(';', word);
                         word = removeSpecialChar(':', word);
                         
                         //Remove Dash and Comma
                         removeDashAndComma(word);
        			 }
        		 }
        	}
        	noOfDocuments++;
        }
        finalTokens = sortByValue(finalTokens);
        //printHashMap();
        int count = noOfTokens();
        int uniqueWords = noOfUniqueWords();
        int occurOnce = noOfOccurOnce();
        System.out.println("Number of Tokens in the carnfield text collection: "+ count);
        System.out.println();
        System.out.println("Number of Unique Words in the carnfield text collection: "+ uniqueWords);
        System.out.println();
        System.out.println("Number of Words that occur once in the carnfield text collection: "+ occurOnce);
        System.out.println();
        System.out.println("The 30 most frequent words in the Carnfield text collection");
        frequentWord();
        int avg = agvWordPerDocument(noOfDocuments);
        System.out.println();
        System.out.println("Average number of word tokens per documents: "+ avg);
        System.out.println();
        
        long endTime = System.currentTimeMillis();
        System.out.println("Time required to acquire text characteristic (in sec): " + ((endTime - startTime) / 1000));
        
	}
	
	// Method for finding Average word per document
    private static int agvWordPerDocument(int noOfDocuments) {
		int totalTokens = noOfTokens();
		int totalDocuments = noOfDocuments;
		return (totalTokens / totalDocuments);
	}


    // Method for Finding top 30 frequent words
	private static void frequentWord() {
    	Iterator it = finalTokens.entrySet().iterator();
        int count = 0;
        String word;
        int frequency;
        System.out.println("Words => Frequency");
        while (it.hasNext() && count < 30) {
            Map.Entry pair = (Map.Entry) it.next();
            	word = (String) pair.getKey();
            	frequency = (Integer) pair.getValue();
            	System.out.println(word + "=>" + frequency);
            	count++;
            }	
        
	}

	
	//Method for finding words with just one occurrence
	private static int noOfOccurOnce() {
    	Iterator it = finalTokens.entrySet().iterator();
        int once = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if((Integer) pair.getValue() == 1){
            	once++;
            }
        }
        return once;
	}

	
	//Method for finding no of Unique Words
	private static int noOfUniqueWords() {
		return finalTokens.size();
	}

	
	//Method for finding no of tokens
	public static int noOfTokens() {
        Iterator it = finalTokens.entrySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            count += (Integer) pair.getValue();
        }
        
        return count;
    }


	
	// Storing the values in Hash map
	public static HashMap sortByValue(HashMap unsortMap) {

        List list = new LinkedList(unsortMap.entrySet());

        Collections.sort(list, new Comparator() {
            public int compare(Object o2, Object o1) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                		.compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        HashMap sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }  
    
	//  For printing HashMap
	private static void printHashMap() {
		for (String name: finalTokens.keySet()){
            String key =name.toString();
            String value = finalTokens.get(name).toString();  
            System.out.println(key + " " + value); 
		} 
		
	}

	
	// Remove Dash and Comma
	private static void removeDashAndComma(String word) {
		String[] newTokens1 = null;
		String[] newTokens2 = null;
		String[] newTokens3 = null;
		if(word.contains(",")){ //If the word contains comma
			newTokens1 = word.split(",");
			for(String newToken1 : newTokens1 ){
				if(newToken1.contains("-")){ //If the split word contains dash
					newTokens2 = newToken1.split("-");
					for(String newToken2 : newTokens2){
						addToHashMap(newToken2);
					}
				}
				else{
					addToHashMap(newToken1);
				}
			}
		}
		
		else if(word.contains("-")){
			newTokens3 = word.split("-");
			for(String newToken3 : newTokens3){
				addToHashMap(newToken3);
			}
		}
		
		else{
			addToHashMap(word);
		}
		
		
	}

	// Add tokes to Hash map
	private static void addToHashMap(String newToken) {
		if(newToken.equals("")){
			return;
		}
		if(finalTokens.containsKey(newToken)){
			int value = finalTokens.get(newToken)+1;
			finalTokens.put(newToken, value);
		}
		else{
			finalTokens.put(newToken, 1);
		}	
	}

	// Remove special characters
	private static String removeSpecialChar(char c, String word) {
		if (word.equals("")) {
            return word;
        }
		String newWord = "";
		if(word.contains(String.valueOf(c))){
			char[] character = word.toCharArray();
			for (int i = 0; i < character.length; i++) {
                if (character[i] == c) {
                    continue;
                }
                newWord = newWord + String.valueOf(character[i]);
            }
			return newWord;
		}
		else{
			return word;
		}
		
	}

	//Remove Tags
	private static String removeTags(String word) {
		if (word.equals("")) {
            return word;
        }
        word = word.replaceAll("<[^>]+>", "");
        return word;
	}
	
	//Remove Numbers
	private static String removeNumbers(String word) {
		 if (word.equals("")) {
	            return word;
	        }
	        word = word.replaceAll("[0-9]", "");
	        return word;
	}
}
