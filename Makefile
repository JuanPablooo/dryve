jar:
	mvn clean install
up: jar
	sudo docker-compose up --build --force-recreate
