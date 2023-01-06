package pro.sky.telegrambot.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification_task")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long chatid;
    private String notification_text;
    private LocalDateTime data_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getChatid() {
        return chatid;
    }

    public void setChatid(Long chatid) {
        this.chatid = chatid;
    }

    public String getNotification_text() {
        return notification_text;
    }

    public void setNotification_text(String notification_text) {
        this.notification_text = notification_text;
    }

    public LocalDateTime getData_time() {
        return data_time;
    }

    public void setData_time(LocalDateTime data_time) {
        this.data_time = data_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationTask)) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getChatid(), that.getChatid()) && Objects.equals(getNotification_text(), that.getNotification_text()) && Objects.equals(getData_time(), that.getData_time());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getChatid(), getNotification_text(), getData_time());
    }
}
  /*  @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", Notification_Text='" + notification_text + '\'' +
                ", data_time=" + data_time +
                '}';
    }*/

