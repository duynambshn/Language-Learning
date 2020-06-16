package jp.helpnserve.LTS.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "sound")
@Data
@NoArgsConstructor
public class Sound {
	@Id
	@Column(name = "id")
	private int id;

	@OneToOne(mappedBy = "sound")
	private Sentence sentence;

	@Column(name = "sound_url", unique = true)
	private String soundURL;
}
