package com.service.services;

import com.form.OutputAPIForm;
import com.basedata.generalcode.CodeException;
import com.dao.entity.EnvUsers;
import com.dao.entity.EnvUsersToken;
import com.dao.repository.IEnvUserTokenRepo;
import com.dao.repository.IUserRepo;
import com.security.UserSecurity;
import com.service.dto.EnvUserDto;
import com.service.dto.EnvUserSaveDto;
import com.utility.StringUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class EvnUsersSrv implements IEvnUsersSrv, UserDetailsService {
    private final IUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final IEnvUserTokenRepo envUserTokenRepo;

    public EvnUsersSrv(IUserRepo userRepo, PasswordEncoder passwordEncoder, IEnvUserTokenRepo envUserTokenRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.envUserTokenRepo = envUserTokenRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        EnvUsers user = userRepo.findByUserName(userName);
        if(user == null){
            log.error("The User do not find in database");
            throw new UsernameNotFoundException("The User do not find in database");
        }else{
            log.info("The User find in database : {}",userName);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserType().toString()));
        UserDetails retVal = new UserSecurity(user.getUserName(),user.getPassword(),true,true,true,true,authorities,new EnvUserDto(user));
        return retVal;
    }

    @Override
    public OutputAPIForm<UserDetails> loadUserByToken(String token) throws UsernameNotFoundException {
        OutputAPIForm<UserDetails> retVal = new OutputAPIForm<>();
        try{
            EnvUsersToken userToken = envUserTokenRepo.getByToken(token);
            if(userToken != null){
                EnvUsers user = userRepo.getOne(userToken.getUserId());
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.getUserType().toString()));
                retVal.setData(new UserSecurity(user.getUserName(),"NULL",true,true,true,true,authorities,new EnvUserDto(user)));
            }else{
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.EXPIRED_TOKEN);
                log.error("The User do not find in database");
                throw new UsernameNotFoundException("The User do not find in database");
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.EXPIRED_TOKEN);
        }
        return retVal;
    }

    @Override
    public OutputAPIForm<EnvUserDto> insertUser(EnvUserSaveDto dto) {
        OutputAPIForm<EnvUserDto> retVal = validationUser(dto);
        if(retVal.isSuccess()){
            try{
                EnvUsers user = new EnvUsers(dto);
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
                user = userRepo.save(user);
                retVal.setData(new EnvUserDto(user));
            }catch (Exception e){
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
            }
        }
        return retVal;
    }

    public OutputAPIForm validationUser(EnvUserSaveDto dto){
        OutputAPIForm retVal = new OutputAPIForm();
        try{
            retVal = StringUtility.checkString(dto.getUserName(),false,4,20,true);
            retVal = retVal.isSuccess()?StringUtility.checkString(dto.getPassword(),false,4,20,false):retVal;
            retVal = retVal.isSuccess()?StringUtility.checkString(dto.getFirstName(),false,0,20,false):retVal;
            retVal = retVal.isSuccess()?StringUtility.checkString(dto.getLastName(),false,0,20,false):retVal;
            if(retVal.isSuccess() && userRepo.findByUserName(dto.getUserName()) != null){
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.INVALID_USERNAME);
            };
        }catch (Exception e){
            log.error(e.getMessage());
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.UNDEFINED);
        }
        return retVal;
    }

    public OutputAPIForm saveToken(UserSecurity user, Map<String,String> tokens){
        OutputAPIForm retVal = new OutputAPIForm();
        try{
            List<EnvUsersToken> envUsersTokens = envUserTokenRepo.getAllByUserId(user.getEnvUser().getUserId());
            if(envUsersTokens != null && envUsersTokens.size() > 3){
                for(int i=3 ;i < envUsersTokens.size();i++){
                    envUserTokenRepo.deleteAllByEnvUsersTokenId(envUsersTokens.get(i).getEnvUsersTokenId());
                }
            }
            EnvUsersToken envUsersToken = new EnvUsersToken(user,tokens);
            envUserTokenRepo.save(envUsersToken);
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return retVal;
    }


}
