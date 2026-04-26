-- Drop tables to reset database
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS problem_statements;

-- Verify tables are dropped
SELECT 'Tables dropped successfully' as status;
