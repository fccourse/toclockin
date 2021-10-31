package digital.ilia.oauthapi.services;

import digital.ilia.oauthapi.entities.User;
import digital.ilia.oauthapi.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userFeignClient.findByEmail(email).getBody();

        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }
        return user;
    }

}
