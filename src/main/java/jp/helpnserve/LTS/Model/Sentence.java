package jp.helpnserve.LTS.Model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private int id;

	@Column(name = "origin_sentence")
	private String originSentence;

	@Column(name = "translate_sentence")
	private String translateSentence;

	@Column(name = "explanation")
	private String explanation;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "sound_id")
	private Sound sound;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dictionary_id", nullable = false)
	private Dictionary dictionary;
}
