package com.service.services;

import com.api.form.OutputAPIForm;
import com.dao.entity.EnvUsers;
import com.dao.repository.IUserRepo;
import com.service.dto.EnvUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EvnUsersSrv implements IEvnUsersSrv, UserDetailsService {
    private IUserRepo userRepo;

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
    public OutputAPIForm<EnvUserDto> insertUser(EnvUserDto userDto) {
        return null;
    }
}
