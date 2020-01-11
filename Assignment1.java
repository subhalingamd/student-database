import java.io.*;
import java.util.Iterator;

class IterP<T> implements Iterator<Position_<T>>{
   Position<T> Curr;

   public IterP(Position<T> Inp){
      Curr=Inp;
   }

   public boolean hasNext(){
      return Curr!=null;     
   }

   public Position<T> next(){
      Position<T> Prev=Curr;
      Curr=Curr.after();
      return Prev;
   }

}

class Iter<T> implements Iterator<T>{
   Iterator<Position_<T>> Curr;

   public Iter(Iterator<Position_<T>> Inp){
      Curr=Inp;
   }

   public boolean hasNext(){
      return Curr.hasNext();     
   }

   public T next(){
      return Curr.next().value();
   }
}



class Position<T> implements Position_<T>{
	T Value;
	Position<T> After;

	public Position(T V){
		Value=V;
		After=null;
	}

	public T value(){
		return Value;
	}

	public Position<T> after(){
		return After;
	}

	public void setAfter(Position<T> A){
		After=A;
	}
}

class LinkedList<T> implements LinkedList_<T>{
   Position<T> Head,Tail;
   int Count;
   public LinkedList(){
   		Head=Tail=null;
   		Count=0;
   }

   public Position<T> add(T e){
   		Position<T> App=new Position<T>(e);
   		Count++;
   		if (Head==null){
   			Head=App;
   			Tail=Head;
   		}
   		else{
   			Tail.setAfter(App);
   			Tail=App;
   		}
         return App;
   }
   public Iterator<Position_<T>> positions(){
      Iterator<Position_<T>> I=new IterP<T>(Head);
      return I;
   }

   public int count(){
   		return Count; 
   }                
}

class GradeInfo implements GradeInfo_ {

   GradeInfo_.LetterGrade Grade;

   public GradeInfo(String gr){
      if (gr.equals("A"))  Grade=GradeInfo_.LetterGrade.A;
      else if (gr.equals("Aminus"))  Grade=GradeInfo_.LetterGrade.Aminus;
      else if (gr.equals("B"))  Grade=GradeInfo_.LetterGrade.B;
      else if (gr.equals("Bminus"))  Grade=GradeInfo_.LetterGrade.Bminus;
      else if (gr.equals("C"))  Grade=GradeInfo_.LetterGrade.C;
      else if (gr.equals("Cminus"))  Grade=GradeInfo_.LetterGrade.Cminus;
      else if (gr.equals("D"))  Grade=GradeInfo_.LetterGrade.D;
      else if (gr.equals("E"))  Grade=GradeInfo_.LetterGrade.E;
      else if (gr.equals("F"))  Grade=GradeInfo_.LetterGrade.F;
      else if (gr.equals("I"))  Grade=GradeInfo_.LetterGrade.I;
   } 
   
   @Override
   public LetterGrade grade(){       
      return Grade;
   }
}

class CourseGrade implements CourseGrade_ {  
   String CourseTitle,CourseNum;
   GradeInfo Grade;

   public CourseGrade(String ct,String cn,String gr){
      CourseTitle=ct;
      CourseNum=cn;
      Grade=new GradeInfo(gr);
   }   

   public String coursetitle(){
      return CourseTitle;
   }      
   public String coursenum(){
      return CourseNum;
   }       
   public GradeInfo_ grade(){
      return Grade;
   }    
}






class Student implements Student_ {
   String Name,EntryNo,Hostel,Department,CompletedCredits,Cgpa;
   LinkedList<CourseGrade_> CGr;

   public Student(String EN,String N,String H, String D,LinkedList<CourseGrade_> CG){
      Name=N;
      EntryNo=EN;
      Hostel=H;
      Department=D;
      Cgpa="0";
      CompletedCredits="0";
      CGr=CG;
      float points=0,cred=0;
      Iterator<Position_<CourseGrade_>> Iter=CGr.positions();
      while (Iter.hasNext()){
         GradeInfo_ Gr=Iter.next().value().grade();
         int currpoint = GradeInfo_.gradepoint(Gr.grade());
         if (currpoint>0){
            cred+=3;
         }
         points+=currpoint*3;
      }
      if (cred!=0)
         Cgpa=String.valueOf(Math.round(points*100.0/cred)/100.0);
         CompletedCredits=String.valueOf(cred);

      

   }

   public String name(){
   	return Name;
   }           	
   public String entryNo(){
   	return EntryNo;
   }                   	
   public String hostel(){
   	return Hostel;
   }                   	
   public String department(){
   	return Department;
   }                	
   public String completedCredits(){
   	return CompletedCredits;
   }          	
   public String cgpa(){
   	return Cgpa;
   }          		
   public Iterator<CourseGrade_> courseList(){
      Iterator<Position_<CourseGrade_>> Temp1 = CGr.positions();
      Iterator<CourseGrade_> Temp2=new Iter<CourseGrade_>(Temp1);
      return Temp2; 
   }
}


class Entity implements Entity_{
	String Name;
   LinkedList<Student_> St; 
	public Entity(String N,LinkedList<Student_> St_List,int Mode){ //Mode=1:Course,2:Dept,3:Hostel
		Name=N;
      Iterator<Position_<Student_>> St_List_Iter=St_List.positions();
      St=new LinkedList<Student_>();
      if (Mode==1){
         while (St_List_Iter.hasNext()){
            Student_ StudNode=St_List_Iter.next().value();
            Iterator<CourseGrade_> Temp1=StudNode.courseList();
            while (Temp1.hasNext()){
               if (N.split(" ")[0].equals(Temp1.next().coursenum()))
                  St.add(StudNode);
            }
         }
      }
      else if(Mode==2){
         while (St_List_Iter.hasNext()){
            Student_ StudNode=St_List_Iter.next().value();
            if (StudNode.department().equals(Name))
               St.add(StudNode);
         }
         
      }
      else if(Mode==3){
         while (St_List_Iter.hasNext()){
            Student_ StudNode=St_List_Iter.next().value();
            if (StudNode.hostel().equals(Name))
               St.add(StudNode);
         }
      }
	}

	public String name(){
		return Name;
	}

	public Iterator<Student_> studentList(){
      Iterator<Position_<Student_>> Temp1=St.positions();
      Iterator<Student_> Temp2=new Iter<Student_>(Temp1);
      return Temp2; 
	}
}


class Course extends Entity{
   Course(String N,LinkedList<Student_> St_List){
      super(N,St_List,1);
   }
}


class Department extends Entity{
   Department(String N,LinkedList<Student_> St_List){
      super(N,St_List,2);
   }
}

class Hostel extends Entity{
   Hostel(String N,LinkedList<Student_> St_List){
      super(N,St_List,3);
   }
}


class Sort{
   public static String sortThis(String S,int p){
      String SSplit[]=S.split(" ");
      for (int i=0;i<SSplit.length;i+=p){
         for (int j=i+p;j<SSplit.length;j+=p){
            if (SSplit[i].compareTo(SSplit[j]) > 0) {
               for (int k=0;k<p;k++){
                  String temp = SSplit[i+k];
                  SSplit[i+k] = SSplit[j+k];
                  SSplit[j+k] = temp;
               }
            }
         }
      }
      String SSorted=new String();
      for (int x=0;x<SSplit.length;x++){
         SSorted=SSorted+SSplit[x]+" ";
      }
      return SSorted;
   }
}

class Stack{
   Position<String> Top;

   public Stack(){
      Top=null;
   }

   public void add(String e){
      Position<String> App=new Position<String>(e);
          if (Top==null)
             Top=App;
          else{
             App.setAfter(Top);
             Top=App;
          }
   }

   public void popPrint(){
      while (Top!=null){
         System.out.println(Top.value());
         Top=Top.after();
      }
   }
}



public class Assignment1{
   private static LinkedList<Course> allCourses = new LinkedList<Course>();;
   private static LinkedList<Department> allDepartments = new LinkedList<Department>();
   private static LinkedList<Hostel> allHostels = new LinkedList<Hostel>();
   private static LinkedList<Student_> allStudents = new LinkedList<Student_>();;



   private static void getData(String S1,String S2){
      try{
         BufferedReader f1 = new BufferedReader(new FileReader(S1));
         
         String row;
         while ((row = f1.readLine()) != null) {
            String[] col=row.split(" ");
            BufferedReader f2 = new BufferedReader(new FileReader(S2));
            LinkedList<CourseGrade_> App_Cgr=new LinkedList<CourseGrade_>();
            while ((row = f2.readLine()) != null) {
                String[] col_=row.split(" ",4);
                   if (col[0].equals(col_[0])){
                     CourseGrade_ App1=new CourseGrade(col_[3],col_[1],col_[2]);
                     App_Cgr.add(App1);
                  }
            }
            Student_ App=new Student(col[0],col[1],col[3],col[2],App_Cgr);
            allStudents.add(App);
            f2.close();
         }

         f1.close();
         

      }
      catch(Exception e){
         System.out.println(e.toString());
      }
      

      try{
         BufferedReader f1 = new BufferedReader(new FileReader(S1));
         BufferedReader f2 = new BufferedReader(new FileReader(S2));
         String row;
         while ((row = f1.readLine()) != null) {
            String[] col=row.split(" ");

            Iterator<Position_<Department>> Iter_Dept=allDepartments.positions();
            boolean flag=true;
            while (Iter_Dept.hasNext()){
               if (Iter_Dept.next().value().name().equals(col[2]))
                  flag=false;
            }
            if (flag){
               Department App=new Department(col[2],allStudents);
               allDepartments.add(App);
            }
            Iterator<Position_<Hostel>> Iter_Host=allHostels.positions();
            flag=true;
            while (Iter_Host.hasNext()){
               if (Iter_Host.next().value().name().equals(col[3]))
                  flag=false;
            }
            if (flag){
               Hostel App=new Hostel(col[3],allStudents);
               allHostels.add(App);
            }

            
         }

         while ((row = f2.readLine()) != null) {
            String[] col=row.split(" ",4);
            Iterator<Position_<Course>> Iter_Crs=allCourses.positions();
            boolean flag=true;
            while (Iter_Crs.hasNext()){
               if (Iter_Crs.next().value().name().split(" ")[0].equals(col[1]))
                  flag=false;
            }
            if (flag){
               String n=col[1]+" "+col[3];
               Course App=new Course(n,allStudents);
               allCourses.add(App);
            }
         }

      }
      catch(Exception e){
         System.out.println(e.toString());
      }
   } 

