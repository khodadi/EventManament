package com.notification.exception;

import com.infra.exception.CodeException;

/**
 * @author m.keyvanlou
 * @created 29/10/2022 - 1:27 PM
 **/


/**
 * @Guide to using and defining new code in ExceptionCode classes:
 * The codes consist of 8 digits,
 * the first digit from the left indicates the HTTPStatus series,
 * the second and third digits indicate the module code,
 * the fourth and fifth digits indicate the group of HTTPStatus errors,
 * for example, if our error code is 404, the fourth and fifth digits will be 04,
 * the last three digits are related to the business codes of the program,
 * starting from 111 and after defining each new error code, this number must be changed.
 * <p>
 * @Example Code 40129113 --> indicates that this error is related to the authentication module with code 01,
 * and its HTTPStatus code is 429 (Too Many Requests) and 113 indicates the business code.
 * <p>
 * @ModuleCode InfraStructure = 00 ,
 * AuthJWT = 01 ,
 * Notification = 02 ,
 * Samt = 03 ,
 * Faragir = 04 ,
 * ExternalApiService = 05 ,
 * ApiGateway = 06
 **/

public class CodeExceptionProject extends CodeException {

    public CodeExceptionProject(String codeDescription, int errorCode) {
        super(codeDescription, errorCode);
    }

    public static final CodeExceptionProject ARGUMENT_NOT_VALID = new CodeExceptionProject("ARGUMENT_NOT_VALID", 40200405);
    public static final CodeExceptionProject LONG_VALUE_NOT_VALID = new CodeExceptionProject("LONG_VALUE_NOT_VALID", 40200405);
    public static final CodeExceptionProject ENUM_VALUES_NOT_ACCEPTED = new CodeExceptionProject("ENUM_VALUES_NOT_ACCEPTED", 40200406);

    public static final CodeExceptionProject SHAHKAR_ERROR = new CodeExceptionProject("SHAHKAR_ERROR", 40201100);
    public static final CodeExceptionProject SEND_MAIL = new CodeExceptionProject("SEND_MAIL", 40201200);
    public static final CodeExceptionProject SEND_SMS = new CodeExceptionProject("SEND_SMS_VALUE", 40201300);

    public static final CodeExceptionProject NOTIFICATION_TITLE_NOTNULL = new CodeExceptionProject("NOTIFICATION_TITLE_NOTNULL", 40200002);
    public static final CodeExceptionProject NOTIFICATION_TITLE_LENGTH = new CodeExceptionProject("NOTIFICATION_TITLE_LENGTH", 40200002);
    public static final CodeExceptionProject NOTIFICATION_UNBOUND_DATE= new CodeExceptionProject("NOTIFICATION_UNBOUND_DATE", 40200403);
    public static final CodeExceptionProject NOTIFICATION_EMPTY_SETTING = new CodeExceptionProject("NOTIFICATION_EMPTY_SETTING",40200405);

    public static final CodeExceptionProject DISABLE_EDIT = new CodeExceptionProject("DISABLE_EDIT" , 40000200);
    public static final CodeExceptionProject DISABLE_DELETE = new CodeExceptionProject("DISABLE_DELETE" , 40000200);
    public static final CodeExceptionProject DISABLE_CANSEL = new CodeExceptionProject("DISABLE_CANSEL" , 40000200);
    public static final CodeExceptionProject NOT_EXIST_DATA = new CodeExceptionProject("NOT_EXIST_DATA" , 40000200);
    public static final CodeExceptionProject DISABLE_PAUSE_OR_RESUME = new CodeExceptionProject("DISABLE_PAUSE_OR_RESUME" , 40000200);

}
