package ale.bbw;

public class MorseTree {
    private Node root;

    public Node getRoot() {
        return root;
    }

    public void addLetter(char letter, String code) {
        if (root == null) {
            root = new Node();
            root.code = "root";
        }
        addRecursive(root, letter, code, code);
    }

    private void addRecursive(Node current, char letter, String code, String codepart) {
        if (codepart.length() == 0) {
            // codepart ist fertig ausgelesen und ich bin der Knoten
            current.letter = letter;
            current.code = code;
        } else if (codepart.charAt(0) == '.') {
            //weiter mit dem dot-knoten
            if (current.dot == null) current.dot = new Node();
            addRecursive(current.dot, letter, code, codepart.substring(1));
        } else if (codepart.charAt(0) == '-') {
            //weiter mit dem dash-knoten
            if (current.dash == null) current.dash = new Node();
            addRecursive(current.dash, letter, code, codepart.substring(1));
        } else {
            //unbekanntes Zeichen, oder Trennzeichen
            addRecursive(current, letter, code, codepart.substring(1));
        }
    }

    public String decode(String code) {
        StringBuilder retVal = new StringBuilder();
        for (String part : code.split("/")) {
            // Wir rufen die öffentliche Methode ohne 'root' Parameter auf
            retVal.append(decodeLetter(part)).append(" ");
        }
        return retVal.toString();
    }

    // Korrigiert: Diese Methode darf sich nicht selbst mit 'this.root' aufrufen (Endlosschleife)
    public Character decodeLetter(String code) {
        if (this.root == null) {
            return ' ';
        }
        return findRecursive(this.root, code);
    }

    // Hilfsmethode umbenannt, um Verwechslung mit der öffentlichen Methode zu vermeiden
    private Character findRecursive(Node current, String code) {
        if (code.length() == 0) {
            return current.letter;
        } else if (code.charAt(0) == '.') {
            if (current.dot == null) return null;
            return findRecursive(current.dot, code.substring(1));
        } else if (code.charAt(0) == '-') {
            if (current.dash == null) return null;
            return findRecursive(current.dash, code.substring(1));
        } else {
            //unbekanntes Zeichen, oder Trennzeichen
            return findRecursive(current, code.substring(1));
        }
    }

    public void traverse(Node node) {
        if (node != null) {
            System.out.println(node.letter + " : " + node.code);
            traverse(node.dot);
            traverse(node.dash);
        }
    }
}