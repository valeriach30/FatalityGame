/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Log;

import java.util.Date;


/**
 *
 * @author Vale
 */
public class TableRow {
    private int id;
    private String json;
    private Date createTS;

    public TableRow(int id, String json, Date createTS) {
        this.id = id;
        this.json = json;
        this.createTS = createTS;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Date getCreateTS() {
        return createTS;
    }

    public void setCreateTS(Date createTS) {
        this.createTS = createTS;
    }

    @Override
    public String toString() {
        return "Fila{" + "id=" + id + ", json=" + json + ", createTS=" + createTS + '}';
    }
    
}
