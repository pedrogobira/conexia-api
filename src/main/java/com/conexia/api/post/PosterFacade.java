package com.conexia.api.post;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PosterFacade {
    private final Poster poster;

    public PosterFacade(@Qualifier("defaultPosterStrategy") Poster poster) {
        this.poster = poster;
    }

    public void create(PostRequestDto dto) {
        if (dto.getPrivacy() != null) {
            var poster = new PrivacyPosterDecoratorStrategy((DefaultPosterStrategy) this.poster);
            var post = poster.make(dto);
            poster.save(post);
            return;
        }

        var post = poster.make(dto);
        poster.save(post);
    }
}
