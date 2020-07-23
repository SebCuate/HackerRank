package JavaStuff.javaBasics;

class Entero {
    public int valor;
    public Entero(int valor){
        this.valor=valor;
    }
}

public class ValorApp2 {
    public static void main(String[] args) {
        Entero aInt=new Entero(3);
        System.out.println("Antes de llamar a la función: " +aInt.valor); //3
        funcion(aInt);
        System.out.println("Después de llamar a la función: " +aInt.valor); //5
    }

    static void funcion(Entero xInt){
//        xInt.valor=5;
        Entero bInt = xInt; 
        bInt = new Entero(10);//constructor por default
        bInt.valor = 1; 
//    	bInt.valor=5;
    }
}
