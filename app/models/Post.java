package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Post extends Model {
	@Required
	public String title;
	
	@Required
	public Date postedAt;
	
	@Lob
	@Required
    @MaxSize(10000)
	public String content;
	
	@ManyToOne
	@Required
	public User author;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	public List<Image> images;

	public Post(User author, String title, String content) {
		this.images = new ArrayList<Image>();
		this.author = author;
		this.title = title;
		this.content = content;
		this.postedAt = new Date();
	}

	public Post addImage(Image image) {
		this.images.add(image);
		this.save();
		return this;
	}

	public Post previous() {
		return Post.find("postedAt < ? order by postedAt desc", postedAt)
				.first();
	}

	public Post next() {
		return Post.find("postedAt > ? order by postedAt asc", postedAt)
				.first();
	}
}
