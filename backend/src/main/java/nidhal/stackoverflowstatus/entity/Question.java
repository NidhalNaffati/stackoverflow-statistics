package nidhal.stackoverflowstatus.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

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
}