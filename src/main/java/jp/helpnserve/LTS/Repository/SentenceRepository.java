package jp.helpnserve.LTS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.helpnserve.LTS.Model.Sentence;

public interface SentenceRepository extends JpaRepository<Sentence, Integer> {

}
