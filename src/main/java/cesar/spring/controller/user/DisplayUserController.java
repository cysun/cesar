package cesar.spring.controller.user;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cesar.model.Advisement;
import cesar.model.CoursePlan;
import cesar.model.Quarter;
import cesar.model.QuarterPlan;
import cesar.model.Section;
import cesar.model.User;
import cesar.model.dao.AdvisementDao;
import cesar.model.dao.CourseDao;
import cesar.model.dao.CoursePlanDao;
import cesar.model.dao.CourseTakenDao;
import cesar.model.dao.CourseTransferredDao;
import cesar.model.dao.CourseWaivedDao;
import cesar.model.dao.ExtraCurriculumActivityDao;
import cesar.model.dao.ExtraCurriculumActivityTypeDao;
import cesar.model.dao.FinancialAidDao;
import cesar.model.dao.FinancialAidTypeDao;
import cesar.model.dao.SectionDao;
import cesar.model.dao.UserDao;
import cesar.spring.security.SecurityUtils;

@Controller
public class DisplayUserController {

    @Autowired
    private UserDao                        userDao;
    @Autowired
    private AdvisementDao                  advisementDao;
    @Autowired
    private FinancialAidDao                financialAidDao;
    @Autowired
    private FinancialAidTypeDao            financialAidTypeDao;
    @Autowired
    private ExtraCurriculumActivityDao     extraCurriculumActivityDao;
    @Autowired
    private ExtraCurriculumActivityTypeDao extraCurriculumActivityTypeDao;
    @Autowired
    private CourseTakenDao                 courseTakenDao;
    @Autowired
    private CourseDao                      courseDao;
    @Autowired
    private CoursePlanDao                  coursePlanDao;
    @Autowired
    private CourseTransferredDao           courseTransferredDao;
    @Autowired
    private CourseWaivedDao                courseWaivedDao;
    @Autowired
    private SectionDao                     sectionDao;

    @RequestMapping(value = { "/user/display.html" })
    public String displayAccount( Integer userId, ModelMap model, String quarterCode, boolean updated, Integer qCode )
    {
        User user = userDao.getUserById( userId );
        if( user == null )
        {
            model.addAttribute( "error", "User Not Found" );
            model.addAttribute( "errorCause",
                "The user you are looking for does not exist." );
            return "error";
        }

        model.addAttribute( "user", user );
        model.addAttribute( "coursesTaken",
            courseTakenDao.getCourseTakenByStudent( user ) );
        model.addAttribute( "coursesTransferred",
            courseTransferredDao.getCourseTransferreds( user ) );
        model.addAttribute( "coursesWaived",
            courseWaivedDao.getCourseWaiveds( user ) );

        if( user.getMajor() != null )
        {
            model.addAttribute( "courses", user.getMajor().getCourses() );
        }
        else
        {
            model.addAttribute( "courses", courseDao.getAllCourses() );
        }
        model.addAttribute( "advisements",
            advisementDao.getAdvisementsByStudent( user ) );
        model.addAttribute( "coursePlans",
            coursePlanDao.getCoursePlanByStudent( user ) );
        
      //get only quarters advised for enrollment
        List<CoursePlan> coursePlans = coursePlanDao.getCoursePlanByStudent( user );
        
        Map<Integer,String> quarters = new LinkedHashMap<Integer,String>();
		for (int i = 0; i < coursePlans.size(); i++) {
        	Set<QuarterPlan> quarterPlans = coursePlans.get(i).getQuarterPlans();
        	for (QuarterPlan quarterPlan: quarterPlans) {
        		int q = quarterPlan.getQuarter().getCode();
        		if (!quarters.containsKey(q)) {
        			quarters.put(q, quarterPlan.getQuarter().toString());
        		}
        	}
        }
        
        model.addAttribute( "quarters", quarters );

      //display all courses advised for enrollment
       	if( quarterCode != null )
        	{
        		Map<CoursePlan, QuarterPlan> coursePlansMap = new LinkedHashMap<CoursePlan, QuarterPlan>();
        		for (int i = 0; i < coursePlans.size(); i++) {
        			Set<QuarterPlan> quarterPlans = coursePlans.get(i).getQuarterPlans();
        			for (QuarterPlan quarterPlan: quarterPlans) {
        				Integer q = quarterPlan.getQuarter().getCode();
        				Integer quarter = Integer.parseInt(quarterCode);
        				if (quarter.equals(q)) {
        					coursePlansMap.put(coursePlans.get(i), quarterPlan);
        				}
        			}
        		}
        
        		model.addAttribute("currentQuarter", quarterCode);
        		model.addAttribute( "coursePlansMap", coursePlansMap);
        		
    			Set<Section> enrolledSections = user.getEnrolledSections();
    			List<Section> enrolled = new ArrayList<Section>();
    			enrolled.addAll(enrolledSections);
    			model.addAttribute( "enrolled", enrolled );
        }

      //display updating message
        if (updated == true) {
        	model.addAttribute( "updatedQuarter", new Quarter(qCode));
        	model.addAttribute( "updated", true);
        }

        model.addAttribute( "financialAidTypes",
            financialAidTypeDao.getFinancialAidTypes() );
        model.addAttribute( "financialAids",
            financialAidDao.getFinancialAids( user ) );

        model.addAttribute( "extraCurriculumTypes",
            extraCurriculumActivityTypeDao.getExtraCurriculumActivityTypes() );
        model.addAttribute( "extraCurriculums",
            extraCurriculumActivityDao.getExtraCurriculumActivities( user ) );

        return "user/display";
    }

