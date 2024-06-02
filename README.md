There 3 microservice application names are Accounts, Cards & Loan.

Docker Containerization
-----------------------
docker build . -t satish2121/accounts:banking

Push to docker registry
=======================
docker image push docker.io/satish2121/accounts:banking


RabbitMQ (Docker Image)
========================
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.13-management

RabbitMQ is used for spring cloud bus which will refresh configuration from server-config and load to mircoservice

![image](https://github.com/Satish522/BankingApplication/assets/9487171/4fa064cd-162c-4967-9259-8e37bb4c2a24)
