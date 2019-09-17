package ru.pet.multiplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pet.multiplier.entity.service.AuthenticationTokenEntity;

public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationTokenEntity, String> {
}
