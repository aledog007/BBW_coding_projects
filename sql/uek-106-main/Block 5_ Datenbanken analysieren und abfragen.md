# Aufgabe: Subqueries

Produkte die mehr kosten als der Durchschnitt
```
SELECT product, Preis
FROM product
WHERE price > (SELECT AVG(price) FROM product);
```

Produkte die weniger kosten als der Durchschnitt
```
SELECT product, Preis
FROM product
WHERE price < (SELECT AVG(price) FROM product);
```

Bezeichnung und Preis des teuersten Produktes
```
SELECT product, Price
FROM Producte
WHERE price = (SELECT MAX(price) FROM Product);
```

Durchschnittliche Anzahl der Bestellungen pro Kunde
```
SELECT AVG(Anzahl)
FROM (SELECT customerID, COUNT(*)
      FROM order_entry
      GROUP BY customerID);
```

# Aufgabe 5.1: Datenbank analysieren


Welche Tabellen sind in der Datenbank vorhanden und wie sind diese mit Primär- und Fremdschlüssel verbunden?

- Kann man mithilfe des schemas anschauen.


Was wird in dieser Datenbank reps. in den einzelnen Tabellen gespeichert?

`SELECT * FROM *table_name*;`

`DESCRIBE *table_name*;`


Was ist der Umfang der in der Datenbank gespeicherten Daten?

`SELECT COUNT(*) FROM table_name;`


Welchen Geschäftsprozess bildet die Datenbank Sakila ab?

über einen DVD verleih.


# Aufgabe 5.2: SQL Abfragen durchführen

Anzahl Datensätze in der Tabelle payment

`SELECT COUNT(*) FROM payment;`

Anzahl Schauspieler mit dem Namen Julia

```
SELECT COUNT(*) FROM actor
WHERE first_name = 'Julia';
```

Anzahl inaktive Kunden
```
SELECT COUNT(*) FROM customer
WHERE active = 0;
```

Durchschnittliche Länge der Filme mit Rating “PG”
```
SELECT AVG(length) FROM film
WHERE rating = 'PG';
```


Ausleihen, die noch nicht zurück gebracht wurden, sortiert nach Ausleihdatum (Format: dd.mm.YYYY)

```
SELECT DATE_FORMAT(rental_date, '%d.%m.%Y') AS Ausleihdatum, inventory_id, customer_id
FROM rental
WHERE return_date IS NULL
ORDER BY rental_date;
```


Berechnen Sie die durchschnittliche Ausleihdauer in Tagen
```
SELECT AVG(DATEDIFF(return_date, rental_date)) AS Durchschnitt
FROM rental
WHERE return_date IS NOT NULL;
```

Liste der Vornamen von Schauspielern, deren Vorname nur 3 Buchstaben lang ist. Zeigen Sie keine doppelten Vornamen an
```
SELECT DISTINCT first_name FROM actor
WHERE LENGTH(first_name) = 3;
```


Erstellen Sie eine Liste der Schauspieler, deren Nachname mit 'B' beginnt und an zweitletzter Stelle ein 'e' haben. Zeigen Sie keine doppelten Namen an
```
SELECT DISTINCT first_name, last_name FROM actor
WHERE last_name LIKE 'B%e_';
```


Zählen Sie bei allen Datensätzen der Tabelle "rental" 12 Jahre zu Ausleih- und Rückgabedatum dazu
```
SELECT DATE_ADD(rental_date, INTERVAL 12 YEAR) AS Neues_Ausleihdatum, DATE_ADD(return_date, INTERVAL 12 YEAR) AS Neues_Rückgabedatum, inventory_id, customer_id, staff_id
FROM rental;
```


Erstellen Sie eine neue Kategorie namens "Art"
```
INSERT INTO category (name)
VALUES ('Art');
```



