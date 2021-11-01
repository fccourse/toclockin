# toclockin

Projeto realizado para o processo seletivo da [ília digital](https://exemplo.com/), para a vaga de Java Backend Developer.

O projeto consiste em uma API para controle de ponto da jornada diária de trabalho.

### Tecnologias utilizadas
* Java 11
* Spring Boot;
* Eureka Discovery;
* Spring Gateway;
* Spring Security;
* MariaDB / MySQL;
* Junit;
* Feign para comunicação entre serviços;
* Flyway (Database migrations);

### Arquitetura inicial

![Arquitetura](design-api.png)

### Regras
* Apenas 4 horários podem ser registrados por dia.
* Deve haver no mínimo 1 hora de almoço.
* Sábado e domingo não são permitidos como dia de trabalho.

### Features implementadas
* API de batimento de ponto com banco de dados SQL e principais testes unitários;
* Serviço de Discovery para descoberta das APIs e camada de Gateway para receber as chamadas;
* API de Autenticação/Autorização;

### Features em aberto
* Autenticação e autorização do lado da API de batimento de ponto;
* Maior cobertura de testes unitários e testes de integração;
* Validações de ponto duplicado e datas passadas;
* Autorização de endpoints através da tabela **ROLE** que foi criada no banco de dados;
* Circuit Breaker para tolerância a falhas nas comunicações entre serviços;
* Uso de uma ferramenta mais especializada para Autenticação e autorização (Keycloak)
* Arquivos de propriedade separados por ambiente e com variáveis de ambiente;
* Swagger para documentação dos endpoints;

### Execução
verificar as configurações de banco de dados no arquivo **application.yml**.

#### Requisitos
* Java 11;
* Maven;
* MySQL or MariaDB;
* IDE de preferência;
* Docker *
* Docker Compose *

##### Maven

```
./mvnw spring-boot:run
```

Executar inicialmente o projeto **discovery-api**, em seguida os outros.

##### Docker compose
Execute nos diretórios dos projetos o seguinte comando para geração do jar:
```
 mvn clean package
```

Na pasta principal onde se encontra o arquivo **docker-compose.yml**, execute:

```
docker-compose up --build
```


#### Endpoints

**Bater ponto:**
http://localhost:8080/toclockin/hits/

cURL:
```
curl --location --request POST 'http://localhost:8080/toclockin/hits/?email=email@email.com' \
--header 'Content-Type: application/json' \
--data-raw '{
    "date": "2021-11-01 10:44:06",
    "userMessage": "entry message."
}'
```

**Buscar usuário:**
http://localhost:8080/toclockin/users/search

cURL:
```
curl --location --request GET 'http://localhost:8080/toclockin/users/search?email=email@email.com' \
```

**Autenticação:**
http://localhost:8080/oauth/oauth/token
cURL:
```
curl --location --request POST 'http://localhost:8080/oauth/oauth/token' \
--header 'Authorization: Basic dG9jbG9ja2luOnRvY2xvY2tpbi1zZWNyZXQ=' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'username=email@email.com' \
--data-urlencode 'password=test' \
--data-urlencode 'grant_type=password'
```