USE test_prog;
GO

-- Inserción de datos de prueba en Usuarios
INSERT INTO usuario (user_nombre, user_apellido, user_genero, user_nacionalidad, user_telefono, user_email, user_contrasena, refresh_token_hash, refresh_token_expiry, role)
VALUES
('Juan', 'Pérez', 'Masculino', 'Mexicana', '55541234', 'juan.perez@example.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO/6G3SxFukRzXZ0m0oNRcl1I.pM23C1G', NULL, NULL, 'USER'),
('Ana', 'Gómez', 'Femenino', 'Española', '13245678', 'ana.gomez@example.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO/6G3SxFukRzXZ0m0oNRcl1I.pM23C1G', NULL, NULL, 'ADMIN');
GO

-- Inserción de datos de prueba en Post
INSERT INTO post (post_titulo, post_contenido, post_fecha_publicacion, post_fecha_modificacion, post_status, user_id)
VALUES
('Primer Post', 'Este es el contenido del primer post.', GETDATE(), GETDATE(), 1, 1),
('Segundo Post', 'Este es el contenido del segundo post.', GETDATE(), GETDATE(), 1, 2);
GO