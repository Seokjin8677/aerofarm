package yj.capstone.aerofarm.repository;

import yj.capstone.aerofarm.domain.board.PostLike;
import yj.capstone.aerofarm.dto.PostLikeDto;
import yj.capstone.aerofarm.dto.QPostLikeDto;
import yj.capstone.aerofarm.repository.support.Querydsl5RepositorySupport;

import java.util.List;

import static yj.capstone.aerofarm.domain.board.QPostLike.postLike;

public class PostLikeRepositoryImpl extends Querydsl5RepositorySupport implements PostLikeRepositoryCustom {

    public PostLikeRepositoryImpl() {
        super(PostLike.class);
    }

    @Override
    public Long findLikeCount(Long postId) {

        return select(postLike.count())
                .from(postLike)
                .groupBy(postLike.post)
                .having(postLike.post.id.eq(postId))
                .fetchOne();
    }
}
