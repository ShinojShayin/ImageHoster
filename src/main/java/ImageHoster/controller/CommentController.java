package ImageHoster.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ImageHoster.model.Comment;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;

@Controller
public class CommentController {
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
    private CommentService commentService;
	
	//This controller method is called when the User type comment and submit below image. Incoming request is of type post.
    //This code will save the comments against image along with the user details of the person who uploaded the image
    //After you get the comment, set the user of the comment by getting the logged in user from the Http Session
    //Call the getImage() method in the business logic to fetch all the details of that image
    //Add user comment in 'commentObj' object, along with current LocalDate
    //Redirect the request to /images/{imageId}/{title}

    //this request is sent image screen where newly submitted comment is displayed below
    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String createComment(@PathVariable("imageId") Integer imageId, 
    		@PathVariable("imageTitle") String title , @RequestParam("comment") String comment, 
    		 HttpSession session, RedirectAttributes redirect) throws IOException {
    	
        User user = (User) session.getAttribute("loggeduser");
        Comment commentObj = new Comment();
        commentObj.setImage(imageService.getImage(imageId));
        commentObj.setUser(user);
        commentObj.setText(comment);
        commentObj.setCreatedDate(LocalDate.now());
        commentService.addComment(commentObj);

        return "redirect:/images/"+imageId+"/"+title;
    }
}