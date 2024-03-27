package org.example.service;

import lombok.Getter;
import org.example.repository.Repository;
import org.example.repository.RepositoryInstance;
import org.example.service.impl.ServiceImpl;

@Getter
public class ServiceInstance {

    private static ServiceInstance instance;
    private final Repository repository = RepositoryInstance.getInstance().getRepository();
    private final Service service = new ServiceImpl(repository);

    private ServiceInstance(){}

    public static ServiceInstance getInstance() {
        if (instance == null) {
            instance = new ServiceInstance();
        }
        return instance;
    }
}
