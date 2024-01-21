package com.remedy.iarlen.course.User;


import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public record GetUserDTO(String username, List<String> roles) {
}
