package controllers;

import models.User;

public class Security extends Secure.Security {

	static boolean authenticate(String username, String password) {
		User user = User.connect(username, password);
		return user != null /* && user.isAdmin */;
	}

	static void onDisconnected() {
		Application.index();
	}

	static void onAuthenticated() {
		Admin.index();
	}

	static boolean check(String profile) {
		return true;
		/*if ("admin".equals(profile)) {
			return User.find("byEmail", connected()).<User> first().isAdmin;
		}
		return false;*/
	}
}
