package com.auth.dao.entity;

import com.auth.security.UserSecurity;
import com.auth.security.filter.CustomAuthenticationFilter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * @Creator 7/31/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Entity
@Table(name = "ENV_USER_TOKEN",schema = "ENV_AUTH")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvUsersToken {

    public EnvUsersToken(UserSecurity user, Map<String,String> tokens){
        this.setUserId(user.getEnvUser().getUserId());
        this.setToken(tokens.get("access_token"));
        this.setDateTimeExpire(new Timestamp((new Date()).getTime() + CustomAuthenticationFilter.EXPIRE_TOKEN));
        this.setRefreshToken(tokens.get("refresh_token"));
        this.setDateTimeExpireRefresh(new Timestamp((new Date()).getTime() + CustomAuthenticationFilter.EXPIRE_REFRESH_TOKEN));
        this.setCreationDate(new Timestamp((new Date()).getTime()));
    }

    @Id
    @Column(name = "ENV_USER_TOKEN_ID")
    @GeneratedValue(generator = "SEQ_ENV_USERS_TOKEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_ENV_USERS_TOKEN", allocationSize = 1, sequenceName = "SEQ_ENV_USERS_TOKEN",schema = "ENV_AUTH")
    private Long envUsersTokenId;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "token",length = 500)
    private String token;
    @Column(name = "DATATIME_EXPIRE")
    private Timestamp dateTimeExpire;
    @Column(name = "REFRESH_TOKEN",length = 500)
    private String refreshToken;
    @Column(name = "DATETIME_EXPIRE_REFRESH")
    private Timestamp dateTimeExpireRefresh;
    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

}
