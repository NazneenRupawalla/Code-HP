package com.thoughtworks.hp.datastore;

import java.util.List;

public interface Table<T> {

    public T findById(long id);

    public List<T> findAll();

}
