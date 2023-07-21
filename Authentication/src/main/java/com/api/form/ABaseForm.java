package com.api.form;
import com.basedata.CodeException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import java.util.ArrayList;

/*
 *  @Created 16/06/2022
 *  @Project userservice
 *  @Author  kiumars khodadi
 */
@AllArgsConstructor
@Getter
@Setter
public abstract class ABaseForm {
    @Builder.Default
    private ArrayList<CodeException> errors = new ArrayList<>();
    @Builder.Default
    private String message  = "";
    @Builder.Default
    private boolean success = true;

    ABaseForm(){
        setSuccess(true);
        setMessage("");
    }

    public void addError(ABaseForm baseForm){
        getErrors().addAll(baseForm.getErrors());
        setSuccess(isSuccess() && baseForm.isSuccess());
        setMessage(getMessage().concat(baseForm.getMessage()));
    }
}
