package com.gfb.tpl_reg_helper.ui;

import java.io.BufferedReader;
import java.io.IOException;

public class EnumSelectBlock<E extends Enum> implements UIBlock<E> {

    private E[] enumConstants;

    public EnumSelectBlock(E[] enumConstants) {
        this.enumConstants = enumConstants;
    }

    @Override
    public E apply(BufferedReader reader, String propertyName) {
        return this.apply(reader, propertyName, null);
    }

    @Override
    public E apply(
            BufferedReader reader,
            String propertyName,
            E defaultValue
    ) {
        System.out.println(propertyName
                + (null != defaultValue ? " [" + defaultValue + "]" : "")
                + ": ");
        int genderCounter = 1;
        for (Enum anEnum : enumConstants) {
            System.out.println(genderCounter + ") " + anEnum.name());
            genderCounter++;
        }
        System.out.print("  Select case: ");
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e1) {
//            e1.printStackTrace();
            return null;
        }
        if (line.length() > 0) {
            int genderPos = Integer.parseInt(line);
            return enumConstants[genderPos - 1];
        }
        return null;
    }
}