   private static void answerQueries(String S){
      try{
         BufferedReader f = new BufferedReader(new FileReader(S));
         Stack reslist=new Stack();
         String row;
         while ((row = f.readLine()) != null) {
            String[] col=row.split(" ");
            String res=new String();

            if (col[0].equals("INFO")){
               Iterator<Position_<Student_>> Iter_St=allStudents.positions();
               while (Iter_St.hasNext()){
                  Student_ Details=Iter_St.next().value();
                  if (Details.entryNo().equals(col[1]) || Details.name().equals(col[1])){
                     res=Details.entryNo()+" "+Details.name()+" "+Details.department()+" "+Details.hostel()+" "+Details.cgpa()+" ";
                     Iterator<CourseGrade_> Iter_St_CGr=Details.courseList();
                     String UnsortedC=new String();
                     while (Iter_St_CGr.hasNext()){
                        CourseGrade_ C=Iter_St_CGr.next();
                        UnsortedC+=C.coursenum()+" ";
                        GradeInfo.LetterGrade gr = C.grade().grade();
                              if (gr == GradeInfo_.LetterGrade.A) UnsortedC+="A ";

                              else if (gr == GradeInfo_.LetterGrade.Aminus) UnsortedC+="Aminus ";
                              else if (gr == GradeInfo_.LetterGrade.B)UnsortedC+="B ";

                              else if (gr == GradeInfo_.LetterGrade.Bminus) UnsortedC+="Bminus ";

                              else if (gr == GradeInfo_.LetterGrade.C) UnsortedC+="C ";

                              else if (gr == GradeInfo_.LetterGrade.Cminus) UnsortedC+="Cminus ";

                              else if (gr == GradeInfo_.LetterGrade.D) UnsortedC+="D ";

                              else if (gr == GradeInfo_.LetterGrade.E) UnsortedC+="E ";

                              else if (gr == GradeInfo_.LetterGrade.F) UnsortedC+="F ";

                              else if (gr == GradeInfo_.LetterGrade.I) UnsortedC+="I ";

                     }
                     res+=Sort.sortThis(UnsortedC,2);
                  }
               }
            }
            else if (col[0].equals("SHARE")){
               Iterator<Student_> Iter_St;
               Iterator<Position_<Course>> Iter_C=allCourses.positions();
               String UnsortedS=new String();
               while (Iter_C.hasNext()){
                  Course Details=Iter_C.next().value();
                  if (Details.name().split(" ")[0].equals(col[2])){
                     Iter_St=Details.studentList();
                     while (Iter_St.hasNext()){
                        String EntryNum=Iter_St.next().entryNo();           
                        if (!EntryNum.equals(col[1])){
                           UnsortedS=UnsortedS+EntryNum+" "; 
                        }
                     }
                     res=Sort.sortThis(UnsortedS,1);
                  }
                  
               }
               Iterator<Position_<Department>> Iter_D=allDepartments.positions();
               while (Iter_D.hasNext()){
                  Department Details=Iter_D.next().value();
                  if (Details.name().equals(col[2])){
                     Iter_St=Details.studentList();
                     while (Iter_St.hasNext()){
                        String EntryNum=Iter_St.next().entryNo();
                        if (!EntryNum.equals(col[1])){
                           UnsortedS=UnsortedS+EntryNum+" ";
                           
                        }
                     }
                     res=Sort.sortThis(UnsortedS,1);
                     
                  }
                  
               }
               Iterator<Position_<Hostel>> Iter_H=allHostels.positions();
               while (Iter_H.hasNext()){
                  Hostel Details=Iter_H.next().value();
                  if (Details.name().equals(col[2])){
                     Iter_St=Details.studentList();
                     while (Iter_St.hasNext()){
                        String EntryNum=Iter_St.next().entryNo();
                        if (!EntryNum.equals(col[1]))
                           UnsortedS=UnsortedS+EntryNum+" ";
                     }
                  }
                  res=Sort.sortThis(UnsortedS,1);
               }
            }
            else if (col[0].equals("COURSETITLE")){
               Iterator<Position_<Course>> Iter_Ct=allCourses.positions();
               while (Iter_Ct.hasNext()){
                  String Details=Iter_Ct.next().value().name();
                  if (Details.split(" ")[0].equals(col[1]))
                     res=Details.split(" ",2)[1];
               }
            }
            reslist.add(res);
         }
         reslist.popPrint();
         
      }
      catch(Exception e){
         System.out.println(e.toString());
      }
   }

	public static void main(String args[]) {
     
	  getData(args[0],args[1]);
     answerQueries(args[2]);

	}
}
