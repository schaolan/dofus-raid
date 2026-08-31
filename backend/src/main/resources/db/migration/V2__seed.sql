insert into guilds (nom, slug) values
('Les Veilleurs', 'les-veilleurs');

insert into raids (slug, nom, description, niveau_recommande, duree_estimee_minutes, difficulte, actif) values
('sanctuaire-jardins-eternels', 'Sanctuaire des Jardins eternels',
 'Raid de guilde collaboratif : 4 enigmes, 4 gardiens, un corridor de 60 monstres et 2 boss finaux. 8 a 16 joueurs, 2 heures max, 20 PV de raid partages. Chaque epreuve validee l''est pour toute la guilde.',
 '200', 120, 'HARD', true),
('gouffre-gigalodon', 'Gouffre du Gigalodon',
 'Raid de guilde base sur la mecanique de lumiere : 6 etages a descendre, 3 boss, un boss final (le Gigalodon) et 2 enigmes. 8 a 12 joueurs, 1 heure max. Plus une zone est sombre, plus les monstres sont forts.',
 '200', 60, 'MEDIUM', true);

-- =====================================================================
-- Raid 1 : Sanctuaire des Jardins eternels (raid_id = 1)
-- =====================================================================
insert into paliers (
    raid_id, ordre, code, nom, resume, objectif, actions, roles, pieges, conditions_reussite, suite, conseil, faq, actif
) values
(
    1, 1, 'ENIGMES', 'Les 4 enigmes',
    'Resoudre les 4 enigmes du sanctuaire (ordre libre), 2000 points chacune',
    'Valider les enigmes Reserve de Belladone, Cour d''Ephedre, Ouvrage Monochrome et Clos des Protecteurs',
    'Bataille navale : couler les 6 bateaux papier|Echecs : replacer les 4 pieces vertes (2 Tours, 2 Fous) et vaincre Belladone a 4|Fleurs : cliquer les fleurs dans l''ordre des piedestaux|Statues : copier la disposition des 3 statues de chaque jardin',
    'exploration|logique|multicompte utile',
    'Cliquer une mauvaise fleur ou valider une erreur coute 1 PV|Ne pas confondre bateaux et fleurs papier|Perdre le combat des echecs coute 1 PV par perso',
    'Les 4 enigmes validees (8000 points)',
    'Reveille les 4 gardiens',
    'Se repartir les enigmes en parallele ; noter la couleur de l''Ouvrage Monochrome, elle sert au Clos et aux gardiens',
    'Peut-on faire les enigmes dans n''importe quel ordre ?|Oui, l''ordre est libre et chaque validation compte pour toute la guilde',
    true
),
(
    1, 2, 'GARDIENS', 'Les 4 gardiens',
    'Vaincre les 4 gardiens reveilles, 5000 points chacun',
    'Battre le Veilleur de l''Ouvrage, le Gardien du Clos, le Defenseur de la Reserve et la Sentinelle de la Cour',
    'Veilleur : tuer d''abord le monstre correspondant a la statue du Clos|Gardien du Clos : marcher sur les 4 bonnes glyphes|Sentinelle : descendre l''obelisque de la couleur de l''Ouvrage a 1 PV|Debuffer les buffs defensifs (Blindefense, Floriforce)',
    'tank|support (Prymune recommandee)|dps',
    'Mauvais monstre ou mauvaise glyphe = combat perdu et PV perdus|Rester a plus de 4 PO reveille la Ferocite des Protecteurs|Soigner une cible sous Hydrose la blesse',
    'Les 4 gardiens vaincus (20000 points)',
    'Ouvre le Corridor du chateau',
    'Combats a 4 max, parallelisables ; rester a 4 PO ou moins face aux Protecteurs',
    'Comment rendre un gardien vulnerable ?|Remplir sa condition de delock (statue, glyphes ou obelisque) avant de l''attaquer',
    true
),
(
    1, 3, 'CORRIDOR', 'Corridor du chateau',
    '10 salles, 6 monstres en solo chacune : 60 monstres a abattre',
    'Nettoyer les 60 monstres pour ouvrir la porte de la tour en [17,10]',
    'Combattre en solo, sans compagnon|Viser une initiative de 2601+ pour jouer en premier|Bloquer la ligne de vue avec une invocation sur les maps sans obstacle',
    'dps solo autonome',
    'Monstres boostes (+30% vita, +2 PA, +300 puissance, bouclier)|Initiative des monstres a 2600',
    'Les 60 monstres vaincus, porte de la tour ouverte',
    'Acces a la tour et aux boss finaux',
    'Soin complet entre chaque combat ; Dofus Nebuleux et Prycipithon Iridescente pour one-shot',
    'Peut-on venir a plusieurs dans une salle ?|Non, les combats du corridor se font en solo',
    true
),
(
    1, 4, 'BOSS_FINAL', 'Reine Ecarlate & Princesse Maudite',
    'Affronter les 2 boss finaux de la tour, 10000 points chacun',
    'Vaincre la Reine Ecarlate puis la Princesse Maudite',
    'Tuer les 8 monstres des 4 cachots pour liberer et soigner le groupe|Infliger 5000 degats au Floracle pour liberer un perso en Exil Imperial|Gerer le Chatiment Royal (2000 degats a retardement)|Passer la phase 2 : Reine invulnerable + Volonte de la Princesse (30000 PV)',
    'tank|2 soutiens|dps monocible',
    'Impossible d''appliquer Pesanteur (retournee contre vous)|Tout le groupe en cachot = combat perdu|Etat Evade expose a l''Execution de l''Evade (degats feu en melee)',
    'Les 2 boss vaincus',
    'Fin du raid ; le score total determine les recompenses',
    'Combats a 8 joueurs max ; se separer en 2 groupes de 8 seulement a 16 joueurs',
    'Comment liberer un perso envoye en cachot ?|Infliger 5000 degats au Floracle correspondant',
    true
);

