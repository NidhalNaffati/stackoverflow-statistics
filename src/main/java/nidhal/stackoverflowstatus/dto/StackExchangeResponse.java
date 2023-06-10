package nidhal.stackoverflowstatus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import nidhal.stackoverflowstatus.entity.Question;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class StackExchangeResponse {

    private List<Question> items;
    public boolean has_more;
    public int quota_max;
    public int quota_remaining;
}
