package cursoLambdasStreams.fundamentals;

import java.util.List;

public interface DatabaseService<T> {

    T getById(Long id);
    List<T> getAllRecords();

}
