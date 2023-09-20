package com.bot.telega.service;


import com.bot.telega.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;


    }

    @Override
    public void onUpdateReceived(Update update) { //главный центральный метод, здесь обработка того что пишет пользователь и возращается ответ
        if (update.hasMessage() && update.getMessage().hasText()) {                 //проверка на сообщение от пользователя и проверка что там есть текст
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId(); // получение id  чата для отправки сообщений ботом



            switch (messageText){
                case "/start":
                    startCommandReceived(chatId, update.getMessage(). getChat(). getFirstName()); //getFirstName отвечает за получения имени человека

            }


        }

        }
    private void startCommandReceived(long chatId, String name){
        String answer = "Привет, "+name+", рад тебя видеть здесь :)";

    }
    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage(); // класс SendMessage класс для отправки сообщений боту
        message.setChatId(chatId);// чтобы что-то отправить нужен ChatId


    }


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken () {
        return config.getToken();
}
}
