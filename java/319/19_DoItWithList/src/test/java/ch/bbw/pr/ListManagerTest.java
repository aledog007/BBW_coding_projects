package ch.bbw.pr;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListManagerTest {
   @Test
   void aufgabe1Test() {
      ListManager manager = new ListManager();
      List<Integer> expected = List.of(1, 5, 9, 12, 4);
      System.out.println("ListManagerTest.aufgabe1 expected: " + expected);

      List<Integer> result = manager.Aufgabe1(1, 5, 9, 11, 4);
      System.out.println("ListManagerTest.aufgabe1 result: " + result);

      assertTrue(result.equals(expected));
   }
}