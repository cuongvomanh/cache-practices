package com.example.cacheinspring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

public class CachingConfigUtils {
    private Logger LOGGER = LoggerFactory.getLogger(CachingConfigUtils.class);

    public KeyGenerator classNameAndFunctionNameAndParamsKeyGenerator() {
        return new KeyGenerator() {
            public Object generate(Object target, Method method, Object... params) {
                return target.getClass().getSimpleName() + "_"
                        + method.getName() + "_"
                        + StringUtils.arrayToDelimitedString(params, "_");
            }
        };
    }

    public KeyGenerator simpleClassNameAndTenantIdAndFunctionNameAndParamsKeyGenerator(){
        return new KeyGenerator() {
            public Object generate(Object target, Method method, Object... params) {
                String tenantIdString = "";
                try {
                    if (method.getName().matches(".*ByTenantId.*")){
                        int tenantId = ((int) params[0]);
                        tenantIdString = String.valueOf(tenantId);
                    }
                } catch (Exception exception){
                    LOGGER.error("Get tenantId from first Param Error");
                    exception.printStackTrace();
                }
                return target.getClass().getSimpleName().toLowerCase().replaceAll("service", "") + ":"
                        + tenantIdString + ":"
                        + method.getName() + ":"
                        + StringUtils.arrayToDelimitedString(params, ":");
            }
        };
    }
}
