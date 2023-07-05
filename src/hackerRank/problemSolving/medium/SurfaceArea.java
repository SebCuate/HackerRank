package hackerRank.problemSolving.medium;

public class SurfaceArea {

	// Complete the surfaceArea function below.
	static int surfaceArea(int[][] A, int H, int W) {

		int area = 2 * W * H;

//		System.out.println(area);

		// ITERATE BY X DIMENTION
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				if (j == 0)
					area += Math.abs(A[i][j]);
				else
					area += Math.abs(A[i][j] - A[i][j - 1]);

//				System.out.print( A[i][j] + "-" );
			}

			area += Math.abs(A[i][W - 1]);
//			System.out.println("|||"+area);
		}
		// THIS BLOCK IS WORKING

		// ITERATE BY Y DIMENTION
		for (int i = 0; i < W; i++) {
			for (int j = 0; j < H; j++) {
				if (j == 0)
					area += Math.abs(A[j][i]);
				else
					area += Math.abs(A[j][i] - A[j - 1][i]);
//				System.out.print( A[j][i] + "-" );
			}

			area += Math.abs(A[H - 1][i]);
//			System.out.println("|||"+area);
		}
		// THIS BLOCK IS WORKING
		return area;
	}

	public static void main(String[] args) {
		int H = 3, W = 3;

//		1 3 4
//		2 2 3
//		1 2 4

//		int [][]A = {
//				{1,3,4}, {2,2,3}, {1,2,4}
//		};

		int[][] A = { { 1, 3, 4 }, { 2, 2, 3 }, { 1, 2, 4 } };

		System.out.println(surfaceArea(A, H, W));

	}

}
