// script.js
document.addEventListener('DOMContentLoaded', function() {
    // Initialize charts
    initializeCharts();
    
    // Initialize navigation
    initializeNavigation();
    
    // Initialize modals
    initializeModals();
    
    // Load initial data
    loadMockData();
});

// Navigation
function initializeNavigation() {
    // Sidebar toggle
    document.getElementById('sidebarCollapse').addEventListener('click', function() {
        document.getElementById('sidebar').classList.toggle('collapsed');
    });

//     // Page navigation
//     document.querySelectorAll('[data-page]').forEach(link => {
//         link.addEventListener('click', function(e) {
//             e.preventDefault();
//             const pageId = this.getAttribute('data-page');
//             showPage(pageId);
            
//             // Update active state
//             document.querySelectorAll('.components li').forEach(li => li.classList.remove('active'));
//             this.parentElement.classList.add('active');
//         });
//     });
}

function showPage(pageId) {
    document.querySelectorAll('.page').forEach(page => page.classList.remove('active'));
    document.getElementById(`${pageId}-page`).classList.add('active');
}

// Charts
function initializeCharts() {
    // Revenue Chart
    const revenueCtx = document.getElementById('revenueChart').getContext('2d');
    new Chart(revenueCtx, {
        type: 'line',
        data: {
            labels: ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Jun'],
            datasets: [{
                label: 'Chiffre d\'affaires',
                data: [12000, 19000, 15000, 21000, 16000, 25000],
                borderColor: '#4e73df',
                tension: 0.3,
                fill: true,
                backgroundColor: 'rgba(78, 115, 223, 0.05)'
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    display: false
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: function(value) {
                            return '€' + value;
                        }
                    }
                }
            }
        }
    });

    // Repairs Chart
    const repairsCtx = document.getElementById('repairsChart').getContext('2d');
    new Chart(repairsCtx, {
        type: 'doughnut',
        data: {
            labels: ['En cours', 'En attente', 'Terminé'],
            datasets: [{
                data: [24, 8, 45],
                backgroundColor: ['#4e73df', '#f6c23e', '#1cc88a']
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom'
                }
            }
        }
    });
}

// Modals
function initializeModals() {
    // Client Modal
    const clientModal = document.getElementById('clientModal');
    const addClientBtn = document.getElementById('addClientBtn');
    const clientForm = document.getElementById('clientForm');

    addClientBtn.addEventListener('click', () => {
        clientModal.classList.add('active');
    });

    // Computer Modal
    const computerModal = document.getElementById('computerModal');
    const addComputerBtn = document.getElementById('addComputerBtn');
    const computerForm = document.getElementById('computerForm');

    addComputerBtn.addEventListener('click', () => {
        computerModal.classList.add('active');
    });

    // Close buttons
    document.querySelectorAll('.close').forEach(closeBtn => {
        closeBtn.addEventListener('click', function() {
            this.closest('.modal').classList.remove('active');
        });
    });

    // Form submissions
    clientForm.addEventListener('submit', handleClientSubmit);
    computerForm.addEventListener('submit', handleComputerSubmit);
}

// Mock Data and Data Handling
let clients = [];
let computers = [];
let repairs = [];

function loadMockData() {
    // Mock Clients
    clients = [
        { id: 1, name: 'Jean Dupont', email: 'jean@email.com', phone: '0123456789', devices: 2 },
        { id: 2, name: 'Marie Martin', email: 'marie@email.com', phone: '0987654321', devices: 1 }
    ];

    // Mock Computers
    computers = [
        {
            id: 1,
            model: 'MacBook Pro 2020',
            client: 'Jean Dupont',
            problem: 'Écran cassé',
            startDate: '2024-01-01',
            endDate: '2024-01-07',
            status: 'terminé'
        },
        {
            id: 2,
            model: 'Dell XPS 13',
            client: 'Marie Martin',
            problem: 'Ne démarre pas',
            startDate: '2024-01-05',
            endDate: null,
            status: 'en_cours'
        }
    ];

    // Mock Repairs
    repairs = [
        {
            id: 1,
            computer: 'MacBook Pro 2020',
            client: 'Jean Dupont',
            status: 'en_cours',
            progress: 75
        },
        {
            id: 2,
            computer: 'Dell XPS 13',
            client: 'Marie Martin',
            status: 'en_attente',
            progress: 0
        }
    ];

    updateTables();
    updateRepairCards();
}

function updateTables() {
    // Update Clients Table
    const clientsTableBody = document.getElementById('clientsTableBody');
    clientsTableBody.innerHTML = clients.map(client => `
        <tr>
            <td>${client.id}</td>
            <td>${client.name}</td>
            <td>${client.email}</td>
            <td>${client.phone}</td>
            <td>${client.devices}</td>
            <td>
                <button class="btn-edit" onclick="editClient(${client.id})">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn-delete" onclick="deleteClient(${client.id})">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        </tr>
    `).join('');

    // Update Computers Table
    const computersTableBody = document.getElementById('computersTableBody');
    computersTableBody.innerHTML = computers.map(computer => `
        <tr>
            <td>${computer.id}</td>
            <td>${computer.model}</td>
            <td>${computer.client}</td>
            <td>${computer.problem}</td>
            <td>${computer.startDate}</td>
            <td>${computer.endDate || '-'}</td>
            <td>
                <span class="status ${computer.status}">${computer.status}</span>
            </td>
            <td>
                <button class="btn-edit" onclick="editComputer(${computer.id})">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn-delete" onclick="deleteComputer(${computer.id})">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        </tr>
    `).join('');
}

