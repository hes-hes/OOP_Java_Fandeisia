package pt.ulusofona.lp2.fandeisiaGame;

public  class TesouroBronze extends Tesouro {

    public TesouroBronze(){
        this.valor = 1;
    }

    public TesouroBronze (int id, String type, int x, int y){
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.valor = 1;
    }

}
