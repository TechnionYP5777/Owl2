package upandgo.shared.entities;



import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;


public class Exam implements IsSerializable{
	
	private String month;
	private String dayOfMonth;
	private String year;
	private String day;
	private String time;
	
	private int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		
	public Exam() {
		time = day = month = dayOfMonth = year = "";
	}
	
	public Exam(String format) {
		time = day = month = dayOfMonth = year = "";
		String[] splited = format.split(" ");
		if (splited.length == 5) {
			this.time = splited[0];
			this.dayOfMonth = splited[1];
			this.month = splited[2];
			this.year = splited[3];
			this.day = splited[4];
		}
		
	}
	
	public String getDay() {
		return day;
	}
	
	public String getDayOfMonth() {
		return dayOfMonth;
	}
	
	public String getMonth() {
		return month;
	}
	
	public String getYear() {
		return year;
	}
	
	public String getTime() {
		return time;
	}
	
	public String getDate() {
		return dayOfMonth + "." + month;
	}
	
	@Override
	public String toString(){
		return getDate() + " בשעה"  + time;
	}
	
	public String compareString() {
		return year + month + dayOfMonth;
	}
	
	@SuppressWarnings("boxing")
	private static Boolean isLeap(String year) {
		int yearnum = Integer.parseInt(year);
		if (((yearnum % 4 == 0) && (yearnum % 100 != 0)) || (yearnum % 400 == 0)) {
			return true;
		}
		return false;
	}
	
	public int compare(Exam exam) {
		int examA = Integer.parseInt(this.compareString());
		int examB = Integer.parseInt(exam.compareString());
		if (examA > examB) {
			return 1;
		}
		if (examA == examB) {
			return 0;
		} 
		return -1;
	}
	
	//the exams should be in order, exama must be early then examb
	// both exams must be in the same year - for now
	@SuppressWarnings("boxing")
	public int daysBetweenExams (Exam examb) {
		Exam exama = this;
		if (exama.compare(examb) > 0) {
			Exam temp = exama;
			exama = examb;
			examb = temp;
		}
		int res = 0;
		if (isLeap(exama.getYear())) {
			monthDays[1] = 29;
		}
		if (exama.getMonth().equals(examb.getMonth())) {
			monthDays[1] = 28;
			return Integer.parseInt(examb.getDayOfMonth()) - Integer.parseInt(exama.getDayOfMonth());
		}
		for (int i = Integer.parseInt(exama.getMonth()); i < Integer.parseInt(examb.getMonth()) -1 ; i++) {
			res += monthDays[i];
		}
		res += (monthDays[Integer.parseInt(exama.getMonth()) - 1] - Integer.parseInt(exama.getDayOfMonth()))
				+ Integer.parseInt(examb.getDayOfMonth());
		monthDays[1] = 28;
		return res;
	}
	
	//the exams should be in order, exama must be early then examb
	public List<String> datesBetweenExams (Exam examb) {
		Exam exama = this;
		if (exama.compare(examb) > 0) {
			Exam temp = exama;
			exama = examb;
			examb = temp;
		}
		int examMonthA = Integer.parseInt(exama.getMonth());
		int examMonthB = Integer.parseInt(examb.getMonth());
		int dayIndex = Integer.parseInt(exama.getDayOfMonth()) + 1;
		int monthIndex;
		List<String> res = new ArrayList<>();
		if (isLeap(exama.getYear())) {
			monthDays[1] = 29;
		}
		while (examMonthA <= examMonthB) {
			if (examMonthA == examMonthB) {
				monthIndex = Integer.parseInt(examb.getDayOfMonth()) - 1;
			} else {
				monthIndex = monthDays[examMonthA -1];
			}
			while (dayIndex <= monthIndex) {
				res.add(dayIndex + "." +  examMonthA);		
				dayIndex++;
			}
			dayIndex = 1;
			examMonthA++;
		}
		monthDays[1] = 28;
		return res;
	}

}
