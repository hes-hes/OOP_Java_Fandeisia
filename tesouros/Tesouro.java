package pt.ulusofona.lp2.fandeisiaGame;

public  class Tesouro extends Creature{

    public Tesouro(){
    }

    public Tesouro(int id, String type, int x, int y){
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setImagePNG(){
        if(type.equals("gold")){
            this.imagePNG = "gold-box.png";
        }
        else if(type.equals("bronze")){
            this.imagePNG = "bronze-box.png";
        }
        else if(type.equals("silver")){
            this.imagePNG = "silver-box.png";
        }
    }

}
