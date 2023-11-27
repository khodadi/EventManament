package com.auth.dao.repository;

import com.auth.dao.entity.EnvUsers;
import com.auth.dao.entity.EnvUsersToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Creator 7/31/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public interface IEnvUserTokenRepo extends JpaRepository<EnvUsersToken,Long> {

    EnvUsersToken getByToken(String token);
    EnvUsersToken getByUserId(Long userId);
    List<EnvUsersToken> getAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
    void deleteAllByToken(String token);
    void deleteAllByEnvUsersTokenId(Long envUsersToken);

}
