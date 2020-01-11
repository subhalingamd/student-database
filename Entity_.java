import java.util.Iterator;

public interface Entity_ { // Entities Classes Hostel, Dept, and Course all have this functionality. 
   public String name();                 // Returns this  entitys name
   public Iterator<Student_> studentList();        // Returns all students of this entity
}