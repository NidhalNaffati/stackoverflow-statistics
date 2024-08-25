using System.ComponentModel.DataAnnotations;
using Newtonsoft.Json;

namespace stackoverflow_statistics.Models;

public class QuestionTag
{
    [Key]
    [JsonProperty("question_id")]
    public long QuestionId { get; set; }

    [JsonProperty("tag")]
    public string Tag { get; set; }

    public Question Question { get; init; }
}