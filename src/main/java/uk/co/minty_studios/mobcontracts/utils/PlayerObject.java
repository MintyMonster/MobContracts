package uk.co.minty_studios.mobcontracts.utils;

import java.util.UUID;


public class PlayerObject {

    private final UUID uuid;
    private final String name;
    private final int commonSlain;
    private final int epicSlain;
    private final int legendarySlain;
    private final int totalSlain;
    private final int currentXp;
    private final int currentLevel;
    private final int totalXp;

    public PlayerObject(UUID uuid,
                        String name,
                        int commonSlain,
                        int epicSlain,
                        int legendarySlain,
                        int totalSlain,
                        int currentXp,
                        int currentLevel,
                        int totalXp) {

        this.uuid = uuid;
        this.name = name;
        this.commonSlain = commonSlain;
        this.epicSlain = epicSlain;
        this.legendarySlain = legendarySlain;
        this.totalSlain = totalSlain;
        this.currentXp = currentXp;
        this.currentLevel = currentLevel;
        this.totalXp = totalXp;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public int getCommonSlain() {
        return commonSlain;
    }

    public int getEpicSlain() {
        return epicSlain;
    }

    public int getLegendarySlain() {
        return legendarySlain;
    }

    public int getTotalSlain() {
        return totalSlain;
    }

    public int getCurrentXp() {
        return currentXp;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getTotalXp() {
        return totalXp;
    }

    /*public int getCommonOwned(){
        return ownedCommon;
    }

    public int getEpicOwned(){
        return ownedEpic;
    }

    public int getLegendaryOwned(){
        return ownedLegendary;
    }*/
}
