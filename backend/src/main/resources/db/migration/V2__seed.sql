insert into guilds (nom, slug) values
('Les Veilleurs', 'les-veilleurs');

insert into raids (slug, nom, description, niveau_recommande, duree_estimee_minutes, difficulte, actif) values
('forge-obscure', 'Forge Obscure', 'Raid de guilde niveau intermediaire', '190+', 45, 'MEDIUM', true);

insert into paliers (
    raid_id, ordre, code, nom, resume, objectif, actions, roles, pieges, conditions_reussite, suite, conseil, faq, actif
) values
(
    1, 1, 'ENTREE', 'Salle d''entree',
    'Nettoyer la salle puis ouvrir le passage',
    'Eliminer les ennemis de la premiere salle',
    'Focus cible principale|Proteger le support|Ne pas se disperser',
    'tank|support|dps melee',
    'Separer le groupe|Ignorer la cible prioritaire',
    'Salle nettoyee et passage ouvert',
    'Acces a la salle des gardiens',
    'Restez groupes pour accelerer le nettoyage',
    'Comment ouvrir la porte ?|Eliminer toute la vague',
    true
),
(
    1, 2, 'GARDIENS', 'Salle des gardiens',
    'Tenir deux vagues puis activer le levier',
    'Survivre aux vagues et securiser le levier',
    'Focus garde gauche|Proteger le soigneur|Activer le levier apres la vague 2',
    'tank|support|dps distance',
    'Declencher trop tot|Se disperser',
    'Deux vagues terminees et levier active',
    'Acces au mini-boss',
    'Prioriser la survie plutot que la vitesse',
    'Quand activer le levier ?|Apres la deuxieme vague',
    true
),
(
    1, 3, 'MINIBOSS', 'Mini-boss',
    'Stabiliser le groupe puis tomber le mini-boss',
    'Controler les invocations et finir la cible principale',
    'Interrompre la competence chargee|Nettoyer les invocations|Burst en fenetre',
    'tank|support|dps burst',
    'Ignorer les invocations|Burst hors fenetre',
    'Mini-boss vaincu',
    'Acces au boss final',
    'Gardez les cooldowns pour la fenetre de vulnerabilite',
    'Quand burst ?|Pendant la vulnerabilite',
    true
);
