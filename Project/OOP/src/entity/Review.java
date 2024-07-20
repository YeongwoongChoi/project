package entity;
import java.util.ArrayList;
import java.util.Scanner;

import facade.UIData;
import mgr.Manageable;

public class Review implements Manageable, UIData {
   public float rating;
   String date;
   //Guest guest; Guest find 가 안돼서 일단 Guest 안받음
   String name;
   String line;
   Lecture lecture;
   ArrayList <String> lineList = new ArrayList<>();
   
   Review(Lecture lecture, String name, Scanner scan){
      //4.5 20.11.11 
      this.lecture = lecture;
      //this.guest = guest;
      this.name = name;
      rating = scan.nextFloat();
      
      date = scan.nextLine();
      String tmp = null;
      while(true) {
         tmp = scan.nextLine();
         if(tmp.contains("end")) {
            break;
         }
         lineList.add(tmp);      
      }
   }
   @Override
   public void print() {
      System.out.format("[%s %s님의 후기 : %f점]", date, name, rating);
      for(String i: lineList){
         System.out.format("\t%s\n", i);
      }
   }
   
   @Override
   public void read(Scanner scan) {
      // TODO Auto-generated method stub
      
   }
   @Override
   public boolean matches(String kwd) {
      // TODO Auto-generated method stub
      return true;
   }
   @Override
   public void set(Object[] uitexts) {
      // TODO Auto-generated method stub
      
   }
   @Override
   public String[] getUiTexts() {  // "고객이름", "날짜", "점수"
      // TODO Auto-generated method stub
      String[] texts = new String[3];
      texts[0] = ""+name;
      texts[1] = ""+date;
      texts[2] = "평점: "+rating;
      return texts;
   }
   
   public ArrayList <String> getReviewTexts() {
	   return lineList;
   }
   @Override
   public boolean matchesPrice(int from, int to) {
      // TODO Auto-generated method stub
      return false;
   }
   public void print(boolean is) {}
}