<img src = "docs/imgs/bookwhere_logo.png" alt = "Logo BookWhere" width = "350">

---

# Backend

Backend desenvolvido em Java Spring Boot para o projeto BookWhere, responsável por fornecer APIs para gerenciamento de bibliotecas, livros, autores e usuários.<br>
O frontend estará disponível em breve.<br>

---

### Índice

- [Descrição](#descrição)
- [Funcionalidades](#funcionalidades)
- [Decisões de Projeto](#decisões-de-projeto)
- [Tecnologias e Dependências](#tecnologias-e-dependências)
- [Segurança](#segurança)
- [Arquitetura](#arquitetura)
- [Estrutura de pastas](#estrutura-de-pastas)
- [Roadmap](#roadmap)
- [Status do Projeto](#status-do-projeto)

---

### Descrição

O BookWhere é um sistema desenvolvido para centralizar informações sobre livros e bibliotecas, permitindo que usuários encontrem obras disponíveis em diferentes locais.

A aplicação separa claramente o conceito de **obra**, **edição** e **exemplar físico**, garantindo maior consistência de dados e escalabilidade. Bibliotecas podem cadastrar seus acervos, enquanto usuários podem buscar livros e visualizar onde estão disponíveis.

---

### Funcionalidades

#### Tipos de Usuário

1. Usuário
- Cadastro e autenticação
- Busca de livros
- Visualização de bibliotecas
- Consulta de disponibilidade de exemplares

2. Biblioteca
- Cadastro da biblioteca
- Gerenciamento de acervo
- Cadastro de exemplares de livros
- Definição de horários de funcionamento

---

### Decisões de Projeto

- Arquitetura em camadas (Controller → Service → Repository)
- Uso de DTOs para isolamento da camada de API
- Modelagem rica de domínio separando:
  - Work (obra)
  - BookEdition (edição)
  - BookCopy (exemplar físico)
- Relacionamentos N:N com entidades intermediárias (WorkAuthor, WorkGenre)
- Uso de JWT para autenticação stateless
- Tratamento global de exceções

---

### Tecnologias e Dependências

![Java](https://img.shields.io/badge/java-21-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/postgresql-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

**Base**
- Java 21
- Spring Boot 4.0.3
- Maven

**Módulos Spring utilizados**
- spring-boot-starter-webmvc
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-boot-starter-validation

**Segurança**
- Spring Security
- JWT (jjwt 0.11.5)

**Banco de dados**
- PostgreSQL
- H2 (testes)

**Auxiliares**
- Lombok
- DevTools
- Docker Compose

---

### Segurança

A API implementa autenticação baseada em **JWT (JSON Web Token)**.

- Login gera um token JWT
- Rotas protegidas exigem autenticação
- Controle de acesso baseado em roles
- Senhas criptografadas com BCrypt

---

### Arquitetura

O projeto segue uma arquitetura em **camadas**, promovendo separação de responsabilidades:

- **Controller** → Entrada das requisições HTTP  
- **Service** → Regras de negócio  
- **Repository** → Acesso ao banco  
- **Model** → Entidades JPA  
- **DTO / Mapper** → Transferência de dados  
- **Security** → Configurações de autenticação e autorização  
- **Exception** → Tratamento global de erros  

Essa organização facilita manutenção, testes e escalabilidade.

---

### Estrutura de pastas

```
bookwhere-api/
    ├── src/
    │   ├── main/
    │   │   ├── java/com/viniciusstorch/bookwhere_api/
    │   │   │   ├──account/
    │   │   │   ├──book/
    │   │   │   ├──library/
    │   │   │   ├──user/
    │   │   │   ├──security/
    │   │   │   ├──exception/
    │   │   └── resources/
    │   └── test/
```
---

### Roadmap

#### 🔐 Autenticação e Segurança
- [x] Implementação de JWT
- [x] Controle de acesso por roles
- [ ] Refresh Token
- [ ] Recuperação de senha

#### 📚 Domínio de Livros
- [x] Modelagem de Work, Edition e Copy
- [x] Relacionamento com autores e gêneros
- [ ] Cadastro completo de obras
- [ ] Integração com OpenLibrary (importação automática via ISBN)

#### 🏛️ Bibliotecas
- [x] Cadastro de bibliotecas
- [x] Horários de funcionamento
- [ ] Sistema de avaliação de bibliotecas
- [ ] Upload de imagens

#### 🔎 Busca e Descoberta
- [ ] Busca por título, autor e gênero
- [ ] Filtros avançados (idioma, ano, editora)
- [ ] Ordenação por relevância
- [ ] Busca por proximidade de bibliotecas
- [ ] Autocomplete / sugestões de busca

#### 📦 Infraestrutura
- [ ] Docker Compose completo
- [ ] Configuração de ambientes (dev/prod)
- [ ] Variáveis de ambiente seguras
- [ ] CI/CD (GitHub Actions)
- [ ] Deploy em cloud (AWS)

#### 🧠 Dados & Consistência
- [ ] Normalização de autores (evitar duplicatas)
- [ ] Normalização de obras (mesma obra com títulos diferentes)
- [ ] Estratégia de deduplicação
- [ ] Validação de ISBN

#### 🧪 Testes
- [x] Testes unitários básicos
- [ ] Testes de integração
- [ ] Testes de segurança (JWT)
- [ ] Cobertura de testes

#### 🌐 Futuro
- [ ] Integração com frontend
- [ ] Sistema de favoritos
- [ ] Histórico de leituras
- [ ] Recomendações de livros com base em gêneros
- [ ] Avaliações de livros

### Status do Projeto

**Em desenvolvimento**
Foco atual: domínio dos livros