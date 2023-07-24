package nidhal.stackoverflowstatus.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Builder
@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Question implements Serializable {
    @Id
    private long question_id;
    public Boolean is_answered;
    // Adjust the length of the column to avoid the following error:
    // Data truncation: Data too long for column 'title' at row 1
    @Column(length = 510)
    private String title;
    @Column(length = 510)
    private String link;
    private int score;
    private int answer_count;
    public long accepted_answer_id;
    private int view_count;
    @ElementCollection
    private List<String> tags;
    private long creation_date;
    public long closed_date;
    public long last_activity_date;
    public long last_edit_date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question question)) return false;
        return getQuestion_id() == question.getQuestion_id() &&
               getScore() == question.getScore() &&
               getAnswer_count() == question.getAnswer_count() &&
               getAccepted_answer_id() == question.getAccepted_answer_id() &&
               getView_count() == question.getView_count() &&
               getCreation_date() == question.getCreation_date() &&
               getClosed_date() == question.getClosed_date() &&
               getLast_activity_date() == question.getLast_activity_date() &&
               getLast_edit_date() == question.getLast_edit_date() &&
               Objects.equals(getIs_answered(), question.getIs_answered()) &&
               Objects.equals(getTitle(), question.getTitle()) &&
               Objects.equals(getLink(), question.getLink()) &&
               Objects.equals(getTags(), question.getTags());
    }

}