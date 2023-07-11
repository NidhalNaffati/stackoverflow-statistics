package nidhal.stackoverflowstatus.repository;

import nidhal.stackoverflowstatus.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
