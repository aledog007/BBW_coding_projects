package ale.bbw.coding;

import java.util.ArrayList;
import java.util.Iterator;

public class Notebook {

    private ArrayList<String> notes;

    public Notebook() {
        notes = new ArrayList<>();
    }

    public void storeNote(String note) {
        notes.add(note);
    }

    public int numberofNotes() {
        return notes.size();
    }

    public void showNotes(int noteNumber) {
        if (noteNumber < 0) {

        } else if (noteNumber < numberofNotes()) {
            System.out.println(notes.get(noteNumber));
        } else {

        }

    }

    public void delteNotes(int noteNumber) {
        if (noteNumber < 0) {

        } else if (noteNumber < numberofNotes()) {
            notes.remove(noteNumber);
        } else {

        }
    }

    public void showNotesfor() {
        for (int i = 0; i < numberofNotes(); i++) {
        System.out.println(notes.get(i));
        }
    }

    public void showNotesWhile() {
        int i = 0;
        while (i < numberofNotes()) {
            System.out.println(notes.get(i));
            i++;
        }
    }

    public void showNotesForEach() {
        for (String note : notes) {
            System.out.println(note);
        }
    }

    public void showNotesDoWhile() {
        int i = 0;
        if (numberofNotes() > 0) {
            do {
                System.out.println(notes.get(i));
                i++;
            } while (i < numberofNotes());
        }
    }

    public void showNotesInterator() {
        Iterator<String> it = notes.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

}
