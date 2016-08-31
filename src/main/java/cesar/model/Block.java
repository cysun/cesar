package cesar.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "block")
public class Block {
	private static final long    serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
    @JoinColumn(name = "advisor_id", nullable = false)
	private User advisor;
	
	@Column(name = "start_date_time", nullable = false)
	private Calendar startDateTime;
	
	@Column(name = "end_date_time", nullable = false)
	private Calendar endDateTime;
	
	public Block() {
		
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public User getAdvisor() {
		return advisor;
	}

	public void setAdvisor(User advisor) {
		this.advisor = advisor;
	}

	public Calendar getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Calendar startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Calendar getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Calendar endDateTime) {
		this.endDateTime = endDateTime;
	}
}

