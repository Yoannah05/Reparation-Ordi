-- Insérer des ordinateurs
INSERT INTO Ordinateurs (numero_serie)
VALUES
    ('SN12345ABC'),  -- Ordinateur 1
    ('SN12346DEF'),  -- Ordinateur 2
    ('SN12347GHI'),  -- Ordinateur 3
    ('SN12348JKL');  -- Ordinateur 4


INSERT INTO Problemes (val, description_probleme)
VALUES 
    ('Écran', 'Problème lié à lécran, par exemple écran cassé, noir, ou pixel défectueux.'),
    ('Batterie', 'Problème lié à la batterie, par exemple la batterie se décharge trop rapidement.'),
    ('Clavier', 'Problème avec le clavier, par exemple touches non fonctionnelles ou non réactives.'),
    ('Memoire', '...'),
    ('Processeur', 'Problème lié au processeur, comme un ralentissement ou une surchauffe.');
-- Insérer des réparations
INSERT INTO Reparations (id_ordinateur, date_debut, date_fin, prix_reparation, statut)
VALUES
    (1, '2024-01-15', '2024-01-20', 150.00, FALSE),  -- Réparation pour l'ordinateur 1
    (2, '2024-01-10', '2024-01-15', 120.00, TRUE),   -- Réparation pour l'ordinateur 2
    (3, '2024-01-18', '2024-01-22', 100.00, FALSE),  -- Réparation pour l'ordinateur 3
    (4, '2024-01-12', '2024-01-18', 180.00, TRUE);   -- Réparation pour l'ordinateur 4

-- Insérer les liaisons entre les réparations, les ordinateurs et les problèmes
INSERT INTO Reparations_ordi (id_ordi, id_probleme, id_reparation)
VALUES
    (1, 1, 1),  -- Ordinateur 1, problème "Écran", réparation 1
    (2, 1, 2),  -- Ordinateur 2, problème "Écran", réparation 2
    (3, 1, 3),  -- Ordinateur 3, problème "Écran", réparation 3
    (4, 2, 4);  -- Ordinateur 4, problème "Batterie", réparation 4
