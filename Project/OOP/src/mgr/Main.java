package mgr;
import java.awt.Font;
import java.util.Enumeration;
import java.util.Scanner;

import javax.swing.UIManager;

import entity.Guest;
import entity.Lecture;
import entity.Review;
import entity.Teacher;
import gui.GUIManager;
import view.LectureView;

public class Main {
   Scanner scan = new Scanner(System.in);
   
   public static Manager <Lecture> lectureMgr = new Manager <>();
   public static Manager <Guest> guestMgr = new Manager <>();
   public static Manager <Teacher> teacherMgr = new Manager<>();
   
   public void run() {
      setGlobalFont(new Font("Malgun Gothic", Font.PLAIN, 22));		//전체 폰트를 16px 맑은고딕으로 설정
      
      lectureMgr.readAll("onlineLecture.txt", new Factory <Lecture> () {		//각각의 Text File을 읽어 객체 생성
         public Lecture create() {
            return new Lecture();
         }
      });
      guestMgr.readAll("guest.txt", new Factory <Guest> () {
         public Guest create() {
            return new Guest();
         }
      });
      teacherMgr.readAll("teacher.txt", new Factory <Teacher> () {
    	  public Teacher create() {
    		  return new Teacher();
    	  }
      });
      
      lectureMgr.printAll();
      System.out.println();
      guestMgr.printAll();
      System.out.println();
      teacherMgr.printAll();
      
      LectureMgr engine = new LectureMgr();
      engine.readAll("onlineLecture.txt");
      GUIManager.run(engine);		//강좌 목록을 GUI로 출력하기 위함
      
   }
   
   public static void setGlobalFont(Font font) {	//모든 Object의 기본 폰트를 font로 설정
       Enumeration <Object> keys = UIManager.getDefaults().keys();
       while (keys.hasMoreElements()) {
          Object key = keys.nextElement();
          Object value = UIManager.get(key);
          if (value instanceof Font) {
             UIManager.put(key, font);
          }
       }
   }

   public static void main(String[] args) {
      Main main = new Main();
      main.run();
   }
}