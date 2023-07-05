package hackerRank.problemSolving.medium;

public class AbsolutePermutation {
	
	static int[] absolutePermutation(int n, int k) {
        int[] per = new int[n];
                
        if(k==0) {
            for (int i = 0; i < per.length; i++) {
                per[i]= i+1;
            }
            return per;
        }        

        if(n%(2*k) != 0 || n%k != 0) 
            return new int[]{-1};
        
        int d = 1;
        for (int i = 0, j = 0; i < n; i++, j++) {
            if (j == k) {
                d *= -1;
                j = 0;
            }

            int value = i + 1 + k * d;
            if (value < 1 || value > n)
                return new int[]{-1};
            per[i] = value;
        }
        return per;
    }
	
	public static void main(String[] args) {
		int[] help = absolutePermutation(100, 5);
		for (int i : help) {
			System.out.print(i +  " ");
		}
		

	}

}
