package edu.upc.dsa.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"id", "name", "manufacturer", "model", "hours", "maintenance"})
public class Drone {

    Integer id;
    String name;
    String manufacturer;
    String model;
    Float hours;
    Boolean maintenance;

    //Constructor with no arguments that allows the serialization of a Drone object
    public Drone() {
    }

    //Constructor that defines the main characteristics of a Drone
    public Drone(Integer id, String name, String manufacturer, String model, Float hours) {
        this.setId(id);
        this.setName(name);
        this.setManufacturer(manufacturer);
        this.setModel(model);
        if (hours == null)
        {
            setHours(0f);
        }
        else
        {
            this.setHours(hours);
        }
        this.setMaintenance(false);
    }

    @XmlElement(name = "id")
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "manufacturer")
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @XmlElement(name = "model")
    public String getModel(){
        return model;
    }

    public void setModel(String model){
        this.model = model;
    }

    @XmlElement(name = "hours")
    public Float getHours()
    {
        return hours;
    }

    public void setHours(Float hours)
    {
        this.hours = hours;
    }

    @XmlElement(name = "maintenance")
    public Boolean getMaintenance()
    {
        return maintenance;
    }

    public void setMaintenance(Boolean maintenance)
    {
        this.maintenance = maintenance;
    }

    @Override
    public String toString() {
        return "Drone [id=" + id + ", name=" + name + ", manufacturer=" + manufacturer +", model=" + model + ", hours=" + hours + "]";
    }

}