# Aufgabe 4.1: Einfache Datenabfragen


-- Liste aller Produkte


`SELECT * FROM Product;`

# 
-- Liste aller Kategorien


`SELECT * FROM Category;`


# 
-- Liste aller Kunden. Geben Sie nur Vorname, Nachname und Emailadresse aus


`SELECT firstname, email FROM customer;`

# 

-- Liste aller Bestellungen sortiert nach Bestelldatum


`SELECT * FROM order_entery ORDER BY ordered_at;`
# 


-- Liste aller Produkte absteigend sortiert nach Preis


`SELECT * FROM Product ORDER BY price DESC;`
# 


-- Liste der teuersten 3 Produkte


`SELECT * FROM Product ORDER BY price DESC LIMIT 3;`

# 

-- Liste der günstigsten 3 Produkte


`SELECT * FROM Product ORDER BY price ASC LIMIT 3;`



# Aufgabe 4.2: Funktionen anwenden


-- Berechnen Sie die Quadratwurzel aller Produktpreise.


`SELECT SQRT(price) FROM Product;`

# 

-- Geben Sie den Namen des Monats aus dem Datum der Bestellungen aus.


`SELECT *, MONTHNAME(ordered_at) AS monat FROM order_entry;`

# 

-- Zählen Sie die Anzahl Buchstaben in den Vornamen der Kunden.


`SELECT LENGTH(firstname) FROM customer;`

# 

-- Liste der Email Adressen aller Kunden. Teilen Sie die Adresse in zwei Spalten auf Account und Domain.


`SELECT SUBSTRING_INDEX(email, '@', 1) AS Account, SUBSTRING_INDEX(email, '@', -1) AS Domain FROM customer;`

# 

-- Geben Sie die Initialen der Kunden in einer Spalte aus.


`SELECT firstname, lastname, CONCAT(left(firstname, 1), left (lastname, 1)) AS Initialen`

# 
-- Berechnen Sie die 8% Mehrwertsteuer, die in den Preisen inbegriffen ist (Optional: Runden Sie die MwSt auf 5 Rappen)


`SELECT ROUND(price * 0.08 / 0.05) * 0.05 AS MwSt FROM Product;`

# 

-- Geben Sie die Anzahl Datensätze ihrer Produkttabelle aus.


`SELECT COUNT(*) FROM Product;`

# 

-- Berechnen Sie Mindest-, Höchst- und Durchschnittspreis aller Produkte


`SELECT MIN(price) , MAX(price) , AVG(price) FROM Product;`
# 

# Aufgabe 4.3: WHERE Bedingungen

-- Produkt mit dem Primärschlüssel 5


`SELECT * FROM Product WHERE id = 5;`
# 


-- Kunden deren Primärschlüssel kleiner ist als 3


`SELECT * FROM customer WHERE id < 3;`
# 


-- Kunden deren Primärschlüssel kleiner ist als 3 oder grösser als 8


`SELECT * FROM customer WHERE id < 3 OR id > 8;`

# 

-- Bestellungen mit Primärschlüssel zwischen 3 und 7


`SELECT * FROM order_entery WHERE id BETWEEN 3 AND 7;`

# 

-- Kunden mit den Primärschlüsseln 1,3,5 und 6


`SELECT * FROM customer WHERE id IN (1, 3, 5, 6);`

# 

-- Bestellungen deren Lieferdatum NULL ist


`SELECT * FROM order_entery WHERE ordered_at IS NULL;`
# 


-- Produkte die mehr kosten als der Durchschnitt


`SELECT AVG(price) AS Durchschnittspreis FROM Product;`


`SELECT * FROM Product WHERE price > (SELECT AVG(price) FROM Product);`


# Aufgabe 4.4: LIKE Patterns

-- Kunden deren Vorname mit "f" beginnt


`SELECT * FROM customer WHERE firstname LIKE 'f%';`
# 


-- Kunden deren Nachname mit dem Buchstaben "r" endet


`SELECT * FROM customer WHERE lastname LIKE '% r';`

# 

-- Kunden deren Nachname ein "e" enthält


`SELECT * FROM customer WHERE lastname LIKE '%e%';`


# 
-- Kunden deren Vorname aus 5 Buchstaben besteht


`SELECT * FROM customer WHERE firstname LIKE '_____';`

# 

-- Kunden deren Nachname an der zweitletzter Stelle ein "e" haben


`SELECT * FROM customer WHERE lastname LIKE '%e_';`
# 






