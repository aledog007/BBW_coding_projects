DROP DATABASE IF EXISTS PIZZA_EXPRESS;

CREATE DATABASE PIZZA_EXPRESS;

USE PIZZA_EXPRESS;

CREATE TABLE ZIP (
    ID INT UNSIGNED NOT NULL AUTO_INCREMENT, -- bigint UNSIGNED NOT NULL auto_increment
    ZIP SMALLINT(4) UNSIGNED NOT NULL,
    CITY VARCHAR(255) NOT NULL,
    PRIMARY KEY(ID),
    UNIQUE(ZIP, CITY)
);

CREATE TABLE CUSTOMER (
    ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    FK_ZIP_ID INT UNSIGNED NOT NULL,
    FIRSTNAME VARCHAR(255) NOT NULL,
    LASTNAME VARCHAR(255) NOT NULL,
    ADDRESS VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(384) NOT NULL UNIQUE,
    PASSWORD VARCHAR(255) NOT NULL,
    PHONE VARCHAR(255),
    PRIMARY KEY(ID),
    FOREIGN KEY(FK_ZIP_ID) REFERENCES ZIP(ID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE CATEGORY (
    ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    CATEGORY VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY(ID)
);

CREATE TABLE PRODUCT (
    ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    FK_CATEGORY_ID INT UNSIGNED NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    DESCRIPTION VARCHAR(255) NOT NULL,
    PRICE DECIMAL(6, 2) NOT NULL,
    PRIMARY KEY(ID),
    FOREIGN KEY(FK_CATEGORY_ID) REFERENCES CATEGORY(ID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE ORDER_ENTRY (
    ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    FK_CUSTOMER_ID INT UNSIGNED NOT NULL,
    ORDERED_AT DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    DELIVERED_AT DATETIME NULL,
    PRIMARY KEY(ID),
    FOREIGN KEY(FK_CUSTOMER_ID) REFERENCES CUSTOMER(ID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE PRODUCT_ORDER_ENTRY (
    ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
    FK_PRODUCT_ID INT UNSIGNED NOT NULL,
    FK_ORDER_ENTRY_ID INT UNSIGNED NOT NULL,
    AMOUNT INT UNSIGNED NOT NULL DEFAULT 1,
    PRICE DECIMAL(6, 2) NULL,
    PRIMARY KEY(ID),
    FOREIGN KEY(FK_PRODUCT_ID) REFERENCES PRODUCT(ID) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(FK_ORDER_ENTRY_ID) REFERENCES ORDER_ENTRY(ID) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Sie wollen in der Kundentabelle auch die mobile Telefonnummer speichern. Fügen Sie eine entsprechende Spalte hinzu
ALTER TABLE CUSTOMER ADD MOBILE VARCHAR(255);

-- Ändern Sie den Namen der Spalte für die Produktbezeichung
ALTER TABLE PRODUCT CHANGE NAME PRODUCT_NAME VARCHAR(255) NOT NULL;

ALTER TABLE PRODUCT CHANGE PRODUCT_NAME NAME VARCHAR(255) NOT NULL;

-- Ändern Sie den Datentyp des Produktpreises auf DECIMAL(6,2) UNSIGNED
ALTER TABLE PRODUCT MODIFY PRICE DECIMAL(6, 2) UNSIGNED;

-- Setzten Sie nachträglich NOT NULL für den Produktpreis
ALTER TABLE PRODUCT MODIFY PRICE DECIMAL(6, 2) NOT NULL;

-- Fügen Sie ein neues Attribut (created_at, DATETIME) in die Produkttabelle ein und stellen Sie sicher, dass dieses Feld automatisch mit dem aktuellen Zeitpunkt bei einem INSERT befüllt wird.
ALTER TABLE PRODUCT ADD CREATED_AT DATETIME NOT NULL DEFAULT NOW();

-- Entfernen Sie die Spalte für die mobile Telefonnummer wieder
ALTER TABLE CUSTOMER DROP MOBILE;

-- Entfernen Sie den Foreign Key CONSTRAINT vom Postleitzahlen Fremdschlüssel aus der Kundentabelle
SHOW CREATE TABLE customer; -- Name des CONSTRAINTs herausfinden (z.B. customer_ibfk_1)

ALTER TABLE CUSTOMER DROP CONSTRAINT CUSTOMER_IBFK_1;

-- Fügen Sie den Foreign Key CONSTRAINT wieder hinzu
ALTER TABLE CUSTOMER ADD FOREIGN KEY(FK_ZIP_ID) REFERENCES ZIP(ID);

USE PIZZA_EXPRESS;

-- Transaktion starten
BEGIN
    ; INSERT INTO ZIP (
        ZIP,
        CITY
    ) VALUES (
        8001,
        'Zürich'
    ), (
        8002,
        'Zürich'
    ), (
        8048,
        'Zürich'
    ), (
        8008,
        'Zürich'
    ), (
        8049,
        'Zürich'
    ), (
        8051,
        'Zürich'
    ), (
        8902,
        'Urdorf'
    ), (
        8952,
        'Schlieren'
    ), (
        8600,
        'Dübendorf'
    ), (
        8402,
        'Winterthur'
    );
    INSERT INTO CUSTOMER (
        FK_ZIP_ID,
        FIRSTNAME,
        LASTNAME,
        ADDRESS,
        EMAIL,
        PASSWORD,
        PHONE
    ) VALUES (
        2,
        'Hans',
        'Muster',
        'Nordstrasse 1',
        'hans@muster.com',
        SHA('insecure'),
        '044 123 45 67'
    ), (
        5,
        'Barbara',
        'Meier',
        'Baslerstrasse 20',
        'b.meier@gmail.com',
        SHA('1234'),
        '044 101 45 80'
    ), (
        6,
        'Fritz',
        'Müller',
        'Bernerstrasse 209',
        'fritz@mueller.com',
        SHA('spiderman'),
        NULL
    ), (
        3,
        'Freddy',
        'Brunner',
        'Blumenweg 10',
        'fred@outlook.com',
        SHA('porsche'),
        '044 330 23 09'
    ), (
        4,
        'Peter',
        'Keller',
        'Seestrasse 171',
        'peter.keller@outlook.com',
        SHA('letmein'),
        NULL
    ), (
        7,
        'Bruno',
        'Kuster',
        'Dorfstrasse 21',
        'bk@hotmail.com',
        SHA('kakadu'),
        '044 880 13 50'
    ), (
        8,
        'Brigitte',
        'Maler',
        'Alte Landstrasse 201',
        'brigitte@gmail.com',
        SHA('starwars'),
        '043 627 89 14'
    ), (
        6,
        'Andrea',
        'Pfister',
        'Stadthausstrasse 9a',
        'apfister98@gmail.com',
        SHA('4ocean'),
        NULL
    ), (
        9,
        'Heinz',
        'Fuhrer',
        'Überlandstrasse 21',
        'hfuhrer@outlook.com',
        SHA('foobar'),
        '044 443 50 84'
    ), (
        10,
        'Stephanie',
        'Gerber',
        'Schulhausstrasse 4',
        'stephi@gmail.com',
        SHA('password'),
        '078 210 40 53'
    );
    INSERT INTO CATEGORY (
        CATEGORY
    ) VALUES (
        'Pizza'
    ), (
        'Salate'
    ), (
        'Getränke'
    ), (
        'Pasta'
    ), (
        'Dessert'
    );
    INSERT INTO PRODUCT (
        FK_CATEGORY_ID,
        NAME,
        DESCRIPTION,
        PRICE
    ) VALUES (
        1,
        'Margherita',
        'Tomaten, Mozzarella',
        17.00
    ), (
        1,
        'Prosciutto',
        'Tomaten, Schinken, Mozzarella',
        18.00
    ), (
        1,
        'Siziliana',
        'Tomaten, Salami, Mozzarella',
        19.00
    ), (
        1,
        'Quattro Stagioni',
        'Tomaten, Schinken, Peperoni, Artischocken, Mozzarella',
        17.00
    ), (
        1,
        'Padrone',
        'Tomaten, Kalbfleisch, Mozzarella',
        17.00
    ), (
        2,
        'Kleiner grüner Salat',
        'Grüner Blattsalat',
        6.00
    ), (
        2,
        'Kleiner gemischter Salat',
        'Gemischter Blattsalat',
        7.00
    ), (
        3,
        'Coca-Cola',
        '5dl',
        4.50
    ), (
        3,
        'Fanta',
        '5dl',
        4.50
    ), (
        3,
        'Valser',
        '5dl',
        4.50
    ), (
        3,
        'Red Bull',
        '33cl',
        5.50
    ), (
        1,
        'Hawaii',
        'Tomaten, Schinken, Ananas, Mozzarella',
        18.00
    ), (
        1,
        'Tonno',
        'Tomaten, Thunfisch, Mozzarella',
        18.00
    ), (
        1,
        'Fiorentina',
        'Tomaten, Zwiebeln, Spinat, Mascarpone, Mozzarella',
        19.00
    ), (
        1,
        'Calzone',
        'Tomaten, Schinken, Champignons, Ei, Mozzarella',
        20.00
    ), (
        1,
        'Ai Funghi',
        'Tomaten, Champignons, Mozzarella',
        18.00
    ), (
        3,
        'Sprite',
        '5dl',
        4.50
    ), (
        3,
        'Red Bull',
        '50cl',
        4.50
    ), (
        2,
        'Insalata Caprese',
        'Tomaten, Mozzarella, Balsamico',
        11.00
    ), (
        4,
        'Spaghetti Carbonara',
        'Rahmsauce, Zwiebel, Speck, Käse',
        17.00
    ), (
        4,
        'Spaghetti Bolognese',
        'Tomatensauce mit Rindfleisch',
        18.00
    ), (
        4,
        'Spaghetti Aglio e olio',
        'Tomatensauce mit Rindfleisch',
        16.00
    ), (
        4,
        'Penne Arrabiata',
        'Scharfe Tomatensauce',
        16.00
    );
    INSERT INTO ORDER_ENTRY (
        FK_CUSTOMER_ID,
        ORDERED_AT,
        DELIVERED_AT
    ) VALUES (
        2,
        CURRENT_TIMESTAMP - INTERVAL 5 DAY,
        CURRENT_TIMESTAMP - INTERVAL 3 DAY
    ), (
        1,
        CURRENT_TIMESTAMP - INTERVAL 5 DAY,
        NULL
    ), (
        4,
        CURRENT_TIMESTAMP - INTERVAL 5 DAY,
        CURRENT_TIMESTAMP - INTERVAL 1 DAY
    ), (
        2,
        CURRENT_TIMESTAMP - INTERVAL 5 DAY,
        NULL
    ), (
        2,
        CURRENT_TIMESTAMP - INTERVAL 4 DAY,
        NULL
    ), (
        1,
        CURRENT_TIMESTAMP - INTERVAL 4 DAY,
        NULL
    ), (
        3,
        CURRENT_TIMESTAMP - INTERVAL 4 DAY,
        NULL
    ), (
        5,
        CURRENT_TIMESTAMP - INTERVAL 4 DAY,
        NULL
    ), (
        6,
        CURRENT_TIMESTAMP - INTERVAL 4 DAY,
        CURRENT_TIMESTAMP - INTERVAL 1 DAY
    ), (
        1,
        CURRENT_TIMESTAMP - INTERVAL 4 DAY,
        NULL
    ), (
        3,
        CURRENT_TIMESTAMP - INTERVAL 4 DAY,
        NULL
    ), (
        7,
        CURRENT_TIMESTAMP - INTERVAL 3 DAY,
        NULL
    ), (
        4,
        CURRENT_TIMESTAMP - INTERVAL 3 DAY,
        NULL
    ), (
        4,
        CURRENT_TIMESTAMP - INTERVAL 3 DAY,
        NULL
    ), (
        8,
        CURRENT_TIMESTAMP - INTERVAL 2 DAY,
        NULL
    ), (
        9,
        CURRENT_TIMESTAMP - INTERVAL 2 DAY,
        CURRENT_TIMESTAMP
    ), (
        1,
        CURRENT_TIMESTAMP - INTERVAL 1 DAY,
        NULL
    ), (
        5,
        CURRENT_TIMESTAMP - INTERVAL 1 DAY,
        NULL
    ), (
        8,
        CURRENT_TIMESTAMP - INTERVAL 1 DAY,
        CURRENT_TIMESTAMP - INTERVAL 1 DAY
    ), (
        7,
        CURRENT_TIMESTAMP - INTERVAL 1 DAY,
        NULL
    ), (
        5,
        CURRENT_TIMESTAMP - INTERVAL 1 DAY,
        NULL
    ), (
        9,
        CURRENT_TIMESTAMP,
        NULL
    ), (
        2,
        CURRENT_TIMESTAMP,
        NULL
    ), (
        5,
        CURRENT_TIMESTAMP,
        NULL
    ), (
        3,
        CURRENT_TIMESTAMP,
        NULL
    ), (
        6,
        CURRENT_TIMESTAMP,
        NULL
    );
    INSERT INTO PRODUCT_ORDER_ENTRY (
        FK_PRODUCT_ID,
        FK_ORDER_ENTRY_ID,
        AMOUNT
    ) VALUES (
        3,
        1,
        1
    ), (
        4,
        1,
        2
    ), (
        5,
        1,
        3
    ), (
        1,
        2,
        1
    ), (
        3,
        2,
        1
    ), (
        2,
        3,
        1
    ), (
        2,
        4,
        2
    ), (
        7,
        4,
        2
    ), (
        8,
        5,
        1
    ), (
        6,
        5,
        1
    ), (
        1,
        5,
        4
    ), (
        3,
        5,
        1
    ), (
        1,
        6,
        1
    ), (
        6,
        6,
        3
    ), (
        2,
        6,
        1
    ), (
        3,
        7,
        1
    ), (
        4,
        7,
        2
    ), (
        3,
        1,
        1
    ), (
        4,
        1,
        2
    ), (
        5,
        1,
        3
    ), (
        1,
        2,
        1
    ), (
        3,
        2,
        1
    ), (
        2,
        3,
        1
    ), (
        2,
        4,
        2
    ), (
        7,
        4,
        2
    ), (
        8,
        5,
        1
    ), (
        6,
        5,
        1
    ), (
        1,
        5,
        4
    ), (
        3,
        5,
        1
    ), (
        1,
        6,
        1
    ), (
        6,
        6,
        3
    ), (
        2,
        6,
        1
    ), (
        3,
        7,
        1
    ), (
        4,
        7,
        2
    ), (
        3,
        1,
        1
    ), (
        4,
        1,
        2
    ), (
        5,
        1,
        3
    ), (
        1,
        2,
        1
    ), (
        3,
        2,
        1
    ), (
        2,
        3,
        1
    ), (
        2,
        4,
        2
    ), (
        7,
        4,
        2
    ), (
        8,
        5,
        1
    ), (
        6,
        5,
        1
    ), (
        1,
        5,
        4
    ), (
        3,
        5,
        1
    ), (
        1,
        6,
        1
    ), (
        6,
        6,
        3
    ), (
        2,
        6,
        1
    ), (
        3,
        7,
        1
    ), (
        4,
        7,
        2
    ), (
        3,
        8,
        1
    ), (
        4,
        8,
        2
    ), (
        5,
        8,
        3
    ), (
        1,
        8,
        1
    ), (
        3,
        8,
        1
    ), (
        2,
        9,
        1
    ), (
        2,
        9,
        2
    ), (
        17,
        10,
        1
    ), (
        5,
        11,
        1
    ), (
        15,
        11,
        1
    ), (
        10,
        11,
        1
    ), (
        13,
        12,
        1
    ), (
        1,
        12,
        2
    ), (
        13,
        13,
        2
    ), (
        12,
        14,
        1
    ), (
        11,
        14,
        1
    ), (
        18,
        14,
        2
    ), (
        3,
        14,
        1
    ), (
        14,
        15,
        2
    ), (
        15,
        15,
        3
    ), (
        11,
        15,
        1
    ), (
        13,
        15,
        1
    ), (
        5,
        15,
        4
    ), (
        16,
        15,
        2
    ), (
        7,
        15,
        2
    ), (
        8,
        16,
        3
    ), (
        14,
        16,
        1
    ), (
        13,
        16,
        2
    ), (
        17,
        17,
        1
    ), (
        16,
        18,
        1
    ), (
        6,
        19,
        1
    ), (
        2,
        19,
        1
    ), (
        6,
        19,
        1
    ), (
        4,
        20,
        1
    ), (
        8,
        20,
        1
    ), (
        14,
        21,
        1
    ), (
        19,
        21,
        1
    ), (
        9,
        21,
        2
    ), (
        2,
        22,
        1
    ), (
        12,
        23,
        1
    ), (
        8,
        23,
        3
    ), (
        18,
        23,
        1
    ), (
        12,
        24,
        5
    ), (
        15,
        24,
        2
    ), (
        3,
        24,
        2
    ), (
        3,
        25,
        1
    ), (
        6,
        26,
        1
    ), (
        14,
        26,
        1
    ), (
        9,
        26,
        1
    );
 -- Aktuellen Preis setzen
    UPDATE PRODUCT P INNER JOIN PRODUCT_ORDER_ENTRY PB ON P.ID = PB.FK_PRODUCT_ID
    SET
        PB.PRICE = P.PRICE;
 -- Transaktion abschliessen
    COMMIT;