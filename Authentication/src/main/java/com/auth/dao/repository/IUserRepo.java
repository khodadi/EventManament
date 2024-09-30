package com.auth.dao.repository;

import com.auth.basedata.UserType;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.auth.dao.entity.EnvUsers;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUserRepo extends JpaRepository<EnvUsers,Long> {
    EnvUsers findByCellPhone(String cellPhone);
    EnvUsers findByUserName(String userName);
    List<EnvUsers> findAllByCreatorUserId(Long creatorUserId);
    @Query( "select ent from EnvUsers ent where                      " +
            "      ent.userType = 0                                  " +
            "  and(:lastName is null or ent.lastName like :lastName) " +
            "  and(:cellPhone is null or ent.cellPhone = :cellPhone) " +
            "  and(:userName is null or ent.userName like :userName) "
          )
    List<EnvUsers> findByCriteria(@Param("lastName") String lastName, @Param("cellPhone") String cellPhone, @Param("userName") String userName, Pageable page);

}
