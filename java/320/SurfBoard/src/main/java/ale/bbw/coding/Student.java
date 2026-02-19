package ale.bbw.coding;

public class Student extends Person implements Surf {
    private SurfBoard surfboard;
    public Student(SurfBoard surfboard) {
        this.surfboard = surfboard;
    }
    @Override
    public void carryBoard() {

    }
    @Override
    public void ride() {
    }
    @Override
    public void standUp() {
    }
}