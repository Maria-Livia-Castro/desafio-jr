alter table usuario 
	add column nome varchar(100) not null,
	add column email varchar(150) not null unique,
	add column login varchar(60) not null unique,
	add column senha varchar(200) not null;