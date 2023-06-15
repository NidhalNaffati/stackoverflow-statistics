package nidhal.stackoverflowstatus.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nidhal.stackoverflowstatus.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Map<String, Integer> getQuestionCountByDayOfWeek(String tag) {
        log.error("getQuestionCountByDayOfWeek for tag: {}", tag);

        List<Long> creationDates = questionRepository.findCreationDatesByTagsContaining(tag);
        Map<String, Integer> questionsCountByDayOfWeek = new HashMap<>();

        for (Long creationDate : creationDates) {
            LocalDateTime creationDateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(creationDate),
                    ZoneId.systemDefault()
            );
            DayOfWeek dayOfWeek = creationDateTime.getDayOfWeek();
            String dayOfWeekName = dayOfWeek.toString().toLowerCase();

            questionsCountByDayOfWeek.put(
                    dayOfWeekName,
                    questionsCountByDayOfWeek.getOrDefault(dayOfWeekName, 0) + 1
            );
        }

        return questionsCountByDayOfWeek;
    }

}
