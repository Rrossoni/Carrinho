/**
* Projeto 2: Controle de estoque
* Versão 1.0
* @author Leonardo Lima & Ricardo Rossoni
*/ 
show databases;
create database dbcarrinho;
use dbcarrinho;

-- Criar tabela

create table carrinho (
	codigo int primary key auto_increment,
    barcode varchar(5) unique,
    produto varchar (100) not null,
    quantidade int not null,
    valor decimal(10,2)
);

describe carrinho;

insert into carrinho
(barcode, produto, quantidade, valor)
values
('00001', 'Celular A20', '1', '1200.00');

insert into carrinho
(barcode, produto, quantidade, valor)
values
('00002', 'TV samsung 50 Polegadas', '1', '4000.00');

insert into carrinho
(barcode, produto, quantidade, valor)
values
('00003', 'Playstation 5', '1', '8000.00');

insert into carrinho
(barcode, produto, quantidade, valor)
values
('00004', 'Computador Dell', '1', '500.00');

insert into carrinho
(barcode, produto, quantidade, valor)
values
('00005', 'Geladeira duas portas', '1', '2400.00');

insert into carrinho
(barcode, produto, quantidade, valor)
values
('00006', 'Fogão inox 5 bocas', '1', '1800.00');

update carrinho set  produto='Celular Xiomi Note 10' where barcode='00001';

delete from  carrinho where barcode='00006';

select * from carrinho order by produto;

select sum(valor * quantidade) as Total from carrinho;

select * from carrinho;



