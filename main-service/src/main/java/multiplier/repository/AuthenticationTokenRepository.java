package multiplier.repository;

import multiplier.entity.service.AuthenticationTokenEntity;
import multiplier.entity.service.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationTokenEntity, String> {
    void deleteAllByUser(UserEntity user);
}
