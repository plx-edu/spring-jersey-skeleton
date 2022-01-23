package app.component.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import app.component.User;
import app.component.service.UserService;
import app.custom.CustomMethods;


@Path("users")
@Produces(MediaType.APPLICATION_JSON +"; charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON +"; charset=UTF-8")
public class UserController {
	// REMINDER: update .getLogger(this.class)
		private static final Logger logger = LoggerFactory.getLogger(UserController.class);
		private CustomMethods cm = new CustomMethods();
		
		@Autowired
		private UserService userService;
		
		// ***** GET Methods *****
		
		@GET
		public List<User> getUsers(@QueryParam("handle") String handle,
				@QueryParam("name") String name) {
			logger.info(":: /users");
			
			List<User> l = new ArrayList<User>();
			if(handle != null /*|| name != null*/) {
				System.out.println("handle query "+handle);
				//System.out.println("name query "+name);
				l = userService.findByHandle(handle);
			}else {
				System.out.println("\n# FIND ALL UserController #\n");
				l = userService.findAll();
			}
			
			// Remove password & salt from results
			/*if(!l.isEmpty()) {
				for (int i = 0; i < l.size(); i++) {
					// cm.validHandle(l.get(i).getHandle());
					l.get(i).hideInfo();
				}
			}*/
			
			return l;
		}// getUsers()
		
		
		@GET
		@Path("{id}")
		public User getUser(@PathParam("id") String id) {
			logger.info(":: /users/"+id);
			
			if (cm.validID(id)) {
				Optional<User> u = userService.findById(id);
				System.out.println(":: Optional user "+u);
				if(u.isPresent()) {
//					System.out.println(":: ***** user "+u.get());
					u.get().hideInfo();
					return u.get();
				}
			}
			return new User();
		}// getUser(id)
		
		
		// ***** POST Methods *****
		@POST
		public User addUser(User user) {
			logger.info(":: /users ");
			// TO DO: make sure user not null
			// AND/OR make sure json is consumed
			
			
			if(cm.validHandle(user.getHandle()) && cm.validPassword(user.getPassword())) {
				// TO DO: do this in User class maybe(?)
				// add user.isValid() to check(?)
				user.setSalt(cm.generateSalt());
				user.setPassword(cm.hashPassword(user.getPassword(), user.getSalt()));
				user.checkName(); 
				
				try {
					User u = userService.save(user);
					u.hideInfo();
					return u;
//					return userService.save(user);
				} catch (Exception e) {}
			}
			return new User();
//			return null;
		}// addUser()

		// ***** PUT Methods *****
		// ***** DELETE Methods *****
		
}// - UserController
