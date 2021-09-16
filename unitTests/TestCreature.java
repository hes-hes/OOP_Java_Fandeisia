package pt.ulusofona.lp2.fandeisiaGame;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCreature {
    @Test
    public void testMoveNorte() {
        Elfo e = new Elfo(3,3);
        Dragao d = new Dragao(5,4);
        e.moveNorte();
        d.moveNorte();
        assertEquals(1,e.getY());
        assertEquals(1,d.getY());
    }

    @Test
    public void testMoveSul() {
        Humano h = new Humano(4,6);
        Dragao d = new Dragao(5,4);
        h.moveSul();
        d.moveSul();
        assertEquals(7,d.getY());
        assertEquals(8,h.getY());
    }

    @Test
    public void testMoveEste() {
        Anao a = new Anao(3,1);
        Dragao d = new Dragao(5,5);
        a.moveEste();
        d.moveEste();
        assertEquals(8,d.getX());
        assertEquals(4,a.getX());
    }

    @Test
    public void testMoveOeste() {
        Anao a = new Anao(3,1);
        Dragao d = new Dragao(5,5);
        a.moveOeste();
        d.moveOeste();
        assertEquals(2,d.getX());
        assertEquals(2,a.getX());
    }

    @Test
    public void testMoveNO() {
        Dragao d = new Dragao(5,5);
        d.moveNO();
        assertEquals(2,d.getY());
        assertEquals(2,d.getX());
    }

    @Test
    public void testMoveSO() {
        Elfo e = new Elfo(3,3);
        e.moveSO();
        assertEquals(5,e.getY());
        assertEquals(1,e.getX());
    }

    @Test
    public void testMoveNE() {
        AngryBird a = new AngryBird(5,7);
        a.moveNE();
        assertEquals(6,a.getY());
        assertEquals(6,a.getX());
    }

    @Test
    public void testMoveSE() {
        AngryBird a = new AngryBird(5,4);
        a.moveSE();
        assertEquals(5,a.getY());
        assertEquals(6,a.getX());
    }
}
