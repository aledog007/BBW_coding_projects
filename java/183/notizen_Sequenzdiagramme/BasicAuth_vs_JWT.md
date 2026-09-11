# Basic-Authentication versus JWT

Dieses Dokument erklärt den Unterschied zwischen klassischer Basic-Authentication und JWT (JSON Web Token) und stellt beide Ansätze in einem Sequenzdiagramm dar.

## 1. Basic-Authentication
Bei der Basic-Authentication müssen der Benutzername und das Passwort bei **jedem einzelnen Request** mitgeschickt werden (meist Base64-codiert). Das ist unsicher, ineffizient und skaliert schlecht.

```mermaid
sequenceDiagram
    participant Client as React Frontend
    participant Server as Spring Boot Backend
    
    Note over Client,Server: JEDER Request enthält das Passwort!
    
    Client->>Server: GET /api/secrets (Auth: Basic bXl1c2VyOm15cGFzcw==)
    Server->>Server: Prüft Username & Passwort gegen DB
    Server-->>Client: 200 OK (Daten)
    
    Client->>Server: POST /api/secrets (Auth: Basic bXl1c2VyOm15cGFzcw==)
    Server->>Server: Prüft Username & Passwort gegen DB
    Server-->>Client: 200 OK (Erfolgreich erstellt)
```

## 2. JWT (JSON Web Token) Authentication
Hier werden die Credentials (E-Mail & Passwort) nur **ein einziges Mal** beim initialen Login gesendet. Der Server stellt einen kryptografisch signierten Ausweis (JWT) aus. Alle weiteren Anfragen verwenden nur noch dieses Token.

```mermaid
sequenceDiagram
    participant Client as React Frontend
    participant Server as Spring Boot Backend
    
    Note over Client,Server: Schritt 1: Einmaliger Login
    Client->>Server: POST /api/auth/login (email, passwort)
    Server->>Server: Passwort validieren (BCrypt)
    Server-->>Client: 200 OK (AccessToken, RefreshToken)
    
    Note over Client,Server: Schritt 2: Stateless Requests mit Token
    Client->>Server: GET /api/secrets (Auth: Bearer <AccessToken>)
    Server->>Server: Signatur des Tokens validieren (ohne DB-Abfrage)
    Server-->>Client: 200 OK (Daten)
```