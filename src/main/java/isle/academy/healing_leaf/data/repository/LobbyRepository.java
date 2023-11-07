package isle.academy.healing_leaf.data.repository;

import isle.academy.healing_leaf.data.entity.lobby.LobbyEntity;
import org.springframework.data.repository.CrudRepository;

public interface LobbyRepository extends CrudRepository<LobbyEntity, Long> {
}
