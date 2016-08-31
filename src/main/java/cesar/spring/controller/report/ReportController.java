
package cesar.spring.controller.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cesar.model.Major;
import cesar.model.NoSeenReasonType;
import cesar.model.Quarter;
import cesar.model.Report;
import cesar.model.VisitReasonType;
import cesar.model.dao.AdvisementDao;
import cesar.model.dao.AppointmentSectionDao;
import cesar.model.dao.CoursePlanDao;
import cesar.model.dao.MajorDao;
import cesar.model.dao.NoSeenReasonDao;
import cesar.model.dao.NoSeenReasonTypeDao;
import cesar.model.dao.ServiceDao;
import cesar.model.dao.ServiceTypeDao;
import cesar.model.dao.UserDao;
import cesar.model.dao.VisitReasonDao;
import cesar.model.dao.VisitReasonTypeDao;
import cesar.model.ServiceType;

@Controller
public class ReportController {
	
	@Autowired
    private AppointmentSectionDao		appointmentSectionDao;
	
	@Autowired
    private ServiceDao              	serviceDao;
	
	@Autowired
    private ServiceTypeDao              serviceTypeDao;
	
	@Autowired
    private VisitReasonDao              visitReasonDao;
	
	@Autowired
    private VisitReasonTypeDao     		visitReasonTypeDao;
	
	@Autowired
    private NoSeenReasonDao             noSeenReasonDao;
	
	@Autowired
    private NoSeenReasonTypeDao      	noSeenReasonTypeDao;
	
	@Autowired
    private AdvisementDao      			advisementDao;
	
	@Autowired
    private UserDao      				userDao;

	@Autowired
    private MajorDao      				majorDao;
	
	@Autowired
    private CoursePlanDao      				coursePlanDao;
	
	@RequestMapping(
	    value = ("/report/viewAppointmentReport.html"),
	    method = RequestMethod.GET)
	public String viewAppointmentReport(ModelMap model)
	{
		Calendar localCalendar = Calendar.getInstance();
		int currentYear = localCalendar.get(Calendar.YEAR);
		 
		Map<Integer,Integer> years = new LinkedHashMap<Integer,Integer>();
		for (int i = 2011; i <= currentYear; i++) {
			years.put(i, i);
		}
		
		model.addAttribute( "yearList", years );
		model.addAttribute( "report", new Report() );
	    return "report/viewAppointmentReport";
	}
	
	@RequestMapping(
		    value = ("/report/viewAppointmentReport.html"),
		    method = RequestMethod.POST)
		public String viewAppointmentReport(@ModelAttribute("report") Report report, BindingResult result, ModelMap model)
		{
			Quarter fromQ = new Quarter(Integer.parseInt(report.getFromYear()), report.getFromQuarter());
			Quarter toQ = new Quarter(Integer.parseInt(report.getToYear()), report.getToQuarter());
		
			List<Quarter> quarterList = getQuarters(fromQ, toQ);
			
			Map<String, List> allData = new LinkedHashMap<String, List>();
			
			if (report.getOption() != null) {
				String[] ops = report.getOption().split(",");
			
				if (report.getType().equals("appointment")) {
					if (report.getOption().equals("allAppointment")) {
						List<Long> completedList = getCompletedAppoinment(quarterList, fromQ, toQ);
						allData.put("Completed", completedList );
					
						List<Long> cancelledList = getCancelledAppoinment(quarterList, fromQ, toQ);
						allData.put("Cancelled", cancelledList );
					
						List<Long> missedList = getMissedAppoinment(quarterList, fromQ, toQ);
						allData.put("No Shows", missedList );
					
					} else {
						for (int j = 0; j < ops.length; j++) {
							if (ops[j].equals("completed")) {
								List<Long> completedList = getCompletedAppoinment(quarterList, fromQ, toQ);
								allData.put("Completed", completedList );
				
							} else if (ops[j].equals("cancelled")) {
								List<Long> cancelledList = getCancelledAppoinment(quarterList, fromQ, toQ);
								allData.put("Cancelled", cancelledList );
					
							} else if (ops[j].equals("missed")) {
								List<Long> missedList = getMissedAppoinment(quarterList, fromQ, toQ);
								allData.put("No Shows", missedList );
							}
						}
					}
					model.addAttribute( "title", "Appointments" );
				} else if (report.getType().equals("walkin")) {
					if (report.getOption().equals("allWalkin")) {
						List<Long> seenList = getSeenWalkInAppoinment(quarterList, fromQ, toQ);
						allData.put("Seen", seenList );
					
						List<Long> notSeenList = getNotSeenWalkInAppoinment(quarterList, fromQ, toQ);
						allData.put("Not Seen", notSeenList );
					
					} else {
						for (int j = 0; j < ops.length; j++) {
							if (ops[j].equals("seen")) {
								List<Long> seenList = getSeenWalkInAppoinment(quarterList, fromQ, toQ);
								allData.put("Seen", seenList );
				
							} else if (ops[j].equals("notseen")) {
								List<Long> notSeenList = getNotSeenWalkInAppoinment(quarterList, fromQ, toQ);
								allData.put("Not Seen", notSeenList );
					
							}
						}
					}
					model.addAttribute( "title", "Walk-in Appointments" );
				}
			} else {
				model.addAttribute( "optionError", "Please select options" );
				return "report/viewAppointmentReport";
			}
			
			Calendar localCalendar = Calendar.getInstance();
			int currentYear = localCalendar.get(Calendar.YEAR);
			 
			Map<Integer,Integer> years = new LinkedHashMap<Integer,Integer>();
			for (int i = 2011; i <= currentYear; i++) {
				years.put(i, i);
			}
			
			model.addAttribute( "yearList", years );
			model.addAttribute( "qList", quarterList );
			model.addAttribute( "allData", allData );
			model.addAttribute( "fromQ", fromQ );
			model.addAttribute( "toQ", toQ );
			model.addAttribute( "chartFlag", true );
			model.addAttribute( "report", report );
			
		    return "report/viewAppointmentReport";
		}
	
	@RequestMapping(
	    value = ("/report/viewAdvisementEnrollmentReport.html"),
	    method = RequestMethod.GET)
	public String viewAdvisementEnrollmentReport(ModelMap model)
	{
		Calendar localCalendar = Calendar.getInstance();
		int currentYear = localCalendar.get(Calendar.YEAR);
		 
		Map<Integer,Integer> years = new LinkedHashMap<Integer,Integer>();
		for (int i = 2011; i <= currentYear; i++) {
			years.put(i, i);
		}
		
		model.addAttribute( "yearList", years );
		model.addAttribute( "report", new Report() );
	    return "report/viewAdviseEnrollReport";
	}
	
	@RequestMapping(
		    value = ("/report/viewAdvisementEnrollmentReport.html"),
		    method = RequestMethod.POST)
		public String viewAdvisementEnrollmentReport(@ModelAttribute("report") Report report, BindingResult result, ModelMap model)
		{
			Quarter fromQ = new Quarter(Integer.parseInt(report.getFromYear()), report.getFromQuarter());
			Quarter toQ = new Quarter(Integer.parseInt(report.getToYear()), report.getToQuarter());

			List<Quarter> quarterList = getQuarters(fromQ, toQ);
	
			Map<String, List> allData = new LinkedHashMap<String, List>();
			
			if (report.getYear() != null) {
			if (report.getType().equals("advisement")) {
				if (report.getGroup().equals("advised")) {
					String[] ops = report.getYear().split(",");
					List<Long> studentList = getStudentsByQuarter(quarterList, Integer.parseInt(ops[0])*10);
					for (int i = 1; i < ops.length; i++) {
						List<Long> tempList = getStudentsByQuarter(quarterList, Integer.parseInt(ops[i])*10);
			
						for (int k = 0; k < tempList.size(); k++) {
							studentList.add(k, studentList.remove(k) + tempList.get(k));
						}
					}
					
					List<Long> advisementList = new ArrayList<Long>();
					if (report.getYear().equals("10")) {
						List app = advisementDao.getAdvisementsByQuarter(fromQ, toQ);
				
						for (int m = 0; m < quarterList.size(); m++) {
							boolean noData = true;
							for (int n = 0; n < app.size(); n++) {
								Object[] row = (Object[]) app.get(n);
						
								if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
									advisementList.add((Long) row[0]);
									noData = false;
									break;
								}
							}
							if (noData == true) {
								advisementList.add((long) 0);
							}
						}
					} else {
						List app = null;
						switch (Integer.parseInt(ops[0])) {
						case 1: app = advisementDao.getAdvisementsByQuarterOfStudent(fromQ, toQ, 0, 10);
							break;
						case 2: app = advisementDao.getAdvisementsByQuarterOfStudent(fromQ, toQ, 10, 20);
							break;
						case 3: app = advisementDao.getAdvisementsByQuarterOfStudent(fromQ, toQ, 20, 30);
							break;
						case 4: app = advisementDao.getAdvisementsByQuarterOfStudent(fromQ, toQ, 30, 100);
							break;
						}
						
						for (int m = 0; m < quarterList.size(); m++) {
							boolean noData = true;
							for (int n = 0; n < app.size(); n++) {
								Object[] row = (Object[]) app.get(n);
						
								if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
									advisementList.add((Long) row[0]);
									noData = false;
									break;
								}
							}
							if (noData == true) {
								advisementList.add((long) 0);
							}
						}
						
						for (int j = 1; j < ops.length; j++) {
							switch (Integer.parseInt(ops[j])) {
							case 1: app = advisementDao.getAdvisementsByQuarterOfStudent(fromQ, toQ, 0, 10);
								break;
							case 2: app = advisementDao.getAdvisementsByQuarterOfStudent(fromQ, toQ, 10, 20);
								break;
							case 3: app = advisementDao.getAdvisementsByQuarterOfStudent(fromQ, toQ, 20, 30);
								break;
							case 4: app = advisementDao.getAdvisementsByQuarterOfStudent(fromQ, toQ, 30, 100);
								break;
							}
							
							for (int m = 0; m < quarterList.size(); m++) {
								boolean noData = true;
								for (int n = 0; n < app.size(); n++) {
									Object[] row = (Object[]) app.get(n);
							
									if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
										advisementList.add(m, advisementList.remove(m) + (Long) row[0]);
										noData = false;
										break;
									}
								}
								if (noData == true) {
									advisementList.add(m, advisementList.remove(m) + (long) 0);
								}
							}
						}
					}
					
					List<Long> noAdvisementList = new ArrayList<Long>();
					for (int p = 0; p < advisementList.size(); p++) {
						noAdvisementList.add(studentList.get(p)-advisementList.get(p));
					}
			
					allData.put("Advised", advisementList );
					allData.put("Not Advised", noAdvisementList );
					model.addAttribute( "title", "Advised" );
				} else if (report.getGroup().equals("student")) {
					List app = advisementDao.getAdvisementsByStudentClassification(fromQ, toQ);
					
					List<Long> freshmenList = new ArrayList<Long>();
					List<Long> sophomoreList = new ArrayList<Long>();
					List<Long> juniorList = new ArrayList<Long>();
					List<Long> seniorList = new ArrayList<Long>();
					List<Long> unspecifiedList = new ArrayList<Long>();
					boolean addUnspecified = false;
					
					for (int m = 0; m < quarterList.size(); m++) {
						boolean noFreshmen = true;
						boolean noSophomore = true;
						boolean noJunior = true;
						boolean noSenior = true;
						boolean noUnspecified = true;
						long totalFreshman = 0;
						long totalSophomore = 0;
						long totalJunior = 0;
						long totalSenior = 0;
						long totalUnspecified = 0;
						for (int n = 0; n < app.size(); n++) {
							Object[] row = (Object[]) app.get(n);
							
							if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
								if (row[2] == null) {
									totalUnspecified = totalUnspecified + (Long) row[0];
									noUnspecified = false;
									addUnspecified = true;
								} else {
									if ((Integer)row[2] < 10) {
										totalFreshman = totalFreshman + (Long) row[0];
										noFreshmen = false;
									}
						
									if ((Integer)row[2] >= 10 && (Integer)row[2] < 20) {
										totalSophomore = totalSophomore + (Long) row[0];
										noSophomore = false;
									}
								
									if ((Integer)row[2] >= 20 && (Integer)row[2] < 30) {
										totalJunior = totalJunior + (Long) row[0];
										noJunior = false;
									}
						
									if ((Integer)row[2] >= 30) { 
										totalSenior = totalSenior + (Long) row[0];
										noSenior = false;
									}
								}
							}
						}
						
						if (noFreshmen == true) {
							freshmenList.add((long) 0);
						} else {
							freshmenList.add(totalFreshman);
						}
						if (noSophomore == true) {
							sophomoreList.add((long) 0);
						} else {
							sophomoreList.add(totalSophomore);
						}
						if (noJunior == true) {
							juniorList.add((long) 0);
						} else {
							juniorList.add(totalJunior);
						}
						if (noSenior == true) {
							seniorList.add((long) 0);
						} else {
							seniorList.add(totalSenior);
						}
						if (noUnspecified == true) {
							unspecifiedList.add((long) 0);
						} else {
							unspecifiedList.add(totalUnspecified);
						}
					}
			
					allData.put("Freshmen", freshmenList);
					allData.put("Sophomore", sophomoreList);
					allData.put("Junior", juniorList);
					allData.put("Senior", seniorList);
					if (addUnspecified == true) {
						allData.put("Unspecified", unspecifiedList);
					}

					model.addAttribute( "title", "Advised by Student Classification" );
				} else if (report.getGroup().equals("department")) {
					List<Major> majorTypeList = majorDao.getMajors();
					
					String[] ops = report.getYear().split(",");
					if (report.getYear().equals("10")) {
						List app = advisementDao.getAdvisementsByDepartment(fromQ, toQ);
						
						for (int i = 0; i < majorTypeList.size(); i++) {
							List<Long> majorList = new ArrayList<Long>();
							Major st = majorTypeList.get(i);
							for (int m = 0; m < quarterList.size(); m++) {
								boolean noData = true;				
								for (int n = 0; n < app.size(); n++) {
									Object[] row = (Object[]) app.get(n);
									if (row[2] != null) {	
										Major t = (Major)row[2];
										if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
											if (st.getId().equals(t.getId())) {
												majorList.add((Long) row[0]);
												noData = false;
												break;
											}
										}
									}
								}
								if (noData == true) {
									majorList.add((long) 0);
								}
							}
							allData.put(st.getSymbol(), majorList);
						}

						boolean addUnspecified = false;
						List<Long> unspecifiedList = new ArrayList<Long>();
						for (int m = 0; m < quarterList.size(); m++) {
							boolean noData = true;				
							for (int n = 0; n < app.size(); n++) {
								Object[] row = (Object[]) app.get(n);
								if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
									if (row[2] == null) {
										unspecifiedList.add((Long) row[0]);
										noData = false;
										addUnspecified = true;
										break;
									}
								}
							}
							if (noData == true) {
								unspecifiedList.add((long) 0);
							}
						}
						if (addUnspecified == true) {
							allData.put("Unspecified", unspecifiedList);
						}
					} else {
						List app = null;
						switch (Integer.parseInt(ops[0])) {
						case 1: app = advisementDao.getAdvisementsByDepartmentOfStudent(fromQ, toQ, 0, 10);
							break;
						case 2: app = advisementDao.getAdvisementsByDepartmentOfStudent(fromQ, toQ, 10, 20);
							break;
						case 3: app = advisementDao.getAdvisementsByDepartmentOfStudent(fromQ, toQ, 20, 30);
							break;
						case 4: app = advisementDao.getAdvisementsByDepartmentOfStudent(fromQ, toQ, 30, 100);
							break;
						}
						
						for (int i = 0; i < majorTypeList.size(); i++) {
							List<Long> majorList = new ArrayList<Long>();
							Major st = majorTypeList.get(i);
							for (int m = 0; m < quarterList.size(); m++) {
								boolean noData = true;				
								for (int n = 0; n < app.size(); n++) {
									Object[] row = (Object[]) app.get(n);
									if (row[2] != null) {	
										Major t = (Major)row[2];
										if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
											if (st.getId().equals(t.getId())) {
												majorList.add((Long) row[0]);
												noData = false;
												break;
											}
										}
									}
								}
								if (noData == true) {
									majorList.add((long) 0);
								}
							}
							allData.put(st.getSymbol(), majorList);
						}

						boolean addUnspecified = false;
						List<Long> unspecifiedList = new ArrayList<Long>();
						for (int m = 0; m < quarterList.size(); m++) {
							boolean noData = true;				
							for (int n = 0; n < app.size(); n++) {
								Object[] row = (Object[]) app.get(n);
								if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
									if (row[2] == null) {
										unspecifiedList.add((Long) row[0]);
										noData = false;
										addUnspecified = true;
										break;
									}
								}
							}
							if (noData == true) {
								unspecifiedList.add((long) 0);
							}
						}
						if (addUnspecified == true) {
							allData.put("Unspecified", unspecifiedList);
						}
						
						for (int j = 1; j < ops.length; j++) {
							switch (Integer.parseInt(ops[j])) {
							case 10: app = advisementDao.getAdvisementsByDepartment(fromQ, toQ);
								break;
							case 1: app = advisementDao.getAdvisementsByDepartmentOfStudent(fromQ, toQ, 0, 10);
								break;
							case 2: app = advisementDao.getAdvisementsByDepartmentOfStudent(fromQ, toQ, 10, 20);
								break;
							case 3: app = advisementDao.getAdvisementsByDepartmentOfStudent(fromQ, toQ, 20, 30);
								break;
							case 4: app = advisementDao.getAdvisementsByDepartmentOfStudent(fromQ, toQ, 30, 100);
								break;
							}
							
							for (int i = 0; i < majorTypeList.size(); i++) {
								Major st = majorTypeList.get(i);
								List majorList = allData.get(st.getSymbol());
								for (int m = 0; m < quarterList.size(); m++) {
									boolean noData = true;				
									for (int n = 0; n < app.size(); n++) {
										Object[] row = (Object[]) app.get(n);
										if (row[2] != null) {	
											Major t = (Major)row[2];
											if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
												if (st.getId().equals(t.getId())) {
													majorList.add(m, (Long)majorList.remove(m) + (Long) row[0]);
													noData = false;
													break;
												}
											}
										}
									}
									if (noData == true) {
										majorList.add(m, (Long)majorList.remove(m) + (long) 0);
									}
								}
								allData.put(st.getSymbol(), majorList);
							}

							addUnspecified = false;
							boolean unsp = false;
							if (allData.containsKey("Unspecified")) {
								unspecifiedList = allData.get("Unspecified");
								unsp = true;
							} else {
								unspecifiedList = new ArrayList<Long>();
							}
							for (int m = 0; m < quarterList.size(); m++) {
								boolean noData = true;				
								for (int n = 0; n < app.size(); n++) {
									Object[] row = (Object[]) app.get(n);
									if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
										if (row[2] == null) {
											if (unsp == true) {
												unspecifiedList.add(m, (Long)unspecifiedList.remove(m) + (Long) row[0]);
											} else {
												unspecifiedList.add((Long) row[0]);
											}
											noData = false;
											addUnspecified = true;
											break;
										}
									}
								}
								if (noData == true) {
									if (unsp == true) {
										unspecifiedList.add(m, (Long)unspecifiedList.remove(m) + (long) 0);
									} else {
										unspecifiedList.add((long) 0);
									}
								}
							}
							if (addUnspecified == true) {
								allData.put("Unspecified", unspecifiedList);
							}
						}
					}
					
					model.addAttribute( "title", "Advised by Department" );

				} else if (report.getGroup().equals("gender")) {
					List<Long> maleList = new ArrayList<Long>();
					List<Long> femaleList = new ArrayList<Long>();
					
					String[] ops = report.getYear().split(",");
					
					if (report.getYear().equals("10")) {
						List app = advisementDao.getAdvisementsByGender(fromQ, toQ);
						
						for (int m = 0; m < quarterList.size(); m++) {
							boolean noMale = true;
							boolean noFemale = true;
							for (int n = 0; n < app.size(); n++) {
								Object[] row = (Object[]) app.get(n);
								
								if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
									if (row[2].equals("M")) {
										maleList.add((Long) row[0]);
										noMale = false;
									}
							
									if (row[2].equals("F")) {
										femaleList.add((Long) row[0]);
										noFemale = false;
									}
								}
						
								if (noMale == false && noFemale== false) {
									break;
								}
							}
							if (noMale == true) {
								maleList.add((long) 0);
							}
							if (noFemale == true) {
								femaleList.add((long) 0);
							}
						}
					} else {
						List app = null;
						switch (Integer.parseInt(ops[0])) {
						case 1: app = advisementDao.getAdvisementsByGenderOfStudent(fromQ, toQ, 0, 10);
							break;
						case 2: app = advisementDao.getAdvisementsByGenderOfStudent(fromQ, toQ, 10, 20);
							break;
						case 3: app = advisementDao.getAdvisementsByGenderOfStudent(fromQ, toQ, 20, 30);
							break;
						case 4: app = advisementDao.getAdvisementsByGenderOfStudent(fromQ, toQ, 30, 100);
							break;
						}
						
						for (int m = 0; m < quarterList.size(); m++) {
							boolean noMale = true;
							boolean noFemale = true;
							for (int n = 0; n < app.size(); n++) {
								Object[] row = (Object[]) app.get(n);
								
								if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
									if (row[2].equals("M")) {
										maleList.add((Long) row[0]);
										noMale = false;
									}
							
									if (row[2].equals("F")) {
										femaleList.add((Long) row[0]);
										noFemale = false;
									}
								}
						
								if (noMale == false && noFemale== false) {
									break;
								}
							}
							if (noMale == true) {
								maleList.add((long) 0);
							}
							if (noFemale == true) {
								femaleList.add((long) 0);
							}
						}
						
						for (int j = 1; j < ops.length; j++) {
							switch (Integer.parseInt(ops[j])) {
							case 1: app = advisementDao.getAdvisementsByGenderOfStudent(fromQ, toQ, 0, 10);
								break;
							case 2: app = advisementDao.getAdvisementsByGenderOfStudent(fromQ, toQ, 10, 20);
								break;
							case 3: app = advisementDao.getAdvisementsByGenderOfStudent(fromQ, toQ, 20, 30);
								break;
							case 4: app = advisementDao.getAdvisementsByGenderOfStudent(fromQ, toQ, 30, 100);
								break;
							}
							
							for (int m = 0; m < quarterList.size(); m++) {
								boolean noMale = true;
								boolean noFemale = true;
								for (int n = 0; n < app.size(); n++) {
									Object[] row = (Object[]) app.get(n);
									
									if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
										if (row[2].equals("M")) {
											maleList.add(m, maleList.remove(m) + (Long) row[0]);
											noMale = false;
										}
								
										if (row[2].equals("F")) {
											femaleList.add(m, femaleList.remove(m) + (Long) row[0]);
											noFemale = false;
										}
									}
							
									if (noMale == false && noFemale== false) {
										break;
									}
								}
								if (noMale == true) {
									maleList.add(m, maleList.remove(m) + (long) 0);
								}
								if (noFemale == true) {
									femaleList.add(m, femaleList.remove(m) + (long) 0);
								}
							}
						}
					}
					
					allData.put("Male", maleList);
					allData.put("Female", femaleList);

					model.addAttribute( "title", "Advised by Gender" );
				}
			} else if (report.getType().equals("enrollment")) {
				if (report.getGroup1().equals("enrolled")) {
					String[] ops = report.getYear().split(",");
					List<Long> studentList = getStudentsByQuarter(quarterList, Integer.parseInt(ops[0])*10);
					for (int i = 1; i < ops.length; i++) {
						List<Long> tempList = getStudentsByQuarter(quarterList, Integer.parseInt(ops[i])*10);
			
						for (int k = 0; k < tempList.size(); k++) {
							studentList.add(k, studentList.remove(k) + tempList.get(k));
						}
					}
					
					List<Long> enrollList = new ArrayList<Long>();
					if (report.getYear().equals("10")) {
						List app = coursePlanDao.getCoursePlansByQuarter(fromQ, toQ);
						
						for (int m = 0; m < quarterList.size(); m++) {
							boolean noData = true;
							for (int n = 0; n < app.size(); n++) {
								Object[] row = (Object[]) app.get(n);
				
								if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
									enrollList.add((Long) row[0]);
									noData = false;
									break;
								}
							}
							if (noData == true) {
								enrollList.add((long) 0);
							}
						}
					} else {
						List app = null;
						switch (Integer.parseInt(ops[0])) {
						case 10: app = coursePlanDao.getCoursePlansByQuarter(fromQ, toQ);
							break;
						case 1: app = coursePlanDao.getCoursePlansByQuarterOfStudent(fromQ, toQ, -10, 10);
							break;
						case 2: app = coursePlanDao.getCoursePlansByQuarterOfStudent(fromQ, toQ, 10, 20);
							break;
						case 3: app = coursePlanDao.getCoursePlansByQuarterOfStudent(fromQ, toQ, 20, 30);
							break;
						case 4: app = coursePlanDao.getCoursePlansByQuarterOfStudent(fromQ, toQ, 30, 100);
							break;
						}

						for (int m = 0; m < quarterList.size(); m++) {
							boolean noData = true;
							for (int n = 0; n < app.size(); n++) {
								Object[] row = (Object[]) app.get(n);
				
								if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
									enrollList.add((Long) row[0]);
									noData = false;
									break;
								}
							}
							if (noData == true) {
								enrollList.add((long) 0);
							}
						}
						for (int j = 1; j < ops.length; j++) {
							switch (Integer.parseInt(ops[j])) {
							case 10: app = coursePlanDao.getCoursePlansByQuarter(fromQ, toQ);
								break;
							case 1: app = coursePlanDao.getCoursePlansByQuarterOfStudent(fromQ, toQ, -10, 10);
								break;
							case 2: app = coursePlanDao.getCoursePlansByQuarterOfStudent(fromQ, toQ, 10, 20);
								break;
							case 3: app = coursePlanDao.getCoursePlansByQuarterOfStudent(fromQ, toQ, 20, 30);
								break;
							case 4: app = coursePlanDao.getCoursePlansByQuarterOfStudent(fromQ, toQ, 30, 100);
								break;
							}

							for (int m = 0; m < quarterList.size(); m++) {
								boolean noData = true;
								for (int n = 0; n < app.size(); n++) {
									Object[] row = (Object[]) app.get(n);
					
									if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
										enrollList.add(m, enrollList.remove(m) + (Long) row[0]);
										noData = false;
										break;
									}
								}
								if (noData == true) {
									enrollList.add(m, enrollList.remove(m) + (long) 0);
								}
							}
						}
					}
					
					List<Long> noEnrollList = new ArrayList<Long>();
					for (int p = 0; p < enrollList.size(); p++) {
						noEnrollList.add(studentList.get(p)-enrollList.get(p));
					}
			
					allData.put("Enrolled", enrollList );
					allData.put("Not Enrolled", noEnrollList );
					model.addAttribute( "title", "Enrolled" );

				} else if (report.getGroup1().equals("student")) {
					List app = coursePlanDao.getCoursePlansByStudentClassification(fromQ, toQ);

					List<Long> freshmenList = new ArrayList<Long>();
					List<Long> sophomoreList = new ArrayList<Long>();
					List<Long> juniorList = new ArrayList<Long>();
					List<Long> seniorList = new ArrayList<Long>();
					List<Long> unspecifiedList = new ArrayList<Long>();
					boolean addUnspecified = false;
					
					for (int m = 0; m < quarterList.size(); m++) {
						boolean noFreshmen = true;
						boolean noSophomore = true;
						boolean noJunior = true;
						boolean noSenior = true;
						boolean noUnspecified = true;
						long totalFreshman = 0;
						long totalSophomore = 0;
						long totalJunior = 0;
						long totalSenior = 0;
						long totalUnspecified = 0;
						for (int n = 0; n < app.size(); n++) {
							Object[] row = (Object[]) app.get(n);
					
							if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
								if (row[2] == null) {
									totalUnspecified = totalUnspecified + (Long) row[0];
									noUnspecified = false;
									addUnspecified = true;
								} else {
									if ((Integer)row[2] < 10) {
										totalFreshman = totalFreshman + (Long) row[0];
										noFreshmen = false;
									}
				
									if ((Integer)row[2] >= 10 && (Integer)row[2] < 20) {
										totalSophomore = totalSophomore + (Long) row[0];
										noSophomore = false;
									}
						
									if ((Integer)row[2] >= 20 && (Integer)row[2] < 30) {
										totalJunior = totalJunior + (Long) row[0];
										noJunior = false;
									}
				
									if ((Integer)row[2] >= 30) { 
										totalSenior = totalSenior + (Long) row[0];
										noSenior = false;
									}
								}
							}
						}
						
						if (noFreshmen == true) {
							freshmenList.add((long) 0);
						} else {
							freshmenList.add(totalFreshman);
						}
						if (noSophomore == true) {
							sophomoreList.add((long) 0);
						} else {
							sophomoreList.add(totalSophomore);
						}
						if (noJunior == true) {
							juniorList.add((long) 0);
						} else {
							juniorList.add(totalJunior);
						}
						if (noSenior == true) {
							seniorList.add((long) 0);
						} else {
							seniorList.add(totalSenior);
						}
						if (noUnspecified == true) {
							unspecifiedList.add((long) 0);
						} else {
							unspecifiedList.add(totalUnspecified);
						}
					}
	
					allData.put("Freshmen", freshmenList);
					allData.put("Sophomore", sophomoreList);
					allData.put("Junior", juniorList);
					allData.put("Senior", seniorList);
					if (addUnspecified == true) {
						allData.put("Unspecified", unspecifiedList);
					}
					model.addAttribute( "title", "Enrolled by Student Classification" );
					
				} else if (report.getGroup1().equals("department")) {
					List<Major> majorTypeList = majorDao.getMajors();
			
					String[] ops = report.getYear().split(",");
					if (report.getYear().equals("10")) {
						List app = coursePlanDao.getCoursePlansByDepartment(fromQ, toQ);

						for (int i = 0; i < majorTypeList.size(); i++) {
							List<Long> majorList = new ArrayList<Long>();
							Major st = majorTypeList.get(i);
							for (int m = 0; m < quarterList.size(); m++) {
								boolean noData = true;				
								for (int n = 0; n < app.size(); n++) {
									Object[] row = (Object[]) app.get(n);
									if (row[2] != null) {	
										Major t = (Major)row[2];
										if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
											if (st.getId().equals(t.getId())) {
												majorList.add((Long) row[0]);
												noData = false;
												break;
											}
										}
									}
								}
								if (noData == true) {
									majorList.add((long) 0);
								}
							}
							allData.put(st.getSymbol(), majorList);
						}
						
						boolean addUnspecified = false;
						List<Long> unspecifiedList = new ArrayList<Long>();
						for (int m = 0; m < quarterList.size(); m++) {
							boolean noData = true;				
							for (int n = 0; n < app.size(); n++) {
								Object[] row = (Object[]) app.get(n);
								if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
									if (row[2] == null) {
										unspecifiedList.add((Long) row[0]);
										noData = false;
										addUnspecified = true;
										break;
									}
								}
							}
							if (noData == true) {
								unspecifiedList.add((long) 0);
							}
						}
						if (addUnspecified == true) {
							allData.put("Unspecified", unspecifiedList);
						}
					} else {
						List app = null;
						switch (Integer.parseInt(ops[0])) {
						case 1: app = coursePlanDao.getCoursePlansByDepartmentOfStudent(fromQ, toQ, -10, 10);
							break;
						case 2: app = coursePlanDao.getCoursePlansByDepartmentOfStudent(fromQ, toQ, 10, 20);
							break;
						case 3: app = coursePlanDao.getCoursePlansByDepartmentOfStudent(fromQ, toQ, 20, 30);
							break;
						case 4: app = coursePlanDao.getCoursePlansByDepartmentOfStudent(fromQ, toQ, 30, 100);
							break;
						}
						
						for (int i = 0; i < majorTypeList.size(); i++) {
							List<Long> majorList = new ArrayList<Long>();
							Major st = majorTypeList.get(i);
							for (int m = 0; m < quarterList.size(); m++) {
								boolean noData = true;				
								for (int n = 0; n < app.size(); n++) {
									Object[] row = (Object[]) app.get(n);
									if (row[2] != null) {	
										Major t = (Major)row[2];
										if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
											if (st.getId().equals(t.getId())) {
												majorList.add((Long) row[0]);
												noData = false;
												break;
											}
										}
									}
								}
								if (noData == true) {
									majorList.add((long) 0);
								}
							}
							allData.put(st.getSymbol(), majorList);
						}
						
						boolean addUnspecified = false;
						List<Long> unspecifiedList = new ArrayList<Long>();
						for (int m = 0; m < quarterList.size(); m++) {
							boolean noData = true;				
							for (int n = 0; n < app.size(); n++) {
								Object[] row = (Object[]) app.get(n);
								if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
									if (row[2] == null) {
										unspecifiedList.add((Long) row[0]);
										noData = false;
										addUnspecified = true;
										break;
									}
								}
							}
							if (noData == true) {
								unspecifiedList.add((long) 0);
							}
						}
						if (addUnspecified == true) {
							allData.put("Unspecified", unspecifiedList);
						}
						
						for (int j = 1; j < ops.length; j++) {
							switch (Integer.parseInt(ops[j])) {
							case 1: app = coursePlanDao.getCoursePlansByDepartmentOfStudent(fromQ, toQ, -10, 10);
								break;
							case 2: app = coursePlanDao.getCoursePlansByDepartmentOfStudent(fromQ, toQ, 10, 20);
								break;
							case 3: app = coursePlanDao.getCoursePlansByDepartmentOfStudent(fromQ, toQ, 20, 30);
								break;
							case 4: app = coursePlanDao.getCoursePlansByDepartmentOfStudent(fromQ, toQ, 30, 100);
								break;
							}
							
							for (int i = 0; i < majorTypeList.size(); i++) {
								Major st = majorTypeList.get(i);
								List majorList = allData.get(st.getSymbol());
								for (int m = 0; m < quarterList.size(); m++) {
									boolean noData = true;				
									for (int n = 0; n < app.size(); n++) {
										Object[] row = (Object[]) app.get(n);
										if (row[2] != null) {	
											Major t = (Major)row[2];
											if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
												if (st.getId().equals(t.getId())) {
													majorList.add(m, (Long)majorList.remove(m) + (Long) row[0]);
													noData = false;
													break;
												}
											}
										}
									}
									if (noData == true) {
										majorList.add(m, (Long)majorList.remove(m) + (long) 0);
									}
								}
								allData.put(st.getSymbol(), majorList);
							}
							
							addUnspecified = false;
							boolean unsp = false;
							if (allData.containsKey("Unspecified")) {
								unspecifiedList = allData.get("Unspecified");
								unsp = true;
							} else {
								unspecifiedList = new ArrayList<Long>();
							}
			
							for (int m = 0; m < quarterList.size(); m++) {
								boolean noData = true;				
								for (int n = 0; n < app.size(); n++) {
									Object[] row = (Object[]) app.get(n);
									if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
										if (row[2] == null) {
											if (unsp == true) {
												unspecifiedList.add(m, (Long)unspecifiedList.remove(m) + (Long) row[0]);
											} else {
												unspecifiedList.add((Long) row[0]);
											}
											noData = false;
											addUnspecified = true;
											break;
										}
									}
								}
								if (noData == true) {
									if (unsp == true) {
										unspecifiedList.add(m, (Long)unspecifiedList.remove(m) + (long) 0);
									} else {
										unspecifiedList.add((long) 0);
									}
								}
							}
							if (addUnspecified == true) {
								allData.put("Unspecified", unspecifiedList);
							}
						}
					}
					
					model.addAttribute( "title", "Enrolled by Department" );

				} else if (report.getGroup1().equals("gender")) {
					List<Long> maleList = new ArrayList<Long>();
					List<Long> femaleList = new ArrayList<Long>();
					
					String[] ops = report.getYear().split(",");
					
					if (report.getYear().equals("10")) {
						List app = coursePlanDao.getCoursePlansByGender(fromQ, toQ);
						
						for (int m = 0; m < quarterList.size(); m++) {
							boolean noMale = true;
							boolean noFemale = true;
							for (int n = 0; n < app.size(); n++) {
								Object[] row = (Object[]) app.get(n);
						
								if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
									if (row[2].equals("M")) {
										maleList.add((Long) row[0]);
										noMale = false;
									}
					
									if (row[2].equals("F")) {
										femaleList.add((Long) row[0]);
										noFemale = false;
									}
								}
				
								if (noMale == false && noFemale== false) {
									break;
								}
							}
							if (noMale == true) {
								maleList.add((long) 0);
							}
							if (noFemale == true) {
								femaleList.add((long) 0);
							}
						}
					} else {
						List app = null;
						switch (Integer.parseInt(ops[0])) {
						case 1: app = coursePlanDao.getCoursePlansByGenderOfStudent(fromQ, toQ, -10, 10);
							break;
						case 2: app = coursePlanDao.getCoursePlansByGenderOfStudent(fromQ, toQ, 10, 20);
							break;
						case 3: app = coursePlanDao.getCoursePlansByGenderOfStudent(fromQ, toQ, 20, 30);
							break;
						case 4: app = coursePlanDao.getCoursePlansByGenderOfStudent(fromQ, toQ, 30, 100);
							break;
						}
						
						for (int m = 0; m < quarterList.size(); m++) {
							boolean noMale = true;
							boolean noFemale = true;
							for (int n = 0; n < app.size(); n++) {
								Object[] row = (Object[]) app.get(n);
						
								if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
									if (row[2].equals("M")) {
										maleList.add((Long) row[0]);
										noMale = false;
									}
					
									if (row[2].equals("F")) {
										femaleList.add((Long) row[0]);
										noFemale = false;
									}
								}
				
								if (noMale == false && noFemale== false) {
									break;
								}
							}
							if (noMale == true) {
								maleList.add((long) 0);
							}
							if (noFemale == true) {
								femaleList.add((long) 0);
							}
						}
						
						for (int j = 1; j < ops.length; j++) {
							switch (Integer.parseInt(ops[j])) {
							case 1: app = coursePlanDao.getCoursePlansByGenderOfStudent(fromQ, toQ, -10, 10);
								break;
							case 2: app = coursePlanDao.getCoursePlansByGenderOfStudent(fromQ, toQ, 10, 20);
								break;
							case 3: app = coursePlanDao.getCoursePlansByGenderOfStudent(fromQ, toQ, 20, 30);
								break;
							case 4: app = coursePlanDao.getCoursePlansByGenderOfStudent(fromQ, toQ, 30, 100);
								break;
							}
							
							for (int m = 0; m < quarterList.size(); m++) {
								boolean noMale = true;
								boolean noFemale = true;
								for (int n = 0; n < app.size(); n++) {
									Object[] row = (Object[]) app.get(n);
							
									if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
										if (row[2].equals("M")) {
											maleList.add(m, maleList.remove(m) + (Long) row[0]);
											noMale = false;
										}
						
										if (row[2].equals("F")) {
											femaleList.add(m, femaleList.remove(m) + (Long) row[0]);
											noFemale = false;
										}
									}
					
									if (noMale == false && noFemale== false) {
										break;
									}
								}
								if (noMale == true) {
									maleList.add(m, maleList.remove(m) + (long) 0);
								}
								if (noFemale == true) {
									femaleList.add(m, femaleList.remove(m) + (long) 0);
								}
							}
						}
					}
					
					allData.put("Male", maleList);
					allData.put("Female", femaleList);

					model.addAttribute( "title", "Enrolled by Gender" );

				}
			}
			} else {
				Calendar localCalendar = Calendar.getInstance();
				int currentYear = localCalendar.get(Calendar.YEAR);
				 
				Map<Integer,Integer> years = new LinkedHashMap<Integer,Integer>();
				for (int i = 2011; i <= currentYear; i++) {
					years.put(i, i);
				}
				
				model.addAttribute( "yearList", years );
				model.addAttribute( "optionError", "Please select options" );
				return "report/viewAdviseEnrollReport";
			}
			
			String[] ops = report.getYear().split(",");
			if (report.getYear().equals("10")) {
				model.addAttribute( "students", "All" );
			} else {
				String students = "";
				for (int j = 0; j < ops.length; j++) {
					if (ops[j].equals("1")) {
						students = students + " First-Year ";
					} else if (ops[j].equals("2")) {
						students = students + " Second-Year ";
					} else if (ops[j].equals("3")) {
						students = students + " Third-Year ";
					} else if (ops[j].equals("4")) {
						students = students + " Fourth-Year ";
					} 
					
					if (j != ops.length-1) {
						students = students + " and ";
					}
				}
				model.addAttribute( "students", students );
			}
			
			Calendar localCalendar = Calendar.getInstance();
			int currentYear = localCalendar.get(Calendar.YEAR);
			 
			Map<Integer,Integer> years = new LinkedHashMap<Integer,Integer>();
			for (int i = 2011; i <= currentYear; i++) {
				years.put(i, i);
			}
			
			model.addAttribute( "yearList", years );
			model.addAttribute( "qList", quarterList );
			model.addAttribute( "allData", allData );
			model.addAttribute( "fromQ", fromQ );
			model.addAttribute( "toQ", toQ );
			model.addAttribute( "chartFlag", true );
			
			return "report/viewAdviseEnrollReport";
		}
	
	@RequestMapping(
	    value = ("/report/viewVisitReport.html"),
	    method = RequestMethod.GET)
	public String viewVisitReport(ModelMap model)
	{
		Calendar localCalendar = Calendar.getInstance();
		int currentYear = localCalendar.get(Calendar.YEAR);
		 
		Map<Integer,Integer> years = new LinkedHashMap<Integer,Integer>();
		for (int i = 2011; i <= currentYear; i++) {
			years.put(i, i);
		}
		
		model.addAttribute( "yearList", years );
		model.addAttribute( "report", new Report() );
	    return "report/viewVisitReport";
	}
	
	@RequestMapping(
		    value = ("/report/viewVisitReport.html"),
		    method = RequestMethod.POST)
		public String viewVisitReport(@ModelAttribute("report") Report report, BindingResult result, ModelMap model)
		{
			Quarter fromQ = new Quarter(Integer.parseInt(report.getFromYear()), report.getFromQuarter());
			Quarter toQ = new Quarter(Integer.parseInt(report.getToYear()), report.getToQuarter());
	
			List<Quarter> quarterList = getQuarters(fromQ, toQ);
			
			Map<String, List> allData = new LinkedHashMap<String, List>();
			
			if (report.getType().equals("service")) {
			
				List<ServiceType> serviceTypeList = serviceTypeDao.getServiceTypes();
		
				List app = serviceDao.getServicesByQuarter(fromQ, toQ);
				
				for (int i = 0; i < serviceTypeList.size(); i++) {
					List<Long> serviceList = new ArrayList<Long>();
					ServiceType st = serviceTypeList.get(i);
					for (int m = 0; m < quarterList.size(); m++) {
				
						boolean noData = true;				
						for (int n = 0; n < app.size(); n++) {
							Object[] row = (Object[]) app.get(n);
							ServiceType t = (ServiceType)row[2];
					
							if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
								if (st.getId().equals(t.getId())) {
									serviceList.add((Long) row[0]);
									noData = false;
									break;
								}
							}
						}
						if (noData == true) {
							serviceList.add((long) 0);
						}
					}
					allData.put(st.getName(), serviceList);
				}	 
				model.addAttribute( "title", "Service" );
				
			} else if (report.getType().equals("visit")) {
				List<VisitReasonType> visitReasonTypeList = visitReasonTypeDao.getVisitReasonTypes();
				
				List app = visitReasonDao.getVisitReasonsByQuarter(fromQ, toQ);
				
				for (int i = 0; i < visitReasonTypeList.size(); i++) {
					List<Long> visitReasonList = new ArrayList<Long>();
					VisitReasonType st = visitReasonTypeList.get(i);
					for (int m = 0; m < quarterList.size(); m++) {
					
						boolean noData = true;				
						for (int n = 0; n < app.size(); n++) {
							Object[] row = (Object[]) app.get(n);
							VisitReasonType t = (VisitReasonType)row[2];
						
							if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
								if (st.getId().equals(t.getId())) {
									visitReasonList.add((Long) row[0]);
									noData = false;
									break;
								}
							}
						}
						if (noData == true) {
							visitReasonList.add((long) 0);
						}
					}
					allData.put(st.getName(), visitReasonList);
				}
				model.addAttribute( "title", "Visit Reason" );
				
			} else if (report.getType().equals("noseen")) {
				List<NoSeenReasonType> noSeenReasonTypeList = noSeenReasonTypeDao.getNoSeenReasonTypes();
				
				List app = noSeenReasonDao.getNoSeenReasonsByQuarter(fromQ, toQ);
				
				for (int i = 0; i < noSeenReasonTypeList.size(); i++) {
					List<Long> noSeenReasonList = new ArrayList<Long>();
					NoSeenReasonType st = noSeenReasonTypeList.get(i);
					for (int m = 0; m < quarterList.size(); m++) {
				
						boolean noData = true;				
						for (int n = 0; n < app.size(); n++) {
							Object[] row = (Object[]) app.get(n);
							NoSeenReasonType t = (NoSeenReasonType)row[2];
					
							if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
								if (st.getId().equals(t.getId())) {
									noSeenReasonList.add((Long) row[0]);
									noData = false;
									break;
								}
							}
						}
						if (noData == true) {
							noSeenReasonList.add((long) 0);
						}
					}
					allData.put(st.getName(), noSeenReasonList);
				}
				model.addAttribute( "title", "No Seen Reason" );
			}
			
			Calendar localCalendar = Calendar.getInstance();
			int currentYear = localCalendar.get(Calendar.YEAR);
			 
			Map<Integer,Integer> years = new LinkedHashMap<Integer,Integer>();
			for (int i = 2011; i <= currentYear; i++) {
				years.put(i, i);
			}
			
			model.addAttribute( "yearList", years );
			model.addAttribute( "qList", quarterList );
			model.addAttribute( "allData", allData );
			model.addAttribute( "fromQ", fromQ );
			model.addAttribute( "toQ", toQ );
			model.addAttribute( "chartFlag", true );
			
		    return "report/viewVisitReport";
		}
	
	public List<Quarter> getQuarters(Quarter fromQ, Quarter toQ) {
		List<Quarter> list = new ArrayList<Quarter>();
		list.add(fromQ);
		
		while (fromQ.getCode() < toQ.getCode()) {
			fromQ = fromQ.next();
			list.add(fromQ);
		}
		
		return list;
	}
	
	public List<Long> getCompletedAppoinment(List<Quarter> quarterList, Quarter fromQ, Quarter toQ) {
		List app = appointmentSectionDao.getCompletedAppointmentSectionsByQuarter(fromQ, toQ);
		
		List<Long> completedList = new ArrayList<Long>();
	
		for (int m = 0; m < quarterList.size(); m++) {
			boolean noData = true;
			for (int n = 0; n < app.size(); n++) {
				Object[] row = (Object[]) app.get(n);
			
				if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
					completedList.add((Long) row[0]);
					noData = false;
					break;
				}
			}
			if (noData == true) {
				completedList.add((long) 0);
			}
		}
	
		return completedList;
	}
	
	public List<Long> getCancelledAppoinment(List<Quarter> quarterList, Quarter fromQ, Quarter toQ) {
		List app = appointmentSectionDao.getCancelledAppointmentSectionsByQuarter(fromQ, toQ);
		
		List<Long> cancelledList = new ArrayList<Long>();
	
		for (int m = 0; m < quarterList.size(); m++) {
			boolean noData = true;
			for (int n = 0; n < app.size(); n++) {
				Object[] row = (Object[]) app.get(n);
				
				if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
					cancelledList.add((Long) row[0]);
					noData = false;
					break;
				}
			}
			if (noData == true) {
				cancelledList.add((long) 0);
			}
		}
		
		return cancelledList;
	}
	
	public List<Long> getMissedAppoinment(List<Quarter> quarterList, Quarter fromQ, Quarter toQ) {
		List app = appointmentSectionDao.getMissedAppointmentSectionsByQuarter(fromQ, toQ);
		
		List<Long> missedList = new ArrayList<Long>();
	
		for (int m = 0; m < quarterList.size(); m++) {
			boolean noData = true;
			for (int n = 0; n < app.size(); n++) {
				Object[] row = (Object[]) app.get(n);
				
				if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
					missedList.add((Long) row[0]);
					noData = false;
					break;
				}
			}
			if (noData == true) {
				missedList.add((long) 0);
			}
		}
	
		return missedList;
	}
	
	public List<Long> getSeenWalkInAppoinment(List<Quarter> quarterList, Quarter fromQ, Quarter toQ) {
		List app = appointmentSectionDao.getSeenWalkInAppointmentSectionsByQuarter(fromQ, toQ);
		
		List<Long> seenList = new ArrayList<Long>();
	
		for (int m = 0; m < quarterList.size(); m++) {
			boolean noData = true;
			for (int n = 0; n < app.size(); n++) {
				Object[] row = (Object[]) app.get(n);
				
				if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
					seenList.add((Long) row[0]);
					noData = false;
					break;
				}
			}
			if (noData == true) {
				seenList.add((long) 0);
			}
		}
	
		return seenList;
	}
	
	public List<Long> getNotSeenWalkInAppoinment(List<Quarter> quarterList, Quarter fromQ, Quarter toQ) {
		List app = appointmentSectionDao.getNotSeenWalkInAppointmentSectionsByQuarter(fromQ, toQ);
		
		List<Long> notSeenList = new ArrayList<Long>();
	
		for (int m = 0; m < quarterList.size(); m++) {
			boolean noData = true;
			for (int n = 0; n < app.size(); n++) {
				Object[] row = (Object[]) app.get(n);
				
				if (((Integer)quarterList.get(m).getCode()).equals((Integer)row[1])) {
					notSeenList.add((Long) row[0]);
					noData = false;
					break;
				}
			}
			if (noData == true) {
				notSeenList.add((long) 0);
			}
		}
	
		return notSeenList;
	}
	
	public List<Long> getStudentsByQuarter(List<Quarter> quarterList, int year) {
		List<Long> studentList = new ArrayList<Long>();
		
		for (int i = 0; i < quarterList.size(); i++) {
			List students = userDao.getStudentsByYearOfStudent(quarterList.get(i), year);
			if (students != null) {
				studentList.add((Long) students.get(0));
			} else {
				studentList.add((long) 0);
			}
		}
		
		return studentList;
	}
	
}
