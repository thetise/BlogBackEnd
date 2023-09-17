package com.project.smartICT.services;

import com.project.smartICT.entities.Post;
import com.project.smartICT.repos.IPostRepository;
import com.project.smartICT.responses.AuthResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    private UserService userService;

    private IPostRepository iPostRepository;

    public AdminService(UserService userService, IPostRepository iPostRepository) {
        this.userService = userService;
        this.iPostRepository = iPostRepository;
    }

    public AuthResponse deleteOnePostAdminAuthority(Long postId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username  = authentication.getName();

        Optional<Post> post = iPostRepository.findById(postId);

        if(post.isPresent()){
            AuthResponse authResponse = new AuthResponse();
            if(authentication != null && authentication.isAuthenticated()){
                for (GrantedAuthority grantedAuthority: authentication.getAuthorities()){
                    String role = grantedAuthority.getAuthority();
                    if(role.equals("admin")){
                        iPostRepository.deleteById(postId);
                        System.out.println("Kullanıcı adı: " + username + ", Rol:  " + role);

                        authResponse.setMessage(username + " tarafından " +  post.get().getUserId().getUserName() + " adlı kullanıcının " + post.get().getId() + ". postu silinmiştir.");
                        authResponse.setUserId(post.get().getUserId().getId());
                        return authResponse;
                    }
                    System.out.println("KULLANICI adı: " + username + ", Rol:  " + role);
                }
                authResponse.setMessage("Admin rolünüz yok.");
                // alt satırda hata yok sadece "user" tanımlayacagıma "userId" tanımlamısım taaa post entitysinde.
                authResponse.setUserId(post.get().getUserId().getId());
                return authResponse;
            }
        }
        return null;
    }
}
