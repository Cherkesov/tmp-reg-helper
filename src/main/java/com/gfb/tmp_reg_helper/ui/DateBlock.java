package com.gfb.tmp_reg_helper.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by goforbroke on 16.06.17.
 */
public class DateBlock implements IUIBlock<Date> {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date apply(BufferedReader reader, String propertyName) {
        return this.apply(reader, propertyName, new Date());
    }

    @Override
    public Date apply(BufferedReader reader, String propertyName, Date defaultValue) {
        System.out.print(propertyName
                + (
                null != defaultValue
                        ? " [" + simpleDateFormat.format(defaultValue) + "]"
                        : " (input format yyyy-mm-dd)")
                + ": ");

        String line = null;
        try {
            line = reader.readLine();
            return simpleDateFormat.parse(line);
        } catch (IOException e1) {
//            e1.printStackTrace();
        } catch (ParseException e) {
//            e.printStackTrace();
        }


        return null;
    }
}
