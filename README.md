### 1. Database Setup

The project uses MSSQL running in a Docker container.

1.  **Start the Database:**
    Open a terminal in the project root and run:
    ```bash
    docker compose up -d
    ```
    This will start the MSSQL server on **port 1430** (mapped from 1433 inside the container).

2.  **Initialize the Database:**
    Since the MSSQL image does not automatically run SQL scripts on startup by default, you need to manually execute the initialization script to create the database and user.

    Run the following command *after* the container is up and healthy:
    ```bash
    cat init-db.sql | docker exec -i mssql-countrydb /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P 'Admin123!' -C
    ```
    *This script creates the `countryInfoDb` database and the `burak` user.*
