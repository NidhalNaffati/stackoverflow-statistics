using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Newtonsoft.Json;

namespace stackoverflow_statistics.Models;

public class Question
{
    [Key]
    [JsonProperty("question_id")]
    public long QuestionId { get; init; }

    [JsonProperty("is_answered")]
    public bool IsAnswered { get; init; }

    [MaxLength(510)]
    [JsonProperty("title")]
    public string Title { get; init; }

    [MaxLength(510)]
    [JsonProperty("link")]
    public string Link { get; init; }

    [JsonProperty("score")]
    public int Score { get; init; }

    [JsonProperty("answer_count")]
    public int AnswerCount { get; init; }

    [JsonProperty("accepted_answer_id")]
    public long AcceptedAnswerId { get; init; }

    [JsonProperty("view_count")]
    public int ViewCount { get; init; }

    [JsonProperty("creation_date")]
    public long CreationDate { get; init; }

    [JsonProperty("closed_date")]
    public long ClosedDate { get; init; }

    [JsonProperty("last_activity_date")]
    public long LastActivityDate { get; init; }

    [JsonProperty("last_edit_date")]
    public long LastEditDate { get; init; }

    [NotMapped]
    [JsonProperty("tags")]
    public List<string> Tags { get; init; }
}