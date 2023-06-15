package nidhal.stackoverflowstatus.repository;

import nidhal.stackoverflowstatus.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q.creation_date FROM Question q WHERE :tag MEMBER OF q.tags")
    List<Long> findCreationDatesByTagsContaining(String tag);

    @Query("SELECT COUNT(q) FROM Question q WHERE q.is_answered = :isAnswered AND :tag MEMBER OF q.tags")
    int countByIs_answeredAndTagsContains(boolean isAnswered, String tag);

    @Query("SELECT COUNT(q) FROM Question q WHERE q.is_answered = :isAnswered")
    int countByIs_answered(boolean isAnswered);

    @Query("SELECT COUNT(q) FROM Question q")
    int countAll();
}
