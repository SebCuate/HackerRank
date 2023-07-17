package cursoLambdasStreams.util_function;

import lombok.ToString;

@ToString
class MyProduct {

    Integer price;
    String name;

    public MyProduct(Integer price, String name) {
        this.price = price;
        this.name = name;
    }
}
