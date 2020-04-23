package com.lagou.io;

import java.io.InputStream;

public class Resources {
    public static InputStream getResourceAsInputeStream(String path){
        InputStream inputStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return inputStream;
    }
}
