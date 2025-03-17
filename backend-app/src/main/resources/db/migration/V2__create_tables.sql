USE test_prog;
GO

CREATE TABLE test_prog.dbo.usuario (
                          user_id INT IDENTITY(1,1) PRIMARY KEY,
                          user_nombre VARCHAR(255) NOT NULL,
                          user_apellido VARCHAR(255) NOT NULL,
                          user_genero VARCHAR(50),
                          user_nacionalidad VARCHAR(100),
                          user_telefono VARCHAR(50),
                          user_email VARCHAR(255) UNIQUE NOT NULL,
                          user_contrasena VARCHAR(60) NOT NULL,
                          refresh_token_hash VARCHAR(60),
                          refresh_token_expiry DATETIME2,
                          role VARCHAR(50) NOT NULL
);
GO

CREATE TABLE test_prog.dbo.post (
                      post_id INT IDENTITY(1,1) PRIMARY KEY,
                      post_titulo VARCHAR(255) NOT NULL,
                      post_contenido VARCHAR(MAX),
    post_fecha_publicacion DATETIME2,
    post_fecha_modificacion DATETIME2,
    post_status BIT,
    user_id INT NOT NULL,
    CONSTRAINT FK_Post_Usuario FOREIGN KEY (user_id) REFERENCES Usuarios(user_id)
);
GO