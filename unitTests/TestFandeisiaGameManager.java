package pt.ulusofona.lp2.fandeisiaGame;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class TestFandeisiaGameManager {

    @Test
    public void testCreateComputerArmy() {
        FandeisiaGameManager f = new FandeisiaGameManager();
        Map<String, Integer> map;
        Map<String, Integer> map2;
        Map<String, Integer> map3;
        Map<String, Integer> map4;
        Map<String, Integer> map5;

        map = f.createComputerArmy();
        map2 = f.createComputerArmy();
        map3 = f.createComputerArmy();
        map4 = f.createComputerArmy();
        map5 = f.createComputerArmy();

        boolean vazio = false;

        assertEquals(vazio, map.isEmpty());
        assertEquals(vazio, map2.isEmpty());
        assertEquals(vazio, map3.isEmpty());
        assertEquals(vazio, map4.isEmpty());
        assertEquals(vazio, map5.isEmpty());
    }

    @Test
    public void testGetResultsEmpate() {
        FandeisiaGameManager f = new FandeisiaGameManager();
        f.setPontosLdr(2);
        f.setPontosResistencia(2);
        List<String> res = f.getResults();
        String empate = "Resultado: EMPATE";
        assertEquals(empate, res.get(1));
    }

    @Test
    public void testGetResultsVitoriaLdr() {
        FandeisiaGameManager f = new FandeisiaGameManager();
        f.setPontosLdr(2);
        f.setPontosResistencia(0);
        List<String> res = f.getResults();
        String vitoriaLdr = "Resultado: Vitória da equipa LDR";
        assertEquals(vitoriaLdr, res.get(1));
    }

    @Test
    public void testGetResultsVitoriaResistencia() {
        FandeisiaGameManager f = new FandeisiaGameManager();
        f.setPontosLdr(0);
        f.setPontosResistencia(2);
        List<String> res = f.getResults();
        String vitoriaRes = "Resultado: Vitória da equipa RESISTENCIA";
        assertEquals(vitoriaRes, res.get(1));
    }

    @Test
    public void testGetCurrentScore() {
        FandeisiaGameManager f = new FandeisiaGameManager();
        f.setPontosLdr(10);
        f.setPontosResistencia(32);
        assertEquals(f.getPontosLdr(), f.getCurrentScore(10));
        assertEquals(f.getPontosResistencia(), f.getCurrentScore(20));
    }

    @Test
    public void testGetCreatures() throws InsufficientCoinsException {
        FandeisiaGameManager f = new FandeisiaGameManager();
        String[] content = {"id: -4, type: gold, x: 2, y: 1"};
        f.startGame(content, 10, 12);
        List<Creature> list = f.getCreatures();
        assertEquals(0, list.size());
    }

    @Test
    public void testAlcances() {
        Anao a = new Anao();
        Elfo e = new Elfo();
        Dragao d = new Dragao();
        Humano h = new Humano();
        AngryBird aB = new AngryBird();
        Gigante g = new Gigante();

        assertEquals(1, a.getAlcance());
        assertEquals(2, e.getAlcance());
        assertEquals(3, d.getAlcance());
        assertEquals(2, h.getAlcance());
        assertEquals(1, aB.getAlcance());
        assertEquals(3, g.getAlcance());
    }

    @Test
    public void testDuplicaAlcance() {
        FandeisiaGameManager f = new FandeisiaGameManager();
        Anao a = new Anao(3, 3);
        a.setAlcance(a.getAlcance());
        assertEquals(2, a.getAlcance());
        a.moveNorte();
        assertEquals(f.getElementId(3, 1), a.getId());
    }

    @Test
    public void testGetCoins() {
        FandeisiaGameManager f = new FandeisiaGameManager();
        f.processTurn();
        f.processTurn();
        int ldrCoins = 52;
        assertEquals(ldrCoins, f.getCoinTotal(10));
    }

    @Test
    public void getAuthors() {
        FandeisiaGameManager f = new FandeisiaGameManager();
        List<String> authors = f.getAuthors();
        assertEquals(2, authors.size());
    }

    @Test
    public void testSetInitialTeamId() {
        FandeisiaGameManager f = new FandeisiaGameManager();
        f.setInitialTeam(20);
        assertEquals(20, f.getCurrentTeamId());
    }

    @Test
    public void testTurnCount() throws InsufficientCoinsException {
        FandeisiaGameManager f = new FandeisiaGameManager();
        f.setTurnCounts(14);
        f.processTurn();
        int turnCountEsperado = 15;
        int turnCountObtido = f.getTurnCounts();
        assertEquals(turnCountEsperado, turnCountObtido);
    }

    @Test
    public void processGameOverTurn() throws InsufficientCoinsException {
        FandeisiaGameManager f = new FandeisiaGameManager();
        f.setTurnCounts(14);
        f.processTurn();
        boolean gameOverEsperado = true;
        boolean gameOverObtido = f.gameIsOver();
        assertEquals(gameOverEsperado, gameOverObtido);
    }

    @Test
    public void processGetCurrentTeam() throws InsufficientCoinsException {
        FandeisiaGameManager f = new FandeisiaGameManager();
        f.setCurrentTeamId(20);
        f.processTurn();
        int currentTeamIdEsperado = 10;
        int currentTeamIdObtido = f.getCurrentTeamId();
        assertEquals(currentTeamIdEsperado, currentTeamIdObtido);
    }

    @Test
    public void testAnaoCapturaTesouro() throws InsufficientCoinsException {
        FandeisiaGameManager f = new FandeisiaGameManager();
        String[] content = {"id: 1, type: Anão, teamId: 10, x: 1, y: 1, orientation: Este", "id: -4, type: gold, x: 2, y: 1"};
        f.startGame(content, 10, 12);
        f.processTurn();
        int idAnao = 1;
        assertEquals(idAnao, f.getElementId(2, 1));
    }

    @Test
    public void testAnaoFronteira() throws InsufficientCoinsException {
        FandeisiaGameManager f = new FandeisiaGameManager();
        String[] content = {"id: 1, type: Anão, teamId: 10, x: 0, y: 0, orientation: Norte"};
        f.startGame(content, 10, 12);
        f.processTurn();
        int idAnao = 1;
        assertEquals(idAnao, f.getElementId(0, 0));
    }

    @Test
    public void testAnaoFeitico() throws InsufficientCoinsException {
        FandeisiaGameManager f = new FandeisiaGameManager();
        String[] content = {"id: 1, type: Anão, teamId: 10, x: 0, y: 0, orientation: Norte"};
        f.startGame(content, 10, 12);
        f.enchant(0, 0, "EmpurraParaSul");
        //String spell = f.getSpell(0,0);
        //f.processTurn();
        assertEquals("EmpurraParaSul", f.getSpell(0, 0));
    }

    @Test
    public void testElfoNormal() throws InsufficientCoinsException {
        FandeisiaGameManager f = new FandeisiaGameManager();
        String[] content = {"id: 1, type: Elfo, teamId: 10, x: 3, y: 3, orientation: Nordeste", "id: 2, type: Elfo, teamId: 10, x: 3, y: 2, orientation: Sudoeste"};
        f.startGame(content, 10, 12);
        f.processTurn();
        int id33 = 1;
        int id32 = 2;
        assertEquals(id33, f.getElementId(5, 1));
        assertEquals(id32, f.getElementId(1, 4));
    }

    @Test
    public void testElfoComCriatura() throws InsufficientCoinsException {
        FandeisiaGameManager f = new FandeisiaGameManager();
        String[] content = {"id: 1, type: Elfo, teamId: 10, x: 3, y: 3, orientation: Nordeste", "id: 2, type: Elfo, teamId: 10, x: 5, y: 1, orientation: Sudoeste"};
        f.startGame(content, 10, 12);
        f.processTurn();
        int id33 = 1;
        assertEquals(id33, f.getElementId(3, 3));
    }

    @Test
    public void testElfoComBuraco() throws InsufficientCoinsException {
        FandeisiaGameManager f = new FandeisiaGameManager();
        String[] content = {"id: 1, type: Elfo, teamId: 10, x: 3, y: 3, orientation: Nordeste", "id: -501, type: hole, x: 4, y: 2"};
        f.startGame(content, 10, 12);
        f.processTurn();
        int id33 = 1;
        assertEquals(id33, f.getElementId(5, 1));
    }

    @Test
    public void testGiganteComBuraco() throws InsufficientCoinsException {
        FandeisiaGameManager f = new FandeisiaGameManager();
        String[] content = {"id: 1, type: Gigante, teamId: 10, x: 3, y: 3, orientation: Norte", "id: -501, type: hole, x: 3, y: 0"};
        f.startGame(content, 10, 12);
        f.processTurn();
        int id33 = 1;
        assertEquals(id33, f.getElementId(3, 3));
    }

    @Test
    public void testGiganteComGigante() throws InsufficientCoinsException {
        FandeisiaGameManager f = new FandeisiaGameManager();
        String[] content = {"id: 1, type: Gigante, teamId: 10, x: 3, y: 3, orientation: Norte", "id: 2, type: Gigante, teamId: 20, x: 3, y: 2, orientation: Este"};
        f.startGame(content, 10, 12);
        f.processTurn();
        int id33 = 1;
        assertEquals(id33, f.getElementId(3, 3));
    }

    @Test
    public void testDragaoComBuraco() throws InsufficientCoinsException {
        FandeisiaGameManager f = new FandeisiaGameManager();
        String[] content = {"id: 1, type: Dragão, teamId: 10, x: 3, y: 3, orientation: Nordeste", "id: -501, type: hole, x: 5, y: 1"};
        f.startGame(content, 10, 12);
        f.processTurn();
        int id33 = 1;
        assertEquals(id33, f.getElementId(6, 0));
    }

    @Test
    public void testAngryBirdCapturaTesouro() throws InsufficientCoinsException {
        FandeisiaGameManager f = new FandeisiaGameManager();
        String[] content = {"id: 1, type: Angry Bird, teamId: 20, x: 3, y: 3, orientation: Sul", "id: -4, type: gold, x: 2, y: 4"};
        f.startGame(content, 10, 12);
        f.processTurn();
        int idABird = 1;
        assertEquals(idABird, f.getElementId(2, 4));
    }

    @Test
    public void testFeit() throws InsufficientCoinsException {
        String[] conteudo = new String[2];
        conteudo[0] = "id: 1, type: Anão, teamId: 20, x: 0, y: 0, orientation: Sul";
        conteudo[1] = "id: 1, type: Dragão, teamId: 10, x: 3, y: 3, orientation: Nordeste";
        FandeisiaGameManager f = new FandeisiaGameManager();
        f.startGame(conteudo, 9, 11);
        assertEquals(true, f.enchant(0, 0, "DuplicaAlcance"));
    }
}
