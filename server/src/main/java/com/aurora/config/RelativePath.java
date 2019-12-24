package com.aurora.config;

public class RelativePath {




    public static String getRelativePath()
    {
        String path = DataSourceConfig.class.getResource("").getPath();
        String fullPath = path.replace("/WEB-INF/classes/com/aurora/config/", "/");
        return fullPath;
    }
}
