<%-- 
    Document   : postDetail
    Created on : Sep 19, 2020, 9:25:08 PM
    Author     : Tin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post Detail - Social Network</title>

        <!--Bootstrap CSS-->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
        <!-- Main CSS -->
        <link rel="stylesheet" href="assets/css/main.css"/>
    </head>

    <body>
        <c:set var="userFullName" value="${requestScope.FULL_NAME}" />
        <c:set var="userEmail" value="${requestScope.USER_EMAIL}" />
        <c:set var="post" value="${requestScope.SINGLE_POST}" />
        <c:set var="commentsList" value="${requestScope.STD_COMMENTS_LIST}" />
        <c:set var="notiList" value="${requestScope.NOTI_LIST}" />
        <c:set var="cmtError" value="${requestScope.COMMENT_ERROR}" />
        <c:set var="cmtId" value="${requestScope.COMMENT_ID}" />
        <c:set var="postError" value="${requestScope.POST_ERROR}" />
        <c:set var="actionError" value="${requestScope.ACTION_ERROR}" />

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
                                       placeholder="Search Articles...">
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
                    <c:if test="${postError == null}">
                        <!-- Start of a Post section -->
                        <div class="card content-post article" id="post${post.postsDTO.postId}">
                            <div class="card-body">
                                <h6 class="card-subtitle mt-2 text-muted float-left post-owner">${post.userFullName}</h6>

                                <c:if test="${post.postsDTO.userPostId == userEmail}">
                                    <c:url var="deletePost" value="DispatchController">
                                        <c:param name="postId" value="${post.postsDTO.postId}" />
                                        <c:param name="btnAction" value="DeletePost" />
                                    </c:url>
                                    <div class="options float-right">
                                        <button class="btn btn-outline-danger btn-sm" onclick="deletePost(${post.postsDTO.postId})">
                                            Delete Post
                                        </button>
                                    </div>
                                </c:if>

                                <p class="post-time">${post.createdAtFull}</p>

                                <c:if test="${not empty post.postsDTO.postTitle}">
                                    <h5 class="card-title post-title">${post.postsDTO.postTitle}</h5>
                                </c:if>

                                <p class="card-text post-body">${post.postsDTO.postContent}</p>
                                
                                <c:if test="${not empty post.postsDTO.image}">
                                    <img class="post-image" src="assets/images/${post.postsDTO.image}">
                                </c:if>
                                
                                <div class="user-emotion">
                                    <c:set var="emotion" value="${post.emotion}" />

                                    <c:url var="like" value="DispatchController">
                                        <c:param name="txtPostId" value="${post.postsDTO.postId}" />
                                        <c:param name="btnAction" value="PostLike" />
                                    </c:url>

                                    <c:url var="dislike" value="DispatchController">
                                        <c:param name="txtPostId" value="${post.postsDTO.postId}" />
                                        <c:param name="btnAction" value="PostDislike" />
                                    </c:url>

                                    <c:choose>
                                        <c:when test="${emotion == 'Like'}">
                                            <a href="${like}" class="btn btn-outline-primary active"><i
                                                    class="fas fa-thumbs-up mr-3"></i>${post.countLike}</a>
                                            <a href="${dislike}" class="btn btn-outline-primary"><i
                                                    class="fas fa-thumbs-down mr-3"></i>${post.countDislike}</a>
                                            <a href="#" class="btn btn-outline-primary"><i
                                                    class="fas fa-comments mr-3"></i>${post.countComment}</a>
                                            </c:when>

                                        <c:when test="${emotion == 'Dislike'}">
                                            <a href="${like}" class="btn btn-outline-primary"><i
                                                    class="fas fa-thumbs-up mr-3"></i>${post.countLike}</a>
                                            <a href="${dislike}" class="btn btn-outline-primary active"><i
                                                    class="fas fa-thumbs-down mr-3"></i>${post.countDislike}</a>
                                            <a href="#" class="btn btn-outline-primary"><i
                                                    class="fas fa-comments mr-3"></i>${post.countComment}</a>
                                            </c:when>

                                        <c:when test="${emotion == null || emotion == 'Deleted'}">
                                            <a href="${like}" class="btn btn-outline-primary"><i
                                                    class="fas fa-thumbs-up mr-3"></i>${post.countLike}</a>
                                            <a href="${dislike}" class="btn btn-outline-primary"><i
                                                    class="fas fa-thumbs-down mr-3"></i>${post.countDislike}</a>
                                            <a href="#" class="btn btn-outline-primary"><i
                                                    class="fas fa-comments mr-3"></i>${post.countComment}</a>
                                            </c:when>
                                        </c:choose>
                                </div>
                                    
                                <c:if test="${not empty actionError.notAuthorizedToDelete}">
                                    <p class="post-error float-left">
                                        ${actionError.notAuthorizedToDelete}
                                    </p>
                                </c:if>

                                <c:if test="${not empty actionError.deleteFail}">
                                    <p class="post-error float-left">
                                        ${actionError.deleteFail}
                                    </p>
                                </c:if>  
                            </div>
                        </div>
                        <!-- End of a Post section -->


                        <!-- Start of add Comment section -->
                        <div class="card content-post">
                            <div class="card-body">
                                <h6 class="title card-subtitle mb-2 text-muted">Writing your comment: </h6>
                                <form action="AddCommentServlet" method="POST">
                                    <div class="mb-3">
                                        <input type="hidden" name="txtPostId" value="${post.postsDTO.postId}" />
                                        <textarea class="form-control" placeholder="What is your opinion..."
                                                  name="txtCommentContent" required value="${requestScope.txtPostContent}"></textarea>
                                        <c:if test="${not empty cmtError.addCommentError}">
                                            <p class="new-post-error float-left">
                                                ${cmtError.addCommentError}
                                            </p>
                                        </c:if>
                                    </div>
                                    <button id="createNewPost"
                                            class="btn btn-outline-secondary btn-sm text-uppercase float-right"
                                            type="submit">Post</button>
                                </form>
                            </div>
                        </div>
                        <!-- End of add Comment section -->

                        <!--Start of Comments section-->
                        <c:forEach var="comment" items="${commentsList}">
                            <div class="card content-post comment" id="comment${comment.commentsDTO.commentId}">
                                <div class="card-body">
                                    <h6 class="card-subtitle mt-2 text-muted float-left post-owner">${comment.userFullName}</h6>

                                    <c:if test="${comment.commentsDTO.commentUser == userEmail}">
                                        <div class="options float-right">
                                            <button class="btn btn-outline-danger btn-sm"
                                                    onclick="deleteComment(${comment.commentsDTO.commentId})">
                                                Delete Comment
                                            </button>
                                        </div>
                                    </c:if>

                                    <p class="post-time">${comment.createdAtFull}</p>

                                    <p class="card-text comment-body">${comment.commentsDTO.commentContent}</p>

                                    <c:if test="${cmtError.notAuthorizedToDelete != null && comment.commentsDTO.commentId == cmtId}">
                                        <p class="comment-error float-left">
                                            ${cmtError.notAuthorizedToDelete}
                                        </p>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                        <!--End of Comments section-->
                    </c:if>

                    <c:if test="${postError != null}">
                        <h4 class="text-center error">${postError.notFoundPost}</h4>
                        <h5 class="text-center">Sorry for this inconvenience</h5>
                        <div class="d-flex justify-content-center">
                            <a class="btn btn-outline-info" href="DispatchController"><i class="fas fa-arrow-circle-left mr-1"></i> Go to Home</a>
                        </div>
                    </c:if>
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
            $(".article").css("margin-top", "7em");
        }

        $(".content-post").last().css("margin-bottom", "2em");
        
        scrollToComment(${param.commentId});        
    });

    function deleteComment(commentId) {
        let confirm = window.confirm("Are you sure you want to delele this comment?");
        if (confirm) {
            window.location.href = "DispatchController?btnAction=DeleteComment&txtCommentId=" + commentId;
        }
    }
    
    function deletePost(postId) {
        let confirm = window.confirm("Are you sure you want to delete this post?");
        if(confirm) {
            window.location.href = "DispatchController?btnAction=DeletePost&txtPostId=" + postId;
        }
    }

    //Sua lai thanh scroll to Comment
    <c:if test="${param.commentId != null}">
        function scrollToComment(commentNumber) {
            let offset = $("#comment" + commentNumber).offset().top;

            $("html, body").animate({
                scrollTop: offset
            }, "slow");
            
            $("#comment" + commentNumber).removeClass("comment").addClass("comment-active");
        }
        
    </c:if>
    </script>

</html>
