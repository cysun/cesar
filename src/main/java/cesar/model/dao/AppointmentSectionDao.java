package cesar.model.dao;

import java.util.Date;
import java.util.List;

import cesar.model.AppointmentSection;
import cesar.model.Quarter;
import cesar.model.User;

public interface AppointmentSectionDao {

    public AppointmentSection getAppointmentById( Integer id );

    public List<AppointmentSection> getAvailableAppointmentSectionsByStudent(
        User student );

    public List<AppointmentSection> getAvailableAppointmentSectionsByAdvisor(
        User advisor );

    public AppointmentSection getAppointmentSectionByAdvisorAndStartTimeAndNotWorkingApp(
        User advisor, Date start );

    public AppointmentSection getAppointmentSectionByAdvisorAndStartTimeAndWorkingApp(
        User advisor, Date start );

    public List<AppointmentSection> getActiveAppointmentSectionByAdvisorWithSpeDay(
        User advisor, Date currentDate );

    public List<AppointmentSection> getAppointmentSectionByAdvisorAndBetweenSpeDates(
        User advisor, Date start, Date end );

    public List<AppointmentSection> getAppointmentSectionsByStudentWithSpeDay(
        User student, Date currentTime );

    public void saveAppointmentSection( AppointmentSection appointmentSection );

    public void deleteAppointmentSection( AppointmentSection appointmentSection );

    public List<AppointmentSection> getCompletedAppointmentSectionsByQuarter(Quarter fromQuarter, Quarter toQuarter);
    
    public List<AppointmentSection> getCancelledAppointmentSectionsByQuarter(Quarter fromQuarter, Quarter toQuarter);
    
    public List<AppointmentSection> getMissedAppointmentSectionsByQuarter(Quarter fromQuarter, Quarter toQuarter);

    public List<AppointmentSection> getSeenWalkInAppointmentSectionsByQuarter(Quarter fromQuarter, Quarter toQuarter);
    
    public List<AppointmentSection> getNotSeenWalkInAppointmentSectionsByQuarter(Quarter fromQuarter, Quarter toQuarter);
    
}
