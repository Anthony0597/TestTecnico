IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'test_prog')
BEGIN
    CREATE DATABASE test_prog;
    WAITFOR DELAY '00:00:20';
END;
GO