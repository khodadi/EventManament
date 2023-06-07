package com.notification.api;

import com.infra.dto.Output;
import com.infra.dto.UserBaseInfoDto;
import com.notification.service.Iservice.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author m.shahrestanaki @createDate 4/30/2023
 */

@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;

    @GetMapping(value = "/getUserInfo")
    public Output<UserBaseInfoDto> getUserInfo() {
        return accountService.getUserInfo();
    }
}
