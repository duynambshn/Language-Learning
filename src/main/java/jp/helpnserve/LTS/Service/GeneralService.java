package jp.helpnserve.LTS.Service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GeneralService {

	/**
	 * 
	 * @return
	 */
	public boolean HasLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return true;
		}

		return false;
	}
}