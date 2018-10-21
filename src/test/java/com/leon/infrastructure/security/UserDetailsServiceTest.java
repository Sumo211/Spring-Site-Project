package com.leon.infrastructure.security;

import com.leon.user.UserRepository;
import com.leon.user.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserDetailsServiceTest {

    @Test
    public void loadUserByUsername_OK() {
        UserRepository userRepository = mock(UserRepository.class);
        UserDetailsService userDetailsService = new CustomUserDetailsService(userRepository);

        when(userRepository.findByEmailIgnoreCase(Users.USER_EMAIL)).thenReturn(Optional.of(Users.user()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(Users.USER_EMAIL);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(Users.USER_EMAIL);
        assertThat(userDetails.getAuthorities()).extracting(GrantedAuthority::getAuthority).contains("ROLE_USER");
        assertThat(userDetails).isInstanceOfSatisfying(AppUserDetails.class,
                appUserDetails -> assertThat(appUserDetails.getUserId()).isEqualTo(Users.user().getId()));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_NotFound() {
        UserRepository userRepository = mock(UserRepository.class);
        UserDetailsService userDetailsService = new CustomUserDetailsService(userRepository);

        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());

        userDetailsService.loadUserByUsername("unknown@mail.com");
    }

}
