package com.kaiyu.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Google验证码配置类
 * @author mxxxl
 * @date 2021/6/15
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        DefaultKaptcha captchaProducer = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border","no");
        properties.setProperty("kaptcha.image.width","90");
        properties.setProperty("kaptcha.image.height","40");
        properties.setProperty("kaptcha.background.clear.from","248,248,248");
        properties.setProperty("kaptcha.background.clear.to","248,248,248");
        properties.setProperty("kaptcha.textproducer.font.color","0,0,255");
        properties.setProperty("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.WaterRipple");
        properties.setProperty("kaptcha.textproducer.font.size","35");
        properties.setProperty("kaptcha.textproducer.char.length","4");
        properties.setProperty("kaptcha.textproducer.char.space","2");
        properties.setProperty("kaptcha.textproducer.font.names","宋体，楷体，微软雅黑");
        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
        Config config = new Config(properties);
        captchaProducer.setConfig(config);
        return captchaProducer;
    }
}
