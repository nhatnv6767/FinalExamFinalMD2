package ra.DAO;

public interface DAOInterface<T> {
    void insert(T t);

    void update(T t);

    void delete(T t);

    T get(int id);

    T get(String name);

    T get(T t);

    T[] getAll();
}
