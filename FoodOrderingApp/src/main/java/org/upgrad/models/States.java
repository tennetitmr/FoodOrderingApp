package org.upgrad.models;

import javax.persistence.*;

/* Controller  ------> Services Layer ---> Data Access Layer (Model)
 * States model: Map attributes  ----> columns in the states table in the restaurant database.
 * Also,Contains Annotations, getters and setters. Annotations map the fields to table columns.
 */

@Entity
@Table(name = "STATES")
public class States {

    @Id
    private int id;

    @Column(name = "state_name", nullable = false)
    private String stateName;

    //Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }


}
