ALTER USER postgres WITH PASSWORD 'P0stgres';

--    sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'postgres';"

CREATE ROLE facturacion WITH LOGIN SUPERUSER PASSWORD 'facturaci0n';