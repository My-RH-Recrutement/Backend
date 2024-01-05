package ma.youcode.myrhbackendapi.utils;

import java.util.UUID;

public class Utils {
    public static UUID pareseStringToUUID(String uuidString) {
        try { return UUID.fromString(uuidString); }
        catch (IllegalArgumentException exception) { throw new IllegalArgumentException("UUID is Invalid"); }
    }
}
