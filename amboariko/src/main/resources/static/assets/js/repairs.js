// Configuration de DataTables
$(document).ready(function() {
    const table = $('#reparationsTable').DataTable({
        language: {
            url: '/js/datatables-fr.json'
        },
        order: [[4, 'desc']], // Tri par date de début décroissante
        responsive: true,
        pageLength: 10,
        dom: 'Bfrtip',
        buttons: [
            'excel', 'pdf', 'print'
        ]
    });

    // Initialisation des filtres
    initializeFilters(table);
});

// Gestion des filtres
function initializeFilters(table) {
    // Filtre par statut
    $('#filterStatut').on('change', function() {
        table.column(3).search(this.value).draw();
    });

    // Filtre par client
    $('#searchClient').on('keyup', function() {
        table.column(1).search(this.value).draw();
    });

    // Filtre par date
    let dateDebut = null;
    let dateFin = null;

    $('#dateDebut').on('change', function() {
        dateDebut = this.value;
        filterByDate();
    });

    $('#dateFin').on('change', function() {
        dateFin = this.value;
        filterByDate();
    });

    function filterByDate() {
        $.fn.dataTable.ext.search.push(function(settings, data, dataIndex) {
            const dateReparation = new Date(data[4]); // Index de la colonne date début

            if (dateDebut && new Date(dateDebut) > dateReparation) return false;
            if (dateFin && new Date(dateFin) < dateReparation) return false;

            return true;
        });

        table.draw();
    }
}

// Réinitialisation des filtres
function resetFilters() {
    $('#filterStatut').val('');
    $('#searchClient').val('');
    $('#dateDebut').val('');
    $('#dateFin').val('');
    $('#reparationsTable').DataTable().search('').columns().search('').draw();
}

// Fonction pour terminer une réparation
function terminerReparation(id) {
    $('#reparationId').val(id);
    $('#terminerReparationModal').modal('show');
}

// Confirmation de la fin de réparation
function confirmerTerminerReparation() {
    const formData = {
        id: $('#reparationId').val(),
        commentaireFinal: $('#commentaireFinal').val(),
        dureeReelle: $('#dureeReelle').val()
    };

    $.ajax({
        url: '/api/reparations/terminer',
        method: 'POST',
        data: JSON.stringify(formData),
        contentType: 'application/json',
        success: function(response) {
            $('#terminerReparationModal').modal('hide');
            showToast('Succès', 'La réparation a été marquée comme terminée.');
            setTimeout(() => window.location.reload(), 1500);
        },
        error: function(xhr) {
            showToast('Erreur', 'Une erreur est survenue lors de la finalisation de la réparation.', 'error');
        }
    });
}

// Affichage des détails d'une réparation
function voirDetails(id) {
    $.get(`/api/reparations/${id}/details`, function(data) {
        // Remplissage des informations client
        $('#detailsClientNom').text(data.client.nom + ' ' + data.client.prenom);
        $('#detailsClientTelephone').text(data.client.telephone);
        $('#detailsClientEmail').text(data.client.email);

        // Remplissage des informations ordinateur
        $('#detailsOrdinateurModele').text(data.ordinateur.marque + ' ' + data.ordinateur.modele);
        $('#detailsOrdinateurSerie').text(data.ordinateur.numeroSerie);

        // Remplissage des problèmes
        const problemesHtml = data.problemes.map(p => `<li>${p.description}</li>`).join('');
        $('#detailsProblemes').html(problemesHtml);

        // Remplissage des composants
        const composantsHtml = data.composants.map(c => `
            <tr>
                <td>${c.nom}</td>
                <td>${c.quantite}</td>
                <td>${c.prixUnitaire.toFixed(2)}€</td>
                <td>${(c.quantite * c.prixUnitaire).toFixed(2)}€</td>
            </tr>
        `).join('');
        $('#detailsComposants').html(composantsHtml);

        // Remplissage des dates
        $('#detailsDateDebut').text(formatDate(data.dateDebut));
        $('#detailsDateFinPrevue').text(formatDate(data.dateFinPrevue));
        $('#detailsDateFinReelle').text(data.dateFin ? formatDate(data.dateFin) : 'Non terminée');

        // Remplissage des coûts
        $('#detailsMainOeuvre').text(data.coutMainOeuvre.toFixed(2) + '€');
        $('#detailsTotalComposants').text(data.totalComposants.toFixed(2) + '€');
        $('#detailsTotal').text(data.prixTotal.toFixed(2) + '€');

        $('#detailsReparationModal').modal('show');
    });
}

// Fonction pour imprimer la facture
function imprimerFacture() {
    const id = $('#reparationId').val();
    window.open(`/reparations/${id}/facture`, '_blank');
}

// Utilitaire pour formater les dates
function formatDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('fr-FR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    });
}

// Affichage des notifications toast
function showToast(title, message, type = 'success') {
    Toastify({
        text: message,
        duration: 3000,
        close: true,
        gravity: "top",
        position: "right",
        backgroundColor: type === 'success' ? "#22c55e" : "#ef4444",
    }).showToast();
}