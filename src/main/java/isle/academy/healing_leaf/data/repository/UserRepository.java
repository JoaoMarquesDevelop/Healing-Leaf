package isle.academy.healing_leaf.data.repository;

import isle.academy.healing_leaf.data.entity.user.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
