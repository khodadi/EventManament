package com.api.form;

import java.util.ArrayList;

public class OutputAPIForm <T> extends ABaseForm {

    private T data;
    public OutputAPIForm() {
        setErrors(new ArrayList<>());
        setSuccess(true);
        setMessage("");
    }
}
