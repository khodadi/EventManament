package com.auth.service.services;

import com.auth.service.dto.CriEnvUser;
import com.auth.service.dto.EnvUserDto;
import com.auth.service.dto.EnvUserSaveDto;
import com.basedata.generalcode.CodeException;
import com.auth.dao.entity.EnvUsers;
import com.auth.dao.entity.EnvUsersToken;
import com.auth.dao.repository.IEnvUserTokenRepo;
import com.auth.dao.repository.IUserRepo;
import com.form.OutputAPIForm;
import com.auth.security.UserSecurity;
import com.utility.GeneralUtility;
import com.utility.InfraSecurityUtils;
import com.utility.StringUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@Slf4j
public class EvnUsersSrv implements IEvnUsersSrv, UserDetailsService {
    private final IUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final IEnvUserTokenRepo envUserTokenRepo;

    @Value("${application.pageSize.maximum:10}")
    public int pageSize;

    public EvnUsersSrv(IUserRepo userRepo, PasswordEncoder passwordEncoder, IEnvUserTokenRepo envUserTokenRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.envUserTokenRepo = envUserTokenRepo;
    }


    public UserDetailsService userDetailsService() {
        return username -> {
            EnvUsers user =  userRepo.findByUserName(username);
            if (user == null) {
                log.error("the user : {} do not find in database!", username);
                throw new UsernameNotFoundException("the user do not find in database!");
            } else {
                log.info("the user : {} find in database!", username);
            }
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getUserType().toString()));
//            new org.springframework.security.core.userdetails.User(user.getCellPhone(), user.getPassword(), authorities);
            return new UserSecurity(user.getUserName(),user.getPassword(),true,true,true,true,authorities,new com.auth.service.dto.EnvUserDto(user));
        };
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
        UserDetails retVal = new UserSecurity(user.getUserName(),user.getPassword(),true,true,true,true,authorities,new com.auth.service.dto.EnvUserDto(user));
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
                retVal.setData(new UserSecurity(user.getUserName(),"NULL",true,true,true,true,authorities,new com.auth.service.dto.EnvUserDto(user)));
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
    public OutputAPIForm<com.auth.service.dto.EnvUserDto> insertUser(com.auth.service.dto.EnvUserSaveDto dto) {
        OutputAPIForm<com.auth.service.dto.EnvUserDto> retVal = validationUser(dto);
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


    public OutputAPIForm<com.auth.service.dto.EnvUserDto> updateLocaleUser(com.auth.service.dto.EnvUserSaveDto dto) {
        OutputAPIForm<com.auth.service.dto.EnvUserDto> retVal = validationUser(dto);
        if(retVal.isSuccess()){
            try{

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
            retVal = StringUtility.checkString(dto.getUserName(),4,20,true);
            retVal = retVal.isSuccess()?StringUtility.checkString(dto.getPassword(),4,20,false):retVal;
            retVal = retVal.isSuccess()?StringUtility.checkString(dto.getFirstName(),0,20,false):retVal;
            retVal = retVal.isSuccess()?StringUtility.checkString(dto.getLastName(),0,20,false):retVal;
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

    public OutputAPIForm<ArrayList<EnvUserDto>> listUsers(CriEnvUser cri){
        OutputAPIForm<ArrayList<EnvUserDto>> retVal = new OutputAPIForm<>();
        if(InfraSecurityUtils.checkLogin()){
            List<EnvUsers> envUsers = userRepo.findByCriteria(StringUtility.hasLength(cri.getLastName())?cri.getLastName(): null,
                    StringUtility.hasLength(cri.getMobileNumber())?cri.getMobileNumber(): null,
                    StringUtility.hasLength(cri.getUserName())?cri.getUserName(): null,
                    PageRequest.of(cri.getPage().intValue(), pageSize+1, Sort.by("creationDate")));
            if(Objects.nonNull(envUsers)){
                ArrayList<EnvUserDto> envUserDtos= new ArrayList<>();
                retVal.setNextPage(GeneralUtility.checkNextPage(envUsers,pageSize));
                for(EnvUsers ent:envUsers){
                    envUserDtos.add(new EnvUserDto(ent.getFirstName(),ent.getLastName(), ent.getUserId()));
                }
                retVal.setData(envUserDtos);
                return retVal;
            }
        }
        return retVal;
    }
}
