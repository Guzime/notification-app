package ru.filit.notificationapp.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jira_comment", schema = "jira")
public class CommentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;

    @Column(name = "issue_id")
    private Long issueId;
    private Long jiraId;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
