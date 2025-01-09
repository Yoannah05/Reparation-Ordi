// static/js/dashboard.js

// Configuration des graphiques
const chartConfig = {
    revenue: {
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false
                },
                tooltip: {
                    mode: 'index',
                    intersect: false,
                    callbacks: {
                        label: function(context) {
                            let label = context.dataset.label || '';
                            if (label) {
                                label += ': ';
                            }
                            label += new Intl.NumberFormat('fr-FR', { 
                                style: 'currency', 
                                currency: 'EUR' 
                            }).format(context.parsed.y);
                            return label;
                        }
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: function(value) {
                            return new Intl.NumberFormat('fr-FR', { 
                                style: 'currency', 
                                currency: 'EUR',
                                maximumFractionDigits: 0
                            }).format(value);
                        }
                    }
                }
            }
        }
    },
    repairs: {
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false
                }
            }
        }
    }
};

// Initialisation des graphiques du dashboard
function initDashboardCharts(revenueData, repairsData) {
    initRevenueChart(revenueData);
    initRepairsChart(repairsData);
}

// Initialisation du graphique de revenus
function initRevenueChart(data) {
    const ctx = document.getElementById('revenueChart').getContext('2d');
    
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: data.map(item => item.date),
            datasets: [{
                label: 'Chiffre d\'affaires',
                data: data.map(item => item.amount),
                borderColor: 'rgb(59, 130, 246)',
                backgroundColor: 'rgba(59, 130, 246, 0.1)',
                fill: true,
                tension: 0.4
            }]
        },
        options: chartConfig.revenue.options
    });
}

// Initialisation du graphique de réparations
function initRepairsChart(data) {
    const ctx = document.getElementById('repairsChart').getContext('2d');
    
    new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['Terminées', 'En cours', 'En attente'],
            datasets: [{
                data: [
                    data.completed,
                    data.inProgress,
                    data.pending
                ],
                backgroundColor: [
                    'rgb(34, 197, 94)',
                    'rgb(245, 158, 11)',
                    'rgb(239, 68, 68)'
                ]
            }]
        },
        options: chartConfig.repairs.options
    });
}

// Fonction pour rafraîchir le dashboard
function refreshDashboard() {
    fetch('/api/dashboard/data')
        .then(response => response.json())
        .then(data => {
            updateStats(data.stats);
            updateCharts(data.charts);
            updateRecentActivity(data.recentActivity);
        })
        .catch(error => {
            console.error('Erreur lors du rafraîchissement du dashboard:', error);
            showNotification('error', 'Erreur lors du rafraîchissement des données');
        });
}

// Mise à jour des statistiques
function updateStats(stats) {
    document.getElementById('totalRevenue').textContent = formatCurrency(stats.totalRevenue);
    document.getElementById('activeRepairs').textContent = stats.activeRepairs;
    document.getElementById('activeClients').textContent = stats.activeClients;
    document.getElementById('averageRepairTime').textContent = `${stats.averageRepairTime} jours`;
    
    // Mise à jour des tendances
    updateTrend('revenueTrend', stats.revenueGrowth);
    updateTrend('repairTimeTrend', stats.repairTimeImprovement);
}

// Formatage de la monnaie
function formatCurrency(value) {
    return new Intl.NumberFormat('fr-FR', {
        style: 'currency',
        currency: 'EUR'
    }).format(value);
}

// Mise à jour des tendances
function updateTrend(elementId, value) {
    const element = document.getElementById(elementId);
    const icon = element.querySelector('i');
    const percentage = element.querySelector('span');
    
    icon.className = value >= 0 ? 'fas fa-arrow-up' : 'fas fa-arrow-down';
    element.className = `trend ${value >= 0 ? 'positive' : 'negative'}`;
    percentage.textContent = `${Math.abs(value).toFixed(1)}%`;
}

// Export du dashboard en PDF
function exportDashboard(format) {
    const url = `/api/dashboard/export/${format}`;
    
    fetch(url)
        .then(response => response.blob())
        .then(blob => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = `dashboard-${new Date().toISOString().split('T')[0]}.${format}`;
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
        })
        .catch(error => {
            console.error('Erreur lors de l\'export:', error);
            showNotification('error', 'Erreur lors de l\'export du dashboard');
        });
}

// Gestionnaire d'événements pour les filtres de période
document.getElementById('revenueChartPeriod').addEventListener('change', function(e) {
    fetch(`/api/dashboard/revenue?period=${e.target.value}`)
        .then(response => response.json())
        .then(data => {
            updateRevenueChart(data);
        })
        .catch(error => {
            console.error('Erreur lors du changement de période:', error);
        });
});

// Initialisation au chargement de la page
document.addEventListener('DOMContentLoaded', function() {
    // Rafraîchissement automatique toutes les 5 minutes
    setInterval(refreshDashboard, 5 * 60 * 1000);
    
    // Premier chargement
    refreshDashboard();
});