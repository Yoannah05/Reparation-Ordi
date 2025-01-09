// Gestion des filtres et de la recherche
let currentFilters = {
    nom: '',
    telephone: '',
    email: ''
};

function applyFilters() {
    const rows = document.querySelectorAll('#clientsTable tbody tr');
    
    rows.forEach(row => {
        const nom = row.querySelector('td:nth-child(2)').textContent + 
                   ' ' + row.querySelector('td:nth-child(3)').textContent;
        const telephone = row.querySelector('td:nth-child(4)').textContent;
        const email = row.querySelector('td:nth-child(5)').textContent;
        
        const matchNom = !currentFilters.nom || 
                        nom.toLowerCase().includes(currentFilters.nom.toLowerCase());
        const matchTelephone = !currentFilters.telephone || 
                              telephone.includes(currentFilters.telephone);
        const matchEmail = !currentFilters.email || 
                          email.toLowerCase().includes(currentFilters.email.toLowerCase());
        
        row.style.display = (matchNom && matchTelephone && matchEmail) ? '' : 'none';
    });
}

// Gestionnaires d'événements pour les filtres
document.getElementById('searchName')?.addEventListener('input', (e) => {
    currentFilters.nom = e.target.value;
    applyFilters();
});

document.getElementById('searchPhone')?.addEventListener('input', (e) => {
    currentFilters.telephone = e.target.value;
    applyFilters();
});

document.getElementById('searchEmail')?.addEventListener('input', (e) => {
    currentFilters.email = e.target.value;
    applyFilters();
});

function resetFilters() {
    document.getElementById('searchName').value = '';
    document.getElementById('searchPhone').value = '';
    document.getElementById('searchEmail').value = '';
    currentFilters = {
        nom: '',
        telephone: '',
        email: ''
    };
    applyFilters();
}

// Validation du formulaire client
const form = document.querySelector('form.needs-validation');
if (form) {
    form.addEventListener('submit', function(event) {
        if (!this.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        }
        this.classList.add('was-validated');
    });

    // Validation du numéro de téléphone
    const phoneInput = document.getElementById('telephone');
    phoneInput?.addEventListener('input', function() {
        this.value = this.value.replace(/[^0-9]/g, '');
        if (this.value.length === 10) {
            this.setCustomValidity('');
        } else {
            this.setCustomValidity('Le numéro doit contenir 10 chiffres');
        }
    });
}

// Confirmation de suppression
function confirmDelete(id) {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce client ?')) {
        fetch(`/clients/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                window.location.reload();
            } else {
                throw new Error('Erreur lors de la suppression');
            }
        })
        .catch(error => {
            console.error('Erreur:', error);
            alert('Erreur lors de la suppression');
        });
    }
}

// Export des données clients
function exportClients(format) {
    window.location.href = `/clients/export?format=${format}`;
}

// Mise à jour des statistiques en temps réel
function updateClientStats() {
    fetch('/api/clients/stats')
        .then(response => response.json())
        .then(stats => {
            document.getElementById('totalClients').textContent = stats.total;
            document.getElementById('newClientsThisMonth').textContent = stats.nouveauxCeMois;
            document.getElementById('activeClients').textContent = stats.clientsActifs;
        })
        .catch(error => console.error('Erreur lors de la mise à jour des statistiques:', error));
}