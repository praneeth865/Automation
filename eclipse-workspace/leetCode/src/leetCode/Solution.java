package leetCode;

public class Solution {
	public static boolean isPalindrome(String s) {
        char[] ch=s.toCharArray();
        int i=0;
        int j=s.length()-1;
        while(i<j){
            char first=s.charAt(i);
            char last=s.charAt(j);
            if(!Character.isLetterOrDigit(first))
            {
                i++;
                first=s.charAt(i);
            }
            if(!Character.isLetterOrDigit(last)){
                j--;
                last=s.charAt(j);
            }
            if(Character.toLowerCase(first)!=Character.toLowerCase(last))
                return false;
            else{
            i++;
            j--;
            }
        }
        return true;
    }
	public static void main(String[] args) {
	boolean s=	isPalindrome("A man, a plan, a canal: Panama");
			System.out.println(s);
	}

}
