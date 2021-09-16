package pt.ulusofona.lp2.fandeisiaGame;

public class Anao extends Creature {

    public Anao(int id, String type, int teamId, int x, int y, String orientation) {
        this.id = id;
        this.type = type;
        this.teamId = teamId;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.valor = 1;
        this.alcance = 1;
    }

    public Anao(int x, int y) {
        this.x = x;
        this.y = y;
        this.alcance = 1;
    }

    public Anao(){
        this.alcance = 1;
    }

    @Override
    public int getAlcance() {
        return alcance;
    }

    @Override
    public void resetAlcance(){
        alcance = 1;
    }

}
