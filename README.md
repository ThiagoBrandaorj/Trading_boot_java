# API de Usuários - TradingBoot

Endpoints para gerenciamento de usuários, configurações e tickers monitorados.

---

## **Rotas Disponíveis**

### **1. Cadastrar Usuário**
**`POST /users`**  
Cria um novo usuário.

**Request Body (JSON):**
```json
{
    "login": "string",
    "password": "string",
    "binanceApiKey": "string",
    "binanceSecretKey": "string",
    "saldoInicio": 0.0
}
```
### **2. Obter Usuário**
**`GET /users/{user_id}`**
Retorna um usuário existente.

### **3. Atualizar Usuário**
**`PUT /users/{user_id}`**
Atualiza um usuário existente.

**Request Body (JSON):**
```json
{
    "login": "novo_login",
    "password": "nova_senha",
    "binanceApiKey": "nova_api_key",
    "binanceSecretKey": "nova_secret_key",
    "saldoInicio": 1000.0
}
```
### **4. Obter Usuários**
**`GET /users`**
Retorna todos os usuários existentes.

### **5. Cadastrar Configuração de Usuário**
**`POST /users/{user_id}/configurations`**
Adiciona configurações de trade para um usuário.

**Request Body (JSON):**
```json
{
    "lossPercent": 5.0,
    "profitPercent": 10.0,
    "quantityPerOrder": 100.0
}
```

### **6. Cadastrar TrackingTickers de Usuáio**
**`POST /users/{user_id}/tracked-tickers`**
Adiciona tracking tickers(moedas de rastreio) para um usuário.

**Request Body (JSON):**
```json
{
    "symbol": "BTCUSDT"
}
```