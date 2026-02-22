# 🛠️ Flyer | Technical Specification

Este documento detalha a stack tecnológica, a infraestrutura e as escolhas arquiteturais que sustentam o ecossistema do **Flyer**.

---

## 💻 Stack Tecnológica

### **Back-end (Core API)**
* **Java 25** & **Spring Boot 4+**: Base da aplicação, utilizando o ecossistema Spring para alta produtividade e segurança.
* **Spring Security & OAuth2**: Proteção de endpoints e integração com provedores de identidade.
* **Spring Boot - WebSocket**: Comunicação bidirecional em tempo real para o Contexto de Mensagens.
* **Spring Validation (Hibernate Validator)**: Garantia da integridade dos dados via Bean Validation.

### **Front-end (Client)**
* **Next.js 16+ (App Router)**: Framework React para performance otimizada, Server Components e SEO.
* **TailwindCSS 4+**: Estilização baseada em utilitários para interface responsiva e rápida.
* **ShadcnUI**: Base de componentes de UI acessíveis e altamente customizáveis.
* **Zod**: Esquemas de validação de dados tipados e integrados ao ecossistema Next.js.

### **Authentication Server**
* **Ory Kratos**: Servidor de gerenciamento de identidades.
* **Ory Hydra**: Servidor de OAuth2 e OpenID Connect.
* **Ory Oathkeeper**: Proxy de acesso como guard que fica entre a application/web e resouce server.
* **Ory Keto**: Servidor de gerenciamento de autorização de usuários.

## **Databases**
* **PostgreSQL**
* **Cassandra**
* **Neo4j**

---

## 🗄️ Estratégia de Persistência (Polyglot Persistence)
O Flyer utiliza diferentes modelos de dados para atender aos requisitos específicos de cada contexto:

* **PostgreSQL**: Fonte da verdade para dados transacionais (Contas, Perfis, Channels e Configurações de Privacidade).
* **Neo4J**: Motor de grafos dedicado a gerenciar os **Relacionamentos** (Seguidores/Seguindo) e **Comentários** em flyers, permitindo consultas complexas de rede com alta performance.
* **Apache Cassandra**: Banco colunar otimizado para a escrita massiva e leitura de **Mensagens (Channels)**.

---

## 📨 Mensageria e Eventos
* **RabbitMQ 4+**: Broker de mensagens responsável pela comunicação assíncrona entre contexto.

---

## 🐳 Infraestrutura e Deployment
* **Docker & Docker Compose**: Conteinerização de todos os serviços para garantir paridade entre ambientes de desenvolvimento e produção.

---

## 🏗️ Padrões de Arquitetura
O projeto segue os princípios da **Clean Architecture** combinados com **DDD**, garantindo que a lógica de negócio seja independente de frameworks e bancos de dados.

## Entidades

* **User**:
  * Email:
    - Mínimo 3 caracteres.
  * Username: 
  ```^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$```
    - Contêm apenas letras (maiúsculas/minúsculas) e números.
    - Permitem espaço, underscore (_) ou hífen (-) como separadores.
    Não começam nem terminam com espaço, underscore ou hífen.
    - Não permitem separadores consecutivos.
  * Password:
    - 