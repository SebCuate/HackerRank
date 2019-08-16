package util;

public class QuickSort {
	
	public static void main(String[] args) {
		
		int[] arr = {91,82,23,54,65,46,37,78,19,00};
		
//		arr = quicksortAsc(arr, 0, arr.length-1);
//		arr = quicksortDesc(arr, 0, arr.length-1);
		
		
		for (int i : arr) {
			System.out.print(i + " ");
		}
		
	}
	
	public static int[] quicksortAsc(int A[], int izq, int der) {

		int pivote = A[(izq + der) / 2]; // tomamos primer elemento como pivote
		int i = izq; // i realiza la búsqueda de izquierda a derecha
		int j = der; // j realiza la búsqueda de derecha a izquierda
		int aux;

		while (i < j) { // mientras no se crucen las búsquedas
			while (A[i] < pivote)
				i++; // busca elemento mayor que pivote
			while (A[j] > pivote)
				j--; // busca elemento menor que pivote
			if (i <= j) { // si no se han cruzado
				aux = A[i]; // los intercambia
				A[i] = A[j];
				A[j] = aux;
				i++;//Muevo los pivotes
				j--;//Muevo los pivotes
			}
		}
		
		if (izq < j )
			quicksortAsc(A, izq, j ); // ordenamos subarray izquierdo
		if (i < der)
			quicksortAsc(A, i, der); // ordenamos subarray derecho

		return A;
	}

	public static int[] quicksortDesc(int A[], int izq, int der) {

		int pivote = A[(izq + der) / 2]; // tomamos primer elemento como pivote
		int i = izq; // i realiza la búsqueda de izquierda a derecha
		int j = der; // j realiza la búsqueda de derecha a izquierda
		int aux;

		while (i < j) { // mientras no se crucen las búsquedas
			while (A[i] > pivote)
				i++; // busca elemento menor que pivote
			while (A[j] < pivote)
				j--; // busca elemento mayor que pivote
			if (i <= j) { // si no se han cruzado
				aux = A[i]; // los intercambia
				A[i] = A[j];
				A[j] = aux;
				i++;//Muevo los pivotes
				j--;//Muevo los pivotes
			}
		}

		if (izq < j)
			quicksortDesc(A, izq, j); // ordenamos subarray izquierdo
		if (i < der)
			quicksortDesc(A, i, der); // ordenamos subarray derecho

		return A;
	}
}
