CREATE VIEW v_clients_retours AS
SELECT DISTINCT c.id_client, c.nom, c.prenom, c.email, rt.date_retour
FROM Clients c
JOIN Ordinateurs o ON c.id_client = o.id_client
JOIN Reparations r ON o.id_ordinateur = r.id_ordinateur
JOIN retours rt ON r.id_reparation = rt.id_reparation
WHERE rt.date_retour IS NOT NULL;
