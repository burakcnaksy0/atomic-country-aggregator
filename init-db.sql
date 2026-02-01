-- Create database if not exists
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'countryDb')
BEGIN
    CREATE DATABASE countryDb;
END
GO

-- Create login if not exists
USE master;
GO

IF NOT EXISTS (SELECT name FROM sys.server_principals WHERE name = 'admin')
BEGIN
    CREATE LOGIN admin WITH PASSWORD = 'Admin123!@#';
END
GO

-- Switch to countryDb and create user
USE countryDb;
GO

IF NOT EXISTS (SELECT name FROM sys.database_principals WHERE name = 'admin')
BEGIN
    CREATE USER admin FOR LOGIN admin;
    ALTER ROLE db_owner ADD MEMBER admin;
END
GO
