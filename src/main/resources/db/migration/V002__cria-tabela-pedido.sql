create table pedido (
    id uuid not null,
    aberto boolean,
    data_cancelamento timestamp(6),
    data_confirmacao timestamp(6),
    data_criacao timestamp(6),
    data_entrega timestamp(6),
    desconto numeric(38,2),
    endereco_bairro varchar(255),
    endereco_cep varchar(255),
    endereco_complemento varchar(255),
    endereco_logradouro varchar(255),
    endereco_numero varchar(255),
    status varchar(255),
    valor_total numeric(38,2),
    valor_total_com_desconto numeric(38,2),
    primary key (id)
);