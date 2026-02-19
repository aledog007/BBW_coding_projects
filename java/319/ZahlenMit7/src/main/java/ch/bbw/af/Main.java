package ch.bbw.af;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Zahlen mit 7:");
            System.out.println(ZahlenMit7());

    }
    public static List<String> ZahlenMit7() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            String numberAsAString = Integer.toString(i);
            if (numberAsAString.contains("7")) {
                list.add(numberAsAString);
            }
        }
        return list;
    }
}