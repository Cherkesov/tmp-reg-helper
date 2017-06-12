package com.gfb.tmp_reg_helper.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by goforbroke on 13.06.17.
 */
public class EnumSelectBlock<T extends Enum> implements IUIBlock {

    private Class<T> enumClass;

    public EnumSelectBlock(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public Object apply(InputStream in, String propertyName) {
        return this.apply(in, propertyName, null);
    }

    @Override
    public T apply(
            InputStream in,
            String propertyName,
            Object defaultValue
    ) {
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(in));

        System.out.println(propertyName
                + (null != defaultValue ? " [" + defaultValue + "]" : "")
                + ": ");
        int genderCounter = 1;
        for (Enum anEnum : enumClass.getEnumConstants()) {
            System.out.println(genderCounter + ")" + anEnum.name());
            genderCounter++;
        }
        System.out.print("Select case: ");
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e1) {
//            e1.printStackTrace();
            return null;
        }
        if (line.length() > 0) {
            int genderPos = Integer.parseInt(line);
            return enumClass.getEnumConstants()[genderPos - 1];
        }
        return null;
    }
}
