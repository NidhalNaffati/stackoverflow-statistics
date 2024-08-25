using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace stackoverflow_statistics.Models;

public class Question
{
    [Key]
    public long QuestionId { get; init; }

    public bool? IsAnswered { get; set; }

    [MaxLength(510)]
    public string Title { get; init; }

    [MaxLength(510)]
    public string Link { get; init; }

    public int Score { get; init; }

    public int AnswerCount { get; init; }

    public long AcceptedAnswerId { get; init; }

    public int ViewCount { get; init; }

    [NotMapped]
    public List<string> Tags { get; init; }

    public long CreationDate { get; init; }

    public long ClosedDate { get; init; }

    public long LastActivityDate { get; init; }

    public long LastEditDate { get; init; }

}