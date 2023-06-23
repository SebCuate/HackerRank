package JavaStuff.javaBasics.utilPackage;

import JavaStuff.javaBasics.interfaz.*;

public class Exer1_ConInterfaz_util {

	public static void main(String[] args) {
		
	}
	

//	boolean check(int i1, int i2, I1  func) {
	boolean check(int i1, int i2, I2  func) {
//	boolean check(int i1, int i2, I3  func) {
//		return func.m(i1, i2);
		return I2.m(i1, i2);
	}
	
}
