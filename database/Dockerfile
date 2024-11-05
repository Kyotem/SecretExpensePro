# Use the official Microsoft SQL Server image
FROM mcr.microsoft.com/mssql/server:2022-latest

# Set environment variables for SQL Server
ENV ACCEPT_EULA=Y
ENV SA_PASSWORD=YourPassword!123  
ENV MSSQL_PID=Developer

# Create a new directory for scripts where we have permission
RUN mkdir -p /tmp/mssql/scripts

# Copy the setup SQL script into the container
COPY setup.sql /tmp/mssql/scripts/

# Copy the entrypoint script that will run the SQL script after SQL Server starts
COPY entrypoint.sh /tmp/mssql/scripts/

# Expose SQL Server port 1433 inside the container
EXPOSE 1433

# Set the entrypoint to run the custom script
ENTRYPOINT ["/bin/bash", "/tmp/mssql/scripts/entrypoint.sh"]