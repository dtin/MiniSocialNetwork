<%-- 
    Document   : searchPage
    Created on : Sep 24, 2020, 3:34:13 PM
    Author     : Tin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page - Social Network</title>

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
        <c:set var="searchValue" value="${param.txtSearch}" />
        <c:set var="minPage" value="${requestScope.MIN_PAGE}" />
        <c:set var="prevPage" value="${requestScope.PREV_PAGE}" />
        <c:set var="currentPage" value="${requestScope.CURRENT_PAGE}" />
        <c:set var="nextPage" value="${requestScope.NEXT_PAGE}" />
        <c:set var="maxPage" value="${requestScope.MAX_PAGE}" />

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
                                       placeholder="Search Articles..." value="${searchValue}">
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
                    <c:if test="${empty err}">
                        <!-- Start of Posts section -->
                        <c:forEach var="post" items="${postsList}">
                            <c:url var="postDetail" value="DispatchController">
                                <c:param name="btnAction" value="PostDetail"/>
                                <c:param name="txtPostId" value="${post.postsDTO.postId}" />
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

                        <nav>
                            <ul class="pagination justify-content-center">
                                <c:if test="${minPage != null}">
                                    <c:url var="urlMinPage" value="DispatchController">
                                        <c:param name="txtSearch" value="${searchValue}" />
                                        <c:param name="btnAction" value="Search" />
                                        <c:param name="page" value="${minPage}" />
                                    </c:url>
                                    <li class="page-item">
                                        <a class="page-link" href="${urlMinPage}"><i class="fas fa-arrow-left"></i></a>
                                    </li>
                                </c:if>
                                
                                <c:if test="${prevPage != null}">
                                    <c:url var="urlPrevPage" value="DispatchController">
                                        <c:param name="txtSearch" value="${searchValue}" />
                                        <c:param name="btnAction" value="Search" />
                                        <c:param name="page" value="${prevPage}" />
                                    </c:url>
                                    <li class="page-item">
                                        <a class="page-link" href="${urlPrevPage}">${prevPage}</a>
                                    </li>
                                </c:if>
                                    
                                <c:if test="${currentPage != null}">
                                    <li class="page-item active">
                                        <span class="page-link">
                                            ${currentPage}
                                        </span>
                                    </li>
                                </c:if>
                                    
                                <c:if test="${nextPage != null}">
                                    <c:url var="urlNextPage" value="DispatchController">
                                        <c:param name="txtSearch" value="${searchValue}" />
                                        <c:param name="btnAction" value="Search" />
                                        <c:param name="page" value="${nextPage}" />
                                    </c:url>
                                    <li class="page-item">
                                        <a class="page-link" href="${urlNextPage}">${nextPage}</a>
                                    </li>
                                </c:if>
                                    
                                <c:if test="${maxPage != null}">
                                    <c:url var="urlMaxPage" value="DispatchController">
                                        <c:param name="txtSearch" value="${searchValue}" />
                                        <c:param name="btnAction" value="Search" />
                                        <c:param name="page" value="${maxPage}" />
                                    </c:url>
                                    <li class="page-item">
                                        <a class="page-link" href="${urlMaxPage}"><i class="fas fa-arrow-right"></i></a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>
                    </c:if>

                    <c:if test="${not empty err}">
                        <h4 class="text-center error">${err.searchValueNotFound}</h4>
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
                $(".article").first().css("margin-top", "7em");
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
