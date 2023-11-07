package isle.academy.healing_leaf.helpers;

import java.util.Date;

public class TimeHelper {

    public static long getCurrentTimeInIsleFormat() {
        return new Date().getTime() / 1000L;
    }

}
