package pt.ulusofona.lp2.fandeisiaGame;

public class Humano extends Creature {

    public Humano (int id, String type, int teamId, int x, int y, String orientation) {
        this.id = id;
        this.type = type;
        this.teamId = teamId;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.valor = 3;
        this.alcance = 2;
    }

    public Humano (int x, int y) {
        this.x = x;
        this.y = y;
        this.alcance = 2;
    }

    public Humano(){
        this.alcance = 2;
    }

    @Override
    public int getAlcance() {
        return alcance;
    }

    @Override
    public void resetAlcance(){
        alcance = 2;
    }

}
