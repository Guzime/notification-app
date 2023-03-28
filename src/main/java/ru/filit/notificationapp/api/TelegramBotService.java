package ru.filit.notificationapp.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.filit.notificationapp.dto.CommentsRequestDto;
import ru.filit.notificationapp.dto.IssueRequestDto;

@FeignClient(name = "TelegramBot", url = "${feign.telegramBot.url}")
public interface TelegramBotService {
    @PutMapping("/issue")
    void sendIssueForTelegram(@RequestBody IssueRequestDto issueRequestDto);

    @PutMapping("/comment")
    void sendCommentsForTelegram(@RequestBody CommentsRequestDto commentsRequestDto);
}
