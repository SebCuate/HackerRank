package javaStuff.javaExams.exam_and_curse_java_se_;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Question_7_Ex_Guia {

	/* 1 */ public static final MyArray1[] myArray1 = { new MyArray1(), new MyArray1() };
	/* 2 */ private static final MyArray2[] myArray2 = { new MyArray2(), new MyArray2() };
	/* 3 */ public static final List<MyArray2> VALUES = Collections.unmodifiableList(Arrays.asList(myArray2));

	public static void main(String[] args) {

		System.out.println("Valores iniciales de VALUES");
		for (int i = 0; i < Question_7_Ex_Guia.VALUES.size(); i++) {
			System.out.println( ((MyArray2) Question_7_Ex_Guia.VALUES.get(i)).dato);
		}
		System.out.println("---------------------");
//		Util.myArray1 = { new MyArray1(), new MyArray1() }; // Se ve que el array myArray1 no se puede modificar
		for (int i = 0; i < Question_7_Ex_Guia.myArray1.length; i++) {
			System.out.print(Question_7_Ex_Guia.myArray1[i].dato + " - ");
//			Question_7_Ex_Guia.myArray1[i] = new MyArray1(i);
			Util.modificar(i); // Se ve que los elementos de MyArray1 se puede modificar, incluso desde otra clase
			System.out.println(Question_7_Ex_Guia.myArray1[i].dato);
		}
		System.out.println("---------------------");
		//Util.myArray2 = { new MyArray2(), new MyArray2() };  // Se ve que el array myArray2 no se puede modificar
//		for (int i = 0; i < Util.myArray2.length; i++) { // Se ve que el array myArray2 no se puede ser vista fuera de si misma (es privada)
		for (int i = 0; i < Question_7_Ex_Guia.myArray2.length; i++) {
			System.out.print(Question_7_Ex_Guia.myArray2[i].dato + " - ");
			Question_7_Ex_Guia.myArray2[i] = new MyArray2(i); // Se ve que los elementos de myArray2 se puede modificar, aunque no fuera de la clase 
			System.out.println(Question_7_Ex_Guia.myArray2[i].dato);
		}
		System.out.println("---------------------");
		System.out.println("Valores finales de VALUES");
		for (int i = 0; i < Question_7_Ex_Guia.VALUES.size(); i++) {
			System.out.println( ((MyArray2) Question_7_Ex_Guia.VALUES.get(i)).dato);
		}
	}
}

class MyArray1 {
	int dato;
	public MyArray1() {
		this.dato = 2;
	}
	public MyArray1(int dat) {
		this.dato = dat;
	}
}

class MyArray2 {
	int dato;
	public MyArray2() {
		this.dato = 2;
	}
	public MyArray2(int dat) {
		this.dato = dat;
	}
}

class Util extends Question_7_Ex_Guia {

	public static void modificar(int pos) {
//		Question_7_Ex_Guia.myArray1 = { new MyArray1(), new MyArray1(), new MyArray1(), new MyArray1() }; //No se puede modificar myArray1 desde otra clase
		Question_7_Ex_Guia.myArray1[pos] = new MyArray1(pos);//Se puede modificar los elementos de myArray1 desde otra clase
	}
	
}