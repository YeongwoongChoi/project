package entity;

import java.util.ArrayList;
import java.util.Scanner;

import mgr.Main;
import mgr.Manageable;

public class Guest implements Manageable {

	// 0 도민준 010-0000-0001 서울 가다구 래미안아파트 203동801호 end 12개월 강아지 사료추천 0 강아지 예방접종의 진실
	// 0000 end
	public int type;
	public String name;
	public String phoneNumber;
	public String pwd;
	public ArrayList<String> address = new ArrayList<>();
	public ArrayList<Lecture> completeLecture = new ArrayList<>();
	public ArrayList<Lecture> lectureList = new ArrayList<>();
	public ArrayList<Lecture> basketList = new ArrayList<>();

	public ArrayList<String> getAddress() {
		return address;
	}

	void scanLectures(ArrayList <Lecture> list, Scanner scan) {
		
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
			list.add(lecture);
		}
	}
	
	@Override
	public void read(Scanner scan) {
		// TODO Auto-generated method stub
		type = scan.nextInt();
		if (type == 1) {
			name = scan.next();
			phoneNumber = scan.next();
			pwd = scan.next();
			
		} else {
			name = scan.next();
			phoneNumber = scan.next();
			String tmp = null;
			
			while (true) {
				tmp = scan.next();
				if (tmp.equals("end"))
					break;
				address.add(tmp);
			}
			
			scanLectures(completeLecture, scan);
			scanLectures(lectureList, scan);
			scanLectures(basketList, scan);
			
			pwd = scan.next();
		}
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

		System.out.printf("이름: %s 폰번호: %s 비밀번호: %s\n", name, phoneNumber, pwd);

		if (type == 0) {
			System.out.printf("주소: ");
			for (String s : address) {
				System.out.printf("%s ", s);
			}
			System.out.print("\n강의목록: \n");
			for (Lecture lec : lectureList) {
				lec.print();
			}
		}
		System.out.println();
	}


	@Override
	public boolean matches(String kwd) {

		if (kwd.equals(type + ""))
			return true;
		if (kwd.equals(phoneNumber))
			return true;

		return false;
	}

	@Override
	public boolean matchesPrice(int from, int to) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setModifiedInfo(Guest g) {
		this.name = g.name;
		this.phoneNumber = g.phoneNumber;
		this.address = g.address;
		this.pwd = g.pwd;
	}
	
	public String[] getLoginInfo() {
		String[] info = new String[2];

		info[0] = phoneNumber;
		info[1] = pwd;
		return info;
	}
}