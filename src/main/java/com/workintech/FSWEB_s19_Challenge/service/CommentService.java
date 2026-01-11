package com.workintech.FSWEB_s19_Challenge.service;

import com.workintech.FSWEB_s19_Challenge.entity.Comment;
import com.workintech.FSWEB_s19_Challenge.dto.CommentRequest;

public interface CommentService {
//http://localhost:3000/comment/[POST] => Bir tweete bir kullanıcı tarafından yorum yazılmasını sağlar.
//http://localhost:3000/comment/:id[PUT] => Bir tweete bir kullanıcı tarafından yapılan yorumun update edilmesine olanak sağlar.
//http://localhost:3000/comment/:id[DELETE] => Bir tweete bir kullanıcı tarafından yapılan yorumun silinmesini sağlar
//(Sadece tweet sahibi veya yorum sahibi ilgili yorumu silebilmelidir).

    Comment create(Long tweetId,CommentRequest commentRequest);

    Comment update(Long commentId,CommentRequest commentRequest);

    void delete(Long commentId);
}
