package pro.sky.telegrambot.scheduling;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.Repository.NotificationTaskRepository;

import java.time.LocalDateTime;
@Component
public class TelegramEventScheduler {
    private final TelegramBot telegramBot;
    private final NotificationTaskRepository repository;

    public TelegramEventScheduler(TelegramBot telegramBot, NotificationTaskRepository repository) {
        this.telegramBot = telegramBot;
        this.repository = repository;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void run() {
        repository.findAll().stream()
                .filter(en -> LocalDateTime.now().withNano(0).withSecond(0).isEqual(en.getData_time()))
                .forEach(en -> telegramBot.execute(new SendMessage(en.getChatid(), en.getNotification_text())));
    }
    }