function updateRepairCards() {
    const waitingRepairs = document.getElementById('waitingRepairs');
    const inProgressRepairs = document.getElementById('inProgressRepairs');
    const completedRepairs = document.getElementById('completedRepairs');

    // Clear existing cards
    waitingRepairs.innerHTML = '';
    inProgressRepairs.innerHTML = '';
    completedRepairs.innerHTML = '';

    repairs.forEach(repair => {
        const card = `
            <div class="repair-card ${repair.status}">
                <h4>${repair.computer}</h4>
                <p>Client: ${repair.client}</p>
                <div class="progress-bar">
                    <div class="progress" style="width: ${repair.progress}%"></div>
                </div>
                <div class="card-actions">
                    <button onclick="updateRepairStatus(${repair.id})">
                        <i class="fas fa-arrow-right"></i>
                    </button>
                </div>
            </div>
        `;

        switch(repair.status) {
            case 'en_attente':
                waitingRepairs.innerHTML += card;
                break;
            case 'en_cours':
                inProgressRepairs.innerHTML += card;
                break;
            case 'termine':
                completedRepairs.innerHTML += card;
                break;
        }
    });
}
  // Fonction de gestion du formulaire client
function handleClientSubmit(e) {
    e.preventDefault();
    
    // Récupérer les données du formulaire
    const formData = new FormData(e.target);
    
    // Valider les données
    const name = formData.get('name').trim();
    const email = formData.get('email').trim();
    const phone = formData.get('phone').trim();
    const address = formData.get('address').trim();

    // Vérification des champs requis
    if (!name || !email || !phone) {
        alert('Veuillez remplir tous les champs obligatoires');
        return;
    }

    // Validation de l'email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert('Veuillez entrer une adresse email valide');
        return;
    }

    // Validation du numéro de téléphone (format français)
    const phoneRegex = /^(?:(?:\+|00)33|0)\s*[1-9](?:[\s.-]*\d{2}){4}$/;
    if (!phoneRegex.test(phone)) {
        alert('Veuillez entrer un numéro de téléphone valide');
        return;
    }

    // Créer le nouvel objet client
    const newClient = {
        id: Date.now(), // Utiliser timestamp comme ID unique
        name: name,
        email: email,
        phone: phone,
        address: address,
        devices: 0,
        createdAt: new Date().toISOString(),
        lastModified: new Date().toISOString()
    };
    
    // Ajouter à la liste des clients
    clients.push(newClient);
    
    // Mettre à jour l'affichage
    updateTables();
    
    // Réinitialiser le formulaire
    e.target.reset();
    
    // Fermer le modal
    const modal = e.target.closest('.modal');
    modal.classList.remove('active');
    
    // Afficher une confirmation
    showNotification('Client ajouté avec succès !', 'success');
}

// Fonction utilitaire pour afficher les notifications
function showNotification(message, type = 'info') {
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;
    
    document.body.appendChild(notification);
    
    // Animer l'entrée
    setTimeout(() => notification.classList.add('show'), 100);
    
    // Supprimer après 3 secondes
    setTimeout(() => {
        notification.classList.remove('show');
        setTimeout(() => notification.remove(), 300);
    }, 3000);
}

function handleComputerSubmit(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const newComputer = {
        id: computers.length + 1,
        model: formData.get('model'),
        client: formData.get('client'),
        problem: formData.get('problem'),
        startDate: new Date().toISOString().split('T')[0],
        endDate: null,
        status: 'en_attente'
    };
    
    computers.push(newComputer);
    // Ajouter automatiquement une réparation
    repairs.push({
        id: repairs.length + 1,
        computer: newComputer.model,
        client: newComputer.client,
        status: 'en_attente',
        progress: 0
    });
    
    updateTables();
    updateRepairCards();
    e.target.reset();
    e.target.closest('.modal').classList.remove('active');
}

// Filtres et recherche
function initializeFilters() {
    const statusFilter = document.getElementById('statusFilter');
    const dateFilter = document.getElementById('dateFilter');
    const searchInput = document.querySelector('.search-bar input');

    statusFilter.addEventListener('change', filterComputers);
    dateFilter.addEventListener('change', filterComputers);
    searchInput.addEventListener('input', searchClients);
}

