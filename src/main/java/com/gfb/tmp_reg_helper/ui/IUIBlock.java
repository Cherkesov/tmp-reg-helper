package com.gfb.tmp_reg_helper.ui;

import java.io.InputStream;

/**
 * Created by goforbroke on 13.06.17.
 */
public interface IUIBlock {
    public Object apply(InputStream in, String propertyName);

    public Object apply(InputStream in, String propertyName, Object defaultValue);
}
