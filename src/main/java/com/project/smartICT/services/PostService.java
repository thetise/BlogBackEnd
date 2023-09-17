package com.project.smartICT.services;

import com.project.smartICT.entities.Post;
import com.project.smartICT.entities.User;
import com.project.smartICT.repos.IPostRepository;
import com.project.smartICT.requests.PostCreateRequest;
import com.project.smartICT.requests.PostUpdateRequest;
import com.project.smartICT.responses.AuthResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private IPostRepository iPostRepository;
    private UserService userService;

    public PostService(IPostRepository iPostRepository, UserService userService) {
        this.iPostRepository = iPostRepository;
        this.userService = userService;
    }

    public List<Post> getAllPosts() {
        return iPostRepository.findAll();
    }

    public Post createOnePost(PostCreateRequest newPost) {
        User user = userService.getOneUser(newPost.getUserId());
        if(user == null){
            return null;
        }else{
            Post toSave = new Post();
            toSave.setId(newPost.getId());
            toSave.setTitle(newPost.getTitle());
            toSave.setContent(newPost.getContent());
            toSave.setUserId(user);
            return iPostRepository.save(toSave);
        }
    }

    public Post getOnePost(Long postId) {
        return iPostRepository.findById(postId).orElse(null);

    }

    public Post updateOnePost(Long postId, PostUpdateRequest updatePost) {

        /*
        Aşağıdaki kod parçası, SecurityContextHolder kullanarak oturum açmış kullanıcının adını alır ve kullanıcının
        sadece kendi postunu güncellemesine izin verir. Kullanıcı başka bir kullanıcının postunu
        güncellemeye çalışırsa, bir AccessDeniedException fırlatılır.
        Bu şekilde Spring Security kullanarak kullanıcıların sadece kendi postlarını güncelleyebilmesini sağladık.
         */

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<Post> post = iPostRepository.findById(postId);

        if(post.isPresent()){
            Post foundPost = post.get();

            // Kullanıcı kendi postunu güncelliyorsa devam et
            if(foundPost.getUserId().getUserName().equals(username)){
                if(updatePost.getTitle() != null){
                    foundPost.setTitle(updatePost.getTitle());
                }else{
                    foundPost.setTitle(post.get().getTitle());
                }

                if(updatePost.getContent() != null){
                    foundPost.setContent(updatePost.getContent());
                }else{
                    foundPost.setContent(post.get().getContent());
                }

                iPostRepository.save(foundPost);
                return foundPost;
            }else{
                // Kullanıcı başka bir kullanıcının postunu güncellemeye yetkili değil.
                throw new AccessDeniedException("Bu postu güncelleme yetkiniz yok.");
            }
        }
        return null;
    }

    public AuthResponse deleteOnePost(Long postId) {

        /*
        Aşağıdaki kod parçası, SecurityContextHolder kullanarak oturum açmış kullanıcının adını alır ve kullanıcının
        sadece kendi postunu SİLMESİNE izin verir. Kullanıcı başka bir kullanıcının postunu
        SİLMEYE çalışırsa, bir AccessDeniedException fırlatılır.
        Bu şekilde Spring Security kullanarak kullanıcıların sadece kendi postlarını SİLMESİNİ sağladık.
         */

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<Post> post = iPostRepository.findById(postId);

        if(post.isPresent()){
            Post foundDeletePost = post.get();
            if(foundDeletePost.getUserId().getUserName().equals(username)){
                AuthResponse authResponse = new AuthResponse();
                authResponse.setMessage("Silmek istediğin post silindi.");
                authResponse.setUserId(post.get().getUserId().getId());
                iPostRepository.deleteById(postId);
                return authResponse;
            }else{
                AuthResponse authResponse = new AuthResponse();
                authResponse.setMessage("Silmeye çalıştığın post senin değil!!!");
                // alt satırda hata yok sadece "user" tanımlayacagıma "userId" tanımlamısım taaa post entitysinde.
                authResponse.setUserId(post.get().getUserId().getId());
                return authResponse;
            }
        }
        return null;
    }
}