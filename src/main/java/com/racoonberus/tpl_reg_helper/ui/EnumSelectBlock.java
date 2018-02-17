package com.racoonberus.tpl_reg_helper.ui;

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
        try {
            System.out.println(propertyName
                    + (null != defaultValue ? " [" + defaultValue + "]" : "")
                    + ": ");
            int counter = 1;
            for (Enum anEnum : enumConstants) {
                System.out.println("    " + counter + ") " + anEnum.name());
                counter++;
            }
            System.out.print("  Select case: ");
            String line = reader.readLine();
            if (line.length() > 0) {
                int pos = Integer.parseInt(line);
                return enumConstants[pos - 1];
            }
        } catch (NumberFormatException | IOException e) {
            //
        }
        return defaultValue;
    }
}
