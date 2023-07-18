package cursoLambdasStreams.reference_methods;

import java.util.Random;
import java.util.UUID;

import lombok.ToString;

@ToString
public class MyObjectRefMet {
	
    private  String string;
    private  Integer num;

    MyObjectRefMet() {
        this.string = UUID.randomUUID().toString();
        this.num = new Random().nextInt(1000);
    }
    
//    public String toString(){
//    	return this.string + " " + this.num;
//    }
}
