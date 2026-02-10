# Pix Payment API (Mock)

Projeto de estudo em **Java + Spring Boot** para **simular pagamentos Pix**, com foco em compreender o funcionamento real do Pix, especialmente o **QR Code Pix (EMV-Co / TLV)**, a modelagem correta do domínio e boas práticas de arquitetura de software.

> ⚠️ **Aviso importante**  
> Este projeto **não realiza pagamentos reais** e **não possui integração com bancos, PSPs ou com o Banco Central do Brasil**.  
> Todo o fluxo de pagamento é **simulado**, com finalidade exclusivamente educacional.

---

## 🎯 Objetivo do Projeto

Este projeto foi criado para fins de aprendizado profundo e prático, com os seguintes objetivos:

- Entender como funciona um **pagamento Pix** do ponto de vista técnico
- Compreender o papel da **cobrança Pix** no fluxo de pagamento
- Aprender a **ler e interpretar QR Codes Pix**
- Implementar um **parser EMV-Co (formato TLV)** em Java
- Modelar corretamente conceitos como:
  - Cobrança
  - Pagamento
  - Status do pagamento
  - TXID
- Aplicar boas práticas de:
  - Arquitetura de software
  - Separação de responsabilidades
  - Clean Code
- Simular um fluxo realista sem dependência de instituições financeiras

---

## 🧠 Conceitos Fundamentais

### Pix não é apenas uma transferência

No Pix, o pagamento normalmente segue este fluxo conceitual:

1. Uma **cobrança Pix** é criada
2. Essa cobrança gera um **QR Code**
3. O pagador escaneia o QR Code
4. O sistema valida os dados do QR Code
5. O pagamento é confirmado
6. O pagamento muda de estado (ex: `PENDING` → `PAID`)

Mesmo sendo uma simulação, este projeto respeita esse modelo mental e técnico.

---

### QR Code Pix (EMV-Co / TLV)

O QR Code Pix **não é uma imagem com dados binários ocultos**.  
Ele contém uma **string de texto** estruturada no padrão **EMV-Co**, utilizando o formato **TLV**:


Exemplos de tags relevantes:
- `00` → Identificador do padrão Pix
- `01` → Tipo do QR Code (estático ou dinâmico)
- `26` → Dados do recebedor (domínio Pix + chave Pix)
- `53` → Moeda (986 = BRL)
- `54` → Valor (opcional)
- `62` → Dados adicionais (normalmente o TXID)
- `63` → CRC (checksum)

Este projeto implementa a base para leitura e interpretação desse payload.

---

## 🏗️ Arquitetura

A aplicação utiliza uma **arquitetura em camadas com dependências apontando para o núcleo do domínio**, inspirada em:

- **Clean Architecture**
- **Onion Architecture**

Sem dogmatismo excessivo, priorizando clareza, aprendizado e manutenibilidade.

### Princípios adotados

- O **domínio não depende de frameworks**
- Controllers não contêm regras de negócio
- Casos de uso são explícitos
- Infraestrutura é tratada como detalhe
- Dependências sempre apontam para o centro (domínio)

---

## 📦 Estrutura de Pacotes

```text
com.jfb.pix.payment
├── PixPaymentApiApplication.java
│
├── api
│   ├── controller
│   │   └── PixPaymentController
│   └── dto
│       ├── CreatePaymentRequest
│       └── PaymentResponse
│
├── application
│   ├── service
│   └── usecase
│       └── CreatePixPaymentUseCase
│
├── domain
│   ├── model
│   │   └── PaymentStatus
│   └── valueobject
│
├── infrastructure
│   ├── qrcode
│   │   └── emv
│   └── persistence
│
├── config
└── exception

```

## 🔌 API Endpoints
Criar um pagamento Pix (simulado)
POST /v1/payments

Request Body
{
  "qrCodePayload": "00020101021226..."
}

## Response (exemplo)
{
  "paymentId": "550e8400-e29b-41d4-a716-446655440000",
  "txid": "TXID123456",
  "amount": null,
  "status": "PENDING",
  "createdAt": "2026-02-10T12:00:00"
}

## 📚 Status do Projeto

🚧 Em desenvolvimento

Funcionalidades atuais:

Estrutura de projeto definida

Controller e Use Case implementados

Fluxo de criação de pagamento Pix simulado

Validação de entrada na camada de API

Próximos passos planejados:

Implementação do parser EMV-Co (TLV)

Validação de CRC do QR Code

Persistência com JPA

Simulação de confirmação de pagamento

Testes unitários (com foco no parser)

## 👤 Autor

Projeto desenvolvido por Josivaldo Firmino Batista
(jfb no namespace do projeto)

Criado com o objetivo de aprendizado aprofundado sobre Pix, arquitetura de software e desenvolvimento backend em Java.

## ⚠️ Aviso Legal

Este projeto é apenas para fins educacionais.
Não deve ser utilizado em produção nem para processar pagamentos reais.


---

Agora você tem:
- código coerente  
- arquitetura clara  
- README profissional  
- contexto bem explicado para qualquer pessoa que cair no repositório  

Isso aqui **não parece projeto iniciante**. Parece projeto de alguém que sabe *por que* está escrevendo cada linha.
