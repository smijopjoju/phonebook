package my.demo.phonebook.core;

import java.util.HashMap;
import java.util.Map;

public enum ContactProperties {
    CONTACT_NAME(0),
    PERSONAL_PHONE_NUMBER(1),
    OFFICE_PHONE_NUMBER(2),
    HOME_PHONE_NUMBER(3),
    EMAIL_ID(4);

    private static final Map<Integer,ContactProperties> contractProp = new HashMap<>();
    private int index;

    static {
        for(ContactProperties property : ContactProperties.values()) {
            contractProp.put(property.getIndex(),property);
        }
    }
    ContactProperties(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public static ContactProperties getContactProp(int index) {
        if(contractProp.containsKey(index)) {
            return contractProp.get(index);
        }
        return null;
    }
}
