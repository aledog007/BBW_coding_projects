package ale.bbw.coding;
// Ich benutze das Abstract um zu deklalieren das sie es gibt und MUSS sie in den spezifikationen (Objekt) benutzen
public abstract class Form {
        // Abstrakte Methode für Fläche
        public abstract double getFlaeche();

        // Abstrakte Methode für Umfang
        public abstract double getUmfang();

        // Konkrete Methode für Position
        public String getPosition() {
            return "Position ist noch nicht implementiert.";
        }

}
