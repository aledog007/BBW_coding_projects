# Aufgaben 
1. neuen Datensatz einfugen 
    - Befehl 
    ```
    INSERT INTO <table_name> (<atribut_name>, <atribut_name>, ...) VALUES 
    (<value1>, <value2>, <value3>),
    (<value1>, <value2>, <value3>);
    ```

2. Datensatz Löschen 
    - Befehl 
    ```
    BEGIN;
    DELETE FROM <table_name> WHERE<atribut_name>=<value>;
    COMMIT;
    ``` 

3. 
Single choice besonders auf kleinigkeiten achten.

4. 
Single choice besonders auf kleinigkeiten achten.

5. 
Single choice besonders auf kleinigkeiten achten.

6. 
Single choice besonders auf kleinigkeiten achten.

7. Subquerie Film länge. Die kürzesten Filme auflisten.
    - Befehl
    ```
    SELECT * FROM *table_name* WHERE *attribut_name* = (
    SELECT MIN(*attribut_name*) FROM *table_name*
    );



