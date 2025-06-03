package com.app.workshop_registration_system.Services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.workshop_registration_system.Models.RoleEnum;
import com.app.workshop_registration_system.Models.RoleModel;
import com.app.workshop_registration_system.Models.UserModel;
import com.app.workshop_registration_system.Models.DTO.LoginRequestDTO;
import com.app.workshop_registration_system.Models.DTO.LoginResponseDTO;
import com.app.workshop_registration_system.Models.DTO.RegisterResponseDTO;
import com.app.workshop_registration_system.Models.DTO.RegisterUserRequestDTO;
import com.app.workshop_registration_system.Repositories.RoleRepository;
import com.app.workshop_registration_system.Repositories.UserRepository;
import com.app.workshop_registration_system.Utils.JwtUtils;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailServiceImpl emailServiceImpl;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
            PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository,
            EmailServiceImpl emailServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailServiceImpl = emailServiceImpl;
    }

    @Override
    @Transactional
    public RegisterResponseDTO registerUser(RegisterUserRequestDTO registerUserRequestDTO){

        String emailRequest = registerUserRequestDTO.email();
        String passwordRequest = registerUserRequestDTO.password();

        String encryptedPassword = passwordEncoder.encode(passwordRequest);

        Optional<RoleModel> participantRole = roleRepository.findByRoleEnum(RoleEnum.PARTICIPANT);

        participantRole.orElseThrow(() -> new IllegalArgumentException("Rol especificado no existe"));

        UserModel userModel = UserModel.builder()
                .email(emailRequest)
                .password(encryptedPassword)
                .name(registerUserRequestDTO.name())
                .lastname(registerUserRequestDTO.lastname())
                .phoneNumber(registerUserRequestDTO.phoneNumber())
                .roleModel(participantRole.get())
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .accountNoLocked(true)
                .isEnable(true)
                .build();

        UserModel userCreated = userRepository.save(userModel);

        Map<String, String> datos = new HashMap<>();
        datos.put("name",userCreated.getName()+" "+userCreated.getLastname());
        emailServiceImpl.sendEmail(userCreated.getEmail(), "Registro workshop system", datos);
       
        // En caso de querer crear un token al registrarse
        // SimpleGrantedAuthority authority=new
        // SimpleGrantedAuthority(userCreated.getRoleModel().getRoleEnum().name());
        // List<SimpleGrantedAuthority>authorities=new ArrayList<>(List.of(authority));
        // Authentication authentication= new
        // UsernamePasswordAuthenticationToken(userCreated.getEmail(),null,
        // authorities);

        return maResponseDTO(userCreated);

    }

    @Transactional
    @Override
    public RegisterResponseDTO createAdmin(RegisterUserRequestDTO registerAdminDTO) {

        String emailRequest = registerAdminDTO.email();
        String passwordRequest = registerAdminDTO.password();

        String encryptedPassword = passwordEncoder.encode(passwordRequest);

        Optional<RoleModel> participantRole = roleRepository.findByRoleEnum(RoleEnum.ADMIN);

        participantRole.orElseThrow(() -> new IllegalArgumentException("Rol especificado no existe"));

        UserModel userModel = UserModel.builder()
                .email(emailRequest)
                .password(encryptedPassword)
                .name(registerAdminDTO.name())
                .lastname(registerAdminDTO.lastname())
                .phoneNumber(registerAdminDTO.lastname())
                .roleModel(participantRole.get())
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .accountNoLocked(true)
                .isEnable(true)
                .build();

        UserModel adminCreated = userRepository.save(userModel);

        return maResponseDTO(adminCreated);
    }

    @Transactional
    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
        String email=loginRequestDTO.email();
        String password=loginRequestDTO.password();

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(email, password);

        Authentication userAuthenticated=authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(userAuthenticated);

        String token=jwtUtils.createToken(userAuthenticated);

        return new LoginResponseDTO(email, "Hola "+email, token); 
    }

    private RegisterResponseDTO maResponseDTO(UserModel userModel) {

        RegisterResponseDTO dto = new RegisterResponseDTO(
            "Usuario Registrado", 
            userModel.getId(), 
            userModel.getName(),
            userModel.getLastname(),
            userModel.getEmail(), 
            userModel.getPhoneNumber(), 
            userModel.getCreatedAt());

        return dto;
    }
}
