package ru.filit.notificationapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "issue", schema = "jira")
public class IssueInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String code;
    private String status;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "issue_chat",
            schema = "jira",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    @JsonIgnore
    private Set<Chat> subscribeChats = new LinkedHashSet<>();
    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "issue_id")
    private List<CommentInfo> comments = new LinkedList<>();

    public void addComment(CommentInfo commentInfo) {
        comments.add(commentInfo);

    }
}