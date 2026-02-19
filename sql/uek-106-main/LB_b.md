# LBb (15’’, 25%) – praktisch, schriftlich
**Prüfungsstoff**
- Data Manipulation Language (DML)
- Data Retrieval Language (DRL)
- Data Control Language (DCL)
- Transaction Control Language (TCL)
- Import/Export von Daten und Datenbanken


## Data Manipulation Language (DML)
**Befehle**
- INSERT
    - Mit diesem Befehl kannst du neue Daten in eine Tabelle einfügen.
- UPDATE
    - Mit diesem Befehl kannst du bestehende Daten in einer Tabelle ändern.
- DELETE
    - Mit diesem Befehl kannst du Daten aus einer Tabelle löschen.

## TCL (Transaction Control Language)
**Befehle**
- BEGIN
    - Mit diesem Befehl kannst du eine Transaktion starten, die aus mehreren DML-Befehlen bestehen kann.
- COMMIT
    - Mit diesem Befehl kannst du die Änderungen, die du in einer Transaktion gemacht hast, dauerhaft in der Datenbank speichern.
- ROLLBACK
    - Mit diesem Befehl kannst du die Änderungen, die du in einer Transaktion gemacht hast, rückgängig machen und die Datenbank in den Zustand vor der Transaktion zurücksetzen.


### Best Practice

- INSERT

*Befehl ohne Werte*
```
INSERT INTO <table_name> (<atribut_name>, <atribut_name>, ...) VALUES 
    (<value1>, <value2>, <value3>),
    (<value1>, <value2>, <value3>);
```
*Befehl mit Werte*
```
INSERT INTO productcategory (id, name) VALUES
(1, 'Bücher'),
(2, 'Elektronik'),
(3, 'Sportartikel'),
(4, 'Kleidung');
```


- UPDATE

*Befehl ohne Werte*
```
BEGIN;
UPDATE <table_name> SET 
    <attribut_name1> = <value1>
WHERE <attribut_name> = <value>;
COMMIT;
```
*Befehl mit Werte*
```
BEGIN;
UPDATE person SET 
    firstname = 'Markus' 
WHERE firstname = 'Vorname_S1';
COMMIT;
```


- DELETE

*Befehl ohne Werte*
```
BEGIN;
DELETE FROM <table_name> WHERE<atribut_name>=<value>;
COMMIT;
```

*Befehl mit Werte*
```
BEGIN;
DELETE FROM person_course_execution WHERE fk_participant_id = 7;
COMMIT;
```
 

## DRL (Data Retrieval Language)
**Befehl**
- SELECT
    - Mit dem SELECT-Befehl kannst du Daten aus einer oder mehreren Tabellen abfragen und anzeigen.

*Gruppenfunktionen*

- COUNT 
    - Anzahl der Datensätze
- MIN 
    - Minimaler Wert
- AVG 
    - Durchschnitts Wert
- MAX 
    - Maximaler Wert


### Best Practice
[Hier](https://git.anykey.ch/uek/uek-106/-/blob/main/Block%204:%20Daten%20mit%20SQL%20abfragen.md?ref_type=heads) gibt es Befehle in praktischer verwendung.

*Grundbefehl*
```
SELECT [DISTINCT] { * | Attributliste | mathematische Ausdrücke} Bezeichner FROM Tabelle1 Bezeichner1, Tabelle2 Bezeichner2, ...
[WHERE Bedingungen]
[GROUP BY Attributliste]
[HAVING Bedingungen]
[ORDER BY Attributliste] [ASC | DESC]; 
[LIMIT 2(,4)]
```

*Daten abfragen (Attribute)*
```
SELECT id, firstname, lastname, email FROM person;
```

*Daten abfragen (WHERE, Operatoren, AND/OR, FunkRonen)*
```
SELECT * FROM person WHERE id > 6 AND id < 12;
```

*Daten abfragen (ORDER BY)*
```
SELECT * FROM person WHERE id >= 1 ORDER BY id DESC;
SELECT * FROM person WHERE id >= 1 ORDER BY lastname ASC, firstname ASC;
```
<DESC> = Werte von hoch nach niedrig
<ASC> = Werte von niedrig nach hoch

*Daten abfragen (LIKE Patterns)*
```
SELECT id, firstname, lastname FROM person WHERE firstname LIKE '<PATTERN>’;
```
LIKE PATTERNS:
› % - Beliebiges Zeichen, (0..*)
› _ - Beliebiges Zeichen, (1)

*Daten abfragen (LIMIT)*
```
SELECT id, firstname, lastname FROM person LIMIT 5;
SELECT id, firstname, lastname FROM person LIMIT 2, 5;
``` 

*Export*
``` 
SELECT
*
FROM
customer
INTO OUTFILE 'c:/export/zli-courses.csv'
FIELDS
TERMINATED BY ','
ENCLOSED BY ''
LINES TERMINATED BY '\r\n';
```

*Import*
``` 
LOAD DATA INFILE 'c:/export/zli-courses.csv' 
INTO TABLE test
FIELDS 
TERMINATED BY ',' 
ENCLOSED BY '"' LINES 
TERMINATED BY '\r\n';
```

Wichtige Befhele [hier](https://git.anykey.ch/uek/uek-106/-/blob/main/Block%204:%20Daten%20mit%20SQL%20abfragen.md?ref_type=heads) zu finden








