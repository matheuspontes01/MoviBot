CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE cliente (
    id UUID PRIMARY KEY,
    telefone VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    origem VARCHAR(50) NOT NULL,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    ultima_interacao_em TIMESTAMPTZ
);

CREATE TABLE conversa (
    id UUID PRIMARY KEY,
    cliente_id UUID NOT NULL REFERENCES cliente (id),
    estado VARCHAR(50) NOT NULL,
    contexto JSONB NOT NULL DEFAULT '{}'::jsonb,
    status VARCHAR(50) NOT NULL,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_conversa_cliente_id ON conversa (cliente_id);
CREATE INDEX idx_conversa_status ON conversa (status);

CREATE TABLE mensagem (
    id UUID PRIMARY KEY,
    conversa_id UUID NOT NULL REFERENCES conversa (id),
    remetente VARCHAR(50) NOT NULL,
    origem_resposta VARCHAR(50),
    conteudo TEXT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    whatsapp_message_id VARCHAR(128) UNIQUE,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_mensagem_conversa_id_criado_em ON mensagem (conversa_id, criado_em);

CREATE TABLE categoria (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE produto (
    id UUID PRIMARY KEY,
    categoria_id UUID NOT NULL REFERENCES categoria (id),
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    preco NUMERIC(19,2) NOT NULL,
    url_imagem TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE INDEX idx_produto_categoria_id_ativo ON produto (categoria_id, ativo);

CREATE TABLE lead (
    id UUID PRIMARY KEY,
    cliente_id UUID NOT NULL REFERENCES cliente (id),
    conversa_id UUID NOT NULL REFERENCES conversa (id),
    status VARCHAR(50) NOT NULL,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE lead_item (
    id UUID PRIMARY KEY,
    lead_id UUID NOT NULL REFERENCES lead (id),
    produto_id UUID NOT NULL REFERENCES produto (id),
    quantidade INTEGER NOT NULL,
    preco_no_momento NUMERIC(19,2) NOT NULL
);

CREATE TABLE faq_item (
    id UUID PRIMARY KEY,
    pergunta TEXT NOT NULL,
    resposta TEXT NOT NULL,
    categoria VARCHAR(255) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE gatilho_venda (
    id UUID PRIMARY KEY,
    padrao TEXT NOT NULL,
    tipo_gatilho VARCHAR(50) NOT NULL,
    peso_score INTEGER NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE alerta_venda (
    id UUID PRIMARY KEY,
    conversa_id UUID NOT NULL REFERENCES conversa (id),
    gatilho_id UUID NOT NULL REFERENCES gatilho_venda (id),
    score_acumulado INTEGER NOT NULL,
    prioridade VARCHAR(50) NOT NULL,
    notificado_em TIMESTAMPTZ,
    status_atendimento VARCHAR(50) NOT NULL
);
