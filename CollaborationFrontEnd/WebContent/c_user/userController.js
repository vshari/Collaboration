app.controller('UserController', function($scope, $rootScope, $location,$cookieStore, UserService) {
	console.log('**********From usercontroller.js => Entering UserController');
	$scope.user = {
			id : '',
			username : '',
			password : '',
			email : '',
			role : '',
			isOnline : '',
			enabled : ''
		}
	
	$scope.message
	
	// register button in register.html
	$scope.registerUser = function(){
		console.log('**********From UserController.js => registerUser() => Entering the registerUser function')
		UserService.registerUser($scope.user)
		.then(
		function(response){
			console.log("**********From UserController.js => registerUser() => success - Entering success function for registerUser")
			console.log("**********response.status => " + response.status)
			$scope.message = "Registration Successful. Please login."
			$location.path("/login")
		}
		,function(response){
			console.log("**********From UserController.js => registerUser() => failure - Entering failure function for registerUser")
			console.log("**********response.status => " + response.status)
			$scope.errorMessage = "Registration Failed."
			$location.path("/register")
		})
	}
	
	$scope.login = function() {
		console.log('**********From UserController.js => login() => Entering the login function')
		UserService.login($scope.user)
		.then(
		function(response){
			console.log("**********From UserController.js => login() => success - Entering success function for login")
			console.log("**********response.status => " + response.status)
			$scope.user = response.data
			$rootScope.currentUser=$scope.user
			$location.path("/home")
		}, 
		function(response) {
			console.log("**********From UserController.js => login() => failure - Entering failure function for login")
			console.log("**********response.status => " + response.status)
			$scope.message = "Invalid Username or Password"
			$scope.user.username=''
			$scope.user.password=''
			$location.path("/login")
		})
	}
	
	
	// logout button
	$rootScope.logout=function(){
		console.log('**********From UserController.js => logout() => Entering the logout function')
		delete $rootScope.currentUser
		
		UserService.logout()
		.then(function(response){
			console.log("**********From UserController.js => logout() => success - Entering success function for logout")
			console.log("**********response.status => " + response.status)
			$location.path('/home')
		},
		function(response){
			console.log("**********From UserController.js => logout() => failure - Entering failure function for logout")
			console.log("**********response.status => " + response.status)
		})
		
	}
	
	// send friend request
	$scope.friendRequest=function(username){
		console.log('**********From UserController.js => friendRequest() => Entering the friendRequest function')
		UserService.friendRequest(username)
		.then(function(response){
			console.log("**********From UserController.js => friendRequest() => success - Entering success function for friendRequest")
			console.log("**********response.status => " + response.status)
			alert('Friend request sent')
			getAllUsers();
			$location.path('/getAllUsers')
		},
		function(response){
			console.log("**********From UserController.js => friendRequest() => failure - Entering failure function for friendRequest")
			console.log("**********response.status => " + response.status)
		})
	}
	
	
	// list of users
	function getAllUsers(){
		console.log('entering get all users ')
		UserService.getAllUsers()
		.then(function(response){
		console.log("**********From UserController.js => getAllUsers() => success - Entering success function for getAllUsers")
		console.log("**********response.status => " + response.status)
		console.log("**********response.data => " + response.data)
		$scope.users=response.data
		},
		function(response){
			console.log("**********From UserController.js => getAllUsers() => failure - Entering failure function for getAllUsers")
			console.log("**********response.status => " + response.status)
		})
	}
	
	getAllUsers()
})
	
	

