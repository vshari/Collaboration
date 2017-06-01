package com.niit.collaboration.backend.controller;

import java.util.List;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.backend.dao.BlogDAO;
import com.niit.collaboration.backend.model.BaseDomain;
import com.niit.collaboration.backend.model.Blog;
import com.niit.collaboration.backend.model.BlogComment;
import com.niit.collaboration.backend.model.User;

@RestController
public class BlogController extends BaseDomain {
	@Autowired
	private Blog blog;
	@Autowired
	private BlogDAO blogDao;
	@Autowired
	 HttpSession session;
	

	// create a new Blog
	@RequestMapping(value = "/blog/createBlog", method = RequestMethod.POST)
	public ResponseEntity<?> createBlog(@RequestBody Blog blog, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			user.setErrorMessage("Unauthorized user...login using correct credentials");
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}
		Blog addedBlog = blogDao.createBlog(user, blog);
		return new ResponseEntity<Blog>(addedBlog, HttpStatus.OK);
	}

	
	
	// get all blogs
		@RequestMapping(value = "/blog/getAllBlogs", method = RequestMethod.GET)
		public ResponseEntity<?> getAllBlogs(HttpSession session) {
			User user = (User) session.getAttribute("user");
			if (user == null) {
				user.setErrorMessage("Unauthorized user...login using correct credentials");
				return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
			}
			List<Blog> blogs = blogDao.getAllBlogs();
			return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
		}
		
		@RequestMapping(value = "/blog/getBlogDetails/{id}", method = RequestMethod.GET)
		public ResponseEntity<?> getBlogDetails(@PathVariable(value = "id") int id, HttpSession session) {
			User user = (User) session.getAttribute("user");
			if (user == null) {
				user.setErrorMessage("Unauthorized user...login using correct credentials");
				return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
			}
			Blog blog = blogDao.getBlogById(id);
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		//get Blog Comments of a blog
		@RequestMapping(value = "/blog/getBlogComments/{id}", method = RequestMethod.GET)
		public ResponseEntity<?> getBlogComments(@PathVariable(value = "id") int id, HttpSession session) {
			User user = (User) session.getAttribute("user");
			if (user == null) {
				user.setErrorMessage("Unauthorized user...login using correct credentials");
				return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
			}
			List<BlogComment> blogComments = blogDao.getBlogComments(id);
			System.out.println("BLOGCOMMENTS::: " + blogComments.size());
			return new ResponseEntity<List<BlogComment>>(blogComments, HttpStatus.OK);
		}
		
		
		// add a blog comment
		@RequestMapping(value = "/blog/addBlogComment", method = RequestMethod.POST)
		public ResponseEntity<?> addBlogComment(@RequestBody BlogComment blogComment, HttpSession session) {
			User user = (User) session.getAttribute("user");
			if (user == null) {
				user.setErrorMessage("Unauthorized user...login using correct credentials");
				return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
			}
			System.out.println("BLOG COMMENT is " + blogComment);
			System.out.println("BLOG COMMENT BODY " + blogComment.getBody());

			System.out.println("BLOG POST FROM BLOGCOMMENT " + blogComment.getBlog());
			Blog blogPost = blogDao.getBlogById(blogComment.getBlog().getId());
			if (blogPost == null) {
				user.setErrorMessage("Blog Post is not Found");
				return new ResponseEntity<Blog>( HttpStatus.NOT_FOUND);
			}
			Blog createdBlogPost = blogDao.addBlogPostComment(user, blogComment);
			return new ResponseEntity<Blog>(createdBlogPost, HttpStatus.OK);
		}
}