    @RequestMapping(value = { "/profile.html", "/profile/edit/" },
        method = RequestMethod.GET)
    public String displayProfile( ModelMap model, String quarterCode )
    {
        User user = userDao.getUserByUsername( SecurityUtils.getUsername() );

        List<Advisement> advisementsForStudent = advisementDao
            .getAdvisementsByStudentForStudentOnly( user );

        model.addAttribute( "user", user );

        model.addAttribute( "advisements", advisementsForStudent );
        model.addAttribute( "coursePlans",
            coursePlanDao.getCoursePlanByStudent( user ) );

        model.addAttribute( "financialAidTypes",
            financialAidTypeDao.getFinancialAidTypes() );
        model.addAttribute( "financialAids",
            financialAidDao.getFinancialAids( user ) );

        model.addAttribute( "extraCurriculumTypes",
            extraCurriculumActivityTypeDao.getExtraCurriculumActivityTypes() );
        model.addAttribute( "extraCurriculums",
            extraCurriculumActivityDao.getExtraCurriculumActivities( user ) );

        model.addAttribute( "coursesTaken",
            courseTakenDao.getCourseTakenByStudent( user ) );
        model.addAttribute( "coursesTransferred",
            courseWaivedDao.getCourseWaiveds( user ) );
        model.addAttribute( "coursesWaived",
            courseTransferredDao.getCourseTransferreds( user ) );

        if( user.getMajor() != null )
        {
            model.addAttribute( "courses", user.getMajor().getCourses() );
        }
        else
        {
            model.addAttribute( "courses", courseDao.getAllCourses() );
        }

      //get only quarters advised for enrollment
        List<CoursePlan> coursePlans = coursePlanDao.getCoursePlanByStudent( user );
        
        Map<Integer,String> quarters = new LinkedHashMap<Integer,String>();
		for (int i = 0; i < coursePlans.size(); i++) {
        	Set<QuarterPlan> quarterPlans = coursePlans.get(i).getQuarterPlans();
        	for (QuarterPlan quarterPlan: quarterPlans) {
        		int q = quarterPlan.getQuarter().getCode();
        		if (!quarters.containsKey(q)) {
        			quarters.put(q, quarterPlan.getQuarter().toString());
        		}
        	}
        }
        
        model.addAttribute( "quarters", quarters );

      //display all courses advised for enrollment
       	if( quarterCode != null )
        	{
        		Map<CoursePlan, QuarterPlan> coursePlansMap = new LinkedHashMap<CoursePlan, QuarterPlan>();
        		for (int i = 0; i < coursePlans.size(); i++) {
        			Set<QuarterPlan> quarterPlans = coursePlans.get(i).getQuarterPlans();
        			for (QuarterPlan quarterPlan: quarterPlans) {
        				Integer q = quarterPlan.getQuarter().getCode();
        				Integer quarter = Integer.parseInt(quarterCode);
        				if (quarter.equals(q)) {
        					coursePlansMap.put(coursePlans.get(i), quarterPlan);
        				}
        			}
        		}
        
        		model.addAttribute("currentQuarter", quarterCode);
        		model.addAttribute( "coursePlansMap", coursePlansMap);
        		Set<Section> enrolledSections = user.getEnrolledSections();
    			List<Section> enrolled = new ArrayList<Section>();
    			enrolled.addAll(enrolledSections);
    			model.addAttribute( "enrolled", enrolled );
        }

        return "profile";
    }
    
    @RequestMapping(value = { "/user/displayEnrollment.html" })
    public String sectionsEnrolled( ModelMap model, String userId, String quarterCode, String forProfile )
    {
    	if( StringUtils.hasText( quarterCode ) )
        {
    		if( forProfile == null )
            {
        		return "redirect:/user/display.html?userId=" + userId + "&quarterCode=" + quarterCode + "&#tab-enrollment";
            }
        	else
        	{
        		return "redirect:/profile.html?quarterCode=" + quarterCode + "#tab-enrollment";
        	}
        } else {
        	return "redirect:/user/display.html?userId=" + userId  + "&#tab-enrollment";
        }
    }

    @RequestMapping(value = { "/user/addEnrollment.html" },
            method = RequestMethod.POST)
    public String addEnrollment( Integer userId, String sections, ModelMap model, Boolean forProfile, Integer quarterCode )
    {
    	User user = userDao.getUserById(userId);
    	
    	if (sections != null) {
    		
    		List<CoursePlan> coursePlans = coursePlanDao.getCoursePlanByStudent(user);
    		List<Section> allSections = new ArrayList<Section>();
    		for (int x = 0; x < coursePlans.size(); x++) {
    			Set<QuarterPlan> qpSet = coursePlans.get(x).getQuarterPlans();
    			for (QuarterPlan qp: qpSet) {
    				if (qp.getQuarter().getCode() == quarterCode) {
    					Set<Section> sects = qp.getSections();
    					allSections.addAll(sects);
    				}
    			}
    		}
    		
    		String[] eachSection = sections.split(",");
    		
    		List<Section> checkedSections = new ArrayList<Section>();
    		for (int a = 0; a < eachSection.length; a++) {
    			Integer sectionId = Integer.parseInt(eachSection[a]);
    			checkedSections.add(sectionDao.getSectionById(sectionId));
    		}
    		
    		List<Section> uncheckedSections = new ArrayList<Section>();
    		for (int a = 0; a < allSections.size(); a++) {
    			Section section = allSections.get(a);
    			if (!(checkedSections.contains(section))) {
    				uncheckedSections.add(section);
    			}
    		}
    		
    		Set<Section> enrolledSections = user.getEnrolledSections();
    		for (int a = 0; a < checkedSections.size(); a++) {
    			Section section = checkedSections.get(a);
    			if (!(enrolledSections.contains(section))) {
    				enrolledSections.add(section);
    			}
    		}

    		for (int a = 0; a < uncheckedSections.size(); a++) {
    			Section section = uncheckedSections.get(a);
    			if (enrolledSections.contains(section)) {
    				enrolledSections.remove(section);
    			}
    		}
    		
    		user.setEnrolledSections(enrolledSections);
    		userDao.saveUser(user);
    		
    	} else {
    		
    		List<CoursePlan> coursePlans = coursePlanDao.getCoursePlanByStudent(user);
    		List<Section> allSections = new ArrayList<Section>();
    		for (int x = 0; x < coursePlans.size(); x++) {
    			Set<QuarterPlan> qpSet = coursePlans.get(x).getQuarterPlans();
    			for (QuarterPlan qp: qpSet) {
    				if (qp.getQuarter().getCode() == quarterCode) {
    					Set<Section> sects = qp.getSections();
    					allSections.addAll(sects);
    				}
    			}
    		}
    		
    		Set<Section> enrolledSections = user.getEnrolledSections();
    		for (int i = 0; i < allSections.size(); i++) {
    			Section section = allSections.get(i);
    			if (enrolledSections.contains(section)) {
    				enrolledSections.remove(section);
    			}
    		}
    		
    		user.setEnrolledSections(enrolledSections);
    		userDao.saveUser(user);
    		
    	}
    	
    	model.addAttribute( "qCode", quarterCode );
    	model.addAttribute( "updated", true );
    	
    	return "redirect:/user/display.html?userId=" + userId + "#tab-enrollment";
    }

    @RequestMapping(value = { "/user/cancelEnrollment.html" })
    public String cancelEnrollment( Integer userId, ModelMap model )
    {
    	return "redirect:/user/display.html?userId=" + userId + "&#tab-enrollment";
    }

}
