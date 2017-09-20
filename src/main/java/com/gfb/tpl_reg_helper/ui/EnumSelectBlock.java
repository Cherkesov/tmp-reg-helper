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
        int counter = 1;
        for (Enum anEnum : enumConstants) {
            System.out.println("    " + counter + ") " + anEnum.name());
            counter++;
        }
        System.out.print("  Select case: ");
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e1) {
            return null;
        }
        if (line.length() > 0) {
            int pos = Integer.parseInt(line);
            return enumConstants[pos - 1];
        }
        return null;
    }
}
