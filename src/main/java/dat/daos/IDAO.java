package dat.daos;

import java.util.List;
import java.util.Optional;

public interface IDAO<T> {
    List<T> readAll();
    Optional<T> read(int id);
    T create(T item);
    T update(int id, T item);
    void delete(int id);
}
