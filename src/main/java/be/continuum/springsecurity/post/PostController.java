package be.continuum.springsecurity.post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
@RequestMapping("/")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView get() {
        Collection<Post> posts = service.getPosts();

        ModelAndView result = new ModelAndView("component/posts");
        result.addObject("posts", posts);

        return result;
    }

    @GetMapping("/deleted")
    public ModelAndView getDeleted() {
        return new ModelAndView("component/posts", "posts", service.getDeletedPosts());
    }

    @GetMapping("/post/{externalId}")
    public ModelAndView getPost(@PathVariable String externalId) {

        ModelAndView result = new ModelAndView("component/post");
        result.addObject("post", service.getPost(externalId));

        return result;
    }

    @GetMapping("/post/create")
    public ModelAndView createPost() {
        return new ModelAndView("component/newPost", "post", new Post());
    }

    @PostMapping("/post/create")
    public ModelAndView createPost(Post post) {
        Post result = service.create(post);
        return new ModelAndView("redirect:/post/" + result.getExternalId());
    }

    @PostMapping("/post/delete")
    public String delete(String externalId) {
        service.delete(externalId);
        return "redirect:/";
    }

    @PostMapping("/post/{externalId}/edit")
    public ModelAndView edit(@PathVariable String externalId) {
        return new ModelAndView("component/edit", "post", service.getPost(externalId));
    }

    @PostMapping("/post/{externalId}/update")
    public ModelAndView update(@PathVariable String externalId, Post post) {
        service.update(externalId, post);
        return new ModelAndView("redirect:/post/" + externalId);
    }
}
