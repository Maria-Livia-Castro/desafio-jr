# desafio-jr - API de usuários

API REST desenvolvida em Spring Boot para gerenciamento de usuários, com autenticação via JWT, documentação integrada com Swagger/OpenAPI, e persistência em banco de dados MySQL.
O projeto está totalmente dockerizado, pronto para rodar com docker-compose

⚙️ Tecnologias utilizadas
- Java 21 (Eclipse Temurin)
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA / Hibernate
- MySQL 8.0
- Swagger/OpenAPI
- Lombok
- Docker & Docker Compose

🔒 Autenticação
- A API utiliza JWT para autenticação.
- Endpoints públicos:
- POST /auth/token → gera token JWT
- POST /usuarios/novo → cria novo usuário
- Endpoints do Swagger (/swagger-ui/**, /v3/api-docs/**)
- Endpoints protegidos exigem Bearer Token no header Authorization

📑 Endpoints principais
🔐 Autenticação
- POST /auth/token → autentica usuário e retorna JWT
👤 Usuários
- POST /usuarios/novo → cria novo usuário (sem autenticação)
- GET /usuarios → lista todos os usuários (JWT obrigatório)
- GET /usuarios/{id} → busca usuário por ID (JWT obrigatório)
- PUT /usuarios/{id} → atualiza usuário (JWT obrigatório)
- DELETE /usuarios/{id} → remove usuário (JWT obrigatório)

🗄️ Configuração do banco de dados
No arquivo application.yml:

spring:
  application:
    name: desafio-jr
  datasource:
    url: jdbc:mysql://localhost:3306/desafio?useSSL=false&allowPublicKeyRetrieval=true
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: none

🐳 Docker & Docker Compose
Dockerfile
- Build multi-stage para gerar uma imagem leve.
- Usa eclipse-temurin:21-jdk-alpine.
- Copia apenas o .jar final para o container.
- Expõe a porta 8080.
docker-compose.yml
- Serviço MySQL:
- Porta 3330:3306
- Banco desafio
- Usuário root / senha root
- Volume persistente mysql_data
- Serviço App:
- Porta 8080:8080
- Dependente do MySQL (healthcheck configurado)
- Variáveis de ambiente para datasource

## Rodando o projeto
docker-compose up -d
A aplicação estará disponível em:
👉 http://localhost:8080/swagger-ui/index.html

📂 Estrutura do projeto
- config/ → Configurações de segurança e Swagger
- controller/ → Controllers da API (AuthController, UsuarioController)
- dto/ → Objetos de transferência (LoginRequest, UsuarioRequest, UsuarioResponse)
- exception/ → Tratamento global de erros e exceções personalizadas
- model/ → Entidade Usuario integrada ao Spring Security
- repository/ → Interface UsuarioRepository para acesso ao banco
- service/ → Lógica de negócio (UsuarioService)
- Security/ → Filtro JWT (AuthJwtFilter)
- Util/ → Classe utilitária JwtUtil

📬 Exemplos de requisições (cURL)
Criar usuário
curl -X POST http://localhost:8080/usuarios/novo \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Silva",
    "email": "maria@email.com",
    "login": "maria123",
    "senha": "1234"
  }'


Autenticar e obter token JWT
curl -X POST http://localhost:8080/auth/token \
  -H "Content-Type: application/json" \
  -d '{
    "login": "maria123",
    "senha": "1234"
  }'


🔑 Resposta esperada:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}


Listar usuários
curl -X GET http://localhost:8080/usuarios \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR..."



🧪 Testando no Swagger
- Suba a aplicação com docker-compose up -d.
- Acesse 👉 http://localhost:8080/swagger-ui/index.html.
- Vá até POST /auth/token, clique em Try it out, insira login/senha e execute.
- Copie o token retornado.
- Clique em Authorize no topo do Swagger e insira:
Bearer eyJhbGciOiJIUzI1NiIsInR...
- Agora você pode testar todos os endpoints protegidos diretamente pelo Swagger.

⚠️ Erros comuns
- 401 Unauthorized → Token ausente ou inválido.
- 404 Not Found → Usuário não encontrado pelo ID.
- 409 Conflict → Email ou login já cadastrados.
- 400 Bad Request → Dados inválidos (ex.: senha menor que 4 caracteres).

✅ Fluxo de uso
- Criar usuário via POST /usuarios/novo.
- Autenticar via POST /auth/token com login/senha → receber JWT.
- Usar JWT no header Authorization: Bearer <token> para acessar endpoints protegidos.

