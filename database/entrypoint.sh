#!/bin/bash

# Start SQL Server in the background
/opt/mssql/bin/sqlservr &

# Wait for SQL Server to start
sleep 15s

# Run the SQL script using sqlcmd
/opt/mssql-tools18/bin/sqlcmd -C -S localhost -U sa -P YourPassword!123 -i /tmp/mssql/scripts/setup.sql -o /tmp/mssql/scripts/output.log 2>&1

# Tail the output log to keep the container running
tail -f /tmp/mssql/scripts/output.log