package mgr;

import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;

import entity.Lecture;
import facade.DataEngineInterface;


public class LectureMgr implements DataEngineInterface {

   public static final String[] labels = {"번호", "강의명", "강사명", "가격", "상세정보"};
   Scanner scan = new Scanner(System.in);
   private Manager <Lecture> mgr = new Manager <>();
   
   @Override
   public int getColumnCount() {
      return labels.length;
   }

   // 테이블의 열 제목을 스트링 배열로 돌려줌
   public String[] getColumnNames() {      
      return labels;
   }

   @Override
   public void readAll(String filename) {
      // TODO Auto-generated method stub
      mgr.readAll("onlineLecture.txt", new Factory() {
         public Manageable create() {
            return new Lecture();
         }
      });
   }

   @Override
   public List<Lecture> search(String kwd) {
      if (kwd.equals(""))
         return mgr.mList;
      return mgr.findAll(kwd);
   }
   @Override
   public void addNewItem(String[] uiTexts) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void update(String[] uiTexts) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void remove(String kwd) {
      // TODO Auto-generated method stub
      
   }

}