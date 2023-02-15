package com.zeusbe.service.impl;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class OtpService {
    //cache based on username and OPT MAX 5
    private static final Integer EXPIRE_MIN = 5;

    private final LoadingCache<String, String> otpCache;

    public OtpService(){
        super();
        otpCache= CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES).build(new CacheLoader<String, String>() {
            @Override
            public String load(String username)  {
                return null;
            }
        });
    }
// generateOTP
    public String generateOTP(String username){
        String otp = RandomStringUtils.randomAlphabetic(6);
        otpCache.put(username,otp);
        return otp;
    }
    //get otp save before by username
    public String getOTP(String username){
        try {
            System.out.println(otpCache.get(username));
            return otpCache.get(username);
        }catch (Exception e){
            return null;
        }
    }

    //This method is used to clear the OTP catched already
    public void ClearOTP(String username){
        otpCache.invalidate(username);
    }
}
