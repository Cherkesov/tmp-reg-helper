package com.racoonberus.tpl_reg_helper.ui;

import java.io.BufferedReader;

public interface UIBlock<T> {
    public T apply(BufferedReader reader, String propertyName);

    public T apply(BufferedReader reader, String propertyName, T defaultValue);
}
