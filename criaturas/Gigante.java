package pt.ulusofona.lp2.fandeisiaGame;

public class Gigante extends Creature {

    public Gigante (int id, String type, int teamId, int x, int y, String orientation) {
        this.id = id;
        this.type = type;
        this.teamId = teamId;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.valor = 5;
        this.alcance = 3;
    }

    public Gigante (int x, int y) {
        this.x = x;
        this.y = y;
        this.alcance = 3;
    }

    public Gigante(){
        this.alcance = 3;
    }

    @Override
    public int getAlcance() {
        return alcance;
    }

    @Override
    public void resetAlcance(){
        alcance = 3;
    }

}
