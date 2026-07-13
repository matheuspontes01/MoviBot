INSERT INTO categoria (id, nome) VALUES
    ('11111111-1111-1111-1111-111111111111', 'Colchões'),
    ('22222222-2222-2222-2222-222222222222', 'Camas'),
    ('33333333-3333-3333-3333-333333333333', 'Guarda-roupas'),
    ('44444444-4444-4444-4444-444444444444', 'Cabeceiras');

INSERT INTO produto (id, categoria_id, nome, descricao, preco, url_imagem, ativo) VALUES
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 'Colchão OrtoFlex Solteiro', 'Colchão de espuma para uso diário.', 899.90, 'https://example.com/produtos/colchao-ortoflex-solteiro.jpg', TRUE),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab', '11111111-1111-1111-1111-111111111111', 'Colchão Premium Queen', 'Colchão de molas ensacadas com conforto intermediário.', 2199.00, 'https://example.com/produtos/colchao-premium-queen.jpg', TRUE),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac', '11111111-1111-1111-1111-111111111111', 'Colchão Ortopédico Casal', 'Modelo firme para maior sustentação.', 1499.50, 'https://example.com/produtos/colchao-ortopedico-casal.jpg', TRUE),
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '22222222-2222-2222-2222-222222222222', 'Cama Box Solteiro', 'Base box compacta para quarto individual.', 799.00, 'https://example.com/produtos/cama-box-solteiro.jpg', TRUE),
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbc', '22222222-2222-2222-2222-222222222222', 'Cama Box Queen', 'Base box reforçada para colchões queen.', 1399.90, 'https://example.com/produtos/cama-box-queen.jpg', TRUE),
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbd', '22222222-2222-2222-2222-222222222222', 'Cama Casal com Gavetas', 'Cama com espaço extra de armazenamento.', 1699.00, 'https://example.com/produtos/cama-casal-gavetas.jpg', TRUE),
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', '33333333-3333-3333-3333-333333333333', 'Guarda-roupa 2 Portas', 'Modelo compacto para espaços reduzidos.', 999.90, 'https://example.com/produtos/guarda-roupa-2-portas.jpg', TRUE),
    ('cccccccc-cccc-cccc-cccc-cccccccccccd', '33333333-3333-3333-3333-333333333333', 'Guarda-roupa 6 Portas', 'Guarda-roupa amplo com divisórias internas.', 2399.00, 'https://example.com/produtos/guarda-roupa-6-portas.jpg', TRUE),
    ('cccccccc-cccc-cccc-cccc-ccccccccccce', '33333333-3333-3333-3333-333333333333', 'Guarda-roupa Casal Espelhado', 'Versão com portas espelhadas e acabamento fosco.', 2799.00, 'https://example.com/produtos/guarda-roupa-espelhado.jpg', TRUE),
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', '44444444-4444-4444-4444-444444444444', 'Cabeceira Estofada Linho', 'Cabeceira com revestimento em tecido.', 599.90, 'https://example.com/produtos/cabeceira-linho.jpg', TRUE),
    ('dddddddd-dddd-dddd-dddd-ddddddddddde', '44444444-4444-4444-4444-444444444444', 'Cabeceira Ripada MDF', 'Painel decorativo com acabamento amadeirado.', 449.00, 'https://example.com/produtos/cabeceira-ripada.jpg', TRUE),
    ('dddddddd-dddd-dddd-dddd-dddddddddddf', '44444444-4444-4444-4444-444444444444', 'Cabeceira Premium Queen', 'Cabeceira acolchoada para camas queen.', 799.00, 'https://example.com/produtos/cabeceira-premium-queen.jpg', TRUE);

INSERT INTO faq_item (id, pergunta, resposta, categoria, ativo) VALUES
    ('55555555-5555-5555-5555-555555555551', 'Vocês fazem entrega?', 'Sim. Atendemos entrega conforme a região e o prazo é informado pelo atendente.', 'Entrega', TRUE),
    ('55555555-5555-5555-5555-555555555552', 'Quais formas de pagamento vocês aceitam?', 'Aceitamos cartão, pix e outras opções confirmadas pelo atendimento.', 'Pagamento', TRUE),
    ('55555555-5555-5555-5555-555555555553', 'Vocês montam os móveis?', 'A montagem pode estar disponível dependendo do produto e da região.', 'Serviços', TRUE),
    ('55555555-5555-5555-5555-555555555554', 'Como escolho o colchão ideal?', 'O ideal depende do biotipo, postura e preferência de firmeza.', 'Colchões', TRUE),
    ('55555555-5555-5555-5555-555555555555', 'Posso retirar na loja?', 'A retirada deve ser confirmada com um atendente humano.', 'Retirada', TRUE),
    ('55555555-5555-5555-5555-555555555556', 'Vocês atendem quais bairros?', 'A cobertura de atendimento pode variar por unidade e região.', 'Localização', TRUE),
    ('55555555-5555-5555-5555-555555555557', 'Qual a garantia dos produtos?', 'A garantia segue a política do fabricante e será confirmada no atendimento.', 'Garantia', TRUE);
