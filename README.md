Notions :

- Thread / Multithreading :
  - https://ducmanhphan.github.io/2020-03-20-Waiting-threads-to-finish-completely-in-Java/
- Ressource partagée
- Mutex
- Sémaphore
- Mail / Mailbox
- Priorités
- Concurrence / Synchronisation :
  - https://www.callicoder.com/java-concurrency-issues-and-thread-synchronization/
  - synchronized vs semaphore : https://itsallbinary.com/java-synchronized-vs-semaphore/
  - https://www.baeldung.com/java-start-two-threads-at-same-time
  - https://www.baeldung.com/java-timer-and-timertask
- Algorithme de plus court chemin (matrice)
  - https://www.geeksforgeeks.org/find-whether-path-two-cells-matrix/?ref=lbp
  - https://www.youtube.com/watch?v=2JNEme00ZFA
  - https://www.geeksforgeeks.org/check-possible-path-2d-matrix/
- Synchronized methods

TODO :

- [x] Taquin avec 1 agent

  - [x] Algo de path finding (A\* ou Dijkstra ou Col/Ligne ou BFS)
  - [x] Thread
  - [x] Mettre à jour la vue à chaque fin d'étape
  - [x] Fin du jeu si agent atteint sa destination
  - Grille partagée par tous les agents
  - [x] Source et Destination agents dans Box de Grid
  - [x] Mouvements des agents

- [ ] GUI
  - [x] Nombre d'agents
  - [x] Stratégie
  - [x] Temps entre chaque étape
  - [x] Bouton stop

---

- [ ] Taquin avec >1 agents
  - [x] Algorithme BFS
    - Choisir de contourner ou non les obstacles
    - Agent reste sur place tant qu'il n'a pas de chemin libre vers destination
  - [x] Convertir le chemin BFS en suite de directions
  - [x] Multithreading
    - [ ] Semaphore pour chaque Box
    - [x] Fonctionnement en ronde
    - [x] Mise à jour GUI à la fin d'une ronde
    - [ ] Gestions priorité quand concurrence Box
  - [ ] Détecter quand tous les agents ont atteint leur destination
  - [x] Compteur de rondes

---

- [x] Strategy Pattern
  - [x] Naive
    - Agent rejoint ligne en premier, puis colonne (possibilité choix aléatoire)
    - Agent ne peut pas se déplacer sur case qui contient un agent
    - n=1 OK mais si situations de blocages augments quand n croit
  - [x] Simple
    - Avant de se déplacer, Agent cherche le meilleur chemin vers destination (plus court)
      - Path finding avec ou sans contournement d'obstacle (autre Agent) ?
  - [ ] Cognitive
    - Mailbox / Mail
      - Envoyer un Mail à son voisin de plus petite priorité (s'il y en a)
    - Actions

- [ ] Priorités des agents
  - Valeur symbole
  - Distance Manhattan
    - https://www.geeksforgeeks.org/calculate-the-manhattan-distance-between-two-cells-of-given-2d-array/
    
- [ ] Résoudre Taquin
  - Commencer par première ligne puis première colonne
