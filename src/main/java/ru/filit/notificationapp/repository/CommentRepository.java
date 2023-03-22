package ru.filit.notificationapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.filit.notificationapp.entity.CommentInfo;

public interface CommentRepository extends CrudRepository<CommentInfo, Long> {

}