-- =====================================================================
-- Raid 2 : Gouffre du Gigalodon (raid_id = 2)
-- =====================================================================
insert into paliers (
    raid_id, ordre, code, nom, resume, objectif, actions, roles, pieges, conditions_reussite, suite, conseil, faq, actif
) values
(
    2, 1, 'ETAGE_1', 'Avant-poste des explorateurs (Etage -1)',
    'Nettoyer les 18 groupes de monstres sur 5 maps',
    'Vaincre les 18 groupes puis prendre l''ascenseur en [4,3]',
    'Descendre l''echelle et eliminer les groupes|Alimenter les Luminomachines en Sel des Profondeurs|Recolter le sel quand la luminosite est basse',
    'groupes de 3 a 6 pour maximiser les drops',
    'Le fragment de clef n1 drop a 1% ici|En intensite 0 les monstres deviennent agressifs jusqu''a 10 cases',
    'Les 18 groupes vaincus',
    'Acces a l''etage -2',
    'Gerer la lumiere : intensite 1 pour se deplacer, 3 pour les mobs, 4 pour les boss ; la lumiere baisse toutes les 2 minutes',
    'Pourquoi les monstres sont-ils parfois plus forts ?|A cause des Idees noires : en obscurite totale ils gagnent +200% vitalite, 1000 puissance et 2 PM',
    true
),
(
    2, 2, 'MUREINE', 'Plateau de la Mureine (Etage -2)',
    'Boss : la Mureine (51000 PV) et son escorte',
    'Vaincre la Mureine, accompagnee de Madrepire, Kokayou et Leviatank',
    'Passer le combat en intensite 4 avant de lancer|Eliminer d''abord les 3 monstres|Tenir la Mureine a distance ou la tacler pour l''empecher d''invoquer',
    'tank de blocage|dps distance|support',
    'Disperssssion invoque des Murares (max 10)|Imperatrice : +100 puissance par Murare|Corroside inflige des degats feu cumulables',
    'Mureine vaincue',
    'Unite de Mureine (1000 pts), fragment de clef n2, sel ; acces a l''etage -3',
    'Empecher les invocations est la cle du combat',
    'Faut-il monter la lumiere avant le boss ?|Oui, intensite 4 pour eviter les bonus d''obscurite',
    true
),
(
    2, 3, 'FALAISE', 'Falaise noyee (Etage -3)',
    'Enigme du Luminarium de type Lights Out',
    'Allumer les 16 poissons lanterne du mur 4x4',
    'Se placer dans la safe-zone en [4,12]|Cliquer un poisson allume/eteint lui et ses voisins|Allumer rangee par rangee via les poissons de la ligne du dessous',
    'logique',
    'Cliquer un poisson affecte aussi ses voisins',
    'Les 16 poissons allumes',
    'Acces a l''etage -4',
    'Un solveur automatique existe ; inutile de monter la lumiere ici',
    'Comment resoudre le Lights Out ?|Traiter ligne par ligne en cliquant sur la rangee inferieure jusqu''a tout allumer',
    true
),
(
    2, 4, 'EXECRABE', 'Terrier d''Execrabe (Etage -4)',
    'Boss (Execrabe, 100000 PV) puis enigme des statues',
    'Vaincre l''Execrabe en [9,11] puis cliquer les statues dans l''ordre des seuils de PV',
    'Retirer les PM de l''Execrabe (indeplacable, 64 esquive)|Rester a distance|Noter l''ordre des apparences (statues qui s''illuminent)|Cliquer les statues dans l''ordre des seuils 80k/60k/40k/20k',
    'retrait PM|dps distance',
    'Chaque erreur d''enigme fait perdre 1000 points|Chaque apparence donne un effet (bouclier, renvoi, retrait PO, attraction)',
    'Execrabe vaincu et enigme resolue',
    'Rancune d''Execrabe (5000 pts), Pince d''Execrabe (raccourci [6,10] vers [4,7]), fragment de clef n3, sel',
    'Deposer les tresors apres l''Execrabe pour booster le taux de drop des fragments (viser 10000 de score)',
    'Comment resoudre l''enigme de l''Execrabe ?|Cliquer les statues dans l''ordre ou le boss a change d''apparence a chaque seuil de PV',
    true
),
(
    2, 5, 'OSSUAIRE', 'Ossuaire abyssal (Etage -5)',
    'Farmer les 4 fragments de clef',
    'Obtenir les 4 fragments de clef, dont le n4 (1% sur les Krak''Haine)',
    'Enchainer les combats sur les Krak''Haine (repop rapide)|Prendre la cage de plongee en [10,14] une fois les 4 fragments obtenus',
    'farm de groupe',
    'Les fragments sont des alterations invisibles dans l''inventaire|La transition en [12,13] bug l''Autofollow',
    'Les 4 fragments de clef reunis',
    'Acces a l''etage -6',
    'Le drop est commun a tout le raid, repartissez-vous les combats',
    'Ou trouver le dernier fragment ?|Le fragment n4 drop a 1% sur les Krak''Haine de l''Ossuaire',
    true
),
(
    2, 6, 'WILLORQUE', 'Sombrefond de Willorque (Etage -6)',
    'Boss : Willorque (62000 PV), sans gestion de lumiere',
    'Vaincre Willorque en [11,16]',
    'Placer un Pandawa dans un coin sans poisson et le bloquer en melee|Ignorer la mecanique de Light Count|Cocher Passer les entites au premier plan pour mieux voir',
    'Pandawa (blocage)|dps',
    'Map totalement sombre, Willorque agresse des l''entree|Les poissons allumes le soignent (20% PV), eteints infligent des degats|10 poissons lanterne indestructibles',
    'Willorque vaincu',
    'Noirceur de Willorque (10000 pts), sel ; acces au Gigalodon',
    'Willorque n''a pas d''Idees noires ; pas besoin de gerer la lumiere ici',
    'Que faire des poissons lanterne ?|Ne pas s''en occuper, ils sont indestructibles ; se concentrer sur Willorque',
    true
),
(
    2, 7, 'GIGALODON', 'Le Gigalodon (boss final)',
    'Infliger un maximum de degats en 3 tours',
    'Lancer le combat via le coffre avant la fin du chrono et maximiser les degats',
    'Se placer en diagonale des allies (eviter Ultrasplash et Gigarale)|Ne jamais aller en melee sur les 3 cases avant lui|Eviter les bords du bassin (Tournageoire)|Burst monocible avec stuff full degats',
    'dps monocible (Iop, Pandawa, Huppermage, Sram, Zobal, Ouginak)|augmentation des degats subis',
    'Le Gigalodon joue 2 fois par tour (debut et milieu)|Les dents de l''amer attrapent au contact sur 3 cases|Hitbox multi-cases (glyphe rouge)',
    'Combat termine ; le score depend des degats infliges (jusqu''a 15000 points a 1M de degats)',
    'Fin du raid',
    'Deposer les tresors avant de lancer le Gigalodon ; Dofus Nebuleux et Jugement de Thanathena (+4% degats subis) aident',
    'Comment scorer sur le Gigalodon ?|Le score est proportionnel aux degats totaux infliges pendant les 3 tours',
    true
);
