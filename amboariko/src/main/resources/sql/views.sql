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

CREATE OR REPLACE VIEW v_commissions_techniciens1 AS
SELECT t.id_technicien , t.nom, s.id_sexe,  s.sexe, re.id_retours, r.prix_reparation AS prix,c.val AS pourcentage_commission, (r.prix_reparation * c.val)/100 AS Commission, re.date_retour
FROM Reparations r
JOIN Commissions c ON c.id_commission = r.id_commission
JOIN Techniciens t ON r.id_technicien = t.id_technicien
JOIN Retours re ON re.id_reparation = r.id_reparation
JOIN Sexe s ON t.id_sexe = s.id_sexe
WHERE re.date_retour IS NOT NULL;

CREATE OR REPLACE VIEW v_commissions_techniciens AS
SELECT 
    t.id_technicien,
    t.nom,
    s.sexe,
    s.id_sexe,  -- Add id_sexe to the view
    re.id_retours,
    r.prix_reparation AS prix,
    c.val AS pourcentage_commission,
    (r.prix_reparation * c.val) / 100 AS Commission,
    re.date_retour
FROM 
    Reparations r
JOIN 
    Commissions c ON c.id_commission = r.id_commission
JOIN 
    Techniciens t ON r.id_technicien = t.id_technicien
JOIN 
    Retours re ON re.id_reparation = r.id_reparation
JOIN 
    Sexe s ON t.id_sexe = s.id_sexe
WHERE 
    re.date_retour IS NOT NULL;

    SELECT * 
FROM v_commissions_techniciens1 
WHERE id_sexe = 1  -- Remplacez par la valeur de idSexe
  AND date_retour >= '2023-01-01'  -- Remplacez par la valeur de date1
  AND date_retour <= '2023-12-31';  -- Remplacez par la valeur de date2

      SELECT * 
FROM v_commissions_techniciens 
WHERE id_sexe = COALESCE(1,1)  -- Remplacez par la valeur de idSexe
  AND date_retour >= '2025-01-01'  -- Remplacez par la valeur de date1
  AND date_retour <= '2025-12-31';  -- Remplacez par la valeur de date2