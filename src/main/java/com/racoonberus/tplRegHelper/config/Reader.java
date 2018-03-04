package com.racoonberus.tplRegHelper.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.racoonberus.tplRegHelper.domain.config.Configuration;

import java.io.*;

public class Reader {
    public static Configuration fromFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String line;

        while (null != (line = reader.readLine())) {
            stringBuilder.append(line);
        }

        return fromString(stringBuilder.toString());
    }

    public static Configuration fromString(String content) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Configuration.class);
    }
}
