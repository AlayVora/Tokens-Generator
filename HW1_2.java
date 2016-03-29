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

public class HW1_2 {
	static HashMap<String, Integer> finalTokens = new HashMap<String, Integer>();
	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		//String path  = "C://Users//Alay//workspace//IRHomeWork1//Cranfield//Cranfield"; //Provide your own path
		
		String path  = args[0];
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
        				 word = word.toLowerCase();
        				 word = removeNumbers(word);
        				 word = removeTags(word);
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
        //System.out.println("Number of distinct stems in the carnfield text collection: "+ count);
        System.out.println("Number of distinct stems in the carnfield text collection: "+ uniqueWords);
        System.out.println();
        System.out.println("Number of stems that occur once in the carnfield text collection: "+ occurOnce);
        System.out.println();
        System.out.println("The 30 most frequent stems in the Carnfield text collection");
        frequentWord();
        int avg = agvWordPerDocument(noOfDocuments);
        System.out.println();
        System.out.println("Average number of word stems per documents: "+ avg);
        System.out.println();
        
        long endTime = System.currentTimeMillis();
        System.out.println("Time required to acquire text characteristic (in sec): " + ((endTime - startTime) / 1000));
	}
	
	
    private static int agvWordPerDocument(int noOfDocuments) {
		int totalTokens = noOfTokens();
		int totalDocuments = noOfDocuments;
		return (totalTokens / totalDocuments);
	}


	private static void frequentWord() {
    	Iterator it = finalTokens.entrySet().iterator();
        int count = 0;
        String word;
        int frequency;
        System.out.println("Stems => Frequency");
        while (it.hasNext() && count < 30) {
            Map.Entry pair = (Map.Entry) it.next();
            	word = (String) pair.getKey();
            	frequency = (Integer) pair.getValue();
            	System.out.println(word + "=>" + frequency);
            	count++;
            }	
        
	}


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


	private static int noOfUniqueWords() {
		return finalTokens.size();
	}


	public static int noOfTokens() {
        Iterator it = finalTokens.entrySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            count += (Integer) pair.getValue();
        }
        
        return count;
    }


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
    
	private static void printHashMap() {
		for (String name: finalTokens.keySet()){
            String key =name.toString();
            String value = finalTokens.get(name).toString();  
            System.out.println(key + " " + value); 
		} 
		
	}

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
						
						addToHashMap(Stem(newToken2));
					}
				}
				else{
					addToHashMap(Stem(newToken1));
				}
			}
		}
		
		else if(word.contains("-")){
			newTokens3 = word.split("-");
			for(String newToken3 : newTokens3){
				addToHashMap(Stem(newToken3));
			}
		}
		
		else{
			addToHashMap(Stem(word));
		}
		
		
	}
	
	public static String Stem(String word) {
        Stemmer s = new Stemmer();
        char[] w = word.toCharArray();
        int j = w.length;
        for (int c = 0; c < j; c++) {
            s.add(w[c]);
        }
        s.stem();
        word = s.toString();
        return word;
    }

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

	private static String removeTags(String word) {
		if (word.equals("")) {
            return word;
        }
        word = word.replaceAll("<[^>]+>", "");
        return word;
	}

	private static String removeNumbers(String word) {
		 if (word.equals("")) {
	            return word;
	        }
	        word = word.replaceAll("[0-9]", "");
	        return word;
	}
}

class Stemmer {

    private char[] b;
    private int i, /* offset into b */
            i_end, /* offset to end of stemmed word */
            j, k;
    private static final int INC = 50;
    /* unit of size whereby b is increased */

    public Stemmer() {
        b = new char[INC];
        i = 0;
        i_end = 0;
    }

    /**
     * Add a character to the word being stemmed. When you are finished adding
     * characters, you can call stem(void) to stem the word.
     */
    public void add(char ch) {
        if (i == b.length) {
            char[] new_b = new char[i + INC];
            for (int c = 0; c < i; c++) {
                new_b[c] = b[c];
            }
            b = new_b;
        }
        b[i++] = ch;
    }

    /**
     * Adds wLen characters to the word being stemmed contained in a portion of
     * a char[] array. This is like repeated calls of add(char ch), but faster.
     */
    public void add(char[] w, int wLen) {
        if (i + wLen >= b.length) {
            char[] new_b = new char[i + wLen + INC];
            for (int c = 0; c < i; c++) {
                new_b[c] = b[c];
            }
            b = new_b;
        }
        for (int c = 0; c < wLen; c++) {
            b[i++] = w[c];
        }
    }

    /**
     * After a word has been stemmed, it can be retrieved by toString(), or a
     * reference to the internal buffer can be retrieved by getResultBuffer and
     * getResultLength (which is generally more efficient.)
     */
    public String toString() {
        return new String(b, 0, i_end);
    }

    /**
     * Returns the length of the word resulting from the stemming process.
     */
    public int getResultLength() {
        return i_end;
    }

    /**
     * Returns a reference to a character buffer containing the results of the
     * stemming process. You also need to consult getResultLength() to determine
     * the length of the result.
     */
    public char[] getResultBuffer() {
        return b;
    }

    /* cons(i) is true <=> b[i] is a consonant. */
    private final boolean cons(int i) {
        switch (b[i]) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return false;
            case 'y':
                return (i == 0) ? true : !cons(i - 1);
            default:
                return true;
        }
    }

    /* m() measures the number of consonant sequences between 0 and j. if c is
     a consonant sequence and v a vowel sequence, and <..> indicates arbitrary
     presence,

     <c><v>       gives 0
     <c>vc<v>     gives 1
     <c>vcvc<v>   gives 2
     <c>vcvcvc<v> gives 3
     ....
     */
    private final int m() {
        int n = 0;
        int i = 0;
        while (true) {
            if (i > j) {
                return n;
            }
            if (!cons(i)) {
                break;
            }
            i++;
        }
        i++;
        while (true) {
            while (true) {
                if (i > j) {
                    return n;
                }
                if (cons(i)) {
                    break;
                }
                i++;
            }
            i++;
            n++;
            while (true) {
                if (i > j) {
                    return n;
                }
                if (!cons(i)) {
                    break;
                }
                i++;
            }
            i++;
        }
    }

    /* vowelinstem() is true <=> 0,...j contains a vowel */
    private final boolean vowelinstem() {
        int i;
        for (i = 0; i <= j; i++) {
            if (!cons(i)) {
                return true;
            }
        }
        return false;
    }

    /* doublec(j) is true <=> j,(j-1) contain a double consonant. */
    private final boolean doublec(int j) {
        if (j < 1) {
            return false;
        }
        if (b[j] != b[j - 1]) {
            return false;
        }
        return cons(j);
    }

    /* cvc(i) is true <=> i-2,i-1,i has the form consonant - vowel - consonant
     and also if the second c is not w,x or y. this is used when trying to
     restore an e at the end of a short word. e.g.

     cav(e), lov(e), hop(e), crim(e), but
     snow, box, tray.

     */
    private final boolean cvc(int i) {
        if (i < 2 || !cons(i) || cons(i - 1) || !cons(i - 2)) {
            return false;
        }
        {
            int ch = b[i];
            if (ch == 'w' || ch == 'x' || ch == 'y') {
                return false;
            }
        }
        return true;
    }

    private final boolean ends(String s) {
        int l = s.length();
        int o = k - l + 1;
        if (o < 0) {
            return false;
        }
        for (int i = 0; i < l; i++) {
            if (b[o + i] != s.charAt(i)) {
                return false;
            }
        }
        j = k - l;
        return true;
    }

    /* setto(s) sets (j+1),...k to the characters in the string s, readjusting
     k. */
    private final void setto(String s) {
        int l = s.length();
        int o = j + 1;
        for (int i = 0; i < l; i++) {
            b[o + i] = s.charAt(i);
        }
        k = j + l;
    }

    /* r(s) is used further down. */
    private final void r(String s) {
        if (m() > 0) {
            setto(s);
        }
    }

    /* step1() gets rid of plurals and -ed or -ing. e.g.

     caresses  ->  caress
     ponies    ->  poni
     ties      ->  ti
     caress    ->  caress
     cats      ->  cat

     feed      ->  feed
     agreed    ->  agree
     disabled  ->  disable

     matting   ->  mat
     mating    ->  mate
     meeting   ->  meet
     milling   ->  mill
     messing   ->  mess

     meetings  ->  meet

     */
    private final void step1() {
        if (b[k] == 's') {
            if (ends("sses")) {
                k -= 2;
            } else if (ends("ies")) {
                setto("i");
            } else if (b[k - 1] != 's') {
                k--;
            }
        }
        if (ends("eed")) {
            if (m() > 0) {
                k--;
            }
        } else if ((ends("ed") || ends("ing")) && vowelinstem()) {
            k = j;
            if (ends("at")) {
                setto("ate");
            } else if (ends("bl")) {
                setto("ble");
            } else if (ends("iz")) {
                setto("ize");
            } else if (doublec(k)) {
                k--;
                {
                    int ch = b[k];
                    if (ch == 'l' || ch == 's' || ch == 'z') {
                        k++;
                    }
                }
            } else if (m() == 1 && cvc(k)) {
                setto("e");
            }
        }
    }

    /* step2() turns terminal y to i when there is another vowel in the stem. */
    private final void step2() {
        if (ends("y") && vowelinstem()) {
            b[k] = 'i';
        }
    }

    /* step3() maps double suffices to single ones. so -ization ( = -ize plus
     -ation) maps to -ize etc. note that the string before the suffix must give
     m() > 0. */
    private final void step3() {
        if (k == 0) {
            return;
        } /* For Bug 1 */ switch (b[k - 1]) {
            case 'a':
                if (ends("ational")) {
                    r("ate");
                    break;
                }
                if (ends("tional")) {
                    r("tion");
                    break;
                }
                break;
            case 'c':
                if (ends("enci")) {
                    r("ence");
                    break;
                }
                if (ends("anci")) {
                    r("ance");
                    break;
                }
                break;
            case 'e':
                if (ends("izer")) {
                    r("ize");
                    break;
                }
                break;
            case 'l':
                if (ends("bli")) {
                    r("ble");
                    break;
                }
                if (ends("alli")) {
                    r("al");
                    break;
                }
                if (ends("entli")) {
                    r("ent");
                    break;
                }
                if (ends("eli")) {
                    r("e");
                    break;
                }
                if (ends("ousli")) {
                    r("ous");
                    break;
                }
                break;
            case 'o':
                if (ends("ization")) {
                    r("ize");
                    break;
                }
                if (ends("ation")) {
                    r("ate");
                    break;
                }
                if (ends("ator")) {
                    r("ate");
                    break;
                }
                break;
            case 's':
                if (ends("alism")) {
                    r("al");
                    break;
                }
                if (ends("iveness")) {
                    r("ive");
                    break;
                }
                if (ends("fulness")) {
                    r("ful");
                    break;
                }
                if (ends("ousness")) {
                    r("ous");
                    break;
                }
                break;
            case 't':
                if (ends("aliti")) {
                    r("al");
                    break;
                }
                if (ends("iviti")) {
                    r("ive");
                    break;
                }
                if (ends("biliti")) {
                    r("ble");
                    break;
                }
                break;
            case 'g':
                if (ends("logi")) {
                    r("log");
                    break;
                }
        }
    }

    /* step4() deals with -ic-, -full, -ness etc. similar strategy to step3. */
    private final void step4() {
        switch (b[k]) {
            case 'e':
                if (ends("icate")) {
                    r("ic");
                    break;
                }
                if (ends("ative")) {
                    r("");
                    break;
                }
                if (ends("alize")) {
                    r("al");
                    break;
                }
                break;
            case 'i':
                if (ends("iciti")) {
                    r("ic");
                    break;
                }
                break;
            case 'l':
                if (ends("ical")) {
                    r("ic");
                    break;
                }
                if (ends("ful")) {
                    r("");
                    break;
                }
                break;
            case 's':
                if (ends("ness")) {
                    r("");
                    break;
                }
                break;
        }
    }

    /* step5() takes off -ant, -ence etc., in context <c>vcvc<v>. */
    private final void step5() {
        if (k == 0) {
            return;
        } /* for Bug 1 */ switch (b[k - 1]) {
            case 'a':
                if (ends("al")) {
                    break;
                }
                return;
            case 'c':
                if (ends("ance")) {
                    break;
                }
                if (ends("ence")) {
                    break;
                }
                return;
            case 'e':
                if (ends("er")) {
                    break;
                }
                return;
            case 'i':
                if (ends("ic")) {
                    break;
                }
                return;
            case 'l':
                if (ends("able")) {
                    break;
                }
                if (ends("ible")) {
                    break;
                }
                return;
            case 'n':
                if (ends("ant")) {
                    break;
                }
                if (ends("ement")) {
                    break;
                }
                if (ends("ment")) {
                    break;
                }
                /* element etc. not stripped before the m */
                if (ends("ent")) {
                    break;
                }
                return;
            case 'o':
                if (ends("ion") && j >= 0 && (b[j] == 's' || b[j] == 't')) {
                    break;
                }
                /* j >= 0 fixes Bug 2 */
                if (ends("ou")) {
                    break;
                }
                return;
            /* takes care of -ous */
            case 's':
                if (ends("ism")) {
                    break;
                }
                return;
            case 't':
                if (ends("ate")) {
                    break;
                }
                if (ends("iti")) {
                    break;
                }
                return;
            case 'u':
                if (ends("ous")) {
                    break;
                }
                return;
            case 'v':
                if (ends("ive")) {
                    break;
                }
                return;
            case 'z':
                if (ends("ize")) {
                    break;
                }
                return;
            default:
                return;
        }
        if (m() > 1) {
            k = j;
        }
    }

    /* step6() removes a final -e if m() > 1. */
    private final void step6() {
        j = k;
        if (b[k] == 'e') {
            int a = m();
            if (a > 1 || a == 1 && !cvc(k - 1)) {
                k--;
            }
        }
        if (b[k] == 'l' && doublec(k) && m() > 1) {
            k--;
        }
    }

    /**
     * Stem the word placed into the Stemmer buffer through calls to add().
     * Returns true if the stemming process resulted in a word different from
     * the input. You can retrieve the result with
     * getResultLength()/getResultBuffer() or toString().
     */
    public void stem() {
        k = i - 1;
        if (k > 1) {
            step1();
            step2();
            step3();
            step4();
            step5();
            step6();
        }
        i_end = k + 1;
        i = 0;
    }

    /**
     * Test program for demonstrating the Stemmer. It reads text from a a list
     * of files, stems each word, and writes the result to standard output. Note
     * that the word stemmed is expected to be in lower case: forcing lower case
     * must be done outside the Stemmer class. Usage: Stemmer file-name
     * file-name ...
     */
   
}
