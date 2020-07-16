package jp.helpnserve.LTS.Model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user_info")
@Data
@NoArgsConstructor
@FilterDef(name = "userId", parameters = @ParamDef(name = "userId", type = "integer"))
@Filters({ @Filter(name = "filterUserId", condition = "userId = :userId") })
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "user_id", nullable = false)
	private int userId;

//	@Column(name = "sentence_id", nullable = false)
//	private int sentenceId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sentence_id", nullable = false)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Sentence sentence;

	@Column(name = "status")
	private int status;

	@Column(name = "last_time")
	@UpdateTimestamp
	private Timestamp lastTime;
}