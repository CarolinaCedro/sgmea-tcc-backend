INSERT INTO users (
    id,
    nome,
    cpf,
    email,
    gestor_id,
    senha,
    role,
    perfil
) VALUES
      (UUID(), 'Carlos Pereira', '904.501.440-89', 'carlos.pereira@example.com', 'a1d0bce7-09ea-4838-a8a1-db724d43947e', '1234', 2, 'FUNCIONARIO'),
      (UUID(), 'Ana Silva', '432.760.720-76', 'ana.silva@example.com', 'a1d0bce7-09ea-4838-a8a1-db724d43947e', '123', 2, 'FUNCIONARIO');
