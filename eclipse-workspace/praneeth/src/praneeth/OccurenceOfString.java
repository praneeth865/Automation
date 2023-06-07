package praneeth;

import java.util.HashMap;
import java.util.Map;

public class OccurenceOfString {
	public void characterCount(String s) {
		Map<Character,Integer> countMap = new HashMap<>();
	
		for(int i=0;i<s.length();i++) {
			
			if(!countMap.containsKey(s.charAt(i)))
				countMap.put(s.charAt(i),1);
			else {
			int count = countMap.get(s.charAt(i));
			countMap.put(s.charAt(i), count);
			}
			
				
		}
		
	}
	

}
