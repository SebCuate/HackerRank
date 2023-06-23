package JavaStuff.javaBasics.excepciones;

import java.io.IOException;

import javax.management.RuntimeErrorException;

public class Ejercicios1 {

	public static void main(String[] args) {
		
		//No es obligatorio tener bloques catch
		try {
			 throw new RuntimeErrorException(null,""); //es una excepci�n Uncheked, no hay bloque catch que la capture, pero aun as� el compilador lo permite  
		}
//		catch (IOException e) {} //Es una Excepci�n checked que no puede generar, pues no hay forma de que pueda alcanzarse esta excepcion, Error de Compilaci�n
		catch(NullPointerException e) {} //Excepcion Unchecked, puede declararse, aunque no sea alcanzable
		finally {
			System.out.println("algo");
		}
		
		try{
			new Ejercicios1().metodo();
		} 
//		catch (IOException e) {}
		catch (Exception e) {}
//		finally {		}
	}
	
	private void metodo() {}
}
