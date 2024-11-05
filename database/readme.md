# Installation of the Local DB Environment
1. Open a Terminal in the same directory as the Dockerfile
2. Run the command: docker-compose build --no-cache
3. Run the command: docker-compose up

Docker will now install and configure the required dependencies.
After SQL Server starts up, it may take a few seconds before the DB is created from setup.sql

If the DB does not get created from setup.sql, increase the sleep time in entrypoint.sh, as it's likely taking longer for SQL Server to start up on your device.

# Logging into the SQL Server
**Server name / IP:** localhost,1434
**Auth:** SQL Server Authentication
**Username:** SA
**Password:** YourPassword!123
**Connection Security:** Trust server certificate

Keep in mind that this environment is only to be tested locally for development, as this is an insecure setup for the database itself.

# YOU DO NOT NEED TO BUILD NOR COMPOSE THE IMAGE EVERY TIME
You can run the docker container via Docker Desktop and you should be able to access the database just where you left off.
If someone updates setup.sql or any other process in the building of this docker container, then of course, you wil need to rebuild and run the docker image/container. ***(If you do decide to change anything in the creation of the docker image/container, be sure to use LF line endings and not CRLF)***

**Make sure to delete the old container before re-creating it**. The docker image will always use the same name when building a container, so the names would conflict.

# Implementing permanent testdata
If you want to implement testdata that you require for your feature, add it into setup.sql and check if it populates properly.