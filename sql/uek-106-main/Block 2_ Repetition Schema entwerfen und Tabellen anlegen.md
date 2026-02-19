# Repetition Schema entwerfen und Tabellen anlegen
## Aufgabe 2.3: DBM erstellen
 
```
CREATE DATABASE online_shop;

USE online_shop;


CREATE TABLE product (
  product_id INT AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  price DECIMAL(10,2) NOT NULL,
  category_id INT NOT NULL,
  PRIMARY KEY (product_id),
  FOREIGN KEY (category_id) REFERENCES category (category_id)
);


CREATE TABLE category (
  category_id INT AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  PRIMARY KEY (category_id)
);


CREATE TABLE customer (
  customer_id INT AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  address VARCHAR(255) NOT NULL,
  zip INT(5) NOT NULL,
  city VARCHAR(50) NOT NULL,
  email VARCHAR(50) UNIQUE NOT NULL,
  phone VARCHAR(20) NOT NULL,
  PRIMARY KEY (customer_id)
);


CREATE TABLE order (
  order_id INT AUTO_INCREMENT,
  customer_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT NOT NULL,
  order_date DATE NOT NULL,
  delivery_date DATE NULL,
  PRIMARY KEY (order_id),
  FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
  FOREIGN KEY (product_id) REFERENCES product (product_id)
);
```

## Aufgabe 2.5: Schema mit SQL/DDL bearbeiten
```
-- Sie wollen in der Kundentabelle auch die mobile Telefonnummer speichern. Fügen Sie eine entsprechende Spalte hinzu
ALTER TABLE Kunde ADD mobile VARCHAR(20);

-- Ändern Sie den Namen der Spalte für die Produktbezeichung
ALTER TABLE Product CHANGE name_neu varchar(255) NOT NUlL;

-- Ändern Sie den Datentyp des Produktpreises auf DECIMAL(6,2) UNSIGNED
ALTER TABLE Produkt MODIFY preis DECIMAL(6,2) UNSIGNED;

-- Setzten Sie nachträglich NOT NULL für den Produktpreis
ALTER TABLE Product MODIFY preis DECIMAL(6,2) UNSIGNED NOT NULL;

-- Fügen Sie ein neues Attribut (created_at, DATETIME) in die Produkttabelle ein und stellen Sie sicher, dass dieses Feld automatisch mit dem aktuellen Zeitpunkt bei einem INSERT befüllt wird.
ALTER TABLE Product ADD created_at DATETIME DEFAULT CURRENT_TIMESTAMP;

-- Entfernen Sie die Spalte für die mobile Telefonnummer wieder
ALTER TABLE Kunde DROP COLUMN mobil;

-- Entfernen Sie den Foreign Key Constraint vom Postleitzahlen Fremdschlüssel aus der Kundentabelle
ALTER TABLE Kunde DROP FOREIGN KEY plz_fk;

-- Fügen Sie den Foreign Key Constraint wieder hinzu
ALTER TABLE Kunde ADD CONSTRAINT plz_fk FOREIGN KEY (fk_zip_id) REFERENCES zip(ID));
```
