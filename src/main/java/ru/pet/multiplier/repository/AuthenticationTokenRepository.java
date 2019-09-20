package ru.pet.multiplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pet.multiplier.entity.service.AuthenticationTokenEntity;
import ru.pet.multiplier.entity.service.UserEntity;

public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationTokenEntity, String> {
    void deleteAllByUser(UserEntity user);
}
