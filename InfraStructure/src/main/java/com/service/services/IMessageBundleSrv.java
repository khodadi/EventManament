package com.service.services;

import com.from.OutputAPIForm;

import java.util.Locale;

public interface IMessageBundleSrv {
    String getMessage(String key);
    void createMsg(OutputAPIForm obj);
    String getMessage(String key, Locale locale);
    void createMsg(OutputAPIForm obj, Locale locale);
}
