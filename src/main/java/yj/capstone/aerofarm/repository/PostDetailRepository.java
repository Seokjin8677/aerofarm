package yj.capstone.aerofarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yj.capstone.aerofarm.domain.board.PostDetail;

public interface PostDetailRepository extends JpaRepository<PostDetail, Long> {

}