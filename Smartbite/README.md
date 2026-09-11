# Smartbite (Mock-Engine MVP)

Dieses Projekt ist der Mock-Engine Prototyp für das autonome Zero-Waste-Küchenökosystem "Smartbite". Es simuliert die KI-Komponenten (Vision AI, Recipe Engine) und Hardware-Sensoren (IoT Auto-Drop) für die Klassenpräsentation ohne externe API-Kosten oder Latenzen.

## Projektstruktur

- `/backend`: Ein extrem leichtgewichtiges Go-Backend (Standardbibliothek), das die Mock-APIs bereitstellt.
- `/frontend`: Eine moderne React-Applikation (mit Vite und TailwindCSS), die das Dashboard, den Scanner und eine simulierte IoT-Entwicklerkonsole beinhaltet.

---

## 🚀 Startanleitung für die Präsentation

Um die Anwendung lokal zu starten, benötigst du zwei separate Terminal-Fenster.

### 1. Das Go-Backend starten

Öffne ein Terminal und wechsle in den Ordner `backend` deines Projekts. Starte dann den Server:

```bash
cd backend
go run main.go
```

> **Wichtig:** Lass dieses Terminal während der Präsentation offen (oder auf einem halben Bildschirm sichtbar)! Der Server läuft auf `http://localhost:8080`. Hier werden die simulierten n8n-Webhook-Aufrufe (`[MOCK] Sending Webhook to n8n...`) in Echtzeit geloggt, wenn du Produkte löschst.

### 2. Das React-Frontend starten

Öffne ein **zweites** Terminal und wechsle in den Ordner `frontend`. Starte den Vite-Entwicklungsserver:

```bash
cd frontend
npm run dev
```

> **Wichtig:** Das Frontend läuft in der Regel auf `http://localhost:5173` (die genaue URL steht im Terminal). Öffne diesen Link in deinem Webbrowser (z.B. Chrome, Safari), um die Smartbite App zu nutzen.

---

## 🧪 Empfohlener Ablauf der Demo

1. **Einstieg**: Zeige den eleganten Startbildschirm ("Home") und klicke auf "Enter Dashboard".
2. **Vision AI Simulation**: Klicke im linken Scanner-Bereich auf das leere Feld, lade ein Foto einer Quittung hoch und drücke **"Process Image"**. Die UI zeigt eine animierte Scan-Linie für 2 Sekunden (die simulierte Verarbeitungszeit). Danach tauchen 3 Mock-Produkte (Poulet, Broccoli, Vollmilch) im Inventar auf.
3. **Recipe Engine (Smart Coach)**: Klicke links auf **"Suggest Recipe"**. Die Mock-Engine berechnet aus den eingescannten Produkten sofort ein Rezept ("Cremige Poulet-Broccoli-Pfanne") und listet die Zubereitungsschritte auf.
4. **IoT Auto-Drop & n8n**: Navigiere zur "Dev_Console // IOT" unten rechts. Klicke bei einem Produkt auf **"Force Drop"**. Das Produkt verschwindet sofort aus dem Inventar. 
   *(Zeige jetzt dem Publikum dein erstes Terminal (Backend): Dort steht nun geloggt, dass soeben der n8n Webhook angetriggert wurde!)*

Viel Erfolg bei der Präsentation!
