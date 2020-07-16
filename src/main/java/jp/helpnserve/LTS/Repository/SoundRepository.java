package jp.helpnserve.LTS.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jp.helpnserve.LTS.Model.Sound;

public interface SoundRepository extends CrudRepository<Sound, Integer> {

	@Query(value = "Select case when max(id) is null then 1 else max(id) + 1 end from sound", nativeQuery = true)
	public int getMaxId();

}
