create table tb_customer_account (
	id_customer int identity(1, 1) primary key,
	cpf_cnpj varchar(15),
	nm_customer varchar(20),
	is_active bit,
	vl_total float
);

select * from tb_customer_account;
