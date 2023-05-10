package com.example.project.Logic;

public enum Category {

    ARCADE("zręcznościowe"),
    ADVENTURE("przygodowe"),
    ROLE_PLAY("fabularne"),
    SPORTS("sportowe"),
    STRATEGY("strategiczne"),
    SIMULATION("symulacyjne");


    private String displayName;
    Category(String displayName){
        this.displayName=displayName;
    }
    public String getDisplayName() {
        return displayName;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.getDisplayName().equalsIgnoreCase(text)) {
                return category;
            }
        }
        return null;
    }
}
