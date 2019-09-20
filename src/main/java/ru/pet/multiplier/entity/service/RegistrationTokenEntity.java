package ru.pet.multiplier.entity.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "registration_token")
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationTokenEntity {
    @Id
    private String token;
    @ManyToOne(cascade = {CascadeType.MERGE})
    private UserEntity user;
    private LocalDateTime created;
}
