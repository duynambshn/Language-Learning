package jp.helpnserve.LTS.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "system")
@Data
@NoArgsConstructor
public class System_tbl {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "empty_content")
	private String emptyContent;

	@Column(name = "full_content")
	private String fullContent;

	@Column(name = "user_id")
	private String userId;

}
