package ru.filit.notificationapp.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.filit.notificationapp.entity.jira.JiraIssueInfoResponse;

import java.util.HashMap;
import java.util.List;

@FeignClient(name = "Jira", url = "${feign.jira.url}")
public interface JiraService {
    @GetMapping("/{ticket}")
    JiraIssueInfoResponse findTicketInfo(@PathVariable("ticket") String ticket);

    @GetMapping()
    HashMap<String, JiraIssueInfoResponse> findSeveralTickets(@RequestHeader List<String> tickets);
}
