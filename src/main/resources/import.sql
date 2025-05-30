-- 1. Nettoyage des tables (optionnel)
DELETE FROM achat_tickets;
DELETE FROM ticket_tarifs;
DELETE FROM achat;
DELETE FROM ticket;
DELETE FROM tarif;
DELETE FROM user;
DELETE FROM epreuve_sportive;
DELETE FROM hall;
DELETE FROM complexe_sportif;

-- 2. Insertion des Complexes Sportifs
INSERT INTO complexe_sportif (id, name, location) VALUES 
(1, 'Complexe Olympique', 'Paris'),
(2, 'Stade de France', 'Saint-Denis');

-- 3. Insertion des Halls
INSERT INTO hall (id, name, number_place, complexe_sportif_id) VALUES 
(1, 'Hall A', 500, 1),
(2, 'Hall B', 300, 1),
(3, 'Tribune Principale', 80000, 2);

-- 4. Insertion des Epreuves Sportives
INSERT INTO epreuve_sportive (id, name_epreuve_sportive, type_epreuve_sportive, niveau_epreuve, image_url, hall_id, duration_in_seconds, ticket_price) VALUES 
(1, '100m Haies', 'Athlétisme', 'National', 'https://example.com/100m.jpg', 1, 60, 25.50),
(2, 'Saut en Longueur', 'Athlétisme', 'International', 'https://example.com/saut.jpg', 2, 120, 30.00),
(3, 'Finale Football', 'Football', 'International', 'https://example.com/foot.jpg', 3, 5400, 75.00);

-- 5. Insertion des Utilisateurs
INSERT INTO user (id, username, password, email, role) VALUES 
(1, 'admin', '$2a$10$xJwL5v.nG3Q1I7WXYwB/T.7Xr5O8dF5Qe9W6dF5Qe9W6dF5Qe9W6dF5Qe9', 'admin@example.com', 'ADMIN'), -- Mot de passe : "admin123" hashé avec BCrypt
(2, 'user1', '$2a$10$yH8eQ7vT6r5E9W2D1C3B.A4Z6X8cV2bN3m4Z6X8cV2bN3m4Z6X8cV2bN3', 'user1@example.com', 'USER'), -- Mot de passe : "user123"
(3, 'user2', '$2a$10$zI9fR5tY7uH2J1K3L5M7.O9P0Q2s4D5F6g7H8J9K0L1M2N3O4P5Q6R7S', 'user2@example.com', 'USER');

-- 6. Insertion des Tarifs
INSERT INTO tarif (id, name_tarif, tarif, offre_tarif) VALUES 
(1, 'Plein tarif', 25.50, 'Accès standard'),
(2, 'Tarif réduit', 20.00, 'Étudiants, seniors'),
(3, 'VIP', 100.00, 'Accès premium + goodies');

-- 7. Insertion des Tickets
INSERT INTO ticket (id, seat, used, price, start_time_epreuve, remaining_places, epreuve_sportive_id, hall_id) VALUES 
(1, 'A12', false, 25.50, '2024-06-15 14:00:00', 50, 1, 1),
(2, 'B7', false, 30.00, '2024-06-16 10:30:00', 30, 2, 2),
(3, 'VIP1', false, 100.00, '2024-06-20 20:00:00', 10, 3, 3);

-- 8. Insertion des Achats
INSERT INTO achat (id, date_achat, prix_total, payment_status, user_id) VALUES 
(1, '2024-05-01 09:15:00', 55.50, 'PAID', 2),
(2, '2024-05-02 11:30:00', 130.00, 'PAID', 3);

-- 9. Liaison Achat-Ticket (table de jointure)
INSERT INTO achat_tickets (achat_id, tickets_id) VALUES 
(1, 1),
(1, 2),
(2, 3);

-- 10. Liaison Ticket-Tarif (table de jointure)
INSERT INTO ticket_tarifs (ticket_id, tarifs_id) VALUES 
(1, 1),
(2, 2),
(3, 3);