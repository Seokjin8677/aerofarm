package yj.capstone.aerofarm.domain.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yj.capstone.aerofarm.domain.BaseEntity;
import yj.capstone.aerofarm.domain.member.Member;
import yj.capstone.aerofarm.form.CommentForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * File은 JoinTable 고려
 * 조회 수 증가 메소드 구현 시 modified 호출 되므로 추후 BaseEntity 상속 말고 직접 필드 따로 생성 후 비즈니스 로직 작성 고려
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    private int groupId;

    @OneToMany(mappedBy = "parent")
    private List<Comment> child = new ArrayList<>();

    // 삭제 여부
    private boolean deleteTnF;

    /**
     * 추후 필요 시 주석 해제
     */
//    @OneToMany(mappedBy = "comment")
//    private List<File> files = new ArrayList<>();

    @Builder(builderClassName = "CommentBuilder", builderMethodName = "commentBuilder")
    public Comment(CommentForm commentForm, Post selectPost, Member writer, int groupId) {
        this.post = selectPost;
        this.writer = writer;
        this.content = commentForm.getContent();
        this.groupId = groupId;
    }

    @Builder(builderClassName = "CommentAnswerBuilder", builderMethodName = "commentAnswerBuilder")
    public Comment(CommentForm commentForm, Post selectPost, Member writer, Comment parent, int groupId) {
        this.post = selectPost;
        this.writer = writer;
        this.content = commentForm.getContent();
        this.parent = parent;
        this.groupId = groupId;
    }

    public void updateDeleteTnF(boolean deleteTnF) {
        this.deleteTnF = deleteTnF;
    }
    public void updateContent(String content) { this.content = content; }
}
