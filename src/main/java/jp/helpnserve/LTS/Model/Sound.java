package jp.helpnserve.LTS.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private String id;

	@OneToOne(mappedBy = "sound")
	private Sentence sentence;

	@Column(name = "sound_url")
	private String soundURL;
}
