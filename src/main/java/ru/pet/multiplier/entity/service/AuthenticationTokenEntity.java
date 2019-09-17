package ru.pet.multiplier.entity.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "authentication_token")
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationTokenEntity {
    @Id
    private String token;
    @ManyToOne(optional = false)
    private UserEntity user;
}
