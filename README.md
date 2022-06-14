Notions :

- Thread / Multithreading
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
  - Nombre d'agents
  - Stratégie
  
------
  
- [ ] Taquin avec >1 agents
  - [ ] Algorithme BFS
  - [ ] Convertir le chemin BFS en suite de directions
  - [ ] Multithreading
    - [ ] Semaphore pour chaque Box
    - [ ] Fonctionnement en ronde
    - [ ] Mise à jour GUI à la fin d'une ronde
    - [ ] Gestions priorité quand concurrence Box
  - [ ] Détecter quand tous les agents ont atteint leur destination
  - [ ] Compteur de rondes
  
------

- [ ] Strategy Pattern
  - [x] Naive
  - [ ] Simple
  - [ ] Cognitive

- [ ] Priorités des agents
   