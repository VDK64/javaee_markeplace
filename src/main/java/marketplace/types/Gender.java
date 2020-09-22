package marketplace.types;

/**
 * Enum which represents type of gender.
 */
public enum Gender {
    MALE("Mr."),
    FEMALE("Ms.");

    private final String prefix;

    Gender(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

}
