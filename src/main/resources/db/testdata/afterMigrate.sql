TRUNCATE TABLE item_pedido RESTART IDENTITY CASCADE;
TRUNCATE TABLE pedido RESTART IDENTITY CASCADE;
TRUNCATE TABLE produto RESTART IDENTITY CASCADE;

INSERT INTO public.produto(
	 id, nome, descricao, preco, tipo_item, ativo, data_criacao)
	VALUES ('df3a97a5-2604-47dc-b622-ff6fb13845c2','Mochila Bonita', 'Mochila para encantar corações', 72.00, 'PRODUTO', true, current_timestamp);

INSERT INTO public.produto(
	 id, nome, descricao, preco, tipo_item, ativo, data_criacao)
	VALUES ('9006bac8-f869-4477-ae59-2ebfd769d2f7','Televisão 5D', 'Televisão que permite assistir canais de outras dimensões', 80000.00, 'PRODUTO', true, current_timestamp);  
    
INSERT INTO public.produto(
	 id, nome, descricao, preco, tipo_item, ativo, data_criacao)
	VALUES ('4f6eb736-ae34-44c2-966e-c565fd9f9dd4','Máquina Fotográfica Assombrada', 'Capaz de tirar fotos de fantasmas', 5.00, 'PRODUTO', false, current_timestamp);

INSERT INTO public.produto(
    id, nome, descricao, preco, tipo_item, ativo, data_criacao)
VALUES ('88902103-09a6-4adf-a9f6-cd77a821ca10','Formatar Computador', 'Formata computadores e instala programas essenciais', 125.00, 'SERVICO', false, current_timestamp);

INSERT INTO public.produto(
    id, nome, descricao, preco, tipo_item, ativo, data_criacao)
VALUES ('4ff237f7-ceae-4b13-a7dc-e2c11fdae59c','Espiões', 'Estão te observando desde que você chegou', 500.00, 'SERVICO', true, current_timestamp);

INSERT INTO public.pedido(
	id, aberto, data_criacao, desconto, valor_total, valor_total_com_desconto, endereco_logradouro, endereco_numero, endereco_bairro, endereco_complemento, endereco_cep)
	VALUES ('0f8cec05-e40d-41d0-ad5d-44e73733f735', true, current_timestamp, 0.25, 644.00, 608.00, 'Rua Verde', '2354-B', 'Bairro Azul', 'Perto do Bairro Amarelo', '88888-888');

INSERT INTO public.item_pedido(
	id, pedido_id, produto_id, observacao, preco_unitario, quantidade, preco_total, preco_total_com_desconto)
	VALUES ('02db1d2c-9b7c-472d-9dd0-fbdece8a42b7', '0f8cec05-e40d-41d0-ad5d-44e73733f735', 'df3a97a5-2604-47dc-b622-ff6fb13845c2', 'Uma Mochila azul e outra roxa', 72.00, 2, 144, 108);

INSERT INTO public.item_pedido(
id, pedido_id, produto_id, observacao, preco_unitario, quantidade, preco_total, preco_total_com_desconto)
VALUES ('063577ac-ee35-4a93-a2a4-f74a19b59d31', '0f8cec05-e40d-41d0-ad5d-44e73733f735', '4ff237f7-ceae-4b13-a7dc-e2c11fdae59c', 'Preciso que ele espione meu cachorro', 500.00, 1, 500.00, 500.00);