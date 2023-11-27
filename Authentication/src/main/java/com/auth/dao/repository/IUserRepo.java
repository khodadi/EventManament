package com.auth.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.auth.dao.entity.EnvUsers;

import java.util.List;

public interface IUserRepo extends JpaRepository<EnvUsers,Long> {
    EnvUsers findByCellPhone(String cellPhone);
    EnvUsers findByUserName(String userName);
    List<EnvUsers> findAllByCreatorUserId(Long creatorUserId);
}
