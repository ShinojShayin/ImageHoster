package ImageHoster.service;

import ImageHoster.model.User;
import ImageHoster.repository.UserRepository;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Call the registerUser() method in the UserRepository class to persist the user record in the database
    public void registerUser(User newUser) {
        userRepository.registerUser(newUser);
    }

    //This method receives the User type object
    //Calls the checkUser() method in the Repository passing the username and password which checks the username and password in the database
    //The Repository returns User type object if user with entered username and password exists in the database
    //Else returns null
    public User login(User user) {
        User existingUser = userRepository.checkUser(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            return existingUser;
        } else {
            return null;
        }
    }
    
    //This method receive password string
    //And verifies the password contain at least 1 alphabet (a-z or A-Z), 1 number (0-9) and 1 special character (any character other than a-z, A-Z and 0-9)
    //If the pattern specified matches it will return true else it will return false to indicate invalid password.
    public boolean validatePassword(String password) {
    	 Pattern pattern= Pattern.compile("(?=.*[a-z])(?=.*[0-9])(?=.*[^a-z0-9])", Pattern.CASE_INSENSITIVE);
         boolean valid = pattern.matcher(password).find();
         return valid;
    }

}