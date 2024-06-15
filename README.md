There 3 microservice application names are Accounts, Cards & Loan.

Docker Containerization
----------------------
docker build . -t satish2121/accounts:banking

Push to docker registry
=======================
docker image push docker.io/satish2121/accounts:banking

Config-Server
-------------
We can the help of any secret key we can keep encrypted data in git config-repo
encrypt:
  key: "SJDFHSIDF4NSBD3432KAS"
![image](https://github.com/Satish522/BankingApplication/assets/9487171/46c48d00-2a48-48c7-825c-b38d222a0cf8)


[RabbitMQ (Docker Image)](https://www.rabbitmq.com/docs/download)
---------------------------------------
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.13-management

RabbitMQ is used for spring cloud bus which will refresh configuration from server-config and load to mircoservice

Refresh Config
---------------
Each instance of each microservice has to call to load latest config
![image](https://github.com/Satish522/BankingApplication/assets/9487171/f13061d4-0e5b-4f0e-9ad5-727d4f2122a3)

Busrefresh Config
-------------------
This can be enable with help of actuator and spring-cloud-bus (amqp module). Here from any microservice can all this busrefresh and latest config load to all application
![image](https://github.com/Satish522/BankingApplication/assets/9487171/4fa064cd-162c-4967-9259-8e37bb4c2a24)

[Webhook](https://hookdeck.com/)
Generate webhook link for local application
UI has changed Navigate to **Developers -> Hookdeck Console** 
![image](https://github.com/Satish522/BankingApplication/assets/9487171/3a329489-48dc-434c-99fa-6cb537ab6f9c)

![image](https://github.com/Satish522/BankingApplication/assets/9487171/1b5b6856-18bb-49e3-beee-685f513b62df)

