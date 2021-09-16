package pt.ulusofona.lp2.fandeisiaGame;

public  class Buraco extends Creature{

    public Buraco(){
        this.imagePNG = "black-box.png";
    }

    public Buraco (int id, String type, int x, int y){
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.imagePNG = "black-box.png";
    }

    public int getId() {
        return id;
    }


}
