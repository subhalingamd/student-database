
COL106 - Data Structures & Algorithms
Assignment 1 

+===============================================+

STATUS: All Working | ISSUES: Nil 
(Last Updated: 2019-08-09 22:30:01)

+-----------------------------------------------+
| README.txt									                  |
+-----------------------------------------------+


> class IterP<T> implements Iterator<Position_<T>>
Class implementing Iterator<Position_<T> 

  > public boolean hasNext();
  Returns true if Current Position is NOT null and false otherwise

  > public Position<T> next();
  Returns the Current Position object and sets Current to next object



> class Iter<T> implements Iterator<T>
Class implementing Iterator<T> (for ex. Iterator<Student_>)

  > public boolean hasNext();
  Returns return value from IterP::hasNext() (Same functionality)

  > public T next();
  Returns the object of class T (after calling IterP::next())




> class Position<T> implements Position_<T>
Technically known as the 'Node' containing Value (the data) and After (reference to next 'Node')

  > public T value();
  > public Position<T> after();
  Used as return functions

  > public void setAfter(Position<T> A)
  To set/change the reference



> class LinkedList<T> implements LinkedList_<T>
LinkedList implementation. Contains Head and Tail. 
  - Head is the first Node
  - Tail is the last Node
  - Count returns total number of Nodes in the LinkedList

  > public Position<T> add(T e);
  To add a Node to LinkedList. First make object of Node initialised with passed data and null.
  If Head==Tail => empty LinkedList : Head=NewObj and Tail=Head
  Else : Make Tail's 'After' refer to NewObj and then set Tail=NewObj

  > public Iterator<Position_<T>> positions();
  Returns an Iterator for travesing through the LinkedList using the IterP class

  > public int count();
  Returns number of Nodes in the LinkedList




> class GradeInfo implements GradeInfo_

  > public GradeInfo(String gr);
  Constructor for preparing object from the String value

  > public LetterGrade grade();
  Returns the object storing the grade



> class CourseGrade implements CourseGrade_

  > public String coursetitle();
  > public String coursenum();
  > public GradeInfo_ grade();
  Functions to get the class attributes



> class Student implements Student_
(Note: CGr is a LinkedList<CourseGrade_> containing course details (with his/her grade))

  > public Student(...);
  Constructor which does basic assigning. On top of that, CGPA and Completed Credits are also calculated by traversing through CGr using Iterator. CGPA is then rounded to two decimal places.

  > public String name();
  > public String entryNo();
  > public String hostel();
  > public String department();
  > public String completedCredits();
  > public String cgpa();
  Functions returning class attributes

  > public Iterator<CourseGrade_> courseList();
  Returns an Iterator for list of Courses




> class Entity implements Entity_
Contains the Entity Name and List of Students (as LinkedList) who belong to this entity

  > public Entity(String N,LinkedList<Student_> St_List,int Mode)
  Mode: 1 - Course, 2 - Dept (abbr. Department), 3 - Hostel
  if Mode==1:
    For each Student, check each course he/she has taken, for any match of coursenum
      - If found, add this Student (Node) to LinkedList of Students for that entity
      - Else continue till end is reached
  if Mode==2 (or 3):
    For each Student, check if his/her Dept (or Hostel) matches
      - If found, add this Student (Node) to LinkedList of Students for that entity
      - Else continue till end is reached
  Assign the LinkedList of Students for that Entity

  > public String name();
  Return the class attribute Name

  > public Iterator<Student_> studentList()
  Returns an Iterator for traversing Students (belonging to that Entity)



> class Course extends Entity
> class Department extends Entity
> class Hostel extends Entity
Three Entities inherited from class Entity




> class Sort
A class to Sort (some) output in lexicographic order

  > public static String sortThis(String S,int p)
  Splits S at " " (whitespace) => assigns to SSplit[]
  A Trivial sorting Alogirthm to sort the array of strings SSplit
  #INVARIANT- For 0<=i<SSplit.length (i+=p): 
       i+p<=j<SSplit.length (j+=p)
        if SSplit[i] comes lexicographically after SSplit[j] (i.e. SSplit[j] comes first)
         0<=k<p (k+=p):
           swap SSplit[i+k],SSplit[j+k]
  SSorted (the sorted string)+=SSplit[x] : 0<=x<SSplit.length (x++) and return SSorted after x>=SSplit.length (ie loop end is reached)
  #Why p?
    For a string, split takes place at each " " (whitespace). However, if we need p substrings to be one group, then p-1 whitespaces has to be ignored. p will take care of that.
     For ex, when we need to output EntryNum, each whitespace denotes new coursenum which has to be sorted lexicographically. Here p=1. Now take the case where Course and Grade has to be sorted lexicographically w.r.t. coursenum only. (1 whitespace has to be ignored). Here p=2. So pairs are swapped.




 > class Stack{
Used to reverse the output of the queries.

  > public Stack();
  Constructor initialise Top to null

  > public void add(String e);
  First element (Node) always refers to null and subsequent Nodes refer to previous Node (if Stack is non-empty). e is our data (type String) and this function is uded to add Nodes and changing Top accordingly

  > public void popPrint();
  Prints the data in each Node of Stack in LIFO manner, i.e., data added at the last gets printed out first






> public class Assignment1
Contains LinkedLists allCourses, allDepartments, allHostels, allStudents (private members)

Note: A file is read line-by-line and substrings (split at " " (whitespaces)) are produced as arrays (of string) using the split() function

  > private static void getData(String S1,String S2)
  Get Data from files and create LinkedList.
    
  	Read each line of Student Record file and add Student (Node) to allStudents
  	While doing so, also each line from Courses file and add those coursedetails that match with his/her entrynum

  	Again open Student Record file and add all distinct Depts and Hostels present in the file to allDepartments and allHostels. An iterator is used to check if that entity is already present and that entity is added only if it is not present already 
  	From Courses file, read each line and add all distinct Courses to allCourses (like above)
  	Note #1: The StudentList for allCourses, allDepartments, allHostels are made via constructors when allStudents is passed
  	     #2: Course entity's name is stored in <coursenum>+" "+<coursename> format


  > private static void answerQueries(String S)
  Answer to the queries

    INFO: Searches for Student with the desired name/entry number. If found, the string of his details, in the format required, is added to the stack after sorting coursenum lexicographically (using sortThis() in Sort) and converting CourseGrade from object to String

    SHARE: Searches for the name in all the entities-Course, Dept, Hostel. If given name matches with an entity, all the entrynum of Students in that entity (obtained using an Iterator) are added to the Stack after sorting the entrynum lexicographically. Note that the entered entrynum is ignored.

    COURSETITLE: Searches for a match of coursenum given with allCourses (by splitting at first " "(whitespace)) and adds the other (splitted) part-coursename to the stack, if a match is found

    popPrint() of Sort is called which prints the output of each queries in reverse order


  > public static void main(String args[])
  Calls getData() and answerQueries()



+===============================================+

