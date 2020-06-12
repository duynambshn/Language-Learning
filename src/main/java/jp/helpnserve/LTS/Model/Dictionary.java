package jp.helpnserve.LTS.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "dictionary")
@Data
@NoArgsConstructor
public class Dictionary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "dictionary_name")
	private String dictionaryName;

	@Column(name = "release_flag")
	private String releaseFlag;

	@Column(name = "sentence_language1")
	private String sentenceLanguage1;

	@Column(name = "sentence_language2")
	private String sentenceLanguage2;

	@Column(name = "content_creater")
	private String contentCreater;

	@OneToMany(mappedBy = "dictionary")
	private Set<Sentence> listSentence = new HashSet<>();
}
