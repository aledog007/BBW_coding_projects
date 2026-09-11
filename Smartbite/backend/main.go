package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"sync"
	"time"
)

// --- Models ---

type Product struct {
	ID                      string `json:"id"`
	ProductName             string `json:"productName"`
	EstimatedExpirationDate string `json:"estimatedExpirationDate"`
}

type Recipe struct {
	Title            string   `json:"title"`
	SavedIngredients []string `json:"savedIngredients"`
	Steps            []string `json:"steps"`
}

// --- In-Memory Store ---

var (
	inventory = make(map[string]Product)
	invMu     sync.RWMutex
	nextID    = 1
)

func generateID() string {
	id := fmt.Sprintf("%d", nextID)
	nextID++
	return id
}

// --- Handlers ---

func corsMiddleware(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("Access-Control-Allow-Origin", "*")
		w.Header().Set("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS")
		w.Header().Set("Access-Control-Allow-Headers", "Content-Type")

		if r.Method == "OPTIONS" {
			w.WriteHeader(http.StatusOK)
			return
		}

		next.ServeHTTP(w, r)
	})
}

// GET /api/inventory
func getInventory(w http.ResponseWriter, r *http.Request) {
	invMu.RLock()
	defer invMu.RUnlock()

	var products []Product
	for _, p := range inventory {
		products = append(products, p)
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(products)
}

// POST /api/inventory (for manually adding or testing)
func addInventory(w http.ResponseWriter, r *http.Request) {
	var p Product
	if err := json.NewDecoder(r.Body).Decode(&p); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	invMu.Lock()
	if p.ID == "" {
		p.ID = generateID()
	}
	inventory[p.ID] = p
	invMu.Unlock()

	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(http.StatusCreated)
	json.NewEncoder(w).Encode(p)
}

// DELETE /api/inventory/{id} - IoT Sensor Simulation
func deleteInventory(w http.ResponseWriter, r *http.Request) {
	// Extract ID from path
	id := r.URL.Path[len("/api/inventory/"):]
	if id == "" {
		http.Error(w, "ID required", http.StatusBadRequest)
		return
	}

	invMu.Lock()
	_, exists := inventory[id]
	if exists {
		delete(inventory, id)
	}
	invMu.Unlock()

	if !exists {
		http.Error(w, "Product not found", http.StatusNotFound)
		return
	}

	// Mock n8n Webhook
	log.Printf("[MOCK] Sending Webhook to n8n... Product %s removed.", id)

	w.WriteHeader(http.StatusOK)
}

// POST /api/vision/scan - Vision AI Simulation
func scanReceipt(w http.ResponseWriter, r *http.Request) {
	log.Println("[MOCK] Vision AI scan triggered. Simulating 2000ms delay...")
	time.Sleep(2000 * time.Millisecond)

	mockProducts := []Product{
		{ProductName: "Bio Pouletbrust", EstimatedExpirationDate: "2026-06-20"},
		{ProductName: "Vollmilch 3.5%", EstimatedExpirationDate: "2026-06-25"},
		{ProductName: "Broccoli", EstimatedExpirationDate: "2026-06-23"},
	}

	// Auto-add to inventory for demonstration purposes
	invMu.Lock()
	var added []Product
	for _, p := range mockProducts {
		p.ID = generateID()
		inventory[p.ID] = p
		added = append(added, p)
	}
	invMu.Unlock()

	log.Println("[MOCK] Vision AI scan complete.")
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(added)
}

// GET /api/recipe/suggest - Recipe Engine Simulation
func suggestRecipe(w http.ResponseWriter, r *http.Request) {
	log.Println("[MOCK] Recipe Engine triggered.")
	
	mockRecipe := Recipe{
		Title:            "Cremige Poulet-Broccoli-Pfanne",
		SavedIngredients: []string{"Bio Pouletbrust", "Broccoli", "Vollmilch 3.5%"},
		Steps: []string{
			"Schneide die Pouletbrust in Streifen und brate sie kurz an.",
			"Gib den Broccoli in kleinen Röschen dazu.",
			"Mit der Vollmilch ablöschen, würzen und 5 Minuten zu einer cremigen Sauce einköcheln lassen.",
		},
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(mockRecipe)
}

func main() {
	mux := http.NewServeMux()

	mux.HandleFunc("GET /api/inventory", getInventory)
	mux.HandleFunc("POST /api/inventory", addInventory)
	mux.HandleFunc("DELETE /api/inventory/", deleteInventory) // Handles /api/inventory/{id}
	
	mux.HandleFunc("POST /api/vision/scan", scanReceipt)
	mux.HandleFunc("GET /api/recipe/suggest", suggestRecipe)

	handler := corsMiddleware(mux)

	port := ":8080"
	log.Printf("Smartbite Mock-Engine Backend running on http://localhost%s", port)
	log.Fatal(http.ListenAndServe(port, handler))
}
