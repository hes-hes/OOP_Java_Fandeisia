package pt.ulusofona.lp2.fandeisiaGame;

public class Elfo extends Creature {

    public Elfo (int id, String type, int teamId, int x, int y, String orientation) {
        this.id = id;
        this.type = type;
        this.teamId = teamId;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.valor = 5;
        this.alcance = 2;
    }

    public Elfo (int x, int y) {
        this.x = x;
        this.y = y;
        this.alcance = 2;
    }

    public Elfo(){
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
