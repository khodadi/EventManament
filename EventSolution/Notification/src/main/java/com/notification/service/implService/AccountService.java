package com.notification.service.implService;

import com.infra.dto.Output;
import com.infra.dto.UserBaseInfoDto;
import com.infra.utils.InfraUtility;
import com.notification.service.Iservice.IAccountService;
import com.notification.service.Iservice.ICallOtherServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author m.shahrestanaki @createDate 4/30/2023
 */
@Service
@Slf4j
public class AccountService implements IAccountService {
    @Value("${server.servlet.context-path}")
    private String applicationName;

    private final ICallOtherServices callOtherServices;

    public AccountService(ICallOtherServices callOtherServices) {
        this.callOtherServices = callOtherServices;
    }

    public Output<UserBaseInfoDto> getUserInfo(){
        Output<UserBaseInfoDto> retval = new Output<>();
        try {
            if(InfraUtility.giveCurrentUser().isPresent()){
                Output<List<String>> permissionLst = callOtherServices.getUserPermission(applicationName.replace("/",""));
                if(permissionLst.isSuccess()){
                    retval.setData(new UserBaseInfoDto(InfraUtility.giveCurrentUser().get().getFirstName()
                            ,InfraUtility.giveCurrentUser().get().getLastName()
                            ,permissionLst.getData()));
                }
                retval.setSuccess(permissionLst.isSuccess());
            }else {
                retval.setSuccess(false);
            }
        }catch (Exception e){

        }
        return retval;
    }
}
