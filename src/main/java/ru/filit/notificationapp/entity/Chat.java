package ru.filit.notificationapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.filit.notificationapp.type.ChatStatus;
import ru.filit.notificationapp.type.ChatType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat", schema = "jira")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Long telegramId;
    @Enumerated(EnumType.STRING)
    private ChatType type;
    @Enumerated(EnumType.STRING)
    private ChatStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    @Builder.Default
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "issue_chat",
            schema = "jira",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "issue_id"))
    private Set<IssueInfo> subscribeIssues = new LinkedHashSet<>();

    public void addIssueInfo(IssueInfo issueInfo) {
        subscribeIssues.add(issueInfo);
        issueInfo.getSubscribeChats().add(this);
    }

    public void removeIssueInfo(IssueInfo issueInfo) {
        subscribeIssues.remove(issueInfo);
        issueInfo.getSubscribeChats().remove(this);
    }
}
