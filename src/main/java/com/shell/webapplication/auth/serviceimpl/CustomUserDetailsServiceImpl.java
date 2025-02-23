package com.shell.webapplication.auth.serviceimpl;

import com.shell.webapplication.auth.dto.CustomUserDetails;
import com.shell.webapplication.auth.entity.UserEntity;
import com.shell.webapplication.auth.repository.UserRepository;
import com.shell.webapplication.auth.service.CustomUserDetailsService;
import com.shell.webapplication.constent.AppConstant;
import com.shell.webapplication.utils.app.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        boolean isEmail = AppUtil.isEmail(username);
        if (isEmail) {
            UserEntity userEntity = userRepository.findByUserEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(AppConstant.INVALID_USER_NAME_OR_PASSWORD));
            return new CustomUserDetails(userEntity);
        } else {
            UserEntity userEntity = userRepository.findByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException(AppConstant.INVALID_USER_NAME_OR_PASSWORD));
            return new CustomUserDetails(userEntity);
        }
    }

}
