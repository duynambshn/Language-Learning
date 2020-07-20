package jp.helpnserve.LTS.Model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Immutable
@Table(name = "v_sen_user")
@Data
@NoArgsConstructor
public class ViewSentenceInfo {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "origin_sentence")
	private String originSentence;

	@Column(name = "translate_sentence")
	private String translateSentence;

	@Column(name = "explanation")
	private String explanation;

	@Column(name = "sound_url")
	private String soundUrl;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "status")
	private Integer status;

	@Column(name = "last_time")
	private Timestamp lastTime;
}
