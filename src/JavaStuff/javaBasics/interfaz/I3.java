package JavaStuff.javaBasics.interfaz;

public interface I3 {
	boolean m(Integer I1, Integer I2); //modifcador de acceso default
	default boolean m(long i1, long i2){//modifcador de acceso privado por default
		return i1 == i2;
	}
	void m();//modifcador de acceso privado: los métodos definidos así deben serlo
}
