package jp.helpnserve.LTS.Repository;

import org.springframework.data.repository.CrudRepository;

import jp.helpnserve.LTS.Model.System_tbl;

public interface SystemRepository extends CrudRepository<System_tbl, Integer> {

}
