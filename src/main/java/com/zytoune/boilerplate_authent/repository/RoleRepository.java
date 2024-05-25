package com.zytoune.boilerplate_authent.repository;

import com.zytoune.boilerplate_authent.entity.Role;
import com.zytoune.boilerplate_authent.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByLibelle(RoleEnum roleEnum);
}
