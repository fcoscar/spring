package com.example.webapp.service;

import com.example.webapp.dao.EmpleadoDao;
import com.example.webapp.models.Empleado;
import com.example.webapp.models.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class EmpleadoDetailsService implements UserDetailsService {

    private EmpleadoDao eDao;

    public EmpleadoDetailsService(EmpleadoDao eDao){
        this.eDao = eDao;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Empleado empleado = eDao.findByUsuario(username);

        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

        for(Roles rol : empleado.getRol()){
            roles.add(new SimpleGrantedAuthority(rol.getRol()));
        }

            return new User(empleado.getUsuario(), empleado.getClave(),roles);
    }
}
