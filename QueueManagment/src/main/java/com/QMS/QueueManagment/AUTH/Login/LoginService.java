package com.QMS.QueueManagment.AUTH.Login;


import com.QMS.QueueManagment.ADMIN.Entity.Admin;
import com.QMS.QueueManagment.AUTH.Jwt.JwtService;
import com.QMS.QueueManagment.exception.InvalidCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final JwtService jwtService;
    private final LoginRepo loginRepo;
    private final PasswordEncoder passwordEncoder;


    public LoginService(JwtService jwtService, LoginRepo loginRepo, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.loginRepo = loginRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public String loginAdmin(LoginDTO loginDTO){

        Admin admin=loginRepo.findByName(loginDTO.getName())
                .orElseThrow(()-> new InvalidCredentialsException("Invalid username or password"));

        if(!passwordEncoder.matches(loginDTO.getPassword(), admin.getPassword())){
            throw new InvalidCredentialsException("Invalid username or password");
        }

        return jwtService.generateToken(loginDTO.getName());
    }

}
