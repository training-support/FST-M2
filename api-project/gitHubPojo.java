package gitHubRestAssuredTesting;

public class gitHubPojo {
    String title;
    String key;

    public gitHubPojo(String title,String key) {
        this.title = title;
        this.key= key;
    }

    public String getTitle() {
        return title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}