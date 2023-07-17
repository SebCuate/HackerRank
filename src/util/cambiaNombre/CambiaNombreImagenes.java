package util.cambiaNombre;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class CambiaNombreImagenes {

	static int num = 1;
	
	public static void main(String[] args) {
		
		String path;
		String newName;
		path = "C:/Users/SebasCU/Pictures/Schecter";
//		newName = "Gibson_Classic_1960_Ressiue_";
		newName = "Schecter_Hellraiser_Hybrid_PT_7_TBB";
		List<String> nombreArchivos = Arrays.asList(new File(path).list());
		NumberFormat f2 = new DecimalFormat("000");
		
		Consumer<String> utilCons = (x) -> {
			if(x.endsWith(".jpg")) {
				File utilFile = new File(path+"/"+x);
				File newFile = new File (path+"/"+ newName + f2.format(getNum()) + ".jpg");
//				System.out.println(x);
				System.out.println("El nuevo archivo es: " + newName + f2.format(getNum()) + ".jpg");
				setNum();
				utilFile.renameTo(newFile);
			}
		};
		nombreArchivos
			.stream()
			.sorted(Comparator.reverseOrder())
			.forEach(utilCons);
	}
	
	public static int getNum() {
		return num;
	}
	
	public static void setNum() {
		CambiaNombreImagenes.num++;
	}

}
