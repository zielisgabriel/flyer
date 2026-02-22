# 🚀 Início rápido

## Requisitos
* **Docker** v29.2.1+
* **Docker Compose** v5.0.2+
* **Bun** v1.3.8+

## Passo a passo (Desenvolvimento)

1. Inicialize o Docker Compose
```bash
$ docker compose up -d
```

2. Execute o .sh para criação de clients no Ory Hydra
```bash
$ sh ./auth/hydra/create-client.sh

# Deve retornar
Client ID: <client_id>
Client Secret: <client_secret>
```

3. Execute o site Next.js
```bash
$ cd ./web/flyer_application
$ bun run dev
```