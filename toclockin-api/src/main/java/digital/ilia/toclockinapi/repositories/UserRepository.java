package digital.ilia.toclockinapi.repositories;

import digital.ilia.toclockinapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
