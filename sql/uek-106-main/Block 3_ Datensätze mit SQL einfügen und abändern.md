# Aufgabe 3.1: Datensätze erfassen

```
INSERT INTO productcategory (id, name) VALUES
(1, 'Bücher'),
(2, 'Elektronik'),
(3, 'Sportartikel'),
(4, 'Kleidung');

INSERT INTO product (id, name, preis, kategorie_id, created_at) VALUES
(1, 'Der Herr der Ringe', 29.90, 1, CURRENT_TIMESTAMP),
(2, 'iPhone 12', 999.00, 2, CURRENT_TIMESTAMP),
(3, 'Fussball', 19.95, 3, CURRENT_TIMESTAMP),
(4, 'Laptop', 799.00, 2, CURRENT_TIMESTAMP),
(5, 'Harry Potter', 24.90, 1, CURRENT_TIMESTAMP),
(6, 'Mac Pro', 59000.00, 4, CURRENT_TIMESTAMP);

INSERT INTO customer (id, name, email, adresse, plz) VALUES
(1, 'Nevio Marzo', 'nevio@marzo.com', 'Bahnhofstrasse 12', 8001),
(2, 'Peter Meier', 'peter.meier@example.com', 'Seestrasse 45', 8700),
(3, 'Laura Schmidt', 'laura.schmidt@example.com', 'Limmatquai 7', 8001);


INSERT INTO an_order (id, kunde_id, produkt_id, anzahl, status, bestelldatum, lieferdatum) VALUES
(1, 1, 6, 1, 'ausgeliefert', '2024-02-15', '2024-02-18'),
(2, 2, 1, 1, 'ausgeliefert', '2024-02-16', '2024-02-20'),
(3, 3, 3, 2, 'offen', '2024-02-17', NULL),
(4, 1, 4, 1, 'offen', '2024-02-18', NULL);

START TRANSACTION;
-- INSERT-Statements hier
COMMIT;
```

# Aufgabe 3.2: Datensätze ändern
```
begin;
SELECT * FROM Kunde WHERE name = 'Anna Müller';
UPDATE Kunde SET name = 'Anna Meier' WHERE name = 'Anna Müller';
SELECT * FROM Kunde WHERE name = 'Anna Meier';
COMMIT;


begin;
SELECT * FROM Produkt WHERE name = 'Der Herr der Ringe';
UPDATE Produkt SET name = 'Fantasy-Roman', preis = 34.90 WHERE name = 'Der Herr der Ringe';
SELECT * FROM Produkt WHERE name = 'Fantasy-Roman';
COMMIT;


begin;
SELECT * FROM Product
UPDATE Product SET price = price * 1.1;
SELECT * FROM Produkt;
COMMIT; 


begin;
SELECT * FROM Bestellung WHERE id = 1;
UPDATE Bestellung SET kunde_id = 2, produkt_id = 5 WHERE id = 1;
SELECT * FROM Bestellung WHERE id = 1;
COMMIT;
```

# Aufgabe 3.3: Datensätze löschen
```
begin;
SELECT * FROM Bestellung WHERE id = 1;
DELETE FROM Bestellung WHERE id = 1;
SELECT * FROM Bestellung WHERE id = 1;
COMMIT;

begin;
SELECT * FROM Kunde WHERE name = 'Anna Meier';
DELETE FROM Kunde WHERE name = 'Anna Meier';
SELECT * FROM Kunde WHERE name = 'Anna Meier';
COMMIT; 


begin;
SELECT * FROM Produktkategorie;
DELETE FROM Produktkategorie;
SELECT * FROM Produktkategorie;
COMMIT; 



