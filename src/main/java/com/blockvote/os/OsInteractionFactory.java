package com.blockvote.os;

import static com.blockvote.os.OsInteraction.UNIX;
import static com.blockvote.os.OsInteraction.WINDOWS;
import static java.lang.System.getProperty;

public abstract class OsInteractionFactory {

    public static OsInteraction createOsInteraction(String os) {
        switch (os) {
            case WINDOWS:
                return new WindowsInteraction();
            case UNIX:
                return new UnixInteraction();
            default:
                return getProperty("os.name").startsWith("Windows") ? new WindowsInteraction() : new UnixInteraction();
        }
    }

}
