package jp.helpnserve.LTS.Model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "sentence")
@Data
@NoArgsConstructor
public class Sentence {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "sentence")
	private String sentence;

	@Column(name = "translate_sentence")
	private String translateSentence;

	@Column(name = "explanation")
	private String explanation;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private Sound sound;

	@ManyToOne
	@JoinColumn(name = "id", nullable = false)
	private Dictionary dictionary;
}
