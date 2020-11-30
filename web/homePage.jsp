<%-- 
    Document   : homePage
    Created on : Sep 16, 2020, 8:17:43 PM
    Author     : Tin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Homepage - Social Network</title>

        <!--Bootstrap CSS-->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
        <!-- Main CSS -->
        <link rel="stylesheet" href="assets/css/main.css"/>
    </head>

    <body>
        <c:set var="userFullName" value="${requestScope.FULL_NAME}" />
        <c:set var="userEmail" value="${requestScope.USER_EMAIL}" />
        <c:set var="err" value="${requestScope.ERROR}" />
        <c:set var="postsList" value="${requestScope.POSTS_LIST}" />
        <c:set var="notiList" value="${requestScope.NOTI_LIST}" />

        <!-- Start of header -->
        <div class="header sticky-top fixed-top">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-2">
                        <p class="welcome">Hello ${userFullName}!</p>
                    </div>
                    <div class="col-sm-1">
                        <a class="home float-left" href="DispatchController"><i class="fas fa-home"></i></a>
                    </div>
                    <div class="col-sm-8">
                        <form action="DispatchController" method="GET">
                            <div class="form-row align-item-center">
                                <div class="col-sm-2"></div>
                                <input type="text" class="col-sm-5 search-text" name="txtSearch"
                                       placeholder="Search Articles..." value="">
                                <div class="col-sm-1"></div>
                                <button type="submit" name="btnAction" class="col-sm-1 btn btn-primary" value="Search">Search</button>
                                <div class="col-sm-3"></div>
                            </div>
                        </form>
                    </div>
                    <div class="col-sm-1">
                        <c:url var="logout" value="DispatchController">
                            <c:param name="btnAction" value="Logout" />
                        </c:url>
                        <a href="${logout}" class="btn btn-outline-secondary">
                            Logout
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <!-- End of Header -->

        <div class="container-fluid">
            <div class="row">
                <div class="offset-sm-1 col-sm-7">
                    <!-- Start of New Post section -->
                    <div class="card content-post new-post">
                        <div class="card-body">
                            <h6 class="title card-subtitle mb-2 text-muted">Share with everybody: </h6>
                            <form action="DispatchController" method="POST" enctype='multipart/form-data'>
                                <div class="mb-3">
                                    <input type="text" class="form-control" name="txtPostTitle" required placeholder="Title..." value="${param.txtTitle}">
                                    <textarea class="form-control" placeholder="What is in your mind..."
                                              name="txtPostContent" required value="${param.txtPostContent}"></textarea>
                                    <c:if test="${not empty err.emptyPost}">
                                        <p class="new-post-error float-left">
                                            ${err.emptyPost}
                                        </p>
                                    </c:if>

                                    <c:if test="${not empty err.postFail}">
                                        <p class="new-post-error float-left">
                                            ${err.postFail}
                                        </p>
                                    </c:if>
                                </div>
                                <input type="file" accept="image/*" name="imageUpload"/>
                                <button id="createNewPost" type="submit" class="btn btn-outline-secondary btn-sm text-uppercase float-right">Post</button>
                                <input type="hidden" name="btnAction" value="AddPost" />
                            </form>
                        </div>
                    </div>
                    <!-- End of New Post section -->

                    <!-- Start of Posts section -->
                    <c:forEach var="post" items="${postsList}">
                        <c:url var="postDetail" value="DispatchController">
                            <c:param name="txtPostId" value="${post.postsDTO.postId}" />
                            <c:param name="btnAction" value="PostDetail" />
                        </c:url>
                        <div class="card content-post article">
                            <a href="${postDetail}" class="single-post">
                                <div class="card-body py-3">
                                    <h6 class="card-subtitle mt-2 text-muted float-left post-owner">${post.userFullName}
                                    </h6>

                                    <p class="post-time pt-2">${post.createdAtFull}</p>

                                    <c:if test="${not empty post.postsDTO.postTitle}">
                                        <h5 class="card-title post-title">${post.postsDTO.postTitle}</h5>
                                    </c:if>

                                    <p class="card-text post-body">${post.postsDTO.postContent}</p>
                                    
                                    <c:if test="${not empty post.postsDTO.image}">
                                        <img class="post-image" src="assets/images/${post.postsDTO.image}">
                                    </c:if>
                                </div>
                            </a>

                            <c:if test="${post.postsDTO.userPostId == userEmail}">
                                <div class="options">
                                    <button class="btn btn-outline-danger btn-sm" onclick="deletePost(${post.postsDTO.postId})">
                                        Delete Post
                                    </button>
                                </div>
                            </c:if>         
                        </div>
                    </c:forEach>
                    <!-- End of Posts section -->
                </div>

                <div class="col-sm-3">
                    <!-- Start of Notification section -->
                    <div class="notification">
                        <h5 class="noti-title text-center text-uppercase">
                            Notification
                        </h5>

                        <c:forEach var="noti" items="${notiList}">
                            <!-- Start a notification -->
                            <c:url var="urlNoti" value="DispatchController">
                                <c:param name="txtPostId" value="${noti.notificationsDTO.postId}" />
                                <c:if test="${noti.notificationsDTO.commentId != null}">
                                    <c:param name="commentId" value="${noti.notificationsDTO.commentId}" />
                                </c:if>
                                <c:param name="btnAction" value="PostDetail" />
                            </c:url>
                            <a class="link-noti" href="${urlNoti}">
                                <div class="single-noti">
                                        <p class="noti-content">${noti.notificationsDTO.contentNoti}</p>
                                        <span class="noti-time">${noti.dateAtFull}</span>
                                </div>    
                            </a>
                            
                            <!-- End a notification -->
                        </c:forEach>
                    </div>
                    <!-- End of Notification section -->
                </div>

                <div class="offset-sm-1"></div>
            </div>
        </div>

                                    
        <!--Bootstrap JS-->
        <script src="assets/js/jquery-3.5.1.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/fontawesome.min.js"></script>
    </body>

    <script>
        $(document).ready(function () {
            if ($(".header").css("position") === "fixed") {
                $(".new-post").css("margin-top", "7em");
            }
        });

        function deletePost(postNumber) {
            let confirm = window.confirm("Are you sure you want to delele this post?");
            if (confirm) {
                window.location.href = "DispatchController?btnAction=DeletePost&txtPostId=" + postNumber;
            }
        }
    </script>

</html>