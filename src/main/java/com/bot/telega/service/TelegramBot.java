package com.bot.telega.service;


import com.bot.telega.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
        List<BotCommand> listofCommands = new ArrayList<>(); //list не абстрактный, а содержит команды бота
        listofCommands.add(new BotCommand("/start","начать взаимодействовать с ботом"));
        listofCommands.add(new BotCommand("/plans","узнать о будущих обновах"));
        listofCommands.add(new BotCommand("/help","как пользоваться ботом"));
        try{
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        }
        catch (TelegramApiException e){

        }


    }

    @Override
    public void onUpdateReceived(Update update) { //главный центральный метод, здесь обработка того что пишет пользователь и возращается ответ
        if (update.hasMessage() && update.getMessage().hasText()) {                 //проверка на сообщение от пользователя и проверка что там есть текст
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId(); // получение id  чата для отправки сообщений ботом, при отправке сообщений ChatId типа long



            switch (messageText){
                case "/start":
                case "старт":
                case "Старт":
                case "Начать":
                case "начать":
                    try {
                        startCommandReceived(chatId, update.getMessage(). getChat(). getFirstName()); //getFirstName отвечает за получения имени человека
                        break;
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                case "/plans" :
                    try {
                        sendNextMessage(chatId, update.getMessage().getChat().getFirstName());
                        break;
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                case "/help" :
                    try {
                        sendHelpMessage(chatId, update.getMessage().getChat());
                        break;
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }


                default:
                    try {
                        sendMessage(chatId, "Сорри брат, пока еще не поддерживается такой функционал");
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

            }


        }

        }
    private void startCommandReceived(long chatId, String name) throws TelegramApiException {
        String answer = "Привет, "+name+", рад тебя видеть здесь :) Если напишешь /планы, то узнаешь что я буду делать потом";
        sendMessage(chatId, answer);

    }
    private void sendMessage(long chatId, String textToSend) throws TelegramApiException {
        SendMessage message = new SendMessage(); // класс SendMessage класс для отправки сообщений боту
        message.setChatId(String.valueOf(chatId));// чтобы что-то отправить нужен ChatId, при входящем сообщение ChatId типа String
        message.setText(textToSend);

        try{
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }



    }
    private void sendNextMessage(long chatId, String name) throws TelegramApiException {
        String answer = name + ", смотри, я скоро научусь новым фишкам. Буду стараться освоить поиск определенной информации, также добавлю меню, чтобы было" +
                " проще ко мне обращаться";
        sendMessage(chatId, answer);
    }
    private void sendHelpMessage(long chatId, Chat chat) throws TelegramApiException {
        String answer = "Смотри, я предназначен чтобы тебя приветсвовать, а потом буду отправлять тебе фото с заменами на пары";
        sendMessage(chatId, answer);

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
