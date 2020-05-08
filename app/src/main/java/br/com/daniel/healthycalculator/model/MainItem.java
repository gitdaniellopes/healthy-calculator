package br.com.daniel.healthycalculator.model;

public class MainItem {

    private final int id;
    private final int imgId;
    private final int textId;
    private final int colorValueId;

    public MainItem(int id, int imgId, int textId, int colorValueId) {
        this.id = id;
        this.imgId = imgId;
        this.textId = textId;
        this.colorValueId = colorValueId;
    }

    public int getId() {
        return id;
    }

    public int getImgId() {
        return imgId;
    }

    public int getTextId() {
        return textId;
    }

    public int getColorValueId() {
        return colorValueId;
    }
}
