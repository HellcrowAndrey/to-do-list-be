# Derived from official mysql image (our base image)
FROM mysql:8.0.13

# Add a database
ENV MYSQL_ROOT_PASSWORD=root \
    MYSQL_DATABASE=tests_db

# Add the content of the sql-scripts/ directory to your image
# All scripts in docker-entrypoint-initdb.d/ are automatically
# executed during container startup
COPY ./scripts/ /docker-entrypoint-initdb.d/
