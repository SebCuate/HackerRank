package hackerRank.easy;

public class StrangeCounter {
	
	static long strangeCounter(long t) {
		
		long contador = 3;
		
		while(t > contador) {
			t -= contador;
			contador *= 2;
		}
		
//		System.out.println(contador);
//		System.out.println(t);
//		
//		for (int i = contador; i > 0 ; i--) {
//			if(t == 1)
//				return i;
//			else
//				t--;
////			System.out.println(i + " - " + (t--));
//		}
		
		return contador - t + 1;
    }
	
	public static void main(String[] args) {
		
		System.out.println(strangeCounter(1));

	}

}
