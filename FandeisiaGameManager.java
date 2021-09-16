package pt.ulusofona.lp2.fandeisiaGame;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class FandeisiaGameManager {

    private ArrayList<Tesouro> tesouros = new ArrayList<>();
    private ArrayList<Creature> creaturesC = new ArrayList<>();
    private ArrayList<Creature> creatures = new ArrayList<>();
    private ArrayList<Buraco> buracos = new ArrayList<>();
    private HashMap<String, Integer> computerArmy = new HashMap<>();

    private HashMap<String, List<String>> statistics = new HashMap<>();

    private ArrayList<Gigante> gigantes = new ArrayList<>();

    private ArrayList<String> results = new ArrayList<>();

    private int turnSemCaptura = 0;
    private int turnCount = 0;
    private int currentTeamId = 0;

    private int rows;
    private int columns;

    private int ldrId = 10;
    private int pontosLdr = 0;
    private int ldrCoins = 50;

    private int resistenciaId = 20;
    private int pontosResistencia = 0;
    private int resistenciaCoins = 50;

// ******************************   PARTE 2   **************************************

    public String[][] getSpellTypes() {

        String[][] spellTypes = new String[9][3];

        spellTypes[0][0] = "EmpurraParaNorte";
        spellTypes[0][1] = "Move a criatura 1 unidade para Norte.";
        spellTypes[0][2] = "1";

        spellTypes[1][0] = "EmpurraParaEste";
        spellTypes[1][1] = "Move a criatura 1 unidade para Este.";
        spellTypes[1][2] = "1";

        spellTypes[2][0] = "EmpurraParaSul";
        spellTypes[2][1] = "Move a criatura 1 unidade para Sul.";
        spellTypes[2][2] = "1";

        spellTypes[3][0] = "EmpurraParaOeste";
        spellTypes[3][1] = "Move a criatura 1 unidade para Oeste.";
        spellTypes[3][2] = "1";

        spellTypes[4][0] = "ReduzAlcance";
        spellTypes[4][1] = "Reduz o alcance da criatura para: MIN (alcance original, 1)";
        spellTypes[4][2] = "2";

        spellTypes[5][0] = "DuplicaAlcance";
        spellTypes[5][1] = "Aumenta o alcance da criatura para o dobro.";
        spellTypes[5][2] = "3";

        spellTypes[6][0] = "Congela";
        spellTypes[6][1] = "A criatura alvo não se move neste turno.";
        spellTypes[6][2] = "3";

        spellTypes[7][0] = "Congela4Ever";
        spellTypes[7][1] = "A criatura alvo não se move mais até ao final do jogo.";
        spellTypes[7][2] = "10";

        spellTypes[8][0] = "Descongela";
        spellTypes[8][1] = "Inverte a aplicação de um Feitiço Congela4Ever.";
        spellTypes[8][2] = "8";

        return spellTypes;
    }

    public Map<String, Integer> createComputerArmy() {
        Random r = new Random();
        int x = r.nextInt(12);
        switch (x) {
            case 0:
                computerArmy.put("Angry Bird", 2);

            case 1:
                computerArmy.put("Anão", 6);

            case 2:
                computerArmy.put("Anão", 3);
                computerArmy.put("Humano", 2);

            case 3:
                computerArmy.put("Dragão", 2);
                computerArmy.put("Humano", 2);

            case 4:
                computerArmy.put("Dragão", 5);

            case 5:
                computerArmy.put("Elfo", 5);

            case 6:
                computerArmy.put("Anão", 2);
                computerArmy.put("Dragão", 1);
                computerArmy.put("Elfo", 1);
                computerArmy.put("Humano", 1);

            case 7:
                computerArmy.put("Anão", 2);
                computerArmy.put("Angry Bird", 1);

            case 8:
                computerArmy.put("Elfo", 2);
                computerArmy.put("Angry Bird", 1);

            case 9:
                computerArmy.put("Dragão", 1);
                computerArmy.put("Humano", 3);

            case 10:
                computerArmy.put("Anão", 4);
                computerArmy.put("Dragão", 2);

            case 11:
                computerArmy.put("Anão", 1);
                computerArmy.put("Humano", 1);
                computerArmy.put("Angry Bird", 1);

        }
        return computerArmy;
    }

    public void startGame(String[] content, int rows, int columns) throws InsufficientCoinsException {

        tesouros = new ArrayList<>();
        creaturesC = new ArrayList<>();
        creatures = new ArrayList<>();
        results = new ArrayList<>();
        buracos = new ArrayList<>();
        computerArmy = new HashMap<>();

        gigantes = new ArrayList<>();

        turnSemCaptura = 0;
        turnCount = 0;
        currentTeamId = 0;

        pontosLdr = 0;
        ldrCoins = 50;

        pontosResistencia = 0;
        resistenciaCoins = 50;

        this.rows = rows;
        this.columns = columns;

        for (String s : content) {
            String[] test = s.split(",");
            if (test.length == 6) {
                int id = parseInt(test[0].substring(4));
                String type = test[1].substring(7);
                int teamId = parseInt(test[2].substring(9));
                int x = parseInt(test[3].substring(4));
                int y = parseInt(test[4].substring(4));
                String orientation = test[5].substring(14);

                if (type.equals("Anão")) {
                    Anao a = new Anao(id, type, teamId, x, y, orientation);
                    a.setImagePNG("crazy_emoji_black.png");
                    creatures.add(a);
                    creaturesC.add(a);

                    if (teamId == ldrId) {
                        ldrCoins -= a.getValor();
                    } else {
                        resistenciaCoins -= a.getValor();
                    }
                } else if (type.equals("Dragão")) {
                    Dragao d = new Dragao(id, type, teamId, x, y, orientation);
                    d.setImagePNG("dragao.png");
                    creatures.add(d);
                    creaturesC.add(d);

                    if (teamId == ldrId) {
                        ldrCoins -= d.getValor();
                    } else {
                        resistenciaCoins -= d.getValor();
                    }
                } else if (type.equals("Elfo")) {
                    Elfo e = new Elfo(id, type, teamId, x, y, orientation);
                    e.setImagePNG("elfo.png");
                    creatures.add(e);
                    creaturesC.add(e);

                    if (teamId == ldrId) {
                        ldrCoins -= e.getValor();
                    } else {
                        resistenciaCoins -= e.getValor();
                    }
                } else if (type.equals("Gigante")) {
                    Gigante g = new Gigante(id, type, teamId, x, y, orientation);
                    g.setImagePNG("gigante.png");
                    creatures.add(g);
                    creaturesC.add(g);
                    gigantes.add(g);

                    if (teamId == ldrId) {
                        ldrCoins -= g.getValor();
                    } else {
                        resistenciaCoins -= g.getValor();
                    }
                } else if (type.equals("Humano")) {
                    Humano h = new Humano(id, type, teamId, x, y, orientation);
                    h.setImagePNG("crazy_emoji_white.png");
                    creatures.add(h);
                    creaturesC.add(h);

                    if (teamId == ldrId) {
                        ldrCoins -= h.getValor();
                    } else {
                        resistenciaCoins -= h.getValor();
                    }
                } else if (type.equals("Angry Bird")) {

                    AngryBird aB = new AngryBird(id, type, teamId, x, y, orientation);
                    aB.setImagePNG("angry_bird.png");
                    creatures.add(aB);
                    creaturesC.add(aB);

                    if (teamId == ldrId) {
                        ldrCoins -= aB.getValor();
                    } else {
                        resistenciaCoins -= aB.getValor();
                    }
                }
            } else {
                int id = parseInt(test[0].substring(4));
                String type = test[1].substring(7);
                int x = parseInt(test[2].substring(4));
                int y = parseInt(test[3].substring(4));
                if (!(type.equals("hole"))) {
                    if (type.equals("gold")) {
                        TesouroGold ct = new TesouroGold(id, type, x, y);
                        creatures.add(ct);
                        tesouros.add(ct);
                    } else if (type.equals("silver")) {
                        TesouroSilver ct = new TesouroSilver(id, type, x, y);
                        creatures.add(ct);
                        tesouros.add(ct);
                    } else if (type.equals("bronze")) {
                        TesouroBronze ct = new TesouroBronze(id, type, x, y);
                        creatures.add(ct);
                        tesouros.add(ct);
                    }
                } else {
                    Buraco b = new Buraco(id, type, x, y);
                    creatures.add(b);
                    buracos.add(b);
                }
            }
        }
        Collections.sort(creaturesC);

        if (ldrCoins <= 0 && resistenciaCoins <= 0) {
            throw new InsufficientCoinsException("Nenhuma das equipas respeitou o plafond de moedas fantásticas", ldrCoins, resistenciaCoins);
        } else if (ldrCoins <= 0) {
            throw new InsufficientCoinsException("A equipa LDR não respeitou o plafond de moedas fantásticas", ldrCoins, resistenciaCoins);
        } else if (resistenciaCoins <= 0) {
            throw new InsufficientCoinsException("A equipa RESISTÊNCIA não respeitou o plafond de moedas fantásticas", ldrCoins, resistenciaCoins);
        }
    }

    public boolean enchant(int x, int y, String spellName) {

        if ("EmpurraParaNorte".equals(spellName)) {
            if (y > 0) {
                if (getElementId(x, y - 1) == 0) {
                    if (getCurrentTeamId() == ldrId) {
                        if (ldrCoins >= 1) {
                            for (Creature c : creaturesC) {
                                if (c.getX() == x && c.getY() == y) {
                                    c.setEstado("EmpurraParaNorte");
                                    c.incNumFeitico();
                                    c.switchFeitico();
                                    ldrCoins--;
                                    return true;
                                }
                            }
                        } else {
                            return false;
                        }
                    } else {
                        if (resistenciaCoins >= 1) {
                            for (Creature c : creaturesC) {
                                if (c.getX() == x && c.getY() == y) {
                                    c.setEstado("EmpurraParaNorte");
                                    c.incNumFeitico();
                                    c.switchFeitico();
                                    resistenciaCoins--;
                                    return true;
                                }
                            }
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if ("EmpurraParaEste".equals(spellName)) {

            if (x < columns - 1) {
                if (getElementId(x + 1, y) == 0) {
                    if (getCurrentTeamId() == ldrId) {
                        if (ldrCoins >= 1) {
                            for (Creature c : creaturesC) {
                                if (c.getX() == x && c.getY() == y) {
                                    c.setEstado("EmpurraParaEste");
                                    c.incNumFeitico();
                                    c.switchFeitico();
                                    ldrCoins--;
                                    return true;
                                }
                            }
                        } else {
                            return false;
                        }
                    } else {
                        if (resistenciaCoins >= 1) {
                            for (Creature c : creaturesC) {
                                if (c.getX() == x && c.getY() == y) {
                                    c.setEstado("EmpurraParaEste");
                                    c.incNumFeitico();
                                    c.switchFeitico();
                                    resistenciaCoins--;
                                    return true;
                                }
                            }
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if ("EmpurraParaSul".equals(spellName)) {
            if (y < rows - 1) {
                if (getElementId(x, y + 1) == 0) {
                    if (getCurrentTeamId() == ldrId) {
                        if (ldrCoins >= 1) {
                            for (Creature c : creaturesC) {
                                if (c.getX() == x && c.getY() == y) {
                                    c.setEstado("EmpurraParaSul");
                                    c.incNumFeitico();
                                    c.switchFeitico();
                                    ldrCoins--;
                                    return true;
                                }
                            }
                        } else {
                            return false;
                        }
                    } else {
                        if (resistenciaCoins >= 1) {
                            for (Creature c : creaturesC) {
                                if (c.getX() == x && c.getY() == y) {
                                    c.setEstado("EmpurraParaSul");
                                    c.incNumFeitico();
                                    c.switchFeitico();
                                    resistenciaCoins--;
                                    return true;
                                }
                            }
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if ("EmpurraParaOeste".equals(spellName)) {
            if (x > 0) {
                if (getElementId(x - 1, y) == 0) {
                    if (getCurrentTeamId() == ldrId) {
                        if (ldrCoins >= 1) {
                            for (Creature c : creaturesC) {
                                if (c.getX() == x && c.getY() == y) {
                                    c.setEstado("EmpurraParaOeste");
                                    c.incNumFeitico();
                                    c.switchFeitico();
                                    ldrCoins--;
                                    return true;
                                }
                            }
                        } else {
                            return false;
                        }
                    } else {
                        if (resistenciaCoins >= 1) {
                            for (Creature c : creaturesC) {
                                if (c.getX() == x && c.getY() == y) {
                                    c.setEstado("EmpurraParaOeste");
                                    c.incNumFeitico();
                                    c.switchFeitico();
                                    resistenciaCoins--;
                                    return true;
                                }
                            }
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if ("ReduzAlcance".equals(spellName)) {
            if (getCurrentTeamId() == ldrId) {
                if (ldrCoins >= 2) {
                    for (Creature c : creaturesC) {
                        if (c.getX() == x && c.getY() == y) {
                            c.setEstado("ReduzAlcance");
                            c.incNumFeitico();
                            c.reduzAlcance();
                            c.switchFeitico();
                            ldrCoins -= 2;
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                if (resistenciaCoins >= 2) {
                    for (Creature c : creaturesC) {
                        if (c.getX() == x && c.getY() == y) {
                            c.setEstado("ReduzAlcance");
                            c.incNumFeitico();
                            c.reduzAlcance();
                            c.switchFeitico();
                            resistenciaCoins -= 2;
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            }
        } else if ("DuplicaAlcance".equals(spellName)) {
            if (getCurrentTeamId() == ldrId) {
                if (ldrCoins >= 3) {
                    for (Creature c : creaturesC) {
                        if (c.getX() == x && c.getY() == y) {
                            if (validarDuplicaAlcance(c)) {
                                c.setEstado("DuplicaAlcance");
                                c.incNumFeitico();
                                c.duplicaAlcance();
                                c.switchFeitico();
                                ldrCoins -= 3;
                                return true;
                            }
                        }
                    }
                } else {
                    return false;
                }
            } else {
                if (resistenciaCoins >= 3) {
                    for (Creature c : creaturesC) {
                        if (c.getX() == x && c.getY() == y) {
                            if (validarDuplicaAlcance(c)) {
                                c.setEstado("DuplicaAlcance");
                                c.incNumFeitico();
                                c.duplicaAlcance();
                                c.switchFeitico();
                                resistenciaCoins -= 3;
                                return true;
                            }
                        }
                    }
                } else {
                    return false;
                }
            }
        } else if ("Congela".equals(spellName)) {
            if (getCurrentTeamId() == ldrId) {
                if (ldrCoins >= 3) {
                    for (Creature c : creaturesC) {
                        if (c.getX() == x && c.getY() == y) {
                            c.congela();
                            c.incNumFeitico();
                            c.switchFeitico();
                            ldrCoins -= 3;
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                if (resistenciaCoins >= 3) {
                    for (Creature c : creaturesC) {
                        if (c.getX() == x && c.getY() == y) {
                            c.congela();
                            c.incNumFeitico();
                            c.switchFeitico();
                            resistenciaCoins -= 3;
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            }
        } else if ("Congela4Ever".equals(spellName)) {

            if (getCurrentTeamId() == ldrId) {
                if (ldrCoins >= 10) {
                    for (Creature c : creaturesC) {
                        if (c.getX() == x && c.getY() == y) {
                            c.congela4Ever();
                            c.incNumFeitico();
                            c.switchFeitico();
                            ldrCoins -= 10;
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                if (resistenciaCoins >= 10) {
                    for (Creature c : creaturesC) {
                        if (c.getX() == x && c.getY() == y) {
                            c.congela4Ever();
                            c.incNumFeitico();
                            c.switchFeitico();
                            resistenciaCoins -= 10;
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            }
        } else if ("Descongela".equals(spellName)) {
            if (getCurrentTeamId() == ldrId) {
                if (ldrCoins >= 8) {
                    for (Creature c : creaturesC) {
                        if (c.getX() == x && c.getY() == y) {
                            c.descongela();
                            c.incNumFeitico();
                            c.switchFeitico();
                            ldrCoins -= 8;
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                if (resistenciaCoins >= 8) {
                    for (Creature c : creaturesC) {
                        if (c.getX() == x && c.getY() == y) {
                            c.descongela();
                            c.incNumFeitico();
                            c.switchFeitico();
                            resistenciaCoins -= 8;
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            }
        }

        return false;
    }

    public String getSpell(int x, int y) {
        for (Creature c : creaturesC) {
            if (c.tenFeitico) {
                if (c.getX() == x && c.getY() == y) {
                    return c.getEstado();
                }
            }
        }
        return null;
    }

    public int getCoinTotal(int teamID) {
        if (teamID == ldrId) {
            return ldrCoins;
        }
        return resistenciaCoins;
    }

    public boolean saveGame(File fich) {
        try {
            FileWriter writer = new FileWriter(fich);
            String newLine = System.getProperty("line.separator");

            for (int i = 0; i < creaturesC.size(); i++) {
                writer.write(creaturesC.get(i).toString2() + newLine);
            }
            for (int i = 0; i < tesouros.size(); i++) {
                writer.write(tesouros.get(i).toString3() + newLine);
            }
            for (int i = 0; i < buracos.size(); i++) {
                writer.write(buracos.get(i).toString3() + newLine);
            }

            /*
            TSC -> turnSemCaptura
            TC -> turnCount
            CTid -> currentTeamId
            Pldr -> pontosLdr
            Cldr -> ldrCoins
            Pres -> pontosResistencia
            Cres -> resistenciaCoins
            */

            writer.write("TSC = " + turnSemCaptura + newLine);
            writer.write("TC = " + turnCount + newLine);
            writer.write("CTid = " + currentTeamId + newLine);

            writer.write("Pldr = " + pontosLdr + newLine);
            writer.write("Cldr = " + ldrCoins + newLine);

            writer.write("Pres = " + pontosResistencia + newLine);
            writer.write("Cres = " + resistenciaCoins + newLine);

            writer.close();

        } catch (java.io.IOException e) {
            System.out.println(e.getStackTrace());
            return false;
        }
        return true;
    }

    public boolean loadGame(File fich) {

        List<String> res = new ArrayList<>();

        try {
            Scanner sc = new Scanner(fich);
            while (sc.hasNextLine()) {
                String linha = sc.nextLine();
                res.add(linha);
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println(e.getStackTrace());
            return false;
        }

        creaturesC = new ArrayList<>();
        creatures = new ArrayList<>();
        tesouros = new ArrayList<>();
        buracos = new ArrayList<>();

        for (String s : res) {

            String[] test = s.split(",");

            if (s.substring(0, 3).equals("TSC")) {
                turnSemCaptura = Integer.parseInt(s.substring(6));
            } else if (s.substring(0, 2).equals("TC")) {
                turnCount = Integer.parseInt(s.substring(5));
            } else if (s.substring(0, 4).equals("CTid")) {
                currentTeamId = Integer.parseInt(s.substring(7));
            } else if (s.substring(0, 4).equals("Pldr")) {
                pontosLdr = Integer.parseInt(s.substring(7));
            } else if (s.substring(0, 4).equals("Cldr")) {
                ldrCoins = Integer.parseInt(s.substring(7));
            } else if (s.substring(0, 4).equals("Pres")) {
                pontosResistencia = Integer.parseInt(s.substring(7));
            } else if (s.substring(0, 4).equals("Cres")) {
                resistenciaCoins = Integer.parseInt(s.substring(7));
            } else if (test.length == 6) {

                int id = parseInt(test[0].substring(4));
                String type = test[1].substring(7);
                int teamId = parseInt(test[2].substring(9));
                int x = parseInt(test[3].substring(4));
                int y = parseInt(test[4].substring(4));
                String orientation = test[5].substring(14);

                if (type.equals("Anão")) {
                    Anao a = new Anao(id, type, teamId, x, y, orientation);
                    a.setImagePNG("crazy_emoji_black.png");
                    creatures.add(a);
                    creaturesC.add(a);
                } else if (type.equals("Dragão")) {
                    Dragao d = new Dragao(id, type, teamId, x, y, orientation);
                    d.setImagePNG("dragao.png");
                    creatures.add(d);
                    creaturesC.add(d);
                } else if (type.equals("Elfo")) {
                    Elfo e = new Elfo(id, type, teamId, x, y, orientation);
                    e.setImagePNG("elfo.png");
                    creatures.add(e);
                    creaturesC.add(e);
                } else if (type.equals("Gigante")) {
                    Gigante g = new Gigante(id, type, teamId, x, y, orientation);
                    g.setImagePNG("gigante.png");
                    creatures.add(g);
                    creaturesC.add(g);
                } else if (type.equals("Humano")) {
                    Humano h = new Humano(id, type, teamId, x, y, orientation);
                    h.setImagePNG("crazy_emoji_white.png");
                    creatures.add(h);
                    creaturesC.add(h);
                } else if (type.equals("Angry Bird")) {
                    AngryBird aB = new AngryBird(id, type, teamId, x, y, orientation);
                    aB.setImagePNG("angry_bird.png");
                    creatures.add(aB);
                    creaturesC.add(aB);
                }
            } else {
                int id = parseInt(test[0].substring(4));
                String type = test[1].substring(7);
                int x = parseInt(test[2].substring(4));
                int y = parseInt(test[3].substring(4));
                if (!(type.equals("hole"))) {
                    if (type.equals("gold")) {
                        TesouroGold ct = new TesouroGold(id, type, x, y);
                        creatures.add(ct);
                        tesouros.add(ct);
                    } else if (type.equals("silver")) {
                        TesouroSilver ct = new TesouroSilver(id, type, x, y);
                        creatures.add(ct);
                        tesouros.add(ct);
                    } else if (type.equals("bronze")) {
                        TesouroBronze ct = new TesouroBronze(id, type, x, y);
                        creatures.add(ct);
                        tesouros.add(ct);
                    }
                } else {
                    Buraco b = new Buraco(id, type, x, y);
                    creatures.add(b);
                    buracos.add(b);
                }
            }

        }
        return true;
    }

    public String whoIsLordEder() {
        return "Éderzito António Macedo Lopes";
    }

// *******************************   PARTE 3   ***************************************

    public Map<String, List<String>> getStatistics() {

        List<String> maisCarregadas = new ArrayList();
        List<Creature> temp = new ArrayList();

        List<Creature> l = creaturesC.stream()
                .sorted((c1, c2) -> c2.getTotalTesouros() - c1.getTotalTesouros())
                .collect(Collectors.toList());

        if (l.size() == 1) {
            temp.add(l.get(0));
        } else if (l.size() == 2) {
            temp.add(l.get(0));
            temp.add(l.get(1));
        } else if (l.size() >= 3) {
            temp.add(l.get(0));
            temp.add(l.get(1));
            temp.add(l.get(2));
        }

        temp.stream()
                .forEach(c -> maisCarregadas.add(c.stringMaisCarregadas()));
//________________________________________________________________________________________________________

        List<String> maisRicas = new ArrayList();
        List<Creature> temp2 = new ArrayList();

        List<Creature> l2 = creaturesC.stream()
                .sorted((c1, c2) -> c2.getPontos() - c1.getPontos())
                .collect(Collectors.toList());

        if (l2.size() == 1) {
            temp2.add(l2.get(0));
        } else if (l2.size() == 2) {
            temp2.add(l2.get(0));
            temp2.add(l2.get(1));
        } else if (l2.size() == 3) {
            temp2.add(l2.get(0));
            temp2.add(l2.get(1));
            temp2.add(l2.get(2));
        } else if (l2.size() == 4) {
            temp2.add(l2.get(0));
            temp2.add(l2.get(1));
            temp2.add(l2.get(2));
            temp2.add(l2.get(3));
        } else if (l2.size() >= 5) {
            temp2.add(l2.get(0));
            temp2.add(l2.get(1));
            temp2.add(l2.get(2));
            temp2.add(l2.get(3));
            temp2.add(l2.get(4));
        }
        temp2.stream()
                .forEach(c -> maisRicas.add(c.stringMaisRicas()));
//_________________________________________________________________________________________________________

        List<String> osAlvosFavoritos = new ArrayList();
        List<Creature> temp3 = new ArrayList();

        List<Creature> l3 = creaturesC.stream()
                .sorted((c1, c2) -> c2.getNumFeiticos() - c1.getNumFeiticos())
                .collect(Collectors.toList());

        if (l3.size() == 1) {
            temp3.add(l3.get(0));
        } else if (l3.size() == 2) {
            temp3.add(l3.get(0));
            temp3.add(l3.get(1));
        } else if (l3.size() >= 3) {
            temp3.add(l3.get(0));
            temp3.add(l3.get(1));
            temp3.add(l3.get(2));
        }

        temp3.stream()
                .forEach(c -> osAlvosFavoritos.add(c.stringAlvosFavoritos()));
//_________________________________________________________________________________________________________

        List<String> maisViajadas = new ArrayList();
        List<Creature> temp4 = new ArrayList();

        List<Creature> l4 = creaturesC.stream()
                .sorted((c1, c2) -> c2.getKilometros() - c1.getKilometros())
                .collect(Collectors.toList());

        if (l4.size() == 1) {
            temp4.add(l4.get(0));
        } else if (l4.size() == 2) {
            temp4.add(l4.get(0));
            temp4.add(l4.get(1));
        } else if (l4.size() >= 3) {
            temp4.add(l4.get(0));
            temp4.add(l4.get(1));
            temp4.add(l4.get(2));
        }

        temp4 = temp4.stream()
                .sorted((c1, c2) -> c1.getKilometros() - c2.getKilometros())
                .collect(Collectors.toList());

        temp4.stream()
                .forEach(c -> maisViajadas.add(c.stringMaisViajadas()));
//_________________________________________________________________________________________________________

        List<String> criaturaESeusTesouros = new ArrayList<>();
        List<String> list = new ArrayList();

        long totalElfo = creaturesC.stream()
                .filter(c -> "Elfo".equals(c.getType()))
                .count();

        int totalTesourosElfo = -1;
        if (totalElfo != 0) {
            totalTesourosElfo = creaturesC.stream()
                    .filter(c -> "Elfo".equals(c.getType()))
                    .map(c -> c.getTotalTesouros())
                    .reduce(0, (c1, c2) -> c1 + c2);
        }
        String elfo = "Elfo:" + totalElfo + ":" + totalTesourosElfo;
        list.add("E" + totalTesourosElfo);
        //---------------------------------------------------------------

        long totalHumano = creaturesC.stream()
                .filter(c -> "Humano".equals(c.getType()))
                .count();

        int totalTesourosHumano = -1;
        if (totalHumano != 0) {
            totalTesourosHumano = creaturesC.stream()
                    .filter(c -> "Humano".equals(c.getType()))
                    .map(c -> c.getTotalTesouros())
                    .reduce(0, (c1, c2) -> c1 + c2);
        }
        String humano = "Humano:" + totalHumano + ":" + totalTesourosHumano;
        list.add("H" + totalTesourosHumano);
        //---------------------------------------------------------------

        long totalGigante = creaturesC.stream()
                .filter(c -> "Gigante".equals(c.getType()))
                .count();

        int totalTesourosGigante = -1;
        if (totalGigante != 0) {
            totalTesourosGigante = creaturesC.stream()
                    .filter(c -> "Gigante".equals(c.getType()))
                    .map(c -> c.getTotalTesouros())
                    .reduce(0, (c1, c2) -> c1 + c2);
        }
        String gigante = "Gigante:" + totalGigante + ":" + totalTesourosGigante;
        list.add("G" + totalTesourosGigante);

        //----------------------------------------------------------------

        long totalAnao = creaturesC.stream()
                .filter(c -> "Anão".equals(c.getType()))
                .count();

        int totalTesourosAnao = -1;
        if (totalAnao != 0) {
            totalTesourosAnao = creaturesC.stream()
                    .filter(c -> "Anão".equals(c.getType()))
                    .map(c -> c.getTotalTesouros())
                    .reduce(0, (c1, c2) -> c1 + c2);
        }
        String anao = "Anão:" + totalAnao + ":" + totalTesourosAnao;
        list.add("A" + totalTesourosAnao);

        //------------------------------------------------------------------

        long totalDragao = creaturesC.stream()
                .filter(c -> "Dragão".equals(c.getType()))
                .count();

        int totalTesourosDragao = -1;
        if (totalDragao != 0) {
            totalTesourosDragao = creaturesC.stream()
                    .filter(c -> "Dragão".equals(c.getType()))
                    .map(c -> c.getTotalTesouros())
                    .reduce(0, (c1, c2) -> c1 + c2);
        }
        String dragao = "Dragão:" + totalDragao + ":" + totalTesourosDragao;
        list.add("D" + totalTesourosDragao);

        //------------------------------------------------------------------

        long totalAngryBird = creaturesC.stream()
                .filter(c -> "Angry Bird".equals(c.getType()))
                .count();

        int totalTesourosAngryBird = -1;
        if (totalAngryBird != 0) {
            totalTesourosAngryBird = creaturesC.stream()
                    .filter(c -> "Angry Bird".equals(c.getType()))
                    .map(c -> c.getTotalTesouros())
                    .reduce(0, (c1, c2) -> c1 + c2);
        }
        String angryBird = "Angry Bird:" + totalAngryBird + ":" + totalTesourosAngryBird;
        list.add("B" + totalTesourosAngryBird);

        list = list.stream()
                .sorted((s1, s2) -> parseInt(s2.substring(1)) - parseInt(s1.substring(1)))
                .collect(Collectors.toList());

        if (list.get(0).equals("A" + totalTesourosAnao)) {
            criaturaESeusTesouros.add(anao);
        } else if (list.get(0).equals("E" + totalTesourosElfo)) {
            criaturaESeusTesouros.add(elfo);
        } else if (list.get(0).equals("D" + totalTesourosDragao)) {
            criaturaESeusTesouros.add(dragao);
        } else if (list.get(0).equals("G" + totalTesourosGigante)) {
            criaturaESeusTesouros.add(gigante);
        } else if (list.get(0).equals("H" + totalTesourosHumano)) {
            criaturaESeusTesouros.add(humano);
        } else if (list.get(0).equals("B" + totalTesourosAngryBird)) {
            criaturaESeusTesouros.add(angryBird);
        }

        if (list.get(1).equals("A" + totalTesourosAnao)) {
            criaturaESeusTesouros.add(anao);
        } else if (list.get(1).equals("E" + totalTesourosElfo)) {
            criaturaESeusTesouros.add(elfo);
        } else if (list.get(1).equals("D" + totalTesourosDragao)) {
            criaturaESeusTesouros.add(dragao);
        } else if (list.get(1).equals("G" + totalTesourosGigante)) {
            criaturaESeusTesouros.add(gigante);
        } else if (list.get(1).equals("H" + totalTesourosHumano)) {
            criaturaESeusTesouros.add(humano);
        } else if (list.get(1).equals("B" + totalTesourosAngryBird)) {
            criaturaESeusTesouros.add(angryBird);
        }

        if (list.get(2).equals("A" + totalTesourosAnao)) {
            criaturaESeusTesouros.add(anao);
        } else if (list.get(2).equals("E" + totalTesourosElfo)) {
            criaturaESeusTesouros.add(elfo);
        } else if (list.get(2).equals("D" + totalTesourosDragao)) {
            criaturaESeusTesouros.add(dragao);
        } else if (list.get(2).equals("G" + totalTesourosGigante)) {
            criaturaESeusTesouros.add(gigante);
        } else if (list.get(2).equals("H" + totalTesourosHumano)) {
            criaturaESeusTesouros.add(humano);
        } else if (list.get(2).equals("B" + totalTesourosAngryBird)) {
            criaturaESeusTesouros.add(angryBird);
        }

        if (list.get(3).equals("A" + totalTesourosAnao)) {
            criaturaESeusTesouros.add(anao);
        } else if (list.get(3).equals("E" + totalTesourosElfo)) {
            criaturaESeusTesouros.add(elfo);
        } else if (list.get(3).equals("D" + totalTesourosDragao)) {
            criaturaESeusTesouros.add(dragao);
        } else if (list.get(3).equals("G" + totalTesourosGigante)) {
            criaturaESeusTesouros.add(gigante);
        } else if (list.get(3).equals("H" + totalTesourosHumano)) {
            criaturaESeusTesouros.add(humano);
        } else if (list.get(3).equals("B" + totalTesourosAngryBird)) {
            criaturaESeusTesouros.add(angryBird);
        }

        if (list.get(4).equals("A" + totalTesourosAnao)) {
            criaturaESeusTesouros.add(anao);
        } else if (list.get(4).equals("E" + totalTesourosElfo)) {
            criaturaESeusTesouros.add(elfo);
        } else if (list.get(4).equals("D" + totalTesourosDragao)) {
            criaturaESeusTesouros.add(dragao);
        } else if (list.get(4).equals("G" + totalTesourosGigante)) {
            criaturaESeusTesouros.add(gigante);
        } else if (list.get(4).equals("H" + totalTesourosHumano)) {
            criaturaESeusTesouros.add(humano);
        } else if (list.get(4).equals("B" + totalTesourosAngryBird)) {
            criaturaESeusTesouros.add(angryBird);
        }

        if (list.get(5).equals("A" + totalTesourosAnao)) {
            criaturaESeusTesouros.add(anao);
        } else if (list.get(5).equals("E" + totalTesourosElfo)) {
            criaturaESeusTesouros.add(elfo);
        } else if (list.get(5).equals("D" + totalTesourosDragao)) {
            criaturaESeusTesouros.add(dragao);
        } else if (list.get(5).equals("G" + totalTesourosGigante)) {
            criaturaESeusTesouros.add(gigante);
        } else if (list.get(5).equals("H" + totalTesourosHumano)) {
            criaturaESeusTesouros.add(humano);
        } else if (list.get(5).equals("B" + totalTesourosAngryBird)) {
            criaturaESeusTesouros.add(angryBird);
        }
//_________________________________________________________________________________________________________

        statistics.put("as3MaisCarregadas", maisCarregadas);
        statistics.put("as5MaisRicas", maisRicas);
        statistics.put("osAlvosFavoritos", osAlvosFavoritos);
        statistics.put("as3MaisViajadas", maisViajadas);
        statistics.put("tiposDeCriaturaESeusTesouros", criaturaESeusTesouros);

        tesouros = new ArrayList<>();
        creaturesC = new ArrayList<>();
        creatures = new ArrayList<>();
        results = new ArrayList<>();
        buracos = new ArrayList<>();
        computerArmy = new HashMap<>();

        gigantes = new ArrayList<>();

        turnSemCaptura = 0;
        turnCount = 0;
        currentTeamId = 0;

        pontosLdr = 0;
        ldrCoins = 50;

        pontosResistencia = 0;
        resistenciaCoins = 50;

        return statistics;
    }

// **********************************************************************************

    public FandeisiaGameManager() {
    }

    public String[][] getCreatureTypes() {

        tesouros = new ArrayList<>();
        creaturesC = new ArrayList<>();
        creatures = new ArrayList<>();
        results = new ArrayList<>();
        buracos = new ArrayList<>();
        computerArmy = new HashMap<>();

        turnSemCaptura = 0;
        turnCount = 0;
        currentTeamId = 0;

        pontosLdr = 0;
        ldrCoins = 50;

        pontosResistencia = 0;
        resistenciaCoins = 50;

        String[][] creatureTypes = new String[6][4];

        creatureTypes[0][0] = "Anão";
        creatureTypes[0][1] = "crazy_emoji_black.png";
        creatureTypes[0][2] = "Move na horizontal e na vertical.";
        creatureTypes[0][3] = "1";

        creatureTypes[1][0] = "Dragão";
        creatureTypes[1][1] = "dragao.png";
        creatureTypes[1][2] = "Dragão é capaz de voar.";
        creatureTypes[1][3] = "9";

        creatureTypes[2][0] = "Elfo";
        creatureTypes[2][1] = "elfo.png";
        creatureTypes[2][2] = "Elfo é muito ágil e pode saltar por cima de um buraco.";
        creatureTypes[2][3] = "5";

        creatureTypes[3][0] = "Gigante";
        creatureTypes[3][1] = "gigante.png";
        creatureTypes[3][2] = "Gigante é tão alto e tem as pernas tão compridas";
        creatureTypes[3][3] = "5";

        creatureTypes[4][0] = "Humano";
        creatureTypes[4][1] = "crazy_emoji_white.png";
        creatureTypes[4][2] = "Move na horizontal e na vertical.";
        creatureTypes[4][3] = "3";

        //Criatura daquelas...
        creatureTypes[5][0] = "Angry Bird";
        creatureTypes[5][1] = "angry_bird.png";
        creatureTypes[5][2] = "Movimenta somente na diagonal";
        creatureTypes[5][3] = "10";

        return creatureTypes;
    }

    public void setInitialTeam(int teamId) {
        currentTeamId = teamId;
    }

    public void processTurn() {

        boolean ldrCaptura = false;
        boolean resCaptura = false;

        for (Creature c : creaturesC) {
            if (c.tenFeitico) {
                if ("EmpurraParaNorte".equals(c.getEstado())) {
                    c.empurraNorte();
                } else if ("EmpurraParaEste".equals(c.getEstado())) {
                    c.empurraEste();
                } else if ("EmpurraParaSul".equals(c.getEstado())) {
                    c.empurraSul();
                } else if ("EmpurraParaOeste".equals(c.getEstado())) {
                    c.empurraOeste();
                }
            }
        }
        for (Creature c : creaturesC) {
            if (!("Congela".equals(c.getEstado())) && !("Congela4Ever".equals(c.getEstado()))) {
                if (c.getType().equals("Anão")) {

                    if (c.getOrientation().equals("Norte")) {
                        if (c.getY() - c.getAlcance() >= 0) {
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() && cc.getY() == c.getY() - c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() && b.getY() == c.getY() - c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (t.getX() == c.getX() && t.getY() == c.getY() - c.getAlcance()) {
                                    eTesouro = true;
                                    pointInc(c, t.getValor());
                                    if (c.getTeamId() == ldrId) {
                                        ldrCaptura = true;
                                    } else {
                                        resCaptura = true;
                                    }
                                    c.maisPontos(t);
                                    tesouros.remove(t);
                                    creatures.remove(t);
                                    break;
                                }
                            }

                            if (getElementId(c.getX(), (c.getY() - c.getAlcance())) == 0 || eTesouro) {
                                c.moveNorte();
                                c.incKilometros();
                            } else if (eburaco || eCriatura) {
                                c.setOrientation("Este");
                            }

                        } else {
                            c.setOrientation("Este");
                        }
                    } else if (c.getOrientation().equals("Sul")) {
                        if (c.getY() + c.getAlcance() < rows) {
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() && cc.getY() == c.getY() + c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() && b.getY() == c.getY() + c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (t.getX() == c.getX() && t.getY() == c.getY() + c.getAlcance()) {
                                    eTesouro = true;
                                    pointInc(c, t.getValor());
                                    if (c.getTeamId() == ldrId) {
                                        ldrCaptura = true;
                                    } else {
                                        resCaptura = true;
                                    }

                                    c.maisPontos(t);
                                    tesouros.remove(t);
                                    creatures.remove(t);
                                    break;
                                }
                            }

                            if (getElementId(c.getX(), (c.getY() + c.getAlcance())) == 0 || eTesouro) {
                                c.moveSul();
                                c.incKilometros();
                            } else if (eburaco || eCriatura) {
                                c.setOrientation("Oeste");
                            }
                        } else {
                            c.setOrientation("Oeste");
                        }
                    } else if (c.getOrientation().equals("Este")) {
                        if (c.getX() + c.getAlcance() < columns) {
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() + c.getAlcance() && cc.getY() == c.getY()) {
                                    eCriatura = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() + c.getAlcance() && b.getY() == c.getY()) {
                                    eburaco = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (t.getX() == c.getX() + c.getAlcance() && t.getY() == c.getY()) {
                                    eTesouro = true;
                                    pointInc(c, t.getValor());
                                    if (c.getTeamId() == ldrId) {
                                        ldrCaptura = true;
                                    } else {
                                        resCaptura = true;
                                    }

                                    c.maisPontos(t);
                                    tesouros.remove(t);
                                    creatures.remove(t);
                                    break;
                                }
                            }

                            if (getElementId(c.getX() + c.getAlcance(), c.getY()) == 0 || eTesouro) {
                                c.moveEste();
                                c.incKilometros();
                            } else if (eburaco || eCriatura) {
                                c.setOrientation("Sul");
                            }
                        } else {
                            c.setOrientation("Sul");
                        }
                    } else if (c.getOrientation().equals("Oeste")) {
                        if (c.getX() - c.getAlcance() >= 0) {

                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() - c.getAlcance() && cc.getY() == c.getY()) {
                                    eCriatura = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() - c.getAlcance() && b.getY() == c.getY()) {
                                    eburaco = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (t.getX() == c.getX() - c.getAlcance() && t.getY() == c.getY()) {
                                    eTesouro = true;
                                    pointInc(c, t.getValor());
                                    if (c.getTeamId() == ldrId) {
                                        ldrCaptura = true;
                                    } else {
                                        resCaptura = true;
                                    }

                                    c.maisPontos(t);
                                    tesouros.remove(t);
                                    creatures.remove(t);
                                    break;
                                }
                            }

                            if (getElementId((c.getX() - c.getAlcance()), c.getY()) == 0 || eTesouro) {
                                c.moveOeste();
                                c.incKilometros();
                            } else if (eburaco || eCriatura) {
                                c.setOrientation("Norte");
                            }
                        } else {
                            c.setOrientation("Norte");
                        }
                    }

                } else if (c.getType().equals("Dragão")) {
                    if (c.getOrientation().equals("Norte")) {
                        if (c.getY() - c.getAlcance() >= 0) {
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() && cc.getY() == c.getY() - c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (t.getX() == c.getX() && t.getY() == c.getY() - c.getAlcance()) {
                                    eTesouro = true;
                                    pointInc(c, t.getValor());
                                    if (c.getTeamId() == ldrId) {
                                        ldrCaptura = true;
                                    } else {
                                        resCaptura = true;
                                    }

                                    c.maisPontos(t);
                                    tesouros.remove(t);
                                    creatures.remove(t);
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() && b.getY() == c.getY() - c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                }
                            }

                            if (getElementId(c.getX(), (c.getY() - c.getAlcance())) == 0 || eTesouro) {
                                c.moveNorte();
                                c.incKilometros();
                            } else if (eburaco || eCriatura) {
                                c.setOrientation("Nordeste");
                            }
                        } else {
                            c.setOrientation("Nordeste");
                        }
                    } else if (c.getOrientation().equals("Sul")) {
                        if (c.getY() + c.getAlcance() < rows) {
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() && cc.getY() == c.getY() + c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (t.getX() == c.getX() && t.getY() == c.getY() + c.getAlcance()) {
                                    eTesouro = true;
                                    pointInc(c, t.getValor());
                                    if (c.getTeamId() == ldrId) {
                                        ldrCaptura = true;
                                    } else {
                                        resCaptura = true;
                                    }

                                    c.maisPontos(t);
                                    tesouros.remove(t);
                                    creatures.remove(t);
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() && b.getY() == c.getY() + c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                }
                            }

                            if (getElementId(c.getX(), (c.getY() + c.getAlcance())) == 0 || eTesouro) {
                                c.moveSul();
                                c.incKilometros();
                            } else if (eburaco || eCriatura) {
                                c.setOrientation("Sudoeste");
                            }
                        } else {
                            c.setOrientation("Sudoeste");
                        }
                    } else if (c.getOrientation().equals("Este")) {

                        if (c.getX() + c.getAlcance() < columns) {
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() + c.getAlcance() && cc.getY() == c.getY()) {
                                    eCriatura = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (t.getX() == c.getX() + c.getAlcance() && t.getY() == c.getY()) {
                                    eTesouro = true;
                                    pointInc(c, t.getValor());
                                    if (c.getTeamId() == ldrId) {
                                        ldrCaptura = true;
                                    } else {
                                        resCaptura = true;
                                    }

                                    c.maisPontos(t);
                                    tesouros.remove(t);
                                    creatures.remove(t);
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() + c.getAlcance() && b.getY() == c.getY()) {
                                    eburaco = true;
                                    break;
                                }
                            }

                            if (getElementId(c.getX() + c.getAlcance(), c.getY()) == 0 || eTesouro) {
                                c.moveEste();
                                c.incKilometros();
                            } else if (eburaco || eCriatura) {
                                c.setOrientation("Sudeste");
                            }
                        } else {
                            c.setOrientation("Sudeste");
                        }
                    } else if (c.getOrientation().equals("Oeste")) {
                        if (c.getX() - c.getAlcance() >= 0) {

                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() - c.getAlcance() && cc.getY() == c.getY()) {
                                    eCriatura = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (t.getX() == c.getX() - c.getAlcance() && t.getY() == c.getY()) {
                                    eTesouro = true;
                                    pointInc(c, t.getValor());
                                    if (c.getTeamId() == ldrId) {
                                        ldrCaptura = true;
                                    } else {
                                        resCaptura = true;
                                    }

                                    c.maisPontos(t);
                                    tesouros.remove(t);
                                    creatures.remove(t);
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() - c.getAlcance() && b.getY() == c.getY()) {
                                    eburaco = true;
                                    break;
                                }
                            }

                            if (getElementId((c.getX() - c.getAlcance()), c.getY()) == 0 || eTesouro) {
                                c.moveOeste();
                                c.incKilometros();
                            } else if (eburaco || eCriatura) {
                                c.setOrientation("Noroeste");
                            }
                        } else {
                            c.setOrientation("Noroeste");
                        }
                    } else if (c.getOrientation().equals("Nordeste")) {
                        if (c.getY() - c.getAlcance() >= 0 && c.getX() + c.getAlcance() < columns) {
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() + c.getAlcance() && cc.getY() == c.getY() - c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (t.getX() == c.getX() + c.getAlcance() && t.getY() == c.getY() - c.getAlcance()) {
                                    eTesouro = true;
                                    pointInc(c, t.getValor());
                                    if (c.getTeamId() == ldrId) {
                                        ldrCaptura = true;
                                    } else {
                                        resCaptura = true;
                                    }

                                    c.maisPontos(t);
                                    tesouros.remove(t);
                                    creatures.remove(t);
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() + c.getAlcance() && b.getY() == c.getY() - c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                }
                            }

                            if (c.getElementId(c.getX() + c.getAlcance(), c.getY() - c.getAlcance()) == 0 || eTesouro) {
                                c.moveNE();
                                c.incKilometros();
                            } else if (eburaco || eCriatura) {
                                c.setOrientation("Este");
                            }
                        } else {
                            c.setOrientation("Este");
                        }
                    } else if (c.getOrientation().equals("Sudeste")) {
                        if (c.getY() + c.getAlcance() < rows && c.getX() + c.getAlcance() < columns) {
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() + c.getAlcance() && cc.getY() == c.getY() + c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (t.getX() == c.getX() + c.getAlcance() && t.getY() == c.getY() + c.getAlcance()) {
                                    eTesouro = true;
                                    pointInc(c, t.getValor());
                                    if (c.getTeamId() == ldrId) {
                                        ldrCaptura = true;
                                    } else {
                                        resCaptura = true;
                                    }

                                    c.maisPontos(t);
                                    tesouros.remove(t);
                                    creatures.remove(t);
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() + c.getAlcance() && b.getY() == c.getY() + c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                }
                            }

                            if (c.getElementId(c.getX() + c.getAlcance(), c.getY() + c.getAlcance()) == 0 || eTesouro) {
                                c.moveSE();
                                c.incKilometros();
                            } else if (eburaco || eCriatura) {
                                c.setOrientation("Sul");
                            }
                        } else {
                            c.setOrientation("Sul");
                        }

                    } else if (c.getOrientation().equals("Sudoeste")) {
                        if (c.getY() + c.getAlcance() < rows && c.getX() - c.getAlcance() >= 0) {
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() - c.getAlcance() && cc.getY() == c.getY() + c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (t.getX() == c.getX() - c.getAlcance() && t.getY() == c.getY() + c.getAlcance()) {
                                    eTesouro = true;
                                    pointInc(c, t.getValor());
                                    if (c.getTeamId() == ldrId) {
                                        ldrCaptura = true;
                                    } else {
                                        resCaptura = true;
                                    }

                                    c.maisPontos(t);
                                    tesouros.remove(t);
                                    creatures.remove(t);
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() - c.getAlcance() && b.getY() == c.getY() + c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                }
                            }

                            if (c.getElementId(c.getX() - c.getAlcance(), c.getY() + c.getAlcance()) == 0 || eTesouro) {
                                c.moveSO();
                                c.incKilometros();
                            } else if (eburaco || eCriatura) {
                                c.setOrientation("Oeste");
                            }
                        } else {
                            c.setOrientation("Oeste");
                        }
                    } else if (c.getOrientation().equals("Noroeste")) {
                        if (c.getY() - c.getAlcance() >= 0 && c.getX() - c.getAlcance() >= 0) {
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() - c.getAlcance() && cc.getY() == c.getY() - c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (t.getX() == c.getX() - c.getAlcance() && t.getY() == c.getY() - c.getAlcance()) {
                                    eTesouro = true;
                                    pointInc(c, t.getValor());
                                    if (c.getTeamId() == ldrId) {
                                        ldrCaptura = true;
                                    } else {
                                        resCaptura = true;
                                    }

                                    c.maisPontos(t);
                                    tesouros.remove(t);
                                    creatures.remove(t);
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() - c.getAlcance() && b.getY() == c.getY() - c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                }
                            }

                            if (c.getElementId(c.getX() - c.getAlcance(), c.getY() - c.getAlcance()) == 0 || eTesouro) {
                                c.moveNO();
                                c.incKilometros();
                            } else if (eburaco || eCriatura) {
                                c.setOrientation("Norte");
                            }
                        } else {
                            c.setOrientation("Norte");
                        }
                    }
                } else if (c.getType().equals("Elfo")) {

                    if (c.getOrientation().equals("Norte")) {
                        if (c.getY() - c.getAlcance() >= 0) {
                            boolean salta = false;
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() && cc.getY() == c.getY() - c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                } else if (cc.getX() == c.getX() && cc.getY() == c.getY() - c.getAlcance() + 1) {
                                    salta = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() && b.getY() == c.getY() - c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (!salta) {
                                    if (t.getX() == c.getX() && t.getY() == c.getY() - c.getAlcance()) {
                                        eTesouro = true;
                                        pointInc(c, t.getValor());

                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }


                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                            }

                            if ((getElementId(c.getX(), (c.getY() - c.getAlcance())) == 0 && !(salta)) || eTesouro) {
                                c.moveNorte();
                                c.incKilometros();
                            } else if (eburaco || eCriatura || salta) {
                                c.setOrientation("Nordeste");
                            }
                        } else {
                            c.setOrientation("Nordeste");
                        }
                    } else if (c.getOrientation().equals("Sul")) {
                        if (c.getY() + c.getAlcance() < rows) {
                            boolean salta = false;
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() && cc.getY() == c.getY() + c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                } else if (cc.getX() == c.getX() && cc.getY() == c.getY() + c.getAlcance() - 1) {
                                    salta = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() && b.getY() == c.getY() + c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (!salta) {
                                    if (t.getX() == c.getX() && t.getY() == c.getY() + c.getAlcance()) {
                                        eTesouro = true;
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                            }

                            if ((getElementId(c.getX(), (c.getY() + c.getAlcance())) == 0 && !(salta)) || eTesouro) {
                                c.moveSul();
                                c.incKilometros();
                            } else if (eburaco || eCriatura || salta) {
                                c.setOrientation("Sudoeste");
                            }
                        } else {
                            c.setOrientation("Sudoeste");
                        }
                    } else if (c.getOrientation().equals("Este")) {
                        if (c.getX() + c.getAlcance() < columns) {
                            boolean salta = false;
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() + c.getAlcance() && cc.getY() == c.getY()) {
                                    eCriatura = true;
                                    break;
                                } else if (cc.getX() == c.getX() + c.getAlcance() - 1 && cc.getY() == c.getY()) {
                                    salta = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() + c.getAlcance() && b.getY() == c.getY()) {
                                    eburaco = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (!salta) {
                                    if (t.getX() == c.getX() + c.getAlcance() && t.getY() == c.getY()) {
                                        eTesouro = true;
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }

                            }

                            if ((getElementId(c.getX() + c.getAlcance(), c.getY()) == 0 && !(salta)) || eTesouro) {
                                c.moveEste();
                                c.incKilometros();
                            } else if (eburaco || eCriatura || salta) {
                                c.setOrientation("Sudeste");
                            }
                        } else {
                            c.setOrientation("Sudeste");
                        }
                    } else if (c.getOrientation().equals("Oeste")) {
                        if (c.getX() - c.getAlcance() >= 0) {
                            boolean salta = false;
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() - c.getAlcance() && cc.getY() == c.getY()) {
                                    eCriatura = true;
                                    break;
                                } else if (cc.getX() == c.getX() - c.getAlcance() + 1 && cc.getY() == c.getY()) {
                                    salta = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() - c.getAlcance() && b.getY() == c.getY()) {
                                    eburaco = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (!salta) {
                                    if (t.getX() == c.getX() - c.getAlcance() && t.getY() == c.getY()) {
                                        eTesouro = true;
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }

                            }

                            if ((getElementId(c.getX() - c.getAlcance(), c.getY()) == 0 && !(salta)) || eTesouro) {
                                c.moveOeste();
                                c.incKilometros();
                            } else if (eburaco || eCriatura || salta) {
                                c.setOrientation("Noroeste");
                            }
                        } else {
                            c.setOrientation("Noroeste");
                        }
                    } else if (c.getOrientation().equals("Nordeste")) {

                        if (c.getY() - c.getAlcance() >= 0 && c.getX() + c.getAlcance() < columns) {
                            boolean salta = false;
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() + c.getAlcance() && cc.getY() == c.getY() - c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                } else if (cc.getX() == c.getX() + c.getAlcance() - 1 && cc.getY() == c.getY() - c.getAlcance() + 1) {
                                    salta = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() + c.getAlcance() && b.getY() == c.getY() - c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (!salta) {
                                    if (t.getX() == c.getX() + c.getAlcance() && t.getY() == c.getY() - c.getAlcance()) {
                                        eTesouro = true;
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }

                            }

                            if ((getElementId(c.getX() + c.getAlcance(), c.getY() - c.getAlcance()) == 0 && !(salta)) || eTesouro) {
                                c.moveNE();
                                c.incKilometros();
                            } else if (eburaco || eCriatura || salta) {
                                c.setOrientation("Este");
                            }
                        } else {
                            c.setOrientation("Este");
                        }
                    } else if (c.getOrientation().equals("Sudeste")) {

                        if (c.getY() + c.getAlcance() < rows && c.getX() + c.getAlcance() < columns) {
                            boolean salta = false;
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() + c.getAlcance() && cc.getY() == c.getY() + c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                } else if (cc.getX() == c.getX() + c.getAlcance() - 1 && cc.getY() == c.getY() + c.getAlcance() - 1) {
                                    salta = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() + c.getAlcance() && b.getY() == c.getY() + c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (!salta) {
                                    if (t.getX() == c.getX() + c.getAlcance() && t.getY() == c.getY() + c.getAlcance()) {
                                        eTesouro = true;
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }

                            }

                            if ((getElementId(c.getX() + c.getAlcance(), c.getY() + c.getAlcance()) == 0 && !(salta)) || eTesouro) {
                                c.moveSE();
                                c.incKilometros();
                            } else if (eburaco || eCriatura || salta) {
                                c.setOrientation("Sul");
                            }
                        } else {
                            c.setOrientation("Sul");
                        }
                    } else if (c.getOrientation().equals("Sudoeste")) {
                        if (c.getY() + c.getAlcance() < rows && c.getX() - c.getAlcance() >= 0) {
                            boolean salta = false;
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() - c.getAlcance() && cc.getY() == c.getY() + c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                } else if (cc.getX() == c.getX() - c.getAlcance() + 1 && cc.getY() == c.getY() + c.getAlcance() - 1) {
                                    salta = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() - c.getAlcance() && b.getY() == c.getY() + c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (!salta) {
                                    if (t.getX() == c.getX() - c.getAlcance() && t.getY() == c.getY() + c.getAlcance()) {
                                        eTesouro = true;
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                            }

                            if ((getElementId(c.getX() - c.getAlcance(), c.getY() + c.getAlcance()) == 0 && !(salta)) || eTesouro) {
                                c.moveSO();
                                c.incKilometros();
                            } else if (eburaco || eCriatura || salta) {
                                c.setOrientation("Oeste");
                            }
                        } else {
                            c.setOrientation("Oeste");
                        }
                    } else if (c.getOrientation().equals("Noroeste")) {

                        if (c.getY() - c.getAlcance() >= 0 && c.getX() - c.getAlcance() >= 0) {
                            boolean salta = false;
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() - c.getAlcance() && cc.getY() == c.getY() - c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                } else if (cc.getX() == c.getX() - c.getAlcance() + 1 && cc.getY() == c.getY() - c.getAlcance() + 1) {
                                    salta = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() - c.getAlcance() && b.getY() == c.getY() - c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (!salta) {
                                    if (t.getX() == c.getX() - c.getAlcance() && t.getY() == c.getY() - c.getAlcance()) {
                                        eTesouro = true;
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }

                            }

                            if ((getElementId(c.getX() - c.getAlcance(), c.getY() - c.getAlcance()) == 0 && !(salta)) || eTesouro) {
                                c.moveNO();
                                c.incKilometros();
                            } else if (eburaco || eCriatura || salta) {
                                c.setOrientation("Norte");
                            }
                        } else {
                            c.setOrientation("Norte");
                        }
                    }
                } else if (c.getType().equals("Gigante")) {
                    if (c.getOrientation().equals("Norte")) {
                        boolean salta = false;
                        for (Gigante g : gigantes) {
                            if (g.getX() == c.getX() && g.getY() == c.getY() - c.getAlcance() + 1) {
                                salta = true;
                                break;
                            } else if (g.getX() == c.getX() && g.getY() == c.getY() - c.getAlcance() + 2) {
                                salta = true;
                                break;
                            }
                        }
                        if (c.getY() - c.getAlcance() >= 0 && !salta) {
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() && cc.getY() == c.getY() - c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() && b.getY() == c.getY() - c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (!salta) {
                                    if (t.getX() == c.getX() && t.getY() == c.getY() - c.getAlcance()) {
                                        eTesouro = true;
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                            }

                            if (getElementId(c.getX(), (c.getY() - c.getAlcance())) == 0 || eTesouro) {
                                c.moveNorte();
                                c.incKilometros();
                            } else if (eburaco || eCriatura) {
                                c.setOrientation("Este");
                            }
                        } else {
                            c.setOrientation("Este");
                        }
                    } else if (c.getOrientation().equals("Sul")) {
                        boolean salta = false;
                        for (Gigante g : gigantes) {
                            if (g.getX() == c.getX() && g.getY() == c.getY() + c.getAlcance() - 1) {
                                salta = true;
                                break;
                            } else if (g.getX() == c.getX() && g.getY() == c.getY() + c.getAlcance() - 2) {
                                salta = true;
                                break;
                            }

                        }
                        if (c.getY() + c.getAlcance() < rows && !salta) {
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() && cc.getY() == c.getY() + c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() && b.getY() == c.getY() + c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (!salta) {
                                    if (t.getX() == c.getX() && t.getY() == c.getY() + c.getAlcance()) {
                                        eTesouro = true;
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                            }

                            if (getElementId(c.getX(), (c.getY() + c.getAlcance())) == 0 || eTesouro) {
                                c.moveSul();
                                c.incKilometros();
                            } else if (eburaco || eCriatura) {
                                c.setOrientation("Oeste");
                            }
                        } else {
                            c.setOrientation("Oeste");
                        }
                    } else if (c.getOrientation().equals("Este")) {
                        boolean salta = false;
                        for (Gigante g : gigantes) {
                            if (g.getX() == c.getX() + c.getAlcance() - 1 && g.getY() == c.getY()) {
                                salta = true;
                                break;
                            } else if (g.getX() == c.getX() + c.getAlcance() - 2 && g.getY() == c.getY()) {
                                salta = true;
                                break;
                            }

                        }
                        if (c.getX() + c.getAlcance() < columns && !salta) {
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() + c.getAlcance() && cc.getY() == c.getY()) {
                                    eCriatura = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() + c.getAlcance() && b.getY() == c.getY()) {
                                    eburaco = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (!salta) {
                                    if (t.getX() == c.getX() + c.getAlcance() && t.getY() == c.getY()) {
                                        eTesouro = true;
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                            }

                            if (getElementId(c.getX() + c.getAlcance(), c.getY()) == 0 || eTesouro) {
                                c.moveEste();
                                c.incKilometros();
                            } else if (eburaco || eCriatura) {
                                c.setOrientation("Sul");
                            }
                        } else {
                            c.setOrientation("Sul");
                        }
                    } else if (c.getOrientation().equals("Oeste")) {
                        boolean salta = false;
                        for (Gigante g : gigantes) {
                            if (g.getX() == c.getX() - c.getAlcance() + 1 && g.getY() == c.getY()) {
                                salta = true;
                                break;
                            } else if (g.getX() == c.getX() - c.getAlcance() + 2 && g.getY() == c.getY()) {
                                salta = true;
                                break;
                            }

                        }
                        if (c.getX() - c.getAlcance() >= 0 && !salta) {
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() - c.getAlcance() && cc.getY() == c.getY()) {
                                    eCriatura = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() - c.getAlcance() && b.getY() == c.getY()) {
                                    eburaco = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (!salta) {
                                    if (t.getX() == c.getX() - c.getAlcance() && t.getY() == c.getY()) {
                                        eTesouro = true;
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                            }

                            if (getElementId((c.getX() - c.getAlcance()), c.getY()) == 0 || eTesouro) {
                                c.moveOeste();
                                c.incKilometros();
                            } else if (eburaco || eCriatura) {
                                c.setOrientation("Norte");
                            }
                        } else {
                            c.setOrientation("Norte");
                        }
                    }
                } else if (c.getType().equals("Humano")) {
                    if (c.getOrientation().equals("Norte")) {
                        if (c.getY() - c.getAlcance() >= 0) {
                            boolean salta = false;
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() && cc.getY() == c.getY() - c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                } else if (cc.getX() == c.getX() && cc.getY() == c.getY() - c.getAlcance() + 1) {
                                    salta = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() && b.getY() == c.getY() - c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                } else if (b.getX() == c.getX() && b.getY() == c.getY() - c.getAlcance() + 1) {
                                    salta = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (!salta) {
                                    if (t.getX() == c.getX() && t.getY() == c.getY() - c.getAlcance()) {
                                        eTesouro = true;
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                            }

                            if ((getElementId(c.getX(), (c.getY() - c.getAlcance())) == 0 && !(salta)) || eTesouro) {
                                c.moveNorte();
                                c.incKilometros();
                            } else if (eburaco || eCriatura || salta) {
                                c.setOrientation("Este");
                            }
                        } else {
                            c.setOrientation("Este");
                        }
                    } else if (c.getOrientation().equals("Sul")) {
                        if (c.getY() + c.getAlcance() < rows) {
                            boolean salta = false;
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() && cc.getY() == c.getY() + c.getAlcance()) {
                                    eCriatura = true;
                                    break;
                                } else if (cc.getX() == c.getX() && cc.getY() == c.getY() + c.getAlcance() - 1) {
                                    salta = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() && b.getY() == c.getY() + c.getAlcance()) {
                                    eburaco = true;
                                    break;
                                } else if (b.getX() == c.getX() && b.getY() == c.getY() + c.getAlcance() - 1) {
                                    salta = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (!salta) {
                                    if (t.getX() == c.getX() && t.getY() == c.getY() + c.getAlcance()) {
                                        eTesouro = true;
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                            }

                            if ((getElementId(c.getX(), (c.getY() + c.getAlcance())) == 0 && !(salta)) || eTesouro) {
                                c.moveSul();
                                c.incKilometros();
                            } else if (eburaco || eCriatura || salta) {
                                c.setOrientation("Oeste");
                            }
                        } else {
                            c.setOrientation("Oeste");
                        }
                    } else if (c.getOrientation().equals("Este")) {
                        if (c.getX() + c.getAlcance() < columns) {
                            boolean salta = false;
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() + c.getAlcance() && cc.getY() == c.getY()) {
                                    eCriatura = true;
                                    break;
                                } else if (cc.getX() == c.getX() + c.getAlcance() - 1 && cc.getY() == c.getY()) {
                                    salta = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() + c.getAlcance() && b.getY() == c.getY()) {
                                    eburaco = true;
                                    break;
                                } else if (b.getX() == c.getX() + c.getAlcance() - 1 && b.getY() == c.getY()) {
                                    salta = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (!salta) {
                                    if (t.getX() == c.getX() + c.getAlcance() && t.getY() == c.getY()) {
                                        eTesouro = true;
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                            }

                            if ((getElementId(c.getX() + c.getAlcance(), c.getY()) == 0 && !(salta)) || eTesouro) {
                                c.moveEste();
                                c.incKilometros();
                            } else if (eburaco || eCriatura || salta) {
                                c.setOrientation("Sul");
                            }
                        } else {
                            c.setOrientation("Sul");
                        }
                    } else if (c.getOrientation().equals("Oeste")) {
                        if (c.getX() - c.getAlcance() >= 0) {
                            boolean salta = false;
                            boolean eTesouro = false;
                            boolean eburaco = false;
                            boolean eCriatura = false;

                            for (Creature cc : creaturesC) {
                                if (cc.getX() == c.getX() - c.getAlcance() && cc.getY() == c.getY()) {
                                    eCriatura = true;
                                    break;
                                } else if (cc.getX() == c.getX() - c.getAlcance() + 1 && cc.getY() == c.getY()) {
                                    salta = true;
                                    break;
                                }
                            }
                            for (Creature b : buracos) {
                                if (b.getX() == c.getX() - c.getAlcance() && b.getY() == c.getY()) {
                                    eburaco = true;
                                    break;
                                } else if (b.getX() == c.getX() - c.getAlcance() + 1 && b.getY() == c.getY()) {
                                    salta = true;
                                    break;
                                }
                            }
                            for (Tesouro t : tesouros) {
                                if (!salta) {
                                    if (t.getX() == c.getX() - c.getAlcance() && t.getY() == c.getY()) {
                                        eTesouro = true;
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }

                            }

                            if ((getElementId(c.getX() - c.getAlcance(), c.getY()) == 0 && !(salta)) || eTesouro) {
                                c.moveOeste();
                                c.incKilometros();
                            } else if (eburaco || eCriatura || salta) {
                                c.setOrientation("Norte");
                            }
                        } else {
                            c.setOrientation("Norte");
                        }
                    }
                } else if (c.getType().equals("Angry Bird")) {
                    if (c.getOrientation().equals("Nordeste")) {
                        if (c.getY() - c.getAlcance() >= 0 && c.getX() + c.getAlcance() < columns) {
                            if (getElementId(c.getX() + c.getAlcance(), c.getY() - c.getAlcance()) == 0) {
                                c.moveNE();
                                c.incKilometros();

                            } else if (getElementId(c.getX() + c.getAlcance(), c.getY() - c.getAlcance()) < 0) {
                                for (Tesouro t : tesouros) {
                                    if (t.getId() == getElementId(c.getX() + c.getAlcance(), c.getY() - c.getAlcance())) {
                                        c.moveNE();
                                        c.incKilometros();
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        if (currentTeamId == ldrId) {
                                            ldrCoins++;
                                        } else {
                                            resistenciaCoins++;
                                        }
                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                                for (Creature t : buracos) {
                                    if (t.getId() == getElementId(c.getX() + c.getAlcance(), c.getY() - c.getAlcance())) {
                                        c.setOrientation("Sudeste");
                                    }
                                }
                            }
                        } else {
                            c.setOrientation("Sudeste");
                        }
                    } else if (c.getOrientation().equals("Sudeste")) {
                        if (c.getY() + c.getAlcance() < rows && c.getX() + c.getAlcance() < columns) {
                            if (getElementId(c.getX() + c.getAlcance(), c.getY() + c.getAlcance()) == 0) {
                                c.moveSE();
                                c.incKilometros();
                            } else if (getElementId(c.getX() + c.getAlcance(), c.getY() + c.getAlcance()) < 0) {

                                for (Tesouro t : tesouros) {
                                    if (t.getId() == getElementId(c.getX() + c.getAlcance(), c.getY() + c.getAlcance())) {
                                        c.moveSE();
                                        c.incKilometros();
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        if (currentTeamId == ldrId) {
                                            ldrCoins++;
                                        } else {
                                            resistenciaCoins++;
                                        }
                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                                for (Creature t : buracos) {
                                    if (t.getId() == getElementId(c.getX() + c.getAlcance(), c.getY() + c.getAlcance())) {
                                        c.setOrientation("Sudoeste");
                                    }
                                }
                            }
                        } else {
                            c.setOrientation("Sudoeste");
                        }
                    } else if (c.getOrientation().equals("Sudoeste")) {
                        if (c.getY() + c.getAlcance() < rows && c.getX() - c.getAlcance() >= 0) {
                            if (getElementId(c.getX() - c.getAlcance(), c.getY() + c.getAlcance()) == 0) {
                                c.moveSO();
                                c.incKilometros();
                            } else if (getElementId(c.getX() - c.getAlcance(), c.getY() + c.getAlcance()) < 0) {
                                for (Tesouro t : tesouros) {
                                    if (t.getId() == getElementId(c.getX() - c.getAlcance(), c.getY() + c.getAlcance())) {
                                        c.moveSO();
                                        c.incKilometros();
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        if (currentTeamId == ldrId) {
                                            ldrCoins++;
                                        } else {
                                            resistenciaCoins++;
                                        }
                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                                for (Creature t : buracos) {
                                    if (t.getId() == getElementId(c.getX() - c.getAlcance(), c.getY() + c.getAlcance())) {
                                        c.setOrientation("Noroeste");
                                    }
                                }
                            }
                        } else {
                            c.setOrientation("Noroeste");
                        }
                    } else if (c.getOrientation().equals("Noroeste")) {
                        if (c.getY() - c.getAlcance() >= 0 && c.getX() - c.getAlcance() >= 0) {
                            if (getElementId(c.getX() - c.getAlcance(), c.getY() - c.getAlcance()) == 0) {
                                c.moveNO();
                                c.incKilometros();
                            } else if (getElementId(c.getX() - c.getAlcance(), c.getY() - c.getAlcance()) < 0) {
                                for (Tesouro t : tesouros) {
                                    if (t.getId() == getElementId(c.getX() - c.getAlcance(), c.getY() - c.getAlcance())) {
                                        c.moveNO();
                                        c.incKilometros();
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        if (currentTeamId == ldrId) {
                                            ldrCoins++;
                                        } else {
                                            resistenciaCoins++;
                                        }
                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                                for (Creature t : buracos) {
                                    if (t.getId() == getElementId(c.getX() - c.getAlcance(), c.getY() - c.getAlcance())) {
                                        c.setOrientation("Nordeste");
                                    }
                                }
                            }
                        } else {
                            c.setOrientation("Nordeste");
                        }
                    } else if (c.getOrientation().equals("Norte")) {
                        if (c.getY() - c.getAlcance() >= 0 && c.getX() + c.getAlcance() < columns) {
                            if (getElementId(c.getX() + c.getAlcance(), c.getY() - c.getAlcance()) == 0) {
                                c.moveNE();
                                c.incKilometros();

                            } else if (getElementId(c.getX() + c.getAlcance(), c.getY() - c.getAlcance()) < 0) {
                                for (Tesouro t : tesouros) {
                                    if (t.getId() == getElementId(c.getX() + c.getAlcance(), c.getY() - c.getAlcance())) {
                                        c.moveNE();
                                        c.incKilometros();
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        if (currentTeamId == ldrId) {
                                            ldrCoins++;
                                        } else {
                                            resistenciaCoins++;
                                        }
                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                                for (Creature t : buracos) {
                                    if (t.getId() == getElementId(c.getX() + c.getAlcance(), c.getY() - c.getAlcance())) {
                                        c.setOrientation("Sudeste");
                                    }
                                }
                            }
                        } else {
                            c.setOrientation("Sudeste");
                        }
                    } else if (c.getOrientation().equals("Sul")) {
                        if (c.getY() + c.getAlcance() < rows && c.getX() - c.getAlcance() >= 0) {
                            if (getElementId(c.getX() - c.getAlcance(), c.getY() + c.getAlcance()) == 0) {
                                c.moveSO();
                                c.incKilometros();
                            } else if (getElementId(c.getX() - c.getAlcance(), c.getY() + c.getAlcance()) < 0) {
                                for (Tesouro t : tesouros) {
                                    if (t.getId() == getElementId(c.getX() - c.getAlcance(), c.getY() + c.getAlcance())) {
                                        c.moveSO();
                                        c.incKilometros();
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        if (currentTeamId == ldrId) {
                                            ldrCoins++;
                                        } else {
                                            resistenciaCoins++;
                                        }
                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                                for (Creature t : buracos) {
                                    if (t.getId() == getElementId(c.getX() - c.getAlcance(), c.getY() + c.getAlcance())) {
                                        c.setOrientation("Noroeste");
                                    }
                                }
                            }
                        } else {
                            c.setOrientation("Noroeste");
                        }
                    } else if (c.getOrientation().equals("Este")) {
                        if (c.getY() + c.getAlcance() < rows && c.getX() + c.getAlcance() < columns) {
                            if (getElementId(c.getX() + c.getAlcance(), c.getY() + c.getAlcance()) == 0) {
                                c.moveSE();
                                c.incKilometros();
                            } else if (getElementId(c.getX() + c.getAlcance(), c.getY() + c.getAlcance()) < 0) {

                                for (Tesouro t : tesouros) {
                                    if (t.getId() == getElementId(c.getX() + c.getAlcance(), c.getY() + c.getAlcance())) {
                                        c.moveSE();
                                        c.incKilometros();
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }

                                        if (currentTeamId == ldrId) {
                                            ldrCoins++;
                                        } else {
                                            resistenciaCoins++;
                                        }
                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                                for (Creature t : buracos) {
                                    if (t.getId() == getElementId(c.getX() + c.getAlcance(), c.getY() + c.getAlcance())) {
                                        c.setOrientation("Sudoeste");
                                    }
                                }
                            }
                        } else {
                            c.setOrientation("Sudoeste");
                        }
                    } else if (c.getOrientation().equals("Oeste")) {
                        if (c.getY() - c.getAlcance() >= 0 && c.getX() - c.getAlcance() >= 0) {
                            if (getElementId(c.getX() - c.getAlcance(), c.getY() - c.getAlcance()) == 0) {
                                c.moveNO();
                                c.incKilometros();
                            } else if (getElementId(c.getX() - c.getAlcance(), c.getY() - c.getAlcance()) < 0) {
                                for (Tesouro t : tesouros) {
                                    if (t.getId() == getElementId(c.getX() - c.getAlcance(), c.getY() - c.getAlcance())) {
                                        c.moveNO();
                                        c.incKilometros();
                                        pointInc(c, t.getValor());
                                        if (c.getTeamId() == ldrId) {
                                            ldrCaptura = true;
                                        } else {
                                            resCaptura = true;
                                        }
                                        if (currentTeamId == ldrId) {
                                            ldrCoins++;
                                        } else {
                                            resistenciaCoins++;
                                        }
                                        c.maisPontos(t);
                                        tesouros.remove(t);
                                        creatures.remove(t);
                                        break;
                                    }
                                }
                                for (Creature t : buracos) {
                                    if (t.getId() == getElementId(c.getX() - c.getAlcance(), c.getY() - c.getAlcance())) {
                                        c.setOrientation("Nordeste");
                                    }
                                }
                            }
                        } else {
                            c.setOrientation("Nordeste");
                        }
                    }
                }
            }
        }
        for (Creature c : creaturesC) {
            if (c.tenFeitico) {
                if (!("Congela4Ever".equals(c.getEstado()))) {
                    c.switchFeitico();
                }
            }
        }

        if (ldrCaptura) {
            ldrCoins += 2;
        } else {
            ldrCoins++;
        }
        if (resCaptura) {
            resistenciaCoins += 2;
        } else {
            resistenciaCoins++;
        }
        if (!ldrCaptura && !resCaptura) {
            turnSemCaptura++;
        }
        processCurrentTeamId();
        turnCount++;
    }

    public List<Creature> getCreatures() {
        return creaturesC;
    }

    public boolean gameIsOver() {
        if (tesouros.size() == 0) {
            return true;
        }

        if (turnSemCaptura == 15) {
            return true;
        }

        int pontosRestantes = 0;
        for (Tesouro t : tesouros) {
            pontosRestantes += t.getValor();
        }

        if (pontosLdr > pontosResistencia) {
            if (pontosResistencia + pontosRestantes < pontosLdr) {
                return true;
            }
        }
        if (pontosLdr < pontosResistencia) {
            if (pontosResistencia > pontosRestantes + pontosLdr) {
                return true;
            }
        }
        return false;
    }

    public List<String> getAuthors() {
        ArrayList<String> creditos = new ArrayList<String>();
        creditos.add("   Nome: Héricles Semedo - Numero: 21801188   ");
        creditos.add("   Nome: Armando Carruagem - Numero: 21900252   ");
        return creditos;
    }

    public List<String> getResults() {

        ArrayList<String> resultsCopy = results;

        if (pontosLdr == pontosResistencia) {
            resultsCopy.add("Welcome to FANDEISIA");
            resultsCopy.add("Resultado: EMPATE");
            resultsCopy.add("LDR: " + pontosLdr);
            resultsCopy.add("RESISTENCIA: " + pontosResistencia);
            resultsCopy.add("Nr. de Turnos jogados: " + turnCount);
            resultsCopy.add("-----");
            for (Creature c : creaturesC) {
                resultsCopy.add(c.getId() + " : " + c.getType() + " : " + c.getnOuros() + " : " + c.getnPratas() + " : " + c.getnBronzes() + " : " + c.getPontos());
            }

        } else if (pontosLdr > pontosResistencia) {
            resultsCopy.add("Welcome to FANDEISIA");
            resultsCopy.add("Resultado: Vitória da equipa LDR");
            resultsCopy.add("LDR: " + pontosLdr);
            resultsCopy.add("RESISTENCIA: " + pontosResistencia);
            resultsCopy.add("Nr. de Turnos jogados: " + turnCount);
            resultsCopy.add("-----");
            for (Creature c : creaturesC) {
                resultsCopy.add(c.getId() + " : " + c.getType() + " : " + c.getnOuros() + " : " + c.getnPratas() + " : " + c.getnBronzes() + " : " + c.getPontos());
            }
        } else {
            resultsCopy.add("Welcome to FANDEISIA");
            resultsCopy.add("Resultado: Vitória da equipa RESISTENCIA");
            resultsCopy.add("RESISTENCIA: " + pontosResistencia);
            resultsCopy.add("LDR: " + pontosLdr);
            resultsCopy.add("Nr. de Turnos jogados: " + turnCount);
            resultsCopy.add("-----");
            for (Creature c : creaturesC) {
                resultsCopy.add(c.getId() + " : " + c.getType() + " : " + c.getnOuros() + " : " + c.getnPratas() + " : " + c.getnBronzes() + " : " + c.getPontos());
            }
        }

        return resultsCopy;
    }

    public int getElementId(int x, int y) {
        for (Creature c : creatures) {
            if (c.getX() == x && c.getY() == y) {
                return c.getId();
            }
        }
        return 0;
    }

    public int getCurrentTeamId() {
        return currentTeamId;
    }

    public void processCurrentTeamId() {
        if (currentTeamId == ldrId) {
            currentTeamId = resistenciaId;
        } else {
            currentTeamId = ldrId;
        }
    }

    public int getCurrentScore(int teamID) {
        if (teamID == ldrId) {
            return pontosLdr;
        } else {
            return pontosResistencia;
        }
    }

    public void setCurrentTeamId(int x) {
        currentTeamId = x;
    }

    public void pointInc(Creature c, int valor) {
        if (c.getTeamId() == resistenciaId) {
            pontosResistencia += valor;
        } else {
            pontosLdr += valor;
        }
    }

    public void toggleAI(boolean active) {
        if (active) {
            Random r = new Random();
            int x = r.nextInt(creaturesC.size());
            int y = r.nextInt(9);

            String spell = "EmpurraParaNorte";
            switch (y) {
                case 0:
                    spell = "EmpurraParaNorte";
                case 1:
                    spell = "EmpurraParaEste";
                case 2:
                    spell = "EmpurraParaSul";
                case 3:
                    spell = "EmpurraParaOeste";
                case 4:
                    spell = "ReduzAlcance";
                case 5:
                    spell = "DuplicaAlcance";
                case 6:
                    spell = "Congela";
                case 7:
                    spell = "Congela4Ever";
                default:
                    spell = "Descongela";

            }

            enchant(creaturesC.get(x).getX(), creaturesC.get(x).getY(), spell);
        }
    }

    public boolean validarDuplicaAlcance(Creature c) {

        if (c.getType().equals("Anão")) {

            if (c.getOrientation().equals("Norte")) {
                if (c.getY() - c.getAlcance() * 2 >= 0) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() && cc.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() && b.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        }
                    }

                    if (getElementId(c.getX(), (c.getY() - c.getAlcance() * 2)) == 0) {
                        return true;
                    }
                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Sul")) {
                if (c.getY() + c.getAlcance() * 2 < rows) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() && cc.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() && b.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        }
                    }


                    if (getElementId(c.getX(), (c.getY() + c.getAlcance() * 2)) == 0) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Este")) {
                if (c.getX() + c.getAlcance() * 2 < columns) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() + c.getAlcance() * 2 && cc.getY() == c.getY()) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() + c.getAlcance() * 2 && b.getY() == c.getY()) {
                            return false;
                        }
                    }


                    if (getElementId(c.getX() + c.getAlcance() * 2, c.getY()) == 0) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Oeste")) {
                if (c.getX() - c.getAlcance() * 2 >= 0) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() - c.getAlcance() * 2 && cc.getY() == c.getY()) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() - c.getAlcance() * 2 && b.getY() == c.getY()) {
                            return false;
                        }
                    }


                    if (getElementId((c.getX() - c.getAlcance() * 2), c.getY()) == 0) {
                        return true;
                    }
                } else {
                    return false;
                }
            }

        } else if (c.getType().equals("Dragão")) {
            if (c.getOrientation().equals("Norte")) {
                if (c.getY() - c.getAlcance() * 2 >= 0) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() && cc.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        }
                    }

                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() && b.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        }
                    }

                    if (getElementId(c.getX(), (c.getY() - c.getAlcance() * 2)) == 0) {
                        return true;
                    }
                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Sul")) {
                if (c.getY() + c.getAlcance() * 2 < rows) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() && cc.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        }
                    }

                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() && b.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        }
                    }

                    if (getElementId(c.getX(), (c.getY() + c.getAlcance() * 2)) == 0) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Este")) {

                if (c.getX() + c.getAlcance() * 2 < columns) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() + c.getAlcance() * 2 && cc.getY() == c.getY()) {
                            return false;
                        }
                    }

                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() + c.getAlcance() * 2 && b.getY() == c.getY()) {
                            return false;
                        }
                    }

                    if (getElementId(c.getX() + c.getAlcance() * 2, c.getY()) == 0) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Oeste")) {
                if (c.getX() - c.getAlcance() * 2 >= 0) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() - c.getAlcance() * 2 && cc.getY() == c.getY()) {
                            return false;
                        }
                    }

                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() - c.getAlcance() * 2 && b.getY() == c.getY()) {
                            return false;
                        }
                    }

                    if (getElementId((c.getX() - c.getAlcance() * 2), c.getY()) == 0) {
                        return true;
                    }
                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Nordeste")) {
                if (c.getY() - c.getAlcance() * 2 >= 0 && c.getX() + c.getAlcance() * 2 < columns) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() + c.getAlcance() * 2 && cc.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        }
                    }

                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() + c.getAlcance() * 2 && b.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        }
                    }

                    if (c.getElementId(c.getX() + c.getAlcance() * 2, c.getY() - c.getAlcance() * 2) == 0) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Sudeste")) {
                if (c.getY() + c.getAlcance() * 2 < rows && c.getX() + c.getAlcance() * 2 < columns) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() + c.getAlcance() * 2 && cc.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        }
                    }

                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() + c.getAlcance() * 2 && b.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        }
                    }

                    if (c.getElementId(c.getX() + c.getAlcance() * 2, c.getY() + c.getAlcance() * 2) == 0) {
                        return true;
                    }
                } else {
                    return false;
                }

            } else if (c.getOrientation().equals("Sudoeste")) {
                if (c.getY() + c.getAlcance() * 2 < rows && c.getX() - c.getAlcance() * 2 >= 0) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() - c.getAlcance() * 2 && cc.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        }
                    }

                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() - c.getAlcance() * 2 && b.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        }
                    }

                    if (c.getElementId(c.getX() - c.getAlcance() * 2, c.getY() + c.getAlcance() * 2) == 0) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Noroeste")) {
                if (c.getY() - c.getAlcance() * 2 >= 0 && c.getX() - c.getAlcance() * 2 >= 0) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() - c.getAlcance() * 2 && cc.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        }
                    }

                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() - c.getAlcance() * 2 && b.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        }
                    }

                    if (c.getElementId(c.getX() - c.getAlcance() * 2, c.getY() - c.getAlcance() * 2) == 0) {
                        return true;
                    }

                } else {
                    return false;
                }
            }
        } else if (c.getType().equals("Elfo")) {

            if (c.getOrientation().equals("Norte")) {
                if (c.getY() - c.getAlcance() * 2 >= 0) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() && cc.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        } else if (cc.getX() == c.getX() && cc.getY() == c.getY() - c.getAlcance() * 2 + 1) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() && b.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        }
                    }


                    if ((getElementId(c.getX(), (c.getY() - c.getAlcance() * 2)) == 0)) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Sul")) {
                if (c.getY() + c.getAlcance() * 2 < rows) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() && cc.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        } else if (cc.getX() == c.getX() && cc.getY() == c.getY() + c.getAlcance() * 2 - 1) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() && b.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        }
                    }


                    if ((getElementId(c.getX(), (c.getY() + c.getAlcance() * 2)) == 0)) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Este")) {
                if (c.getX() + c.getAlcance() * 2 < columns) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() + c.getAlcance() * 2 && cc.getY() == c.getY()) {
                            return false;
                        } else if (cc.getX() == c.getX() + c.getAlcance() * 2 - 1 && cc.getY() == c.getY()) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() + c.getAlcance() * 2 && b.getY() == c.getY()) {
                            return false;
                        }
                    }


                    if ((getElementId(c.getX() + c.getAlcance() * 2, c.getY()) == 0)) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Oeste")) {
                if (c.getX() - c.getAlcance() * 2 >= 0) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() - c.getAlcance() * 2 && cc.getY() == c.getY()) {
                            return false;
                        } else if (cc.getX() == c.getX() - c.getAlcance() * 2 + 1 && cc.getY() == c.getY()) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() - c.getAlcance() * 2 && b.getY() == c.getY()) {
                            return false;
                        }
                    }


                    if ((getElementId(c.getX() - c.getAlcance() * 2, c.getY()) == 0)) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Nordeste")) {

                if (c.getY() - c.getAlcance() * 2 >= 0 && c.getX() + c.getAlcance() * 2 < columns) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() + c.getAlcance() * 2 && cc.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        } else if (cc.getX() == c.getX() + c.getAlcance() * 2 - 1 && cc.getY() == c.getY() - c.getAlcance() * 2 + 1) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() + c.getAlcance() * 2 && b.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        }
                    }


                    if ((getElementId(c.getX() + c.getAlcance() * 2, c.getY() - c.getAlcance() * 2) == 0)) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Sudeste")) {

                if (c.getY() + c.getAlcance() * 2 < rows && c.getX() + c.getAlcance() * 2 < columns) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() + c.getAlcance() * 2 && cc.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        } else if (cc.getX() == c.getX() + c.getAlcance() * 2 - 1 && cc.getY() == c.getY() + c.getAlcance() * 2 - 1) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() + c.getAlcance() * 2 && b.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        }
                    }


                    if ((getElementId(c.getX() + c.getAlcance() * 2, c.getY() + c.getAlcance() * 2) == 0)) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Sudoeste")) {
                if (c.getY() + c.getAlcance() * 2 < rows && c.getX() - c.getAlcance() * 2 >= 0) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() - c.getAlcance() * 2 && cc.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        } else if (cc.getX() == c.getX() - c.getAlcance() * 2 + 1 && cc.getY() == c.getY() + c.getAlcance() * 2 - 1) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() - c.getAlcance() * 2 && b.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        }
                    }


                    if ((getElementId(c.getX() - c.getAlcance() * 2, c.getY() + c.getAlcance() * 2) == 0)) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Noroeste")) {

                if (c.getY() - c.getAlcance() * 2 >= 0 && c.getX() - c.getAlcance() * 2 >= 0) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() - c.getAlcance() * 2 && cc.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        } else if (cc.getX() == c.getX() - c.getAlcance() * 2 + 1 && cc.getY() == c.getY() - c.getAlcance() * 2 + 1) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() - c.getAlcance() * 2 && b.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        }
                    }

                    if ((getElementId(c.getX() - c.getAlcance() * 2, c.getY() - c.getAlcance() * 2) == 0)) {
                        return true;
                    }

                } else {
                    return false;
                }
            }
        } else if (c.getType().equals("Gigante")) {
            if (c.getOrientation().equals("Norte")) {
                for (Gigante g : gigantes) {
                    if (g.getX() == c.getX() && g.getY() == c.getY() - c.getAlcance() * 2 + 1) {
                        return false;
                    } else if (g.getX() == c.getX() && g.getY() == c.getY() - c.getAlcance() * 2 + 2) {
                        return false;
                    }
                }
                if (c.getY() - c.getAlcance() * 2 >= 0) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() && cc.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() && b.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        }
                    }

                    if (getElementId(c.getX(), (c.getY() - c.getAlcance() * 2)) == 0) {
                        return true;
                    }
                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Sul")) {
                for (Gigante g : gigantes) {
                    if (g.getX() == c.getX() && g.getY() == c.getY() + c.getAlcance() * 2 - 1) {
                        return false;
                    } else if (g.getX() == c.getX() && g.getY() == c.getY() + c.getAlcance() * 2 - 2) {
                        return false;
                    }

                }
                if (c.getY() + c.getAlcance() * 2 < rows) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() && cc.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() && b.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        }
                    }


                    if (getElementId(c.getX(), (c.getY() + c.getAlcance() * 2)) == 0) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Este")) {
                for (Gigante g : gigantes) {
                    if (g.getX() == c.getX() + c.getAlcance() * 2 - 1 && g.getY() == c.getY()) {
                        return false;
                    } else if (g.getX() == c.getX() + c.getAlcance() * 2 - 2 && g.getY() == c.getY()) {
                        return false;
                    }

                }
                if (c.getX() + c.getAlcance() * 2 < columns) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() + c.getAlcance() * 2 && cc.getY() == c.getY()) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() + c.getAlcance() * 2 && b.getY() == c.getY()) {
                            return false;
                        }
                    }

                    if (getElementId(c.getX() + c.getAlcance() * 2, c.getY()) == 0) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Oeste")) {
                for (Gigante g : gigantes) {
                    if (g.getX() == c.getX() - c.getAlcance() * 2 + 1 && g.getY() == c.getY()) {
                        return false;
                    } else if (g.getX() == c.getX() - c.getAlcance() * 2 + 2 && g.getY() == c.getY()) {
                        return false;
                    }

                }
                if (c.getX() - c.getAlcance() * 2 >= 0) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() - c.getAlcance() * 2 && cc.getY() == c.getY()) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() - c.getAlcance() * 2 && b.getY() == c.getY()) {
                            return false;
                        }
                    }


                    if (getElementId((c.getX() - c.getAlcance() * 2), c.getY()) == 0) {
                        return true;
                    }
                } else {
                    return false;
                }
            }
        } else if (c.getType().equals("Humano")) {
            if (c.getOrientation().equals("Norte")) {
                if (c.getY() - c.getAlcance() * 2 >= 0) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() && cc.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        } else if (cc.getX() == c.getX() && cc.getY() == c.getY() - c.getAlcance() * 2 + 1) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() && b.getY() == c.getY() - c.getAlcance() * 2) {
                            return false;
                        } else if (b.getX() == c.getX() && b.getY() == c.getY() - c.getAlcance() * 2 + 1) {
                            return false;
                        }
                    }


                    if ((getElementId(c.getX(), (c.getY() - c.getAlcance() * 2)) == 0)) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Sul")) {
                if (c.getY() + c.getAlcance() * 2 < rows) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() && cc.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        } else if (cc.getX() == c.getX() && cc.getY() == c.getY() + c.getAlcance() * 2 - 1) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() && b.getY() == c.getY() + c.getAlcance() * 2) {
                            return false;
                        } else if (b.getX() == c.getX() && b.getY() == c.getY() + c.getAlcance() * 2 - 1) {
                            return false;
                        }
                    }


                    if ((getElementId(c.getX(), (c.getY() + c.getAlcance() * 2)) == 0)) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Este")) {
                if (c.getX() + c.getAlcance() * 2 < columns) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() + c.getAlcance() * 2 && cc.getY() == c.getY()) {
                            return false;
                        } else if (cc.getX() == c.getX() + c.getAlcance() * 2 - 1 && cc.getY() == c.getY()) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() + c.getAlcance() * 2 && b.getY() == c.getY()) {
                            return false;
                        } else if (b.getX() == c.getX() + c.getAlcance() * 2 - 1 && b.getY() == c.getY()) {
                            return false;
                        }
                    }


                    if ((getElementId(c.getX() + c.getAlcance() * 2, c.getY()) == 0)) {
                        return true;
                    }

                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Oeste")) {
                if (c.getX() - c.getAlcance() * 2 >= 0) {


                    for (Creature cc : creaturesC) {
                        if (cc.getX() == c.getX() - c.getAlcance() * 2 && cc.getY() == c.getY()) {
                            return false;
                        } else if (cc.getX() == c.getX() - c.getAlcance() * 2 + 1 && cc.getY() == c.getY()) {
                            return false;
                        }
                    }
                    for (Creature b : buracos) {
                        if (b.getX() == c.getX() - c.getAlcance() * 2 && b.getY() == c.getY()) {
                            return false;
                        } else if (b.getX() == c.getX() - c.getAlcance() * 2 + 1 && b.getY() == c.getY()) {
                            return false;
                        }
                    }


                    if ((getElementId(c.getX() - c.getAlcance() * 2, c.getY()) == 0)) {
                        return true;
                    }

                } else {
                    return false;
                }
            }
        } else if (c.getType().equals("Angry Bird")) {
            if (c.getOrientation().equals("Nordeste")) {
                if (c.getY() - c.getAlcance() * 2 >= 0 && c.getX() + c.getAlcance() * 2 < columns) {
                    if (getElementId(c.getX() + c.getAlcance() * 2, c.getY() - c.getAlcance() * 2) == 0) {
                        return true;

                    } else if (getElementId(c.getX() + c.getAlcance() * 2, c.getY() - c.getAlcance() * 2) < 0) {

                        for (Creature t : buracos) {
                            if (t.getId() == getElementId(c.getX() + c.getAlcance() * 2, c.getY() - c.getAlcance() * 2)) {
                                return false;
                            }
                        }
                    }
                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Sudeste")) {
                if (c.getY() + c.getAlcance() * 2 < rows && c.getX() + c.getAlcance() * 2 < columns) {
                    if (getElementId(c.getX() + c.getAlcance() * 2, c.getY() + c.getAlcance() * 2) == 0) {
                        return true;
                    } else if (getElementId(c.getX() + c.getAlcance() * 2, c.getY() + c.getAlcance() * 2) < 0) {


                        for (Creature t : buracos) {
                            if (t.getId() == getElementId(c.getX() + c.getAlcance() * 2, c.getY() + c.getAlcance() * 2)) {
                                return false;
                            }
                        }
                    }
                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Sudoeste")) {
                if (c.getY() + c.getAlcance() * 2 < rows && c.getX() - c.getAlcance() * 2 >= 0) {
                    if (getElementId(c.getX() - c.getAlcance() * 2, c.getY() + c.getAlcance() * 2) == 0) {
                        return true;
                    } else if (getElementId(c.getX() - c.getAlcance() * 2, c.getY() + c.getAlcance() * 2) < 0) {

                        for (Creature t : buracos) {
                            if (t.getId() == getElementId(c.getX() - c.getAlcance() * 2, c.getY() + c.getAlcance() * 2)) {
                                return false;
                            }
                        }
                    }
                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Noroeste")) {
                if (c.getY() - c.getAlcance() * 2 >= 0 && c.getX() - c.getAlcance() * 2 >= 0) {
                    if (getElementId(c.getX() - c.getAlcance() * 2, c.getY() - c.getAlcance() * 2) == 0) {
                        return true;
                    } else if (getElementId(c.getX() - c.getAlcance() * 2, c.getY() - c.getAlcance() * 2) < 0) {

                        for (Creature t : buracos) {
                            if (t.getId() == getElementId(c.getX() - c.getAlcance() * 2, c.getY() - c.getAlcance() * 2)) {
                                return false;
                            }
                        }
                    }
                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Norte")) {
                if (c.getY() - c.getAlcance() * 2 >= 0 && c.getX() + c.getAlcance() * 2 < columns) {
                    if (getElementId(c.getX() + c.getAlcance() * 2, c.getY() - c.getAlcance() * 2) == 0) {
                        return true;

                    } else if (getElementId(c.getX() + c.getAlcance() * 2, c.getY() - c.getAlcance() * 2) < 0) {

                        for (Creature t : buracos) {
                            if (t.getId() == getElementId(c.getX() + c.getAlcance() * 2, c.getY() - c.getAlcance() * 2)) {
                                return false;
                            }
                        }
                    }
                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Sul")) {
                if (c.getY() + c.getAlcance() * 2 < rows && c.getX() - c.getAlcance() * 2 >= 0) {
                    if (getElementId(c.getX() - c.getAlcance() * 2, c.getY() + c.getAlcance() * 2) == 0) {
                        return true;
                    } else if (getElementId(c.getX() - c.getAlcance() * 2, c.getY() + c.getAlcance() * 2) < 0) {

                        for (Creature t : buracos) {
                            if (t.getId() == getElementId(c.getX() - c.getAlcance() * 2, c.getY() + c.getAlcance() * 2)) {
                                return false;
                            }
                        }
                    }
                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Este")) {
                if (c.getY() + c.getAlcance() * 2 < rows && c.getX() + c.getAlcance() * 2 < columns) {
                    if (getElementId(c.getX() + c.getAlcance() * 2, c.getY() + c.getAlcance() * 2) == 0) {
                        return true;
                    } else if (getElementId(c.getX() + c.getAlcance() * 2, c.getY() + c.getAlcance() * 2) < 0) {

                        for (Creature t : buracos) {
                            if (t.getId() == getElementId(c.getX() + c.getAlcance() * 2, c.getY() + c.getAlcance() * 2)) {
                                return false;
                            }
                        }
                    }
                } else {
                    return false;
                }
            } else if (c.getOrientation().equals("Oeste")) {
                if (c.getY() - c.getAlcance() * 2 >= 0 && c.getX() - c.getAlcance() * 2 >= 0) {
                    if (getElementId(c.getX() - c.getAlcance() * 2, c.getY() - c.getAlcance() * 2) == 0) {
                        return true;
                    } else if (getElementId(c.getX() - c.getAlcance() * 2, c.getY() - c.getAlcance() * 2) < 0) {

                        for (Creature t : buracos) {
                            if (t.getId() == getElementId(c.getX() - c.getAlcance() * 2, c.getY() - c.getAlcance() * 2)) {
                                return false;
                            }
                        }
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public void setTurnCounts(int turnCount) {
        this.turnCount = turnCount;
    }

    public int getTurnCounts() {
        return turnCount;
    }

    public int getPontosLdr() {
        return pontosLdr;
    }

    public int getPontosResistencia() {
        return pontosResistencia;
    }

    public void setPontosLdr(int p) {
        pontosLdr = p;
    }

    public void setPontosResistencia(int p) {
        pontosResistencia = p;
    }
}