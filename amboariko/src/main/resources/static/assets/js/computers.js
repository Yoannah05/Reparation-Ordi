// Gestion des filtres et de la recherche
let currentFilters = {
    marque: '',
    statut: '',
    numeroSerie: ''
};

function applyFilters() {
    const rows = document.querySelectorAll('#ordinateursTable tbody tr');
    
    rows.forEach(row => {
        const marque = row.querySelector('td:nth-child(3)').textContent;
        const statut = row.querySelector('td:nth-child(7) .badge').textContent;
        const numeroSerie = row.querySelector('td:nth-child(5)').textContent;
        
        const matchMarque = !currentFilters.marque || marque === currentFilters.marque;
        const matchStatut = !currentFilters.statut || statut.toLowerCase() === currentFilters.statut;
        const matchNumero = !currentFilters.numeroSerie || 
                           numeroSerie.toLowerCase().includes(currentFilters.numeroSerie.toLowerCase());
        
        row.style.display = (matchMarque && matchStatut && matchNumero) ? '' : 'none';
    });
}

// Gestionnaires d'événements pour les filtres
document.getElementById('filterMarque')?.addEventListener('change', (e) => {
    currentFilters.marque = e.target.value;
    applyFilters();
});

document.getElementById('filterStatut')?.addEventListener('change', (e) => {
    currentFilters.statut = e.target.value;
    applyFilters();
});

document.getElementById('searchNumeroSerie')?.addEventListener('input', (e) => {
    currentFilters.numeroSerie = e.target.value;
    applyFilters();
});

function resetFilters() {
    document.getElementById('filterMarque').value = '';
    document.getElementById('filterStatut').value = '';
    document.getElementById('searchNumeroSerie').value = '';
    currentFilters = {
        marque: '',
        statut: '',
        numeroSerie: ''
    };
    applyFilters();
}

// Gestion des modèles en fonction de la marque sélectionnée
document.getElementById('marque')?.addEventListener('change', async function() {
    const marqueId = this.value;
    const modeleSelect = document.getElementById('modele');
    
    modeleSelect.innerHTML = '<option value="">Sélectionnez un modèle</option>';
    
    if (marqueId) {
        try {
            const response = await fetch(`/api/marques/${marqueId}/modeles`);
            const modeles = await response.json();
            
            modeles.forEach(modele => {
                const option = new Option(modele.nomModele, modele.id);
                modeleSelect.add(option);
            });
        } catch (error) {
            console.error('Erreur lors du chargement des modèles:', error);
            alert('Erreur lors du chargement des modèles');
        }
    }
});

// Gestion des problèmes
let problemCounter = document.querySelectorAll('.problem-entry').length;

function addProblem() {
    const container = document.querySelector('.problems-container');
    const newProblem = document.createElement('div');
    newProblem.className = 'problem-entry';
    newProblem.innerHTML = `
        <div class="input-group mb-2">
            <select class="form-control" name="problemes[${problemCounter}].idProbleme" required>
                <option value="">Sélectionnez un problème</option>
                ${document.querySelector('select[name="problemes[0].idProbleme"]')
                    ?.innerHTML
                    .replace(/selected/g, '') || ''}
            </select>
            <button type="button" class="btn btn-outline-danger" onclick="removeProblem(this)">
                <i class="fas fa-minus"></i>
            </button>
        </div>
    `;
    container.appendChild(newProblem);
    problemCounter++;
}

function removeProblem(button) {
    button.closest('.problem-entry').remove();
}

// Confirmation de suppression
function confirmDelete(id) {
    const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
    document.getElementById('confirmDelete').onclick = async () => {
        try {
            const response = await fetch(`/ordinateurs/${id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            
            if (response.ok) {
                window.location.reload();
            } else {
                alert('Erreur lors de la suppression');
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Erreur lors de la suppression');
        }
        modal.hide();
    };
    modal.show();
}