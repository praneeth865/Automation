
public class LinearSearch {
	public static int linearSearch(int a[],int v) {
		for(int i=0;i<a.length;i++) {
			if(a[i]==v) 
				return i;
		}
		return-1;
		
	}
	public static void main(String args[]) {
		int a[]= {1,2,3,5,6};
		int v=2;
		int res=linearSearch(a,v);
		if(res==-1)
			System.out.println("NIL");
		else
			System.out.println(res);
		
	}

}
