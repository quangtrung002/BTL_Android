package cards;

public class CardModel {

    private int resourceID;
    private String title;

    public CardModel(int resourceID, String title) {
        this.resourceID = resourceID;
        this.title = title;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
