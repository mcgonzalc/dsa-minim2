package edu.upc.dsa.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"type", "horizontal", "vertical"})
public class PointofInterest {

    ElementType type;
    Integer horizontal;
    Integer vertical;

    //Constructor with no arguments that allows the serialization of a PointofInterest object
    public PointofInterest() {
    }

    @XmlEnum
    public enum ElementType {
        @XmlEnumValue("door") DOOR,
        @XmlEnumValue("wall") WALL,
        @XmlEnumValue("bridge") BRIDGE,
        @XmlEnumValue("potion") POTION,
        @XmlEnumValue("sword") SWORD,
        @XmlEnumValue("coin") COIN,
        @XmlEnumValue("grass") GRASS,
        @XmlEnumValue("tree") TREE
    }

    //Constructor that defines the main characteristics of a PointofInterest
    public PointofInterest(ElementType type, Integer horizontal, Integer vertical) {
        this.setType(type);
        this.setHorizontal(horizontal);
        this.setVertical(vertical);
    }

    @XmlElement(name = "type")
    public ElementType getType() {
        return this.type;
    }

    public void setType(ElementType id) {
        this.type = type;
    }

    @XmlElement(name = "horizontal")
    public Integer getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(Integer horizontal) {
        this.horizontal = horizontal;
    }

    @XmlElement(name = "vertical")
    public Integer getVertical() {
        return vertical;
    }

    public void setVertical(Integer vertical) {
        this.vertical = vertical;
    }

    @Override
    public String toString() {
        return "PointofInterest [type=" + type + ", horizontal=" + horizontal + ", vertical=" + vertical +"]";
    }

}