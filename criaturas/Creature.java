package pt.ulusofona.lp2.fandeisiaGame;

public class Creature implements Comparable<Creature> {

    protected int id;
    protected String type;
    protected int teamId;
    protected int pontos;
    protected int x;
    protected int y;
    protected int alcance;
    protected int valor;
    private int nOuros;
    private int nPratas;
    private int nBronzes;
    protected String orientation;
    protected String estado;
    protected boolean tenFeitico;
    protected String imagePNG;
    private int numFeiticos;
    private int kilometros;

    public Creature() {
        type = "Crazy emoji black";
        orientation = "Norte";
        imagePNG = "crazy_emoji_black.png";
    }

    public Creature(int id, String type, int teamId, int x, int y, String orientation) {
        this.id = id;
        this.type = type;
        this.teamId = teamId;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public Creature(int id, String type, int x, int y) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public int getAlcance() {
        return alcance;
    }

    public int getKilometros() {
        return kilometros;
    }

    public int getNumFeiticos() {
        return numFeiticos;
    }

    public void incNumFeitico() {
        numFeiticos++;
    }

    public void incKilometros() {
        kilometros += alcance;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getImagePNG() {
        return imagePNG;
    }

    public String toString() {
        return id + " | " + type + " | " + teamId + " | " + getTotalTesouros() + " @ (" + x + ", " + y + ") " + orientation;
    }

    public String toString2() {
        String linha = "id: " + id + ", type: " + type + ", teamId: " + teamId + ", x: " + x + ", y: " + y + ", orientation: " + orientation;
        return linha;
    }

    public String toString3() {
        String linha = "id: " + id + ", type: " + type + ", x: " + x + ", y: " + y;
        return linha;
    }

    public String stringMaisCarregadas() {
        String linha = id + ":" + this.getTotalTesouros();
        return linha;
    }

    public String stringMaisRicas() {
        String linha = id + ":" + pontos + ":" + this.getTotalTesouros();
        return linha;
    }

    public String stringAlvosFavoritos() {
        String linha = id + ":" + teamId + ":" + numFeiticos;
        return linha;
    }

    public String stringMaisViajadas() {
        String linha = id + ":" + kilometros;
        return linha;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int x) {
        teamId = x;
    }

    public void setImagePNG(String imagePNG) {
        this.imagePNG = imagePNG;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String s) {
        orientation = s;
    }

    public String getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setId(int x) {
        this.id = x;
    }

    public void setType(String x) {
        type = x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void resetAlcance() {
        alcance = 1;
    }

    public void switchFeitico() {
        if (tenFeitico) {
            estado = null;
            tenFeitico = false;
        } else {
            tenFeitico = true;
        }
    }

    public void congela() {
        estado = "Congela";
    }

    public void congela4Ever() {
        estado = "Congela4Ever";
    }

    public void descongela() {
        estado = "Descongela";
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getElementId(int x, int y) {
        if (this.x == x && this.y == y) {
            return id;
        }
        return 0;
    }

//---------------------- Movimento -----------------------

    public void moveNorte() {
        y = y - alcance;
    }

    public void moveSul() {
        y = y + alcance;
    }

    public void moveEste() {
        x = x + alcance;
    }

    public void moveOeste() {
        x = x - alcance;
    }

    public void moveNO() {
        x = x - alcance;
        y = y - alcance;
    }

    public void moveSO() {
        x = x - alcance;
        y = y + alcance;
    }

    public int compareTo(Creature c) {
        if (this.id < c.id) {
            return -1;
        } else {
            return 1;
        }
    }

    public void moveNE() {
        x = x + alcance;
        y = y - alcance;
    }

    public void moveSE() {
        x = x + alcance;
        y = y + alcance;
    }

    public void reduzAlcance() {
        alcance = 1;
    }

    public void duplicaAlcance() {
        alcance = 2 * alcance;
    }

    public void empurraNorte() {
        estado = "EmpurraParaNorte";
        y = y - 1;
    }

    public void empurraSul() {
        estado = "EmpurraParaSul";
        y = y + 1;
    }

    public void empurraEste() {
        estado = "EmpurraParaEste";
        x = x + 1;
    }

    public void empurraOeste() {
        estado = "EmpurraParaOeste";
        x = x - 1;
    }

    void setAlcance(int alcance) {
        this.alcance = alcance;
    }

//------------------------------------------------------------------------

    public void maisPontos(Tesouro t) {

        this.pontos += t.getValor();

        if ("gold".equals(t.getType())) {
            nOuros++;
        } else if ("silver".equals(t.getType())) {
            nPratas++;
        } else if ("bronze".equals(t.getType())) {
            nBronzes++;
        }
    }

    public int getTotalTesouros() {
        return nOuros + nBronzes + nPratas;
    }

    public int getPontos() {
        return pontos;
    }

    public int getnOuros() {
        return nOuros;
    }

    public int getnPratas() {
        return nPratas;
    }

    public int getnBronzes() {
        return nBronzes;
    }

    public void addOuros() {
        nOuros++;
    }

    public void addPratas() {
        nPratas++;
    }

    public void addBronzes() {
        nBronzes++;
    }
}