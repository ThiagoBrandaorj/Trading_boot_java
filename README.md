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

### **3. Obter Usuários**
**`GET /users`**
Retorna todos os usuários existentes.

### **4. Cadastrar Configuração de Usuário**
**`POST /users/{user_id}/configurations`**
Adiciona configurações de trade para um usuário, caso o mesmo não possua nenhuma configuração.

**Request Body (JSON):**
```json
{
    "lossPercent": 5.0,
    "profitPercent": 10.0,
    "quantityPerOrder": 100.0
}
```

### **5. Cadastrar TrackingTickers de Usuáio**
**`POST /users/{user_id}/tracked-tickers`**
Adiciona tracking tickers(moedas de rastreio) para um usuário.

**Request Body (JSON):**
```json
{
    "symbol": "BTCUSDT"
}
```

### **6. Deletar Usuário**
**`DELETE /users/{user_id}`**
Deleta um usuário existente.
### **7. Ordem de Compra e Venda*
**`POST /users/{user_id}/order`**
Adiciona tracking tickers(moedas de rastreio) para um usuário.

**Request Body (JSON):**
```json
{
   
  "symbol": "BTCUSDT",
  "side": "BUY/SELL",
  "quantity": 0.001
}

```
