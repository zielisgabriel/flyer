#!/bin/bash

until curl -s http://localhost:4445/health/ready > /dev/null 2>&1; do
  echo "Waiting for Hydra..."
  sleep 2
done

hydra create oauth2-client \
  --name "Flyer Web App" \
  --grant-type authorization_code,refresh_token \
  --endpoint http://localhost:4445 \
  --response-type id_token \
  --scope openid,offline_access,profile,username \
  --redirect-uri http://localhost:3000/api/auth/callback \
  --token-endpoint-auth-method client_secret_post \
  --format json > /tmp/client.json

echo "Client ID: $(cat /tmp/client.json | jq -r '.client_id')"
echo "Client Secret: $(cat /tmp/client.json | jq -r '.client_secret')"

hydra create oauth2-client \
  --endpoint http://localhost:4445 \
  --name "Flyer Backend Service" \
  --grant-type client_credentials \
  --scope posts.read,posts.write,users.read,admin \
  --format json > /tmp/backend-client.json

echo "Backend Client ID: $(cat /tmp/backend-client.json | jq -r '.client_id')"
echo "Backend Client Secret: $(cat /tmp/backend-client.json | jq -r '.client_secret')"