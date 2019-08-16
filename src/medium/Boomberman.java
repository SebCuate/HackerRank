package medium;

/**
 * this problem is a infinite cycle, there is a helpful solution when n <= 1
 * return the input board when n mod 2 == 0 then return all the board with 0
 * when (n - 3) % 4 == 0 then return board1 (you must calculate when n = 3 and
 * repeat every four seconds) when (n - 5) % 4 == 0 then return board2 (you must
 * calculate when n = 5 and repeat every four seconds)
 * 
 * @author Sebastian Cuatepotzo
 */
public class Boomberman {

	static String[] bomberMan(long n, String[] grid) {

		// The board no change, return the board
		if (n <= 1)
			return grid;

		// IN THE BOARD ON EVEN SECONDS IS FULL OF BOMBS
		if (n % 2 == 0) {
			for (int i = 0; i < grid.length; i++) {
				grid[i] = grid[i].replace(".", "O");
			}
			return grid;
		}

		// The next code will only evaluate for n=3 and n=5, after that, the board
		// income into an infinite loop

		int count = 0;

		//Only two cases to check
		while (count < 2) {

			for (int i = 0; i < grid.length; i++) {

				grid[i] = grid[i].replace("O", "1");
				grid[i] = grid[i].replace(".", "O");

			}

			for (int i = 0; i < grid.length; i++) {

				char[] tmpMid = grid[i].toCharArray(); // MID LINE
				char[] tmpUp = grid[i].toCharArray(); // UP LINE
				char[] tmpDw = grid[i].toCharArray(); // DOWN LINE
				
				boolean flagUp = true;
				boolean flagDown = true;
				
				// This help for board with only one line
				
				//First Line
				if (i != 0) 
					tmpUp = grid[i - 1].toCharArray();
				else
					flagUp = false;//First Line
					
				//Last Line
				if (i != grid.length - 1) 
					tmpDw = grid[i + 1].toCharArray();
				else
					flagDown = false;//Last Line

				for (int j = 0; j < grid[i].length(); j++) {
					
					if (tmpMid[j] == '1') {
						
						tmpMid[j] = '.';// The bomb burst

						if (j != 0)
							tmpMid[j - 1] = '.';

						if (j != tmpMid.length - 1) {
							if (tmpMid[j + 1] == 'O') {
								tmpMid[j + 1] = '.';
							}
						}
						
						if(flagDown) {
							if (tmpDw[j] == 'O') {
								tmpDw[j] = '.';
							}
						}
						
						if(flagUp) {
							if (tmpUp[j] == 'O') {
								tmpUp[j] = '.';
							}
						}
					}
					
				}//End for loop that iterate the block of the board 
				
				grid[i] = new String(tmpMid);
				if(flagDown)
					grid[i+1] = new String(tmpDw);
				if(flagUp)
					grid[i-1] = new String(tmpUp);

			}
			count++;

			if ((n + 1) % 4 == 0) {
				break;
			}
		}
		
		for (int i = 0; i < grid.length; i++) {
			grid[i] = grid[i].replace("1", "0");
		}
		
		return grid;
	}
	
	public static void main(String[] args) {

		int n = 2;
		int r = 6;

		String[] grid = new String[r];

//		grid[0] = "O...OO..";
//		grid[1] = ".O.O..O.";
//		grid[2] = "OOOOOOOO";
//		grid[3] = "......O.";
//		grid[4] = ".OOO...O";

		grid[0] = ".......";
		grid[1] = "...O...";
		grid[2] = "....O..";
		grid[3] = ".......";
		grid[4] = "OO.....";
		grid[5] = "OO.....";

		grid = bomberMan(n, grid);

		for (String string : grid) {
			System.out.println(string);
		}

	}// END MAIN METHOD

}
