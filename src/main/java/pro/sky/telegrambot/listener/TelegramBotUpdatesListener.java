package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.Repository.NotificationTaskRepository;
import pro.sky.telegrambot.entity.NotificationTask;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final NotificationTaskRepository repository;
    private final TelegramBot telegramBot;

    public TelegramBotUpdatesListener(NotificationTaskRepository repository, TelegramBot telegramBot) {
        this.repository = repository;
        this.telegramBot = telegramBot;
    }
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }
           @Override
            public int process(List<Update> updates) {
                updates.forEach(update -> {
                    logger.info("Processing update: {}", update);
                    long chatId = (update.message().chat().id());
                    var updateMassage = update.message().text();
                    if (updateMassage == null) {
                        updateMassage = "";
                    }
                    updateMassage = updateMassage.toLowerCase();
                    if ("/start".equals(updateMassage)) {
                        var message = new SendMessage(chatId, "Добро пожаловать");
                        telegramBot.execute(message);
                    }
                    var pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
                    var matcher = pattern.matcher(updateMassage);
                    if (matcher.matches()) {
                        var date = matcher.group(1);
                        var message = matcher.group(3);
                        var timeToSendNotification = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                        var entity = new NotificationTask();
                        entity.setData_time(timeToSendNotification.withNano(0).withSecond(0));
                        entity.setChatId(chatId);
                        entity.setNotification_text(message);
                        repository.save(entity);
                    }
                });
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        }
