package ru.filit.notificationapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.filit.notificationapp.entity.Chat;

import java.util.Optional;

public interface ChatRepository extends CrudRepository<Chat, Long> {

    Optional<Chat> findByTelegramId(Long telegramId);

    void deleteByTelegramId(Long telegramId);
}
