using Microsoft.EntityFrameworkCore;
using stackoverflow_statistics.Data;

namespace stackoverflow_statistics;

public abstract class Program
{
    public static async Task Main(string[] args)
    {
        var builder = Host.CreateApplicationBuilder(args);

        // Add DbContext
        builder.Services.AddDbContext<QuestionsReadingDbContext>(options =>
                options
                    .UseNpgsql(builder.Configuration.GetConnectionString("QuestionContext"))
                    .EnableSensitiveDataLogging() // Optionally enable to log parameter values
                    .EnableDetailedErrors()
        );

        var host = builder.Build();

        // Check database connection
        using (var scope = host.Services.CreateScope())
        {
            var dbContext = scope.ServiceProvider.GetRequiredService<QuestionsReadingDbContext>();
            try
            {
                await dbContext.Database.OpenConnectionAsync();
                Console.WriteLine("Database connection successful.");

                // Apply any pending migrations and create the database if it does not exist
                // await dbContext.Database.MigrateAsync();
                // Console.WriteLine("Database migration successful.");

            }
            catch (Exception ex)
            {
                Console.WriteLine($"Database connection failed: {ex.Message}");
            }
        }

        await host.RunAsync();
    }
}