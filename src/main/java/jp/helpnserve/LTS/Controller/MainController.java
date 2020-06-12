package jp.helpnserve.LTS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jp.helpnserve.LTS.Model.User;
import jp.helpnserve.LTS.Repository.UserRepository;

@Controller // This means that this class is a Controller
//@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {
	@Autowired // This means to get the bean called userRepository
	// Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping(path = "/add/{name}/{password}/{email}") // Map ONLY POST Requests
	public @ResponseBody String addNewUser(@PathVariable String name, @PathVariable String password,
			@PathVariable String email) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		System.out.println("password" + password);
		User n = new User();
		n.setName(name);
		n.setEmail(email);
		n.setPassword(bCryptPasswordEncoder.encode(password));
		userRepository.save(n);
		return "Saved";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}

	@GetMapping(value = "/signup")
	public String signUpForm(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@PostMapping(path = "/signup") // Map ONLY POST Requests
	public ModelAndView signUpSubmit(@ModelAttribute User user, ModelAndView mav) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		System.out.println("User: " + user);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);

		mav.setViewName("index");
		mav.addObject("user", userRepository.findAll());

		return mav;
	}
}