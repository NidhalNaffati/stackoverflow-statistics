using Microsoft.EntityFrameworkCore;
using stackoverflow_statistics.Models;

namespace stackoverflow_statistics.Data
{
    public class QuestionsReadingDbContext : DbContext
    {
        public QuestionsReadingDbContext(DbContextOptions<QuestionsReadingDbContext> options) : base(options)
        {
        }

        public DbSet<Question> Questions { get; set; }

        public DbSet<QuestionTag> QuestionTags { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Question>(entity =>
            {
                entity.ToTable("Questions");

                entity.HasKey(e => e.QuestionId);

                entity.Property(e => e.QuestionId)
                    .HasColumnType("bigint");

                entity.Property(e => e.AcceptedAnswerId)
                    .HasColumnType("bigint");

                entity.Property(e => e.AnswerCount)
                    .HasColumnType("integer");

                entity.Property(e => e.ClosedDate)
                    .HasColumnType("bigint");

                entity.Property(e => e.CreationDate)
                    .HasColumnType("bigint");

                entity.Property(e => e.IsAnswered)
                    .HasColumnType("boolean");

                entity.Property(e => e.LastActivityDate)
                    .HasColumnType("bigint");

                entity.Property(e => e.LastEditDate)
                    .HasColumnType("bigint");

                entity.Property(e => e.Link)
                    .HasMaxLength(510)
                    .HasColumnType("character varying(510)");

                entity.Property(e => e.Score)
                    .HasColumnType("integer");

                entity.Property(e => e.Title)
                    .HasMaxLength(510)
                    .HasColumnType("character varying(510)");

                entity.Property(e => e.ViewCount)
                    .HasColumnType("integer");
            });

            modelBuilder.Entity<QuestionTag>(entity =>
            {
                entity.ToTable("QuestionTags");

                entity.HasKey(e => new { e.QuestionId, e.Tag });

                entity.Property(e => e.QuestionId)
                    .HasColumnType("bigint");

                entity.Property(e => e.Tag)
                    .HasMaxLength(255)
                    .HasColumnType("varchar(255)");

                entity.HasOne<Question>()
                    .WithMany()
                    .HasForeignKey(e => e.QuestionId)
                    .OnDelete(DeleteBehavior.Cascade)
                    .IsRequired();
            });
        }
    }
}