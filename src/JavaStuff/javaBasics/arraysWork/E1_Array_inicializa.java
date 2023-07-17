package javaStuff.javaBasics.arraysWork;

public class E1_Array_inicializa {

	public static void main(String[] args) {
		
		int i[];
		i = new int[3];
		i = new int[5];
//		i = {0,0};
		
		int[][] i2 = 
		{ {1,2,3,4,5}, {1,2,3,4,5}, {1,2,3,4,5} }; // igual a new int[3][5];
//		{ {0,0,0}, {0,0,0} }; // igual a new int[2][3];
//		{ {1,2,3,4,5,6,7,8,9,0}, {1,2,3}, {10,11,12,14,15} }; //Matriz de 2 dimensiones, no tiene comparacion
//		new int[3][10]; 
		
		for (int j = 0; j < i2.length; j++) { //recorre los renglones 
			for (int j2 = 0; j2 < i2[j].length; j2++) { //recorre las columnas
				System.out.print(i2[j][j2] + " - " );
			}
			System.out.println();
		}
		System.out.println("++++++++++++++++++++++++++++++++");
		System.out.println("++++++++++++++++++++++++++++++++");
		System.out.println("++++++++++++++++++++++++++++++++");
		System.out.println("++++++++++++++++++++++++++++++++");
		
int[][][] i3 = 
{  { {1,2,3,4,5}, {1,2,3,4,5}, {1,2,3,4,5} }, { {1,2,3,4,5}, {1,2,3,4,5}, {1,2,3,4,5} } }; // igual a new int[2][3][5];
//{  { {1}, {1,2,3}, {1,2,3,4,5} }, { {1,2,3,4,5}, {3,4,5}, {5} } }; // ;
		
		for (int j = 0; j < i3.length; j++) {//x
			for (int j2 = 0; j2 < i3[j].length; j2++) {//y
				for (int j3 = 0; j3 < i3[j][j2].length; j3++) {//z
					System.out.print(i3[j][j2][j3] + " - " );
				}
				System.out.println("++++++++++++++++++++++++++++++++");
			}
			System.out.println();
		}
		
	}
}
