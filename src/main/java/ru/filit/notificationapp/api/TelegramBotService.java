package ru.filit.notificationapp.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.filit.notificationapp.dto.CommentsRequestDto;
import ru.filit.notificationapp.dto.IssueRequestDto;

@FeignClient(name = "TelegramBot", url = "${feign.telegram-bot.url}")
public interface TelegramBotService {
    @PostMapping("/issue")
    void sendIssueNotification(@RequestBody IssueRequestDto issueRequestDto);

    @PostMapping("/comment")
    void sendCommentNotification(@RequestBody CommentsRequestDto commentsRequestDto);
}
