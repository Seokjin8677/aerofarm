function deletePost(postId) {

    let data = {
        // id: $('#btn-deleteComment').val()
        id: postId
    };
    $.ajax({
        type: 'POST',
        url: '/deletePost',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
    }).done(function () {
        alert('게시글이 삭제 처리 되었습니다.');
        window.location.href ='/community/' + $('#post-category').val() + '/' + $('#post-id').val();
        // $('#comment-area').load(window.location.href + ' #comment-area');
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}