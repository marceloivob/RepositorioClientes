package org.springframework.samples.petclinic.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
	protected void configure(HttpSecurity http)throws Exception {
    	
    	http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET,"/owners/{ownerId}").permitAll()
    	.anyRequest().authenticated().and().formLogin().permitAll().and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    	
    }
    @Bean
    public UserDetailsService userDetailsService() {

        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("123456").roles("USER").build());
        manager.createUser(users.username("admin").password("123456").roles("USER", "ADMIN").build());
        return manager;

    }

    @Override
    public void configure (WebSecurity web) throws Exception{
    	web.ignoring().antMatchers("templates/**");
   
    }
	
}

