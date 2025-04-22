package com.spring.java_springboot_crud_mysql.service.impl;

import lombok.AllArgsConstructor;
import com.spring.java_springboot_crud_mysql.entity.Post;
import com.spring.java_springboot_crud_mysql.repository.PostRepository;
import com.spring.java_springboot_crud_mysql.service.PostService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private String detectLang(String c_){
        String check = "";
        int langKorean = 0;
        int langEnglish = 0;
        int nolang = 0;
        for (char c : c_.toString().toCharArray()) {
            if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_JAMO
                    || Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO
                    || Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_SYLLABLES) {
                langKorean += 1;
            }else if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.BASIC_LATIN) {
                langEnglish += 1;
            }else if (!Character.isWhitespace(c)) {
                nolang += 1;
            }
        }
        if(nolang>0){
            check = "Language must be Korean and English";
        }
        if(langKorean>50){
            if(check!=""){check += ", ";}
            check += "Language max 50 Korean character";
        }
        if(langEnglish>100){
            if(check!=""){check += ", ";}
            check += "Language max 100 English character";
        }
        return check;
    }

    private String detectLang2(String c_){
        String check = "";
        int langKorean = 0;
        int langEnglish = 0;
        int nolang = 0;
        for (char c : c_.toString().toCharArray()) {
            if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_JAMO
                    || Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO
                    || Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_SYLLABLES) {
                langKorean += 1;
            }else if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.BASIC_LATIN) {
                langEnglish += 1;
            }else if (!Character.isWhitespace(c)) {
                nolang += 1;
            }
        }
        if(nolang>0){
            check = "Language must be Korean and English";
        }
        if(langKorean>10){
            if(check!=""){check += ", ";}
            check += "Language max 10 Korean character";
        }
        return check;
    }

    @Override
    public String create() {
        String html = "<html><body style='padding: 5px;'>";
        html += "<div style='font-size: 25px;font-family: monospace;padding: 5px;'>Title : <input type='text' id='title' name='title' value='' placeholder='Input title here'/></a></div>";
        html += "<div style='font-size: 25px;font-family: monospace;padding: 5px;'>Author Name : <input type='text' id='authorname' name='authorname' value='' placeholder='Input Author Name here'/></a></div>";
        html += "<div style='font-size: 25px;font-family: monospace;padding: 5px;'>Save : <button onclick='save()'>Save</button></div>";
        html += "<script>function save() {const xhr = new XMLHttpRequest();xhr.open('POST', '/api/posts');xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');";
        html += "const body = JSON.stringify({title: document.getElementById('title').value,authorname: document.getElementById('authorname').value,viewers:0,datecreated:Date.now()});";
        html += "xhr.onload = () => {if (xhr.readyState == 4 && xhr.status == 201) {c=JSON.parse(xhr.responseText);console.log(c);if((c.id==0)&&(c.datecreated=='error_')){c_='Title: '+c.title;if(c_!=''){c_+=', '};c_+='Author Name: '+c.authorname;alert(c_);}else{window.location.href='/api/posts';}} else {alert('error');}};";
        html += "xhr.send(body);}";
        html += "</script>";
        html += "</body></html>";
        return html;
    }

    @Override
    public String modify(Long postId) {
        Post existingPost = postRepository.findById(postId).get();
        String html = "<html><body style='padding: 5px;'>";
        html += "<div style='font-size: 25px;font-family: monospace;padding: 5px;'>Title : <input type='text' id='title' name='title' value='" + existingPost.getTitle() + "' placeholder='Input title here'/></a></div>";
        html += "<div style='font-size: 25px;font-family: monospace;padding: 5px;'>Author Name : <input type='text' id='authorname' name='authorname' value='" + existingPost.getAuthorname() + "' placeholder='Input Author Name here'/></a></div>";
        html += "<div style='font-size: 25px;font-family: monospace;padding: 5px;'>Save : <button onclick='update()'>Update</button></div>";
        html += "<script>function update() {const xhr = new XMLHttpRequest();xhr.open('PUT', '/api/posts/"+postId+"');xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');";
        html += "const body = JSON.stringify({title: document.getElementById('title').value,authorname: document.getElementById('authorname').value,viewers:0,datecreated:Date.now()});";
        html += "xhr.onload = () => {if (xhr.readyState == 4 && xhr.status == 200) {c=JSON.parse(xhr.responseText);console.log(c);if((c.id==0)&&(c.datecreated=='error_')){c_='Title: '+c.title;if(c_!=''){c_+=', '};c_+='Author Name: '+c.authorname;alert(c_);}else{window.location.href='/api/posts';}} else {alert('error');}};";
        html += "xhr.send(body);}";
        html += "</script>";
        html += "</body></html>";
        return html;
    }

    @Override
    public Post createPost(Post post) {
        String check = detectLang(post.getTitle());
        String check2 = detectLang2(post.getAuthorname());
        if ((check!="")||(check2!="")){
            post.setId(Long.valueOf(0));
            post.setDatecreated("error_");
            post.setTitle(check);
            post.setAuthorname(check2);
            return post;
        }else{
            return postRepository.save(post);
        }
    }

    @Override
    public String getPostById(Long postId) {
        Post existingPost = postRepository.findById(postId).get();
        existingPost.setViewers(existingPost.getViewers()+1);
        Post updatedPost = postRepository.save(existingPost);
        //Optional<Post> optionalPost = postRepository.findById(postId);
        //return optionalPost.get();
        //return updatedPost;
        String html = "<html><body style='padding: 5px;'>";
        html += "<div style='font-size: 25px;font-family: monospace;padding: 5px;'>Title : " + updatedPost.getTitle() + "</a></div>";
        html += "<div style='font-size: 25px;font-family: monospace;padding: 5px;'>Author Name : " + updatedPost.getAuthorname() + "</a></div>";
        html += "<div style='font-size: 25px;font-family: monospace;padding: 5px;'>Viewer : " + updatedPost.getViewers() + "</a></div>";
        //html += "<div style='font-size: 25px;font-family: monospace;padding: 5px;'>Created : " + updatedPost.getDatecreated() + "</a></div>";
        html += "</body></html>";
        return html;
    }

    @Override
    public String getAllPosts() {
        List<Post> existingPost = postRepository.findAll();
        String html = "<html><body style='padding: 5px;'>";
        html += "<div style='font-size: 25px;font-family: monospace;padding: 5px;'>List Post :</div>";
        html += "<div style='font-size: 25px;font-family: monospace;padding: 5px;'>Create : <a style='padding: 2px 10px 2px 10px;border: black solid 2px;text-decoration: none;border-radius: 10px;color: black;' href='/api/posts/create'>Create</a></div>";
        int i = 0;
        for (Post c : existingPost) {
            i++;
            html += "<div style='font-size: 25px;font-family: monospace;padding: 5px;'><span style='display: block;padding-bottom: 5px;'>Post "+i+" : </span><a style='padding: 2px 10px 2px 10px;border: black solid 2px;text-decoration: none;border-radius: 10px;color: black;' href='/api/posts/" + c.getId() + "' target='_blank'>" + c.getTitle() + "</a> <a style='padding: 2px 10px 2px 10px;border: black solid 2px;text-decoration: none;border-radius: 10px;color: black;' href='/api/posts/modify/" + c.getId() + "'>Modify</a> <a style='padding: 2px 10px 2px 10px;border: black solid 2px;text-decoration: none;border-radius: 10px;color: black;' onclick='delete_post(" + c.getId() + "," + '"' + c.getTitle() + '"' + ");'>Delete</a></div>";
        }
        html += "<script>function delete_post(id,title) {c=confirm('Confirm Delete Post '+title+' ?');if(c==true){const xhr = new XMLHttpRequest();xhr.open('DELETE', '/api/posts/'+id);xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');";
        html += "const body = JSON.stringify({});";
        html += "xhr.onload = () => {if (xhr.readyState == 4 && xhr.status == 200) {window.location.href='/api/posts';} else {alert('error');}};";
        html += "xhr.send(body);}}";
        html += "</script>";
        html += "</body></html>";
        return html;
    }

    @Override
    public Post updatePost(Post post) {
        Post existingPost = postRepository.findById(post.getId()).get();
        String check = detectLang(post.getTitle());
        String check2 = detectLang2(post.getAuthorname());
        if ((check!="")||(check2!="")){
            existingPost.setId(Long.valueOf(0));
            existingPost.setDatecreated("error_");
            existingPost.setTitle(check);
            existingPost.setAuthorname(check2);
            return existingPost;
        }else{
            existingPost.setTitle(post.getTitle());
            existingPost.setAuthorname(post.getAuthorname());
            Post updatedPost = postRepository.save(existingPost);
            return updatedPost;
        }
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
