
CREATE DATABASE atelier_reparation;

\c atelier_reparation;


CREATE TABLE Clients (
    id_client SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    adresse VARCHAR(255),
    telephone VARCHAR(15),
    email VARCHAR(100)
);

CREATE TABLE Marques (
    id_marque SERIAL PRIMARY KEY,
    nom_marque VARCHAR(100) NOT NULL
);

CREATE TABLE Modeles (
    id_modele SERIAL PRIMARY KEY,
    id_marque INT REFERENCES Marques(id_marque),
    nom_modele VARCHAR(100) NOT NULL
);

CREATE TABLE Problemes (
    id_probleme SERIAL PRIMARY KEY,
    description_probleme TEXT NOT NULL
);

CREATE TABLE Ordinateurs (
    id_ordinateur SERIAL PRIMARY KEY,
    id_client INT REFERENCES Clients(id_client),
    id_modele INT REFERENCES Modeles(id_modele),
    numero_serie VARCHAR(100)
);

CREATE TABLE Ordi_problemes (
    id_ordi_prob SERIAL PRIMARY KEY,
    id_ordinateur INT REFERENCES Ordinateurs(id_ordinateur),
    id_probleme INT REFERENCES Problemes(id_probleme)
);

CREATE TABLE Ordi_reparations (
    id_ordi_rep SERIAL PRIMARY KEY,
    id_ordi_prob INT REFERENCES Ordi_problemes(id_ordi_prob),
    id_reparation INT REFERENCES Reparations(id_reparation)
);

CREATE TABLE Composants (
    id_composant SERIAL PRIMARY KEY,
    pu DECIMAL(10, 2) NOT NULL,
    nom_composant VARCHAR(100) NOT NULL
);

CREATE TABLE Reparations (
    id_reparation SERIAL PRIMARY KEY,
    id_ordinateur INT REFERENCES Ordinateurs(id_ordinateur),
    date_debut DATE,
    date_fin DATE,
    prix_reparation DECIMAL(10, 2) NOT NULL,
    statut BOOLEAN DEFAULT FALSE
);

CREATE TABLE Composants_Utilises (
    id_cu SERIAL PRIMARY KEY,
    id_reparation INT REFERENCES Reparations(id_reparation),
    id_composant INT REFERENCES Composants(id_composant),
    quantite_utilisee INT,
    PRIMARY KEY (id_reparation, id_composant)
);

CREATE TABLE Devis (
    id_devis SERIAL PRIMARY KEY,
    id_ordinateur INT REFERENCES Ordinateurs(id_ordinateur),
    date_du_devis DATE,
    montant_total DECIMAL(10, 2),
    statut_devis VARCHAR(50)
);

CREATE TABLE Paiements (
    id_paiement SERIAL PRIMARY KEY,
    id_reparation INT REFERENCES Reparations(id_reparation),
    montant_paye DECIMAL(10, 2) NOT NULL,
    date_paiement DATE
);
