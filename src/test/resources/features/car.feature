Feature: Car rental

  Scenario: Lister toutes les voitures disponibles
    Given des voitures sont disponibles
    When je demande la liste des voitures
    Then toutes les voitures sont affichées

  Scenario: Louer une voiture avec succès
    Given une voiture est disponible
    When je loue cette voiture
    Then la voiture n'est plus disponible

  Scenario: Retourner une voiture
    Given une voiture est louée
    When je retourne cette voiture
    Then la voiture est marquée comme disponible
