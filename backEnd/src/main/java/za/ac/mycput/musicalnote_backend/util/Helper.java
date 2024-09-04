package za.ac.mycput.musicalnote_backend.util;



import java.util.Date;
import java.util.List;

public class Helper {

    // Check if a Long object is null or zero
    public static boolean isNullOrEmpty(Long obj) {
        return obj == null || obj == 0;
    }

    // Check if a String object is null or empty
    public static boolean isNullOrEmpty(String obj) {
        return obj == null || obj.trim().isEmpty();
    }

    // Check if a Date object is null
    public static boolean isNullOrEmpty(Date obj) {
        return obj == null;
    }

    // Check if a List object is null or empty
    public static boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    // Check if a generic object is null (this will handle User and other objects)
    public static boolean isNullOrEmpty(Object obj) {
        return obj == null;
    }
}

