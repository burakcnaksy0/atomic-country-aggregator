-- Create database if not exists
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'countryDb')
BEGIN
    CREATE DATABASE countryDb;
END
GO

-- Create login if not exists
USE master;
GO


IF NOT EXISTS (SELECT name FROM sys.server_principals WHERE name = 'burak')
BEGIN
    CREATE LOGIN burak WITH PASSWORD = 'Bur@kCan_2024';
END
GO

-- Switch to countryDb and create user
USE countryDb;
GO

IF NOT EXISTS (SELECT name FROM sys.database_principals WHERE name = 'burak')
BEGIN
    CREATE USER burak FOR LOGIN burak;
    ALTER ROLE db_owner ADD MEMBER burak;
END
GO
