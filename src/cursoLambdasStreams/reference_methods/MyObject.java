package cursoLambdasStreams.reference_methods;

import lombok.ToString;

import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

@ToString
public class MyObject {
    private  String string;
    private  Integer num;

    MyObject() {
        this.string = UUID.randomUUID().toString();
        this.num = new Random().nextInt(1000);
    }
}
