package com.ravn.movies.utils;

import java.net.URLDecoder;
import java.sql.Timestamp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

@Slf4j
public class Utils {

    public static long getCurrentTimeSeconds() {
        return new Timestamp(System.currentTimeMillis()).getTime() / 1000;
    }

    public static String getUniqueKeyName(String fileName) {
        String newCleanName;
        String newCleanNameDecoded;
        newCleanName = FilenameUtils.getName(fileName.trim());
        try{
            newCleanNameDecoded = URLDecoder.decode(newCleanName, "UTF-8");
            newCleanName = newCleanNameDecoded.replaceAll("\\s", "");
        }catch(Exception e){
            log.error("getUniqueKeyName - error decoding utf so get fallback name" + newCleanName);
        }
        newCleanName = newCleanName.replaceAll("[^a-zA-Z0-9-.]", "-");
        newCleanName = newCleanName.toLowerCase();
        newCleanName = newCleanName.replaceAll("___", "---");
        long unixTime = System.currentTimeMillis();
        newCleanName = unixTime + "-" + newCleanName;
        return newCleanName;
    }
}
