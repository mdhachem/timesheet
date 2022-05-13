package tn.esprit.spring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;

public interface TimesheetRepository extends CrudRepository<Timesheet, Integer> {

	@Query("select DISTINCT m from Mission m join m.timesheets t join t.employe e where e.id=:employeId")
	public List<Mission> findAllMissionByEmployeJPQL(@Param("employeId") int employeId);

	@Query("select DISTINCT e from Employe e " + "join e.timesheets t " + "join t.mission m " + "where m.id=:misId")
	public List<Employe> getAllEmployeByMission(@Param("misId") int missionId);

	@Query("Select t from Timesheet t " + "where t.mission=:mis and " + "t.employe=:emp and "
			+ "t.timesheetPK.dateDebut>=:dateD and " + "t.timesheetPK.dateFin<=:dateF")
	// D'un employe bien defini
	public List<Timesheet> getTimesheetsByMissionAndDate(@Param("emp") Employe employe, @Param("mis") Mission mission,
			@Param("dateD") Date dateDebut, @Param("dateF") Date dateFin);

	public Timesheet findBytimesheetPK(TimesheetPK timesheetPK);
}
