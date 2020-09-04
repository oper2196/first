package com.example.demo.cache;

import java.util.HashMap;
import java.util.Map;

public class Cache {

    public static Map<Integer,String> emailCache = new HashMap<>();


    public static synchronized boolean isExist(Integer key){

        if(emailCache.containsKey(key)){
            return true;
        }
        return false;
    }

}
