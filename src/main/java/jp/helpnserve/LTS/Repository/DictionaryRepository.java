package jp.helpnserve.LTS.Repository;

import org.springframework.data.repository.CrudRepository;

import jp.helpnserve.LTS.Model.Dictionary;

public interface DictionaryRepository extends CrudRepository<Dictionary, Integer> {

}
