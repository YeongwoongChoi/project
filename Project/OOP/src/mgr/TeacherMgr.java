package mgr;

import java.util.List;
import java.util.Scanner;

import entity.Teacher;
import facade.DataEngineInterface;

public class TeacherMgr implements DataEngineInterface {

	private static final String[] labels = { "이름", "휴대폰 번호", "이메일" ,"경력"};
	Scanner scan = new Scanner(System.in);
	private Manager <Teacher> mgr = new Manager <>();
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return labels.length;
	}

	@Override
	public String[] getColumnNames() {
		// TODO Auto-generated method stub
		return labels;
	}

	@Override
	public void readAll(String filename) {
		mgr.readAll("teacher.txt", new Factory <Teacher> () {
			public Teacher create() {
				return new Teacher();
			}
		});
		
	}

	@Override
	public List<Teacher> search(String kwd) {
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
