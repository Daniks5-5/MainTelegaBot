package com.bot.telega.service;


import com.bot.telega.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
            long chatId = update.getMessage().getChatId(); // получение id  чата для отправки сообщений ботом, при отправке сообщений ChatId типа long



            switch (messageText){
                case "/start":
                    try {
                        startCommandReceived(chatId, update.getMessage(). getChat(). getFirstName()); //getFirstName отвечает за получения имени человека
                        break;
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                case "/планы" :
                    try {
                        sendNextMessage(chatId, update.getMessage().getChat().getFirstName());
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


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken () {
        return config.getToken();
}
}
