package mq.librarymanager;

/**
 *
 * @author Daniele
 */
public enum Fields {
    TITLE, ARTIST, ALBUM, YEAR, PATH;

    @Override public String toString() {
        switch (this) {
            case TITLE:
                return "Title";
            case ARTIST:
                return "Artist";
            case ALBUM:
                return "Album";
            case YEAR:
                return "Year";
            case PATH:
                return "Path";
            default:
                return "";
        }
    }
}
