package entity;

import java.util.ArrayList;
import java.util.Scanner;

import facade.UIData;
import mgr.Main;
import mgr.Manageable;

public class Teacher extends Guest implements Manageable,UIData{

   public String teacherCode;
   public String name;
   public String phoneNumber;
   public String pwd;
   public String email;
   public String univ;
   public ArrayList<Lecture> lectureList = new ArrayList<>();
   public ArrayList<String> careerList = new ArrayList<>();
   public String job;
   
   
   public ArrayList<String> getCareer() {
      return careerList;
   }
   public void read(Scanner scan) {
      teacherCode = scan.next();
      name = scan.next();
      phoneNumber = scan.next();
      pwd = scan.next();
      email = scan.next();
      univ = scan.next();
      
      String code = null;
      Lecture lecture;
      while (true) {
         code = scan.next();
         if (code.contentEquals("end")) {
            break;
         }
         lecture = (Lecture) Main.lectureMgr.find(code);
         if (lecture == null) {
            System.out.println("해당 코드 강의 없음: " + code);
            continue;
         }
         lectureList.add(lecture);
      }
      job = scan.nextLine();
      String tmp = "";
      tmp = scan.nextLine();
      while(true) {
         if(tmp.contentEquals("end")) {
            break;
         }
         careerList.add(tmp);
         tmp = scan.nextLine();
      }
   }

   @Override
   public void print() {
      // TODO Auto-generated method stub

   }
   
   public void setModifiedInfo(Teacher t) {
      this.phoneNumber = t.phoneNumber;
      this.job = t.job;
      this.email = t.email;
      this.pwd = t.pwd;
   }


   @Override
      public boolean matches(String kwd) {

         if (kwd.equals(phoneNumber)) {
            return true;
         }
         if(kwd.equals(name)) {
            return true;
         }
         if(kwd.equals(teacherCode)) {
            return true;
         }

         return false;
      }

   @Override
   public boolean matchesPrice(int from, int to) {
      // TODO Auto-generated method stub
      return false;
   }

   public String[] getLoginInfo() {
      String[] info = new String[2];

      info[0] = phoneNumber;
      info[1] = pwd;
      return info;
   }

   public void print(boolean isTeacher) {
      System.out.format("이름: %s 폰번호: %s 비밀번호: %s\n", name, phoneNumber, pwd);
   }
      @Override
      public String[] getUiTexts() {
         return new String[] {teacherCode,name, job, phoneNumber, email + "" };
      }

   @Override
   public void set(Object[] uitexts) {
      // TODO Auto-generated method stub
      
   }
   

}