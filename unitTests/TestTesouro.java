package pt.ulusofona.lp2.fandeisiaGame;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestTesouro {

    @Test
    public void setImagePNG(){
        Tesouro t = new Tesouro(0, "gold", 2, 3);
        Tesouro t1 = new Tesouro(1, "silver", 2, 3);
        Tesouro t2 = new Tesouro(2, "bronze", 2, 3);

        t.setImagePNG();
        t1.setImagePNG();
        t2.setImagePNG();

        assertEquals("gold-box.png", t.getImagePNG());
        assertEquals("silver-box.png", t1.getImagePNG());
        assertEquals("bronze-box.png", t2.getImagePNG());
    }

    @Test
    public void valores(){
        TesouroGold t1 = new TesouroGold();
        TesouroSilver t2 = new TesouroSilver();
        TesouroBronze t3 = new TesouroBronze();

        assertEquals(3, t1.getValor());
        assertEquals(2, t2.getValor());
        assertEquals(1, t3.getValor());
    }
}
