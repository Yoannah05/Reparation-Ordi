CREATE OR REPLACE VIEW v_clients_retours AS
SELECT DISTINCT c.id_client, c.nom, c.prenom, c.email, rt.date_retour
FROM Clients c
JOIN Ordinateurs o ON c.id_client = o.id_client
JOIN Reparations r ON o.id_ordinateur = r.id_ordinateur
JOIN retours rt ON r.id_reparation = rt.id_reparation
WHERE rt.date_retour IS NOT NULL;

CREATE OR REPLACE VIEW v_retours_criteria AS
SELECT r.*, c.id_categorie_ordi as categorie, p.id_probleme as probleme, tr.id_type_rep as typerep
FROM retours r
JOIN reparations rp ON r.id_reparation = rp.id_reparation
JOIN reparations_ordi ro ON rp.id_reparation = ro.id_reparation
JOIN ordinateurs o ON rp.id_ordinateur = o.id_ordinateur
JOIN modeles m ON o.id_modele = m.id_modele
JOIN categorie_ordi c ON m.id_categorie_ordi = c.id_categorie_ordi
JOIN problemes p ON ro.id_probleme = p.id_probleme
JOIN type_reparations tr ON ro.id_type_rep = tr.id_type_rep;
