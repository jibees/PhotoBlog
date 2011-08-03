package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import play.db.jpa.Blob;
import play.db.jpa.Model;
import play.libs.MimeTypes;

@Entity
public class Image extends Model {
	@ManyToOne
	public Post post;
	public String contentType;
	public String url;

	public Image(File file) {
		try {
			this.contentType = MimeTypes.getContentType(file.getName());
			
		    // Destination directory
		    File dir = new File("/public/images/user/");
			//if not, create
		    if (!dir.exists()) {
		        dir.mkdirs(); //create new dir if not present
		    }

		    // Move file to new directory
		    File newfile = new File(dir, file.getName());
		    OutputStream os = new FileOutputStream(newfile);
		    IOUtils.copy(new FileInputStream(file), os);

		    this.url = newfile.getAbsolutePath();
		    
			this.save();
		} catch (Exception e) {
			throw new RuntimeException("Could not save Image.", e);
		}
	}
	
}
