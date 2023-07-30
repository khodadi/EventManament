package com.service.services;

import com.api.form.OutputAPIForm;

import java.util.Locale;

public interface IMessageBundleSrv {
    String getMessage(String key);
    void createMsg(OutputAPIForm obj);
    String getMessage(String key, Locale locale);
    void createMsg(OutputAPIForm obj, Locale locale);
}
