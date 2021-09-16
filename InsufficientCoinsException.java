package pt.ulusofona.lp2.fandeisiaGame;

public class InsufficientCoinsException extends Exception {

    private int ldrId = 10;
    private int resistenciaId = 20;
    private int ldrCoins;
    private int resistenciaCoins;

    private String message;

    public InsufficientCoinsException(String message, int ldrCoins, int resistenciaCoins) {
        this.message = message;
        this.ldrCoins = ldrCoins;
        this.resistenciaCoins = resistenciaCoins;
    }

    public String getMessage() {
        return message;
    }

    public boolean teamRequiresMoreCoins(int teamId) {
        if ("Nenhuma das equipas respeitou o plafond de moedas fantásticas".equals(message)) {
            return true;
        }
        if ("A equipa LDR não respeitou o plafond de moedas fantásticas".equals(message) && teamId == ldrId) {
            return true;
        }
        if ("A equipa RESISTÊNCIA não respeitou o plafond de moedas fantásticas".equals(message) && teamId == resistenciaId) {
            return true;
        }
        return false;
    }

    public int getRequiredCoinsForTeam(int teamID) {
        if (teamID == ldrId) {
            return 50 - ldrCoins;
        }
        return 50 - resistenciaCoins;
    }

}
