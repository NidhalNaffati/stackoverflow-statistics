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
}
