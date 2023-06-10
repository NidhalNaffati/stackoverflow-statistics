package nidhal.stackoverflowstatus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Owner {
    @Id
    private long user_id;
    private int reputation;
    private String user_type;
    private String profile_image;
    private String display_name;
    private String link;
}
