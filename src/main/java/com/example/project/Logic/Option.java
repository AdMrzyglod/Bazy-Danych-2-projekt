package com.example.project.Logic;

public enum Option {
    YES("Tak"),
    NO("Nie"),
    BOTH("Oba");

    private String displayName;
    Option(String displayName){
        this.displayName=displayName;
    }
    public String getDisplayName() {
        return displayName;
    }

    public static Option fromString(String text) {
        for (Option option : Option.values()) {
            if (option.getDisplayName().equalsIgnoreCase(text)) {
                return option;
            }
        }
        return null;
    }
}
