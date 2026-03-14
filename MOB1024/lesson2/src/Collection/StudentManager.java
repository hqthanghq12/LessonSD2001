/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Collection;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class StudentManager {
    private List<Student> students = new ArrayList<>();
     public void addStudent(Student student) {
        students.add(student);
    }

    public void displayAll() {
        for (Student s : students) {
            System.out.println(s);
        }
    }

    public Student findById(String id) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    public boolean updateStudent(String id, String newName, double newMarks) {
        Student s = findById(id);
        if (s != null) {
            s.setName(newName);
            s.setMarks(newMarks);
            return true;
        }
        return false;
    }

    public boolean removeStudent(String id) {
        Student s = findById(id);
        if (s != null) {
            students.remove(s);
            return true;
        }
        return false;
    }
}
