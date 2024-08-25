using Microsoft.EntityFrameworkCore;
using stackoverflow_statistics.Models;

namespace stackoverflow_statistics.Data

{
    public class QuestionRepository
    {
        private readonly QuestionsReadingDbContext _context;

        public QuestionRepository(QuestionsReadingDbContext context)
        {
            _context = context;
        }

        public async Task AddAsync(Question question)
        {
            await _context.Questions.AddAsync(question);
            await _context.SaveChangesAsync();
        }

        public async Task UpdateAsync(Question question)
        {
            _context.Questions.Update(question);
            await _context.SaveChangesAsync();
        }

        public async Task<bool> ExistsAsync(long id)
        {
            return await _context.Questions.AnyAsync(q => q.QuestionId == id);
        }
    }

}