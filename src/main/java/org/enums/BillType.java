package org.enums;

/**
 *
 * @author ngus
 */
public enum BillType {
    ELECTRIC("ELECTRIC"),
    WATER("WATER"),
    INTERNET("INTERNET");

    private String type;

    BillType(String type) {
        this.type = type;
    };

    public static BillType fromValue(String value) {
        for (BillType billType : BillType.values()) {
            if (billType.name().equalsIgnoreCase(value)) {
                return billType;
            }
        }
        throw new IllegalArgumentException("Invalid value");
    }
}
