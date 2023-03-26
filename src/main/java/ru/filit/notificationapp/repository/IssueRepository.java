package ru.filit.notificationapp.repository;


import org.springframework.data.repository.CrudRepository;
import ru.filit.notificationapp.entity.IssueInfo;

import java.util.Optional;

public interface IssueRepository extends CrudRepository<IssueInfo, Long> {
    Optional<IssueInfo> findByCode(String issueKey);
}
