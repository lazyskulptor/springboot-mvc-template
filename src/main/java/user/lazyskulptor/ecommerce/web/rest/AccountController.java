package user.lazyskulptor.ecommerce.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AccountController
 */
@RestController
@RequestMapping("/api")
public class AccountController {

	@GetMapping
	public String helloWorld() {
		return "hello World!";
	}
}
