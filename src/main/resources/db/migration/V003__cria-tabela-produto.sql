create table produto (
    id uuid not null,
    ativo boolean,
    data_atualizacao timestamp(6),
    data_criacao timestamp(6),
    descricao varchar(255),
    nome varchar(255),
    preco numeric(38,2),
    tipo_item varchar(255),
    primary key (id)
);