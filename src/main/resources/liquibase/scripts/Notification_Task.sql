--liquibase formatted sql

--changeset scherbakov:1
CREATE TABLE notification_task(
                                  id SERIAL NOT NULL ,
                                  chatId bigint NOT NULL ,
                                  notification_text TEXT,
                                  data_time TIMESTAMP NOT NULL
)