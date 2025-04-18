package projekti.demo;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final CustomCorsConfiguration customCorsConfiguration;
	
	//@Autowired
	private UserDetailsService userDetailsService; // type of attribute -> interface
	
	// Constructor injection
	public WebSecurityConfig(UserDetailsService userDetailsService, CustomCorsConfiguration customCorsConfiguration) {
		this.userDetailsService = userDetailsService;
		this.customCorsConfiguration = customCorsConfiguration; 
	}

	private static final AntPathRequestMatcher[] WHITE_LIST_URLS = {
		new AntPathRequestMatcher("/h2-console/**")

	};

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(
				authorize -> authorize
				.requestMatchers(antMatcher("/css/**")).permitAll()
				.requestMatchers(WHITE_LIST_URLS).permitAll()
				.anyRequest().authenticated())
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions .disable())) // for h2console
        .httpBasic(Customizer.withDefaults())
				.formLogin(formlogin -> formlogin
					.defaultSuccessUrl("/", true)
					.permitAll())
				.logout(logout -> logout.permitAll())
				.cors(c -> c.configurationSource(customCorsConfiguration))
				.csrf(csrf -> csrf.disable()); // not for production, just for development

		return http.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
