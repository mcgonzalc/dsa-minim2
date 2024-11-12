package edu.upc.dsa.models;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

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