the image for postgres and for pgadmin4 were downloaded 
navigate to this location from cmd
run the command docker compose up
navigate to http://localhost:5050/browser/
use the username and password from the yml file
in the UI create a server from Add new server. pick a server name. In the connection tab use the name of the container as host and port 5432. username root, password root
create a database, 
create a table, 
insert data into the table

to enter command line mode
docker exec -it 48805b9e1ac8 bash  - use the id of the database container
psql -U root Student -> root is the username, Student is the name of the database (database is the name of the server)
grant all privileges on database "Student" to root
\c Student -> to connect to the "Student" database
\d -> to see the relations



