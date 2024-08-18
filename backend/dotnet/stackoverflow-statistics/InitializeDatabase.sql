-- InitializeDatabase.sql

-- Create Questions table
CREATE TABLE IF NOT EXISTS Questions
(
    QuestionId       BIGINT       NOT NULL PRIMARY KEY,
    IsAnswered       BOOLEAN      NULL,
    Title            VARCHAR(510) NULL,
    Link             VARCHAR(510) NULL,
    Score            INTEGER      NOT NULL,
    AnswerCount      INTEGER      NOT NULL,
    AcceptedAnswerId BIGINT       NOT NULL,
    ViewCount        INTEGER      NOT NULL,
    CreationDate     BIGINT       NOT NULL,
    ClosedDate       BIGINT       NOT NULL,
    LastActivityDate BIGINT       NOT NULL,
    LastEditDate     BIGINT       NOT NULL
);

-- Create QuestionTags table
CREATE TABLE IF NOT EXISTS QuestionTags
(
    QuestionId BIGINT       NOT NULL,
    Tag        VARCHAR(255) NOT NULL,
    PRIMARY KEY (QuestionId, Tag),
    CONSTRAINT FK_QuestionTags_Questions_QuestionId FOREIGN KEY (QuestionId)
        REFERENCES Questions (QuestionId)
);
