package projekti.demo.web;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import projekti.demo.model.Kayttaja;
import projekti.demo.model.KayttajaRepository;

/**
 * This class is used by spring security to authenticate and authorize user
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService  {
	
	//@Autowired
	KayttajaRepository repository;
	
	// Constructor Injection
	public UserDetailServiceImpl(KayttajaRepository repository) {
		this.repository = repository; 
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{   
		Kayttaja curruser = repository.findByKayttajatunnusIgnoreCase(username);
			UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getSalasana_hash(), 
					AuthorityUtils.createAuthorityList(curruser.getKayttajatyyppi().getKayttajatyyppi()));
			return user;
	}

} 
