package by.kos.techinventory;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tech")
public class TechItem {

  @PrimaryKey(autoGenerate = true)
  private int id;
  private String name;
  private String invent;
  private int condition;

  public TechItem(int id, String name, String invent, int condition) {
    this.id = id;
    this.name = name;
    this.invent = invent;
    this.condition = condition;
  }

  @Ignore
  public TechItem(String name, String invent, int condition) {
    this.name = name;
    this.invent = invent;
    this.condition = condition;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getInvent() {
    return invent;
  }

  public int getCondition() {
    return condition;
  }
}
