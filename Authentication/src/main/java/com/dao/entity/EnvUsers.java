package com.dao.entity;

import com.basedata.DeactivationReason;
import com.basedata.UserType;
import com.service.dto.EnvUserDto;
import com.service.dto.EnvUserSaveDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.unit.DataUnit;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "ENV_USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvUsers extends ABaseEntity {
    public EnvUsers(EnvUserSaveDto dto){
        this.setPassword(dto.getPassword());
        this.setFirstName(dto.getFirstName());
        this.setLastName(dto.getLastName());
        this.setUserName(dto.getUserName());
        this.setUserType(UserType.ordinary);
        this.setUnsuccessfulTries(0);
        this.setCreationDate(new Timestamp(System.currentTimeMillis()));
        this.setCreatorUserId(1L);
    }

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(generator = "SEQ_ENV_USER", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_ENV_USER", allocationSize = 1, sequenceName = "SEQ_ENV_USER")
    private Long userId;
    @Column(name = "PASSWORD",length = 20)
    private String password;
    @Column(name = "FIRST_NAME",length = 100)
    private String firstName;
    @Column(name = "LAST_NAME",length = 100)
    private String lastName;
    @Column(name = "EMAIL",length = 100)
    private String email;
    @Column(name = "USER_NAME",length = 20)
    private String userName;
    @Column(name = "CELLPHONE",length = 13)
    private String cellPhone;
    @Column(name = "LAST_LOGIN")
    private Timestamp lastLogin;
    @Column(name = "LAST_LOGIN_IP",length = 15)
    private String lastLoginIp;
    @Column(name = "CURRENT_LOGIN")
    private Timestamp currentLogin;
    @Column(name = "CURRENT_LOGIN_IP",length = 15)
    private String currentLoginIp;
    @Column(name = "UNSUCCESSFUL_TRY")
    private Integer unsuccessfulTries;
    @Column(name = "DEACTIVATION_REASON")
    private DeactivationReason deactivationReason;
    @Column(name = "USER_TYPE")
    private UserType userType;

}
