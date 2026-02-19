package main

import (
	"fmt"
	"log"
	"net/http"
)

// Handler für die Startseite
func homePage(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Willkommen auf der Startseite!")
	log.Println("Startseite aufgerufen")
}

// Handler für eine /hello Route
func helloPage(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hallo, Go-Entwickler!")
	log.Println("/hello Route aufgerufen")
}

func beginer(writer http.ResponseWriter, request *http.Request) {
	fmt.Fprintf(writer, "Golang is a really cool language!")
	log.Println("/beginer Route aufgerufen")

}

func main() {
	// Routen definieren
	http.HandleFunc("/", homePage)
	http.HandleFunc("/hello", helloPage)
	http.HandleFunc("/beginer", beginer)

	// Server starten
	fmt.Println("Server läuft auf http://localhost:8080")
	log.Fatal(http.ListenAndServe(":8080", nil))
}
