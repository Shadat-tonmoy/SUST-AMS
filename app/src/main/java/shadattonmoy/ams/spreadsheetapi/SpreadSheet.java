package shadattonmoy.ams.spreadsheetapi;

/**
 * Created by Shadat Tonmoy on 2/1/2018.
 */

public class SpreadSheet {
    private String name,id,properties;

    public SpreadSheet(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}
