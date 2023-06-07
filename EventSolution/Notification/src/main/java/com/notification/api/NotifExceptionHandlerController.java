package com.notification.api;

import com.infra.dto.Output;
import com.infra.exception.CodeException;
import com.infra.utils.MessageUtils;
import com.notification.exception.CodeExceptionProject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Locale;

/**
 * @author m.shahrestanaki @createDate 11/28/2022
 */

@ControllerAdvice
public class NotifExceptionHandlerController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Output> handleException(MethodArgumentNotValidException ex) {
        System.out.println(ex.getMessage());
        Output dto = new Output();
        ArrayList<CodeException> errorLst = new ArrayList<>();
        if (ex.getBindingResult().getAllErrors().size() != 0) {
            String msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
            if (msg != null && msg.split(":").length == 2) {
                errorLst.add(new CodeExceptionProject(msg.split(":")[0], Integer.parseInt(msg.split(":")[1])));
                dto.setMessage(MessageUtils.getMessageFromResource("error." + msg.split(":")[0], new Locale("fa")));
            }
        }
        if (errorLst.isEmpty()) {
            errorLst.add(CodeExceptionProject.ARGUMENT_NOT_VALID);
        }
        dto.setErrors(errorLst);
        dto.setSuccess(false);
        if(dto.getMessage() == null || dto.getMessage().equals("")){
            dto.setMessage(MessageUtils.getMessageFromResource("error.argument_not_valid", new Locale("fa")));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Output> handleJsonErrors(HttpMessageNotReadableException exception) {
        Output dto = new Output();
        ArrayList<CodeException> errorLst = new ArrayList<>();
        if(exception.getMessage() != null && exception.getMessage().contains("not one of the values accepted for Enum class")) {
            errorLst.add(CodeExceptionProject.ENUM_VALUES_NOT_ACCEPTED);
            dto.setErrors(errorLst);
            dto.setMessage(MessageUtils.getMessageFromResource("error.enum_values_not_accepted", new Locale("fa")));
        }else if(exception.getMessage() != null && exception.getMessage().contains("not a valid Long value")){
            errorLst.add(CodeExceptionProject.LONG_VALUE_NOT_VALID);
            dto.setErrors(errorLst);
            dto.setMessage(MessageUtils.getMessageFromResource("error.long_value_not_valid", new Locale("fa")));
        }else{
            errorLst.add(CodeExceptionProject.BAD_DATA);
            dto.setErrors(errorLst);
            dto.setMessage(MessageUtils.getLocalizedMessage("error.general", new Locale("fa")));
        }

        dto.setSuccess(false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }
}
