IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='users' AND xtype='U')
CREATE TABLE users (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    surname NVARCHAR(100) NOT NULL,
    username NVARCHAR(50) NOT NULL UNIQUE,
    email NVARCHAR(100) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    user_role NVARCHAR(20) NOT NULL,
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE()
);
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='request_logs' AND xtype='U')
CREATE TABLE request_logs (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(255),
    method VARCHAR(10),
    url VARCHAR(MAX),
    request_body VARCHAR(MAX),
    response_body VARCHAR(MAX),
    status INT,
    timestamp DATETIME2,
    execution_time_ms BIGINT
    );
