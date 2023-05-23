package com.example.project.Logic;

public enum CategoryName {
    ARCADE("zręcznościowe"),
    ADVENTURE("przygodowe"),
    ROLE_PLAY("fabularne"),
    SPORTS("sportowe"),
    STRATEGY("strategiczne"),
    SIMULATION("symulacyjne");


    private String displayName;
    CategoryName(String displayName){
        this.displayName=displayName;
    }
    public String getDisplayName() {
        return displayName;
    }

    public static CategoryName fromString(String text) {
        for (CategoryName categoryName : CategoryName.values()) {
            if (categoryName.getDisplayName().equalsIgnoreCase(text)) {
                return categoryName;
            }
        }
        return null;
    }
}

