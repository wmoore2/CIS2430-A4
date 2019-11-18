package dungeon;

public final class Main {
    /**
    * for javadoc.
    **/
    private Main() {

    }

    /**
    * this is a main.
    *   @param args idk this is for main lmao
    **/
    public static void main(String[] args) {
        DungeonGenerator level = new DungeonGenerator();
        level.buildDungeon();
        System.out.println(level.getDescription());
    }
    //  public static void main(String[] args) {
    //     Chamber chamber = new Chamber();
    //     for(int i = 0; i < 20; i++){
    //         chamber = new Chamber();
    //         //chamber.buildRandomChamber();
    //         System.out.println(chamber.getDescription());
    //     }
    // }
}
