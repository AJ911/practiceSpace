package com.flynava.jupiter.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.flynava.jupiter.daoInterface.UserDao;

@Component
public class JupiterSecurityService implements UserDetailsService {

	@Autowired
	UserDao userRepositoryDao;

	private org.springframework.security.core.userdetails.User userDetails;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		com.flynava.jupiter.model.User user = userRepositoryDao.getUser("username", username);

		System.out.println(username);
		System.out.println(user.getPassword());
		System.out.println(user.getUsername());
		System.out.println(user.getRole());

		userDetails = new User(user.getUsername(), user.getPassword(), enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, getAuthorities(user.getRole()));
		return userDetails;
	}

	public List<GrantedAuthority> getAuthorities(String role) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		if ("Network".equalsIgnoreCase(role)) {
			authList.add(new GrantedAuthorityImpl("ROLE_Network"));

		} else if ("Network".equalsIgnoreCase(role)) {
			authList.add(new GrantedAuthorityImpl("ROLE_Network"));
		}
		System.out.println(authList);
		return authList;
	}

}
