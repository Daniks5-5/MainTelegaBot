package com.bot.telega.service;


import com.bot.telega.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot; // с этой библеотекой бот сам переодически проверяет написал ли ему пользователь, но WebhookBoot эффективен при большой нагрузке
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig config;

    public TelegramBot(BotConfig config){
        this.config = config;


    }

    @Override
    public void onUpdateReceived(Update update) { //главный центральный метод

    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken(){
        return config.getToken();
    }
}
