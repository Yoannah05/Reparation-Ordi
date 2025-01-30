-- Insertion des clients dans la table Clients
INSERT INTO Clients (nom, prenom, adresse, telephone, email)
VALUES
('Dupont', 'Jean', '123 Rue de Paris, 75001 Paris', '0102030405', 'jean.dupont@example.com'),
('Martin', 'Claire', '56 Avenue des Champs-Elysées, 75008 Paris', '0105060708', 'claire.martin@example.com'),
('Lemoine', 'Pierre', '10 Boulevard Saint-Germain, 75005 Paris', '0112233445', 'pierre.lemoine@example.com'),
('Bernard', 'Sophie', '72 Rue de la Paix, 75002 Paris', '0123456789', 'sophie.bernard@example.com'),
('Lemoine', 'Marc', '15 Rue Montorgueil, 75001 Paris', '0145789223', 'marc.lemoine@example.com'),
('Dufresne', 'Amélie', '48 Boulevard Voltaire, 75011 Paris', '0156789101', 'amélie.dufresne@example.com'),
('Rousseau', 'Paul', '28 Rue de la Republique, 69002 Lyon', '0162345678', 'paul.rousseau@example.com');
-- Insertion des marques
INSERT INTO Marques (nom_marque) VALUES
('Dell'),
('HP'),
('Apple'),
('Asus'),
('Lenovo'),
('MSI'),
('Acer');
-- Insertion des categories d'ordinateurs
INSERT INTO categorie_ordi (val) VALUES
('Gamer'),
('Bureau'),
('Portable'),
('Tablette');

-- Insertion des modeles
INSERT INTO Modeles (id_marque, id_categorie_ordi, nom_modele) VALUES
(1, 1, 'Alienware M15'),        -- Dell, Gamer
(2, 1, 'OMEN 15'),              -- HP, Gamer
(3, 2, 'MacBook Air'),          -- Apple, Bureau
(4, 2, 'Zenbook 14'),           -- Asus, Bureau
(5, 3, 'ThinkPad X1 Carbon'),   -- Lenovo, Portable
(6, 3, 'GT76 Titan'),           -- MSI, Portable
(7, 1, 'Predator Helios 300');  -- Acer, Gamer

-- Insertion des ordinateurs
INSERT INTO Ordinateurs (id_client, id_modele, numero_serie) VALUES
(1, 1, 'D12345AW15'),           -- Client 1, Alienware M15
(2, 2, 'H98765OM15'),           -- Client 2, OMEN 15
(3, 3, 'A54321MBAIR'),          -- Client 3, MacBook Air
(4, 4, 'Z12345ZEN14'),          -- Client 4, Zenbook 14
(5, 5, 'T98765X1CARBON'),       -- Client 5, ThinkPad X1 Carbon
(6, 6, 'M12345GT76TITAN'),      -- Client 6, GT76 Titan
(7, 7, 'A67890PH300');          -- Client 7, Predator Helios 300


-- Insertion des types de reparations
INSERT INTO Type_reparations (val) VALUES
('Upgrade'),
('Remplacement de batterie'),
('Réparation'),
('Installation');


INSERT INTO Problemes (val, description_probleme)
VALUES 
    ('Ecran', 'Probleme lie a lecran, par exemple ecran casse, noir, ou pixel defectueux.'),
    ('Batterie', 'Probleme lie a la batterie, par exemple la batterie se decharge trop rapidement.'),
    ('Clavier', 'Probleme avec le clavier, par exemple touches non fonctionnelles ou non reactives.'),
    ('Memoire', '...'),
    ('Processeur', 'Probleme lie au processeur, comme un ralentissement ou une surchauffe.');
-- Insertion dans la table Reparations
INSERT INTO Reparations (id_ordinateur, date_debut, date_fin, prix_reparation, statut, id_technicien, id_commission)
VALUES
(1, '2025-01-01', '2025-01-05', 150.00, TRUE, 1, 1),
(2, '2025-01-02', '2025-01-06', 200.00, FALSE, 2, 2),
(3, '2025-01-03', '2025-01-07', 120.00, TRUE, 3, 3 ),
(4, '2025-01-04', NULL, 4500.00, FALSE, 4, 4),
(5, '2025-01-05', '2025-01-09', 8750.30, TRUE, 5, 5),
(6, '2025-01-06', NULL, 10300.20, FALSE, 6, 6),
(7, '2025-01-07', '2025-01-11', 6500.90, TRUE, 7, 7),
(1, '2025-01-08', NULL, 25000.00, FALSE, 1, 8),
(2, '2025-01-09', '2025-01-12', 19500.50, TRUE, 2, 9),
(3, '2025-01-10', '2025-01-13', 13200.75, TRUE, 3, 10);

-- Insertion dans la table Retours
INSERT INTO Retours (id_reparation, date_retour) 
VALUES
(1, '2025-01-06'),
(3, '2025-01-08'),
(4, '2025-01-10'),
(8, '2025-01-12'),
(10, '2025-01-13');

-- Insertion dans la table Techniciens
INSERT INTO Techniciens (nom) VALUES 
('Rakotoarison Jean'),
('Randrianasolo Marie'),
('Rasolomampionona Thierry'),
('Andriamampionona Claire'),
('Rakotonirina Patrick'),
('Raveloarimisa Noeline'),
('Razafindrakoto Herve'),
('Andrianarisoa Nadia'),
('Rakotovao Julien'),
('Ramanantsoa Sophie'); 

-- Insertion dans la table Sexe
INSERT INTO Sexe(sexe) VALUES
('Homme'),
('Femme');

-- Insertion dans la table Commissions
INSERT INTO Commissions (date, val) VALUES 
('2025-01-01', 5.00),
('2025-01-02', 5.00),
('2025-01-03', 5.00),
('2025-01-04', 5.00),
('2025-01-05', 5.00),
('2025-01-06', 5.00),
('2025-01-07', 5.00),
('2025-01-08', 5.00),
('2025-01-09', 5.00),
('2025-01-10', 5.00);


-- Insertion dans la table Reparations_ordi
INSERT INTO Reparations_ordi (id_ordi, id_probleme, id_reparation, id_type_rep)
VALUES
(1, 4, 1, 1),  -- Ordinateur 1, Probleme "Ecran", Reparation 1, Type de reparation "Upgrade"
(2, 2, 2, 3),  
(4, 4, 2, 3),  
(5, 1, 2, 3),  -- Ordinateur 2, Probleme "Batterie", Reparation 2, Type de reparation "Changement d'ecran"
(3, 3, 3, 4);  -- Ordinateur 3, Probleme "Clavier", Reparation 3, Type de reparation "Reparation de carte mere"

INSERT INTO Composants (nom_composant) VALUES
('Ecran LED'),
('Batterie Lithium'),
('Disque dur SSD');
