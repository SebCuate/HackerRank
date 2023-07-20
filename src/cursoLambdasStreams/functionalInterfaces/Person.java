package cursoLambdasStreams.functionalInterfaces;

import lombok.ToString;

import java.io.Serializable;

@ToString
class Person implements Serializable {
    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
