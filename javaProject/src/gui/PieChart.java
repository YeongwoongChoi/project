package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import entity.Guest;
import entity.Lecture;
import mgr.Main;

class PieChart extends JPanel {
	
	private HashMap <Lecture, Integer> countList = new HashMap<>();
	
	void count() {
		int cnt = 0;
		
		for (Guest g: Main.guestMgr.mList) {
			List <Lecture> list = g.lectureList;
			
			for (Lecture lec: list) {
				if (lec.matches(Login.t.name)) {
					cnt++;
					countList.put(lec, cnt);
				}
				else
					continue;
			}
		}
	}
	
	public PieChart() {
		count();
	}
	
	public void paint(Graphics g) {
		Collection <Integer> real = countList.values();
		Iterator <Integer> it = real.iterator();
		
		int sum = 0, j = 0;
		int r = 170, grn = 150, b = 170;
		
		g.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
		g.drawString("[강의별 수강인원]", 40, 30);
		int [] values = new int[real.size()];
		int [] partial = new int[real.size()];
		
		while (it.hasNext()) {
			values[j] = it.next();
			j++;
		}
		
		int ac = partial[0];
		
		for (Integer i: real) {
			sum += i;
		}
		
		for (int i=0; i<partial.length; i++) {
			partial[i] = (int)((360.0 * values[i]) / sum);
		}
		g.setColor(new Color(r, grn, b));
		g.fillArc(30, 50, 210, 210, 0, partial[0]);
		
		for (int i=1; i<partial.length; i++) {
			ac += partial[i - 1];
			r += 14;
			grn += 10;
			b += 15;
			g.setColor(new Color(r, grn, b));
			g.fillArc(30, 50, 210, 210, ac, 360 - ac);
		}
		
		int point_y = 290;
		ac = 0;
		
		g.setColor(Color.BLACK);		
		
		for (Lecture lec: countList.keySet()) {
			String code = lec.code;
			String cnt = ": (현 수강인원: " + values[ac] + "명)";
			
			g.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
			g.drawString(code + cnt, 20, point_y);
			point_y += 20;
			ac++;
		}
	}
}