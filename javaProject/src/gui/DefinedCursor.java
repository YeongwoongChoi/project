//package gui;
//
//import java.awt.Cursor;
//import java.awt.Image;
//import java.awt.Point;
//import java.awt.Toolkit;
//
//import javax.swing.JFrame;
//
//public class DefinedCursor extends JFrame {
//    Cursor cursor;
//    Image img;
//   
//    public DefinedCursor(){
//        super("User Defined Cursor");
//       
//        Toolkit tk = Toolkit.getDefaultToolkit();
//        img = tk.getImage("./img/cursor");
//        Point point = new Point(0,0);
//        cursor = tk.createCustomCursor(img,point,"foot");
//        setCursor(cursor);
//       
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(400,300);
//        setVisible(true);
//    }
//    public static void main(String args[]){
//        new DefinedCursor();
//    }
//}