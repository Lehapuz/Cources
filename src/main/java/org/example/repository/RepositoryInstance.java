package org.example.repository;

import lombok.Getter;
import org.example.repository.impl.RepositoryImpl;

@Getter
public class RepositoryInstance {

    private static RepositoryInstance instance;
    private final Repository repository = new RepositoryImpl();

    private RepositoryInstance(){}

    public static RepositoryInstance getInstance() {
        if (instance == null) {
            instance = new RepositoryInstance();
        }
        return instance;
    }
}
