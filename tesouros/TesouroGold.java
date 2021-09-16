package pt.ulusofona.lp2.fandeisiaGame;

public  class TesouroGold extends Tesouro {

    TesouroGold(){
        this.valor = 3;
    }

    public TesouroGold (int id, String type, int x, int y){
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.valor = 3;
    }
}
