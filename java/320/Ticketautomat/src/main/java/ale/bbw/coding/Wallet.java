package ale.bbw.coding;

import java.util.ArrayList;
import java.util.List;

public class Wallet {

    double balance = 100.50;
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean legalValue(int value) {
        List<Integer> money = new ArrayList<>();
        money.add(1);
        money.add(2);
        money.add(5);
        money.add(10);
        money.add(20);
        money.add(50);
        money.add(100);
        money.add(200);
        money.add(1000);

        return money.contains(value);
    }
}
