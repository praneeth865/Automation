package praneeth;

public class Program {
	public static String subString() {
    String s="bhavana attending interview for altimetrick today";
	//s.substring(s,8,41);
	StringBuilder sb = new StringBuilder();
	int startIndex = s.indexOf("attending");
	int endIndex = s.indexOf("altimetrick");
	String[] input = s.split(" ");
	sb.append("attending");
	sb.append(" ");
	for(String str:input) {
		/*if(str =="attending") {
			sb.append("attending");
			sb.append(" ");
		
	}*/
		if(s.indexOf(str)>startIndex && s.indexOf(str)<endIndex){
			sb.append(str);
			sb.append(" ");
		}
		

}
	sb.append("altimetrick");
	return sb.toString();
}
	
	public static void main(String[] args) {
		System.out.println(subString());
	}
	
}

