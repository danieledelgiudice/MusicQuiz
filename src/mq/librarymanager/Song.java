package mq.librarymanager;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map.Entry;


/**
 * @author Daniele
 */
public class Song implements Serializable {

    public Song() {
        properties = new EnumMap<Fields, String>(Fields.class);
    }

    public void setProperty(Fields property, String value) {
        properties.put(property, value);
    }
    
    public String getProperty(Fields property) {
        return properties.get(property);
    }

    @Override public String toString() {
        int i = 1;
        StringBuilder sb = new StringBuilder();
        sb.append("Song Object:\n");
        for (Entry<Fields, String> entry : properties.entrySet()) {
            for (int j = 1; j <= i; j++) {
                sb.append("  ");
            }
            i++;
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    private EnumMap<Fields, String> properties;
}
