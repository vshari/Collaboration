app.factory('UserService', function($http) {

	console.log('**********From userservice.js => Entering UserService')

	var userService = this
	var BASE_URL = "//localhost:5002/Collaboration_BackEnd/"
	
	
	userService.registerUser = function(user) {
		console.log('**********From UserService.js => registerUser() => calling backend for /register')
		return $http.post(BASE_URL + "/register", user)
	}

	userService.login = function(user) {
		console.log('**********From UserService.js => login() => calling backend for /login')
		return $http.post(BASE_URL + "/login", user)
	}
		
	userService.logout=function(){
		console.log('**********From UserService.js => logout() => calling backend for /logout')
		return $http.put(BASE_URL + "/logout")
	}
	
	userService.getAllUsers=function(){
		console.log('**********From UserService.js => getAllUsers() => calling backend for /getUsers')
		return $http.get(BASE_URL +"/getAllUsers")
	}
	
	userService.friendRequest=function(username){
		console.log('**********From UserService.js => friendRequest() => calling backend for /friendRequest');
		return $http.post(BASE_URL+ '/friendRequest',username);
	}
	
	
	
	
	
	return userService
	})