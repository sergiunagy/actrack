package sena.activitytracker.acktrack.services;

import java.util.Set;

public interface CrudService <T, ID>{

    Set<T> findAll();

    T findById(ID id);

    T save(T obj);

    Set<T> saveAll(Set<T> entities);

    void delete(T obj);

    void deleteById(ID id);
}
