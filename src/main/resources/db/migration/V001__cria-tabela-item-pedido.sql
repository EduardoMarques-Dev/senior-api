alter table if exists item_pedido drop constraint if exists FK60ym08cfoysa17wrn1swyiuda;
alter table if exists item_pedido drop constraint if exists FKtk55mn6d6bvl5h0no5uagi3sf;
drop table if exists item_pedido cascade;
drop table if exists pedido cascade;
drop table if exists produto cascade;

create table item_pedido (
    id uuid not null,
    observacao varchar(255),
    preco_total numeric(38,2),
    preco_total_com_desconto numeric(38,2),
    preco_unitario numeric(38,2),
    quantidade integer,
    pedido_id uuid not null,
    produto_id uuid not null,
    primary key (id)
);
