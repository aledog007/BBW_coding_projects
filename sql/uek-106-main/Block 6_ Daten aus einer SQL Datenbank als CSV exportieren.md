 # Aufgabe 6.1: CSV Dateien exportieren 

Erstellen Sie eine Adressliste aller Kunden und speichern Sie sie als CSV Datei ab.
```
SELECT
 first_name,
 last_name,
 email,
 create_date
FROM
 customer
INTO OUTFILE
 'c:/temp/address.csv'
;
```
