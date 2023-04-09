package categorys;

import java.util.List;

import cards.CardModel;

public class CategoryModel {
    private String title;
    private List<CardModel> cards;

    public CategoryModel(String title, List<CardModel> cards) {
        this.title = title;
        this.cards = cards;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CardModel> getCards() {
        return cards;
    }

    public void setCards(List<CardModel> cards) {
        this.cards = cards;
    }
}
