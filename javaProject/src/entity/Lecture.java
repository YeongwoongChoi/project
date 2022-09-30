package entity;

import java.util.ArrayList;
import java.util.Scanner;

import facade.UIData;
//import mgr.Main;
import mgr.Manageable;

public class Lecture implements Manageable, UIData {
	public String lectureName;
	public String code;
	public String teacher;
	public int price;
	public String level; // 난이도
	public int runningTime; // 1회당 진행시간
	public int lectureCount;// 레슨휫수
	public int maximumPeople; // 최대인원
	public ArrayList<String> description = new ArrayList<>();
	public ArrayList<Review> reviewList = new ArrayList<>();

	@Override
	public void read(Scanner scan) {
		// 0001 이찬혁 25000 아주쉬움 120 2 0 강아지 사료추천
		// 언제까지 아무거나 살래?
		String tmp;
		String name;
		code = scan.next();
		teacher = scan.next();
		price = scan.nextInt();
		level = scan.next();
		runningTime = scan.nextInt();
		lectureCount = scan.nextInt();
		maximumPeople = scan.nextInt();
		lectureName = scan.nextLine();

		// Guest guest;
		while (true) { // 리뷰등록
			name = scan.next();
			if (name.contentEquals("0"))
				break;
			// guest = (Guest) Main.guestMgr.find(name);
			/*
			 * if (guest == null) { System.out.println("해당 고객 없음: " + name); scan.nextInt();
			 * continue; }
			 */
			reviewList.add(new Review(this, name, scan));
		}
		while (true) { // 자세한 설명 등록
			tmp = scan.nextLine();
			if (tmp.contains("end")) {
				break;
			}
			description.add(tmp);
		}
	}

	boolean isMatched(String kwd) { // matches의 조건판별식을 별도 메소드로 분리
		if (lectureName.contains(kwd))
			return true;
		if (kwd.length() > 2 && code.contains(kwd))
			return true;
		if (kwd.length() > 2 && teacher.contains(kwd))
			return true;
		return false;
	}

	public boolean matches(String kwd) { // 키워드에 부합하는지 판단하여, 일치하면 true 리턴
		return isMatched(kwd) ? true : false;
	}

	public boolean matches(String[] kwdArr) {
		for (String kwd : kwdArr) {
			if (!matches(kwd))
				return false;
		}
		return true;
	}

	@Override
	public void print() {
		// [0001] 강아지 사료추천 (25000원) 이찬혁강사
		System.out.format("[%s] ", code);
		System.out.format(lectureName);
		System.out.format("(%d원) %s 강사", price, teacher);
		System.out.println();
	}

	public void print(boolean a) {
		System.out.format(lectureName);
	}

	@Override
	public boolean matchesPrice(int from, int to) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public void set(Object[] uitexts) {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getUiTexts() {
		return new String[] { code, lectureName, teacher, price + "", runningTime * lectureCount + "분", "" };
	}
}