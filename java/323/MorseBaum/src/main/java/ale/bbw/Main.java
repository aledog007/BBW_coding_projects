package ale.bbw;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        System.out.println("Morse-Tree");
        MorseTree tree = new MorseTree();

        System.out.println("Adding all letters to the tree");
        tree.addLetter('A', ".-");
        tree.addLetter('B', "-...");
        tree.addLetter('C', "-.-.");
        tree.addLetter('D', "-..");
        tree.addLetter('E', ".");
        tree.addLetter('F', "..-.");
        tree.addLetter('G', "--.");
        tree.addLetter('H', "....");
        tree.addLetter('I', "..");
        tree.addLetter('J', ".---");
        tree.addLetter('K', "-.-");
        tree.addLetter('L', ".-..");
        tree.addLetter('M', "--");
        tree.addLetter('N', "-.");
        tree.addLetter('O', "---");
        tree.addLetter('P', ".--.");
        tree.addLetter('Q', "--.-");
        tree.addLetter('R', ".-.");
        tree.addLetter('S', "...");
        tree.addLetter('T', "-");
        tree.addLetter('U', "..-");
        tree.addLetter('V', "...-");
        tree.addLetter('W', ".--");
        tree.addLetter('X', "-..-");
        tree.addLetter('Y', "-.--");
        tree.addLetter('Z', "--..");

        System.out.println();
        System.out.println("The tree is: ");
        tree.traverse(tree.getRoot());
        }
}
