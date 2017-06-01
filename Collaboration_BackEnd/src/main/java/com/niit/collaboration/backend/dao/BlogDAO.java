package com.niit.collaboration.backend.dao;

import java.util.List;

import com.niit.collaboration.backend.model.Blog;
import com.niit.collaboration.backend.model.BlogComment;
import com.niit.collaboration.backend.model.User;

public interface BlogDAO {

	Blog createBlog(User user, Blog blog);

	List<Blog> getAllBlogs();

	Blog getBlogById(int id);

	List<BlogComment> getBlogComments(int id);

	Blog addBlogPostComment(User user, BlogComment blogComment);
}
