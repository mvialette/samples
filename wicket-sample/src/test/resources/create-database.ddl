create table adresse (
id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
rue char(50),
codepostal integer,
ville  char(20)
);
create table utilisateur (
id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
nom char(10),
prenom char(10),
age integer, 
id_adress integer
);