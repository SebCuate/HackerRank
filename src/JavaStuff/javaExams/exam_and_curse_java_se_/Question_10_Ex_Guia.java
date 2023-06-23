package JavaStuff.javaExams.exam_and_curse_java_se_;

import java.util.ArrayList;
import java.util.List;

public class Question_10_Ex_Guia {

	public static void main(String[] args) {
		
		List<String> vegetables = new ArrayList<>();
		vegetables.add("Kale");
		vegetables.add(0, "Lettuce");
		System.out.println(vegetables);
		
//		List<char> fish = new ArrayList<>(); //No hay collection del tipo primitivo
		List<String> fish = new ArrayList<>(); //No hay collection del tipo primitivo
		fish.add("Salmon");
		fish.add(0, "Seabass");
		System.out.println(fish);
	}

}
