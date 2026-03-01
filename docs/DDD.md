# 💬 Flyer | DDD

Este documento descreve os domínios e contextos que compõem o ecossistema do **Flyer**, estabelecendo as regras de negócio e os limites de cada módulo.

---

## 1. Contexto de Identidade e Acesso (IAM)
Responsável pela segurança, credenciais e integridade do acesso à plataforma.

### **Conta de Autenticação (Aggregate Root)**
* **Definição:** Representa a identidade única do usuário no sistema (E-mail/Senha, OAuth).
* **Regra de Ouro:** A existência de uma **Conta** é o pré-requisito obrigatório para a criação de um **Perfil**.
* **Invariante:** Não pode existir um Perfil sem uma Conta ativa e verificada.

---

## 2. Contexto Social e Perfil
Gerencia a persona do usuário e suas interações dentro da rede.

### **Perfil (Aggregate Root)**
* **Definição:** A identidade pública e social do usuário. Contém nome de usuário, informações do usuário, biografia, avatar.
* **Responsabilidades:** Gerenciar seguidores, seguidos e preferências de exibição.

### **Relacionamento (Use Case)**
* **Fluxo:** Um `Perfil (Remetente)` pode seguir um `Perfil (Destinatário)`.
* **Reciprocidade:** O vínculo de "Amizade" é estabelecido apenas quando ambos os perfis se seguem mutuamente.

---

## 3. Contexto de Conteúdo (Flyer/Cartão)
O core business da aplicação. Gerencia a criação e o ciclo de vida dos conteúdos voláteis.

### **Cartão (Aggregate Root)**
* **Definição:** Unidade fundamental de conteúdo (Texto, GIF, Vídeo, Foto).
* **Ciclo de Vida (Duração por Relevância):** A sobrevivência de um Cartão é determinada pelo engajamento da comunidade (curtidas):

| Volume de Curtidas | Tempo de Vida |
| :--- | :--- |
| < 200 | 1 Semana |
| 200 - 500 | 2 Semanas |
| 501 - 2.000 | 1 Mês |
| 2.001 - 10.000 | 6 Meses (1 Semestre) |
| > 10.000 | 1 Ano |

---

## 4. Contexto de Comunicação (Messaging)
Gerencia a troca de mensagens diretas e a interação privada entre perfis.

### **Canal de Mensagens / Channel (Aggregate Root)**
* **Limitação de Spam:** Se o `Remetente` não possui vínculo de amizade com o `Destinatário`:
    * Permitido o envio de no máximo **3 mensagens**.
    * Restrito estritamente a **formato de texto** (bloqueio de mídia).
* **Consentimento:** O destinatário deve "Aceitar" a conversa para desbloquear o envio ilimitado e o uso de mídias.

---

## 5. Contexto de Governança e Privacidade
Módulo transversal que dita as permissões para os outros contextos.

### **Privacidade (Policy/Specification)**
* **Escopo:** Define quem pode visualizar Cartões, enviar solicitações de Relacionamento ou iniciar Canais de Mensagem.
* **Flag "Mensagens de Estranhos":** Quando desativada, bloqueia a criação de `Channels` por perfis sem vínculo de amizade prévio.

---

## 6. Contexto de Grupos (Em definição)
Espaços colaborativos para múltiplos perfis com regras de moderação e persistência de cartões específicas para o coletivo.