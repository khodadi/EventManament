package com.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dao.entity.EnvUsers;

import java.util.List;

public interface IUserRepo extends JpaRepository<EnvUsers,Long> {
    EnvUsers findByCellPhone(String cellPhone);
    EnvUsers findByUserName(String userName);
    List<EnvUsers> findAllByCreatorUserId(Long creatorUserId);
}
