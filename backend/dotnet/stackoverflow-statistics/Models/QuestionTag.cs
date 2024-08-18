using System.ComponentModel.DataAnnotations;

namespace stackoverflow_statistics.Models;

public class QuestionTag
{
    [Key] public long QuestionId { get; set; }
    public string Tag { get; set; }

    public Question Question { get; set; }
}