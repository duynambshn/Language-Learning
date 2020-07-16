package jp.helpnserve.LTS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.helpnserve.LTS.Model.Authorized;

@Repository
public interface AuthorizedRepository extends JpaRepository<Authorized, Long> {

	Authorized findByName(String name);
}
