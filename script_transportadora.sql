use sheikplastic;


CREATE TABLE Transportadora (
    id_transportadora   INT IDENTITY(1,1) PRIMARY KEY,
    nome                VARCHAR(150) NOT NULL,
    cnpj                VARCHAR(14) NOT NULL,
    inscricao_estadual  VARCHAR(30),
    telefone            VARCHAR(20),
    email               VARCHAR(150),
    logradouroEnderecoPessoa          VARCHAR(150),
    numeroEnderecoPessoa              VARCHAR(20),
    complementoEnderecoPessoa         VARCHAR(50),
    bairroEnderecoPessoa              VARCHAR(80),
    idCidade            INT NOT NULL,
    cepEnderecoPessoa                 VARCHAR(9),

    ativo               BIT DEFAULT 1

    CONSTRAINT fk_transportadora_cidade
        FOREIGN KEY (idCidade)
        REFERENCES dbo.Cidade(idCidade)
);


ALTER TABLE dbo.Pessoa
ADD idTransportadora INT NULL;
GO

-- 2. Criar a Foreign Key corretamente
ALTER TABLE dbo.Pessoa
ADD CONSTRAINT fk_pessoa_transportadora
FOREIGN KEY (idTransportadora)
REFERENCES dbo.Transportadora (idTransportadora);
GO

select * from Menu;

INSERT INTO Menu (
    descricaoMenu,
    ordemMenu,
    idMenuSuperior,
    enderecoPagina,
    idRegra,
    visivel
)
VALUES (
    'Transportadoras',
    7,              -- depois de Produtos (6)
    1,              -- Cadastros
    '_cad/transportadorasListar.asp',
    12,             -- ajuste conforme sua regra
    1
);


select * from Pessoa where idPessoa = 4;