package ale.bbw.coding;

public class SurfBoard {

    private Leash leash;
    private Fin fin;

    public SurfBoard(Leash leash) {
        this.leash = leash;
        fin = new Fin();
    }

    public class LongBoard extends SurfBoard {

    }

    public class ShortBoard extends SurfBoard {

    }

    public class Malibu extends SurfBoard {}
}