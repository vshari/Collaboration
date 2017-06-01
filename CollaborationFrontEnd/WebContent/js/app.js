var app = angular.module("myApp", [ 'ngRoute', 'ngCookies' ])
app.config(function($routeProvider) { // configuring route provider
	console.log('**********From App.js => Entering myApp')
	$routeProvider

	// loads this page first - home

	// Home
	.when('/', {
		templateUrl : 'c_home/home.html',
		css : 'css/home.css'
	}).when('/home', {
		templateUrl : 'c_home/home.html'
	})

	// Login
	.when('/login', {
		controller : 'UserController',
		templateUrl : 'c_user/login.html'
	})

	// for a new user to register
	.when('/register', {
		controller : 'UserController',
		templateUrl : 'c_user/register.html'
	})

	// to post a new job
	.when('/postJob', {
		controller : 'JobController',
		templateUrl : 'c_job/createJob.html'
	})

	// to view all the jobs
	.when('/getAllJobs', {
		controller : 'JobController',
		templateUrl : 'c_job/jobsTitles.html'
	})

	// to view the job detail of a job
	.when('/jobDetail/:jobId', {
		controller : 'JobDetailController',
		templateUrl : 'c_job/jobDetail.html'
	})

	// to upload a picture
	.when('/uploadPicture', {
		templateUrl : 'c_user/uploadPicture.html'
	})
	// for a new user to register
	.when('/logout', {
		controller : 'UserController'
	})
	// to create a new blog
	.when('/createBlog', {
		controller : 'BlogController',
		templateUrl : 'c_blog/createBlog.html'
	})

	// to view all blogs
	.when('/getAllBlogs', {
		controller : 'BlogController',
		templateUrl : 'c_blog/listOfBlogs.html'
	})

	// to view the Blog detail of a blog
	.when('/getBlogDetails/:id', {
		controller : 'BlogDetailController',
		templateUrl : 'c_blog/blogDetails.html'
	})
	// to view list of friends
	.when('/friendsList', {
		controller : 'FriendController',
		templateUrl : 'c_friend/listOfFriends.html'
	})

	// to view list of pending firend requests
	.when('/pendingRequest', {
		controller : 'FriendController',
		templateUrl : 'c_friend/pendingRequest.html'
	})
	// to view list of all users
	.when('/getAllUsers', {
		controller : 'UserController',
		templateUrl : 'c_user/listOfUsers.html'
	})
	// to chat
	.when('/chat',{
		controller:'ChatController',
		templateUrl:'c_chat/chat.html'
	})

})

app.run(function($cookieStore, $rootScope, $location, UserService) {

	if ($rootScope.currentUser == undefined)
		$rootScope.currentUser = $cookieStore.get('currentUser')
	$rootScope.logout = function() {
		console.log('logout function')
		delete $rootScope.currentUser;
		$cookieStore.remove('currentUser')
		UserService.logout().then(function(response) {
			console.log("logged out successfully..");
			/* $rootScope.message="Logged out Successfully"; */
			$location.path('/login')
		}, function(response) {
			console.log(response.status);
		})

	}

})