function filterComputers() {
    const status = document.getElementById('statusFilter').value;
    const date = document.getElementById('dateFilter').value;
    
    const filteredComputers = computers.filter(computer => {
        const statusMatch = !status || computer.status === status;
        const dateMatch = !date || computer.startDate === date || computer.endDate === date;
        return statusMatch && dateMatch;
    });

    const computersTableBody = document.getElementById('computersTableBody');
    computersTableBody.innerHTML = filteredComputers.map(computer => `
        <tr>
            <td>${computer.id}</td>
            <td>${computer.model}</td>
            <td>${computer.client}</td>
            <td>${computer.problem}</td>
            <td>${computer.startDate}</td>
            <td>${computer.endDate || '-'}</td>
            <td>
                <span class="status ${computer.status}">${computer.status}</span>
            </td>
            <td>
                <button class="btn-edit" onclick="editComputer(${computer.id})">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn-delete" onclick="deleteComputer(${computer.id})">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        </tr>
    `).join('');
}

function searchClients(e) {
    const searchTerm = e.target.value.toLowerCase();
    const filteredClients = clients.filter(client => 
        client.name.toLowerCase().includes(searchTerm) ||
        client.email.toLowerCase().includes(searchTerm) ||
        client.phone.includes(searchTerm)
    );

    const clientsTableBody = document.getElementById('clientsTableBody');
    clientsTableBody.innerHTML = filteredClients.map(client => `
        <tr>
            <td>${client.id}</td>
            <td>${client.name}</td>
            <td>${client.email}</td>
            <td>${client.phone}</td>
            <td>${client.devices}</td>
            <td>
                <button class="btn-edit" onclick="editClient(${client.id})">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn-delete" onclick="deleteClient(${client.id})">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        </tr>
    `).join('');
}

// Gestion des réparations
function updateRepairStatus(repairId) {
    const repair = repairs.find(r => r.id === repairId);
    if (!repair) return;

    const statusFlow = ['en_attente', 'en_cours', 'termine'];
    const currentIndex = statusFlow.indexOf(repair.status);
    if (currentIndex < statusFlow.length - 1) {
        repair.status = statusFlow[currentIndex + 1];
        if (repair.status === 'en_cours') {
            repair.progress = 25;
        } else if (repair.status === 'termine') {
            repair.progress = 100;
            // Mettre à jour la date de fin de l'ordinateur correspondant
            const computer = computers.find(c => c.model === repair.computer);
            if (computer) {
                computer.status = 'termine';
                computer.endDate = new Date().toISOString().split('T')[0];
            }
        }
    }

    updateTables();
    updateRepairCards();
    updateDashboardStats();
}

// Mise à jour des statistiques du tableau de bord
function updateDashboardStats() {
    // Calculer le chiffre d'affaires (simulation)
    const revenue = repairs.reduce((total, repair) => {
        return total + (repair.status === 'termine' ? Math.random() * 500 + 100 : 0);
    }, 0);

    // Mettre à jour les cartes de statistiques
    document.querySelector('.stats-cards').innerHTML = `
        <div class="card">
            <div class="card-body">
                <h5>Chiffre d'affaires</h5>
                <h3>€${revenue.toFixed(2)}</h3>
                <p class="trend positive">+12.5% <span>vs mois dernier</span></p>
            </div>
        </div>
        <div class="card">
            <div class="card-body">
                <h5>Réparations en cours</h5>
                <h3>${repairs.filter(r => r.status === 'en_cours').length}</h3>
                <p>${repairs.filter(r => r.status === 'en_attente').length} en attente</p>
            </div>
        </div>
        <div class="card">
            <div class="card-body">
                <h5>Clients actifs</h5>
                <h3>${clients.length}</h3>
                <p>${repairs.filter(r => r.status === 'en_cours' || r.status === 'en_attente').length} appareils en traitement</p>
            </div>
        </div>
    `;

    // Mettre à jour les graphiques
    updateCharts();
}

function updateCharts() {
    // Mettre à jour le graphique des réparations
    const repairsChart = Chart.getChart('repairsChart');
    if (repairsChart) {
        const repairCounts = {
            en_cours: repairs.filter(r => r.status === 'en_cours').length,
            en_attente: repairs.filter(r => r.status === 'en_attente').length,
            termine: repairs.filter(r => r.status === 'termine').length
        };

        repairsChart.data.datasets[0].data = [
            repairCounts.en_cours,
            repairCounts.en_attente,
            repairCounts.termine
        ];
        repairsChart.update();
    }
}

// Fonctions utilitaires
function formatDate(date) {
    return new Date(date).toLocaleDateString('fr-FR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });
}

function calculateRepairDuration(startDate, endDate) {
    if (!endDate) return 'En cours';
    const start = new Date(startDate);
    const end = new Date(endDate);
    const days = Math.ceil((end - start) / (1000 * 60 * 60 * 24));
    return `${days} jour${days > 1 ? 's' : ''}`;
}

// Export pour PDF (simulation)
function exportToPDF() {
    alert('Fonctionnalité d\'export en PDF à implémenter');
}

// Initialisation au chargement
document.addEventListener('DOMContentLoaded', function() {
    initializeCharts();
    initializeNavigation();
    initializeModals();
    initializeFilters();
    loadMockData();
    updateDashboardStats();
});