package example.radmila.cdninjaqrcodescanner.data;

import java.util.List;

public interface Repository<T> {

    List<T> findAll();

    T save(T entity);

    void delete(T entity);

    void clear();
}