using Microsoft.EntityFrameworkCore;
using stackoverflow_statistics.Configuration;
using stackoverflow_statistics.Data;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

// Add DbContext
builder.Services.AddDbContext<QuestionsReadingDbContext>(options =>
    options
        .UseNpgsql(builder.Configuration.GetConnectionString("QuestionContext"))
        .EnableSensitiveDataLogging() // Optionally enable to log parameter values
        .EnableDetailedErrors()
);

// Add configuration
builder.Services.Configure<StackExchangeApiConfig>(builder.Configuration.GetSection("StackExchange"));


builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Check database connection
using (var scope = app.Services.CreateScope())
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

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();