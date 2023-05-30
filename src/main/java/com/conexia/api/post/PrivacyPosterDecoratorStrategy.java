package com.conexia.api.post;

import org.springframework.stereotype.Service;

@Service
public class PrivacyPosterDecoratorStrategy implements Poster {
    private final DefaultPosterStrategy poster;

    public PrivacyPosterDecoratorStrategy(DefaultPosterStrategy poster) {
        this.poster = poster;
    }

    private Post setPrivacy(Post post) {
        post.setPrivacy(true);
        return post;
    }

    @Override
    public Post make(PostRequestDto dto) {
        var post = poster.make(dto);
        return setPrivacy(post);
    }

    @Override
    public void save(Post post) {
        poster.save(post);
    }
}
