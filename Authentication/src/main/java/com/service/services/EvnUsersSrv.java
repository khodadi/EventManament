package com.service.services;

import com.api.form.OutputAPIForm;
import com.basedata.CodeException;
import com.dao.entity.EnvUsers;
import com.dao.repository.IUserRepo;
import com.service.dto.EnvUserDto;
import com.service.dto.EnvUserSaveDto;
import com.utility.StringUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
@Slf4j
public class EvnUsersSrv implements IEvnUsersSrv, UserDetailsService {
    private final IUserRepo userRepo;
    public EvnUsersSrv(IUserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String cellphone) throws UsernameNotFoundException {
        EnvUsers user = userRepo.findByCellPhone(cellphone);
        if(user == null){
            log.error("The User do not find in database");
            throw new UsernameNotFoundException("The User do not find in database");
        }else{
            log.info("The User find in database : {}",cellphone);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserType().toString()));
        return new org.springframework.security.core.userdetails.User(user.getCellPhone(),user.getPassword(),authorities);
    }

    @Override
    public OutputAPIForm<EnvUserDto> insertUser(EnvUserSaveDto dto) {
        OutputAPIForm<EnvUserDto> retVal = validationUser(dto);
        if(retVal.isSuccess()){
            try{
                EnvUsers user = new EnvUsers(dto);
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
}
