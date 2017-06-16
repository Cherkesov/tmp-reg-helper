package com.gfb.tmp_reg_helper.ui;

import java.io.BufferedReader;

/**
 * Created by goforbroke on 13.06.17.
 */
public interface IUIBlock<T> {
    public T apply(BufferedReader reader, String propertyName);

    public T apply(BufferedReader reader, String propertyName, T defaultValue);
}
