package com.hlhs.hlhsrpc.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.yaml.YamlUtil;
import com.hlhs.hlhsrpc.config.RpcConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 配置工具类
 */
public class ConfigUtils {
    /**
     * 加载配置对象
     *
     * @param tClass
     * @param prefix
     * @param <T>
     * @return
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    /**
     * 加载配置文件 支持区分环境
     *
     * @param tClass
     * @param prefix
     * @param environment
     * @param <T>
     * @return
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        StringBuilder configFilePBuilder = getStringBuilder(environment);
        configFilePBuilder.append(".properties");
        Props props = null;
        try {
            props = new Props(configFilePBuilder.toString());
        } catch (Exception e) {
            Object yamlObject = YamlUtil.loadByPath(getStringBuilder(environment).append(".yml").toString()).get(prefix);
            return BeanUtil.toBean(yamlObject,tClass);
        }
        return props.toBean(tClass, prefix);
    }

    private static StringBuilder getStringBuilder(String environment) {
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        return configFileBuilder;
    }
}
