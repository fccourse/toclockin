package digital.ilia.toclockinapi.services;

import digital.ilia.toclockinapi.dtos.response.UserResponse;
import digital.ilia.toclockinapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<UserResponse> findByEmail(String email) {
        return repository.findByEmail(email).map(UserResponse::new);
    }
}
