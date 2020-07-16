package jp.helpnserve.LTS.Model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

@Entity
@Table(name = "authorized")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authorized {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(nullable = false, unique = true, name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(nullable = false, name = "create_date")
	@CreationTimestamp
	private Date createDate;

	@Column(nullable = false, name = "update_date")
	@UpdateTimestamp
	private Date updateDate;

	@ManyToMany(mappedBy = "listAuthorized")
	@EqualsAndHashCode.Exclude
	@Exclude
	private Set<User> setUser = new HashSet<>();
}
