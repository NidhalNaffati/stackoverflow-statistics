using Microsoft.EntityFrameworkCore.Migrations;

namespace stackoverflow_statistics.Migrations
{
    public class DbMigration : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Questions",
                columns: table => new
                {
                    QuestionId = table.Column<long>(type: "bigint", nullable: false),
                    IsAnswered = table.Column<bool>(type: "boolean", nullable: true),
                    Title = table.Column<string>(type: "varchar(510)", maxLength: 510, nullable: true),
                    Link = table.Column<string>(type: "varchar(510)", maxLength: 510, nullable: true),
                    Score = table.Column<int>(type: "integer", nullable: false),
                    AnswerCount = table.Column<int>(type: "integer", nullable: false),
                    AcceptedAnswerId = table.Column<long>(type: "bigint", nullable: false),
                    ViewCount = table.Column<int>(type: "integer", nullable: false),
                    CreationDate = table.Column<long>(type: "bigint", nullable: false),
                    ClosedDate = table.Column<long>(type: "bigint", nullable: false),
                    LastActivityDate = table.Column<long>(type: "bigint", nullable: false),
                    LastEditDate = table.Column<long>(type: "bigint", nullable: false)
                },
                constraints: table => { table.PrimaryKey("PK_Questions", x => x.QuestionId); });

            // Additional setup for tags
            migrationBuilder.CreateTable(
                name: "QuestionTags",
                columns: table => new
                {
                    QuestionId = table.Column<long>(type: "bigint", nullable: false),
                    Tag = table.Column<string>(type: "varchar(255)", maxLength: 255, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_QuestionTags", x => new { x.QuestionId, x.Tag });
                    table.ForeignKey(
                        name: "FK_QuestionTags_Questions_QuestionId",
                        column: x => x.QuestionId,
                        principalTable: "Questions",
                        principalColumn: "QuestionId",
                        onDelete: ReferentialAction.Cascade
                    );
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(name: "QuestionTags");

            migrationBuilder.DropTable(name: "Questions");
        }
    }
}