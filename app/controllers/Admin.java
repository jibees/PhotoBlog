package controllers;

import java.io.File;
import java.util.List;

import models.Image;
import models.Post;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Admin extends Controller {

	@Before
	static void setConnectedUser() {
		if (Security.isConnected()) {
			User user = User.find("byEmail", Security.connected()).first();
			renderArgs.put("user", user.fullname);
		}
	}

	public static void index() {
		List<Post> posts = Post.find("author.email", Security.connected())
				.fetch();
		render(posts);
	}

	public static void form(Long id) {
		if (id != null) {
			Post post = Post.findById(id);
			render(post);
		}
		render();
	}

	public static void ajaxUploadImage(File qqfile) {
		Image image = new Image(qqfile);
		renderJSON(image);
	}

	public static void save(Long id, String title, String content, String tags) {
		Post post;
		if (id == null) {
			// Create post
			User author = User.find("byEmail", Security.connected()).first();
			post = new Post(author, title, content);
		} else {
			// Retrieve post
			post = Post.findById(id);
			// Edit
			post.title = title;
			post.content = content;
		}
		// Validate
		validation.valid(post);
		if (validation.hasErrors()) {
			render("@form", post);
		}
		// Save
		post.save();
		index();
	}

}
