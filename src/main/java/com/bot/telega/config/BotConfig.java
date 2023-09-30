package com.bot.telega.config;


import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.name}")
    String botName;


    @Value("${6056194866:AAHNIuIdrdJAIqVcRNufPVrBU9RPtSok84I}")
    String token;


}
