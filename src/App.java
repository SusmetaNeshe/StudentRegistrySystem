import java.io.*;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class App {
   
      private static TreeMap<Integer, Student> students = new TreeMap<>();
      private static final String FILE_NAME = "src/students.txt";
      public static void main(String[] args) {  
       readStudentsFromFile();
       Scanner scanner = new Scanner(System.in);
       int choice;
       do {
           displayMenu();
           System.out.print("Your choice [1-5]? ");

           while (!scanner.hasNextInt()) {
               System.out.println("Illegal menu item selected!");
               scanner.nextLine();
               displayMenu();
               System.out.print("Your choice [1-5]? ");
           }

           choice = scanner.nextInt();
           scanner.nextLine(); 

           switch (choice) {
               case 1:
                   printAllStudents();
                   break;
               case 2:
                   addNewStudent(scanner);
                   break;
               case 3:
                   deleteStudent(scanner);
                   break;
               case 4:
                   findStudent(scanner);
                   break;
               case 5:
                   saveStudentsToFile();
                   System.out.println("Exiting the app. Students saved to file.");
                   break;
               default:
                   System.out.println("Illegal menu item selected!");
           }
       } while (choice != 5);

       scanner.close();
   }

    private static void readStudentsFromFile() {
          try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
              String line;
              while ((line = reader.readLine()) != null) {
                  String[] studentData = line.split(",");
                  if (studentData.length == 4) {
                      int id = Integer.parseInt(studentData[0]);
                      String firstName = studentData[1].trim();
                      String lastName = studentData[2].trim();
                      double gpa = Double.parseDouble(studentData[3]);

                      try {
                          Student student = new Student(id, firstName, lastName, gpa);
                          students.put(id, student);
                      } catch (IllegalArgumentException e) {
                          System.out.println("Skipping invalid student data: " + line);
                          
                      }
                  } else {
                      System.out.println("Skipping invalid student data: " + line);
                     
                  }
              }
          } catch (IOException | NumberFormatException e) {
              System.out.println("Error reading file: " + e.getMessage());
              
          }
      }
    
    private static void displayMenu() {
       
          System.out.println("Available menu items:");
          System.out.println("1. Print all students");
          System.out.println("2. Add a new student");
          System.out.println("3. Delete an existing student");
          System.out.println("4. Find a student with id");
          System.out.println("5. Quit the app");
      
    }
    
    private static void printAllStudents() {
       System.out.println("We have " + students.size() + " students as follows:");
       System.out.println("+-----+----------------------+----------------------+------+");
       System.out.println("|  Id |      First Name      |       Last Name      | GPA  |");
       System.out.println("+-----+----------------------+----------------------+------+");
       for (Student student : students.values()) {
           System.out.printf("|%5d|%22s|%22s| %.2f |%n",
                   student.getId(), student.getFirstName(), student.getLastName(), student.getGpa());
       }
       System.out.println("+-----+----------------------+----------------------+------+");
   }
  
    private static void addNewStudent(Scanner scanner) {
       int id;
       String firstName;
       String lastName;
       double gpa;
       boolean IdNotSame = false;

       do {
           System.out.println("Enter student id [1-99]: ");

           while (!scanner.hasNextInt()) {
               System.out.println("ID must be an integer. Enter a valid student id [1-99]: ");
               scanner.nextLine(); 
           }
           id = scanner.nextInt();
           scanner.nextLine(); 
           if (id < 1 || id > 99) {
               System.out.println("Id must be >=1 and <=99!");
           } else if (students.containsKey(id)) {
               System.out.println( id + " already exists!");
           } else {
              IdNotSame = true;
           }
       } while (!IdNotSame);

       System.out.println("Enter student firstName (at least 2 characters): ");
       while (!(firstName = scanner.nextLine().trim()).matches(".{2,}")) {
           System.out.println("Invalid firstName! Enter a valid student firstName: ");
       }

       System.out.println("Enter student lastName (at least 2 characters): ");
       while (!(lastName = scanner.nextLine().trim()).matches(".{2,}")) {
           System.out.println("Invalid lastName! Enter a valid student lastName: ");
       }

       System.out.println("Enter student gpa [0.0-4.0]: ");
       while (!scanner.hasNextDouble() || (gpa = scanner.nextDouble()) < 0.0 || gpa > 4.0) {
           System.out.println("Invalid gpa! Enter a valid student gpa [0.0-4.0]: ");
           scanner.nextLine(); 
       }
       scanner.nextLine(); 

       try {
           Student newStudent = new Student(id, firstName, lastName, gpa);
           students.put(id, newStudent);
           System.out.println("Student added successfully:");
           System.out.println(newStudent); 
           System.out.printf("%-5s%-20s%-20s%s%n", "Id", "FirstName", "LastName", "Gpa");
           System.out.printf("%-5d%-20s%-20s%.2f%n", id, firstName, lastName, gpa);
       } catch (IllegalArgumentException e) {
           System.out.println("Invalid student information! Student not added.");
       }
   }

    private static void deleteStudent(Scanner scanner) {
 
          System.out.println("Enter the Student ID: ");
          int Delete;

          while (!scanner.hasNextInt() || (Delete = scanner.nextInt()) <= 0 || Delete > 99) {
              System.out.println("Invalid ID! Enter a valid student ID [1-99]: ");
              scanner.nextLine(); 
          }
          scanner.nextLine();

          if (students.containsKey(Delete)) {
              Student deletedStudent = students.remove(Delete);
              System.out.println("Deleted student with ID " + Delete + ":");
              System.out.println(deletedStudent); 
              System.out.printf("%-5s%-20s%-20s%s%n", "Id", "FirstName", "LastName", "Gpa");
              System.out.printf("%-5d%-20s%-20s%.2f%n", deletedStudent.getId(), deletedStudent.getFirstName(),
                      deletedStudent.getLastName(), deletedStudent.getGpa());
          } else {
              System.out.println("Student with ID " + Delete + " not found.");
          }
      }

    private static void findStudent(Scanner scanner) {
          System.out.println("Enter the ID of the student to find: ");
          int Find;

          while (!scanner.hasNextInt() || (Find = scanner.nextInt()) <= 0 || Find > 99) {
              System.out.println("Invalid ID! Enter a valid student ID [1-99]: ");
              scanner.nextLine(); 
          }
          scanner.nextLine(); 

          if (students.containsKey(Find)) {
              System.out.println("Student with ID " + Find + " found:");
              System.out.println(students.get(Find)); 
              System.out.printf("%-5s%-20s%-20s%s%n", "Id", "FirstName", "LastName", "Gpa");
              Student foundStudent = students.get(Find);
              System.out.printf("%-5d%-20s%-20s%.2f%n", foundStudent.getId(), foundStudent.getFirstName(),
                      foundStudent.getLastName(), foundStudent.getGpa());
          } else {
              System.out.println("Student with ID " + Find + " not found.");
          }
      }
    

    private static void saveStudentsToFile() {
          try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
              for (Entry<Integer, Student> entry : students.entrySet()) {
                  Student student = entry.getValue();
                  String studentData = String.format("%d, %s, %s, %.2f%n",
                          student.getId(), student.getFirstName(), student.getLastName(), student.getGpa());
                  writer.write(studentData);
              }
              System.out.println("Students saved to file: " + FILE_NAME);
          } catch (IOException e) {
              System.out.println( e.getMessage());
          }
      }
    }
