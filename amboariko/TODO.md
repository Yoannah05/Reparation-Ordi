##  TODO
## SUJET:
    4.  liste des clients qui ont achété(retour) aujourd'hui
    3.  Composant recommandé du mois

Me :
    -   add constraints

3.  Recommandation :
    -   liste des recommandés 
    -   mettre un filtre de date

2.  Reparation :
    -   afficher la liste des reparations avec les boutons d'actions (delete/edit/details(icones) et retourner/reparer)
    -   mettre un filtre entre deux dates (la deuxieme date est faculatif)
    -   mettre un filtre sur les problemes et type de reparation
    -   retourner (redirection vers insertion retour avec les champs déjà pré-rempli pour ordinateur(cad id)) si pas encore retournée
    -   reparer (si status false) : affiche un formulaire pour choisir les composants utilisées (calcul du prix)
    -   afficher les détails d'une reparation : l'ordinateur en question, le client, les problemes, les composants utilisés, le retour si il y en a 
    -   dans le formulaire de reparation :
        -   selection d'un ordinateur (mettre une option pour en ajouter, afficher un modal pour inserer un ordinateur, de même pour odinateur concernant le client)
        -   choisir date debut et date fin
        -   choix de status
        -   un menu où on peut faire des recherches de compsants puis lorqu'on clique, il sera ajouter automatiquement sur le checkbox
        -   des lignes pour des checkboxs vide avec un input de quantite
        -   un bouton de validation

1.  CRUD 
    -   `Back`:
        -   création des models
        -   création des repo et services 
        -   création des controllers
    
    -   `Front`:
        -   page d'acceuil
        -   page d'insertion 
        -   page pour la liste (avec les boutons d'action Update/Delete)

