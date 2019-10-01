package com.niteroomcreation.beginermade.model;

/**
 * Created by Septian Adi Wijaya on 01/10/19
 */
public class TokohModel {

    public TokohModel(String name, String desc, int image) {
        this.name = name;
        this.desc = desc;
        this.image = image;
    }

    private String name;
    private String desc;
    private int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "TokohModel{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", image=" + image +
                '}';
    }
}
