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
                .antMatchers("/wholesalecomp/auth/signin").permitAll()
                .antMatchers(HttpMethod.GET, "/wholesalecomp/goods/goods").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/wholesalecomp/sales/sales").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/wholesalecomp/warehouse1/addWare").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/wholesalecomp/warehouse2/addWare").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/wholesalecomp/sales/addSale").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/wholesalecomp/warehouse2/removeByBatchId/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/wholesalecomp/warehouse2/removeByGoodId/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/wholesalecomp/warehouse1/removeByGoodId/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/wholesalecomp/warehouse1/removeByBatchId/{id}").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }

}
