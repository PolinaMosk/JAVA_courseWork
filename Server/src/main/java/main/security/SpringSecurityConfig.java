package main.security;

import main.security.jwt.JwtSecurityConfigurer;
import main.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/wholesaledb/auth/signin").permitAll()
                .antMatchers(HttpMethod.GET, "/goods/goods").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/sales/sales").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/goods/goods").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/sales/sales").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/warehouse1/addWare").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/warehouse2/addWare").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/sales/addSale").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/warehouse2/removeByBatchId/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/warehouse1/removeByGoodId/{id}").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }

}
