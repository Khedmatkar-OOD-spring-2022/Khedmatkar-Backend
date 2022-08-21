run:
	docker-compose up --no-deps --build
build:
	docker build --tag khedmatkar:latest .
stop:
	docker-compose stop
clean:
	docker-compose down -v --rmi local --remove-orphans