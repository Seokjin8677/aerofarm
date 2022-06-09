package yj.capstone.aerofarm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yj.capstone.aerofarm.domain.board.Post;
import yj.capstone.aerofarm.dto.CommentDto;
import yj.capstone.aerofarm.dto.PostDto;
import yj.capstone.aerofarm.domain.board.PostCategory;

public interface PostRepositoryCustom {
    Page<PostDto> findPostInfo(PostCategory category, String searchCategory, String keyword, Pageable pageable);
}
