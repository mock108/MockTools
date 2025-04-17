package io.github.mockup.zenith.web.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.mockup.zenith.core.constans.Pages;

@Controller
public class HomeController {
	@GetMapping("/home")
	public String loginForm() {
		return Pages.PAGE_HOME;
	}
}
