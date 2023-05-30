package com.conexia.api.post;

public interface Poster {
    Post make(PostRequestDto dto);

    void save(Post post);
}
