<%-- 
    Document   : activation
    Created on : Sep 28, 2020, 9:44:16 PM
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
        <c:set var="err" value="${requestScope.ERROR}" />
        <c:set var="sendSuccess" value="${requestScope.SENT_SUCCESS}" />
        
        <!-- Start of header -->
        <div class="header sticky-top fixed-top">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-2">
                        <p class="welcome">Hello ${userFullName}!</p>
                    </div>
                    <div class="col-sm-9">
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

        <div class="container">
            <div class="row">
                <div class="col-sm-2"></div>
                <div class="col-sm-8">
                    <h3 class="text-center text-uppercase activation-head head-warning">Your account is not active yet</h3>
                    <h4 class="text-center activation-head">You need to activate your account to continue using</h4>
                    <p class="text-center">Enter your code here</p>

                    <div class="activation-form d-flex justify-content-center">
                        <form action="DispatchController" method="POST">
                            <input type="number" name="txtCode" class="input-code" required>
                            <button type="submit" name="btnAction" value="Verify" class="btn btn-outline-secondary text-uppercase">Verify</button>
                        </form>
                    </div>

                    <c:if test="${not empty err.activationCodeNotCorrect}">
                        <p class="verify-error text-center">${err.activationCodeNotCorrect}</p>
                    </c:if>
                        
                    <c:if test="${not empty err.updateActivationCodeFail}">
                        <p class="verify-error text-center">${err.updateActivationCodeFail}</p>
                    </c:if>
                        
                    <c:if test="${not empty err.sendEmailFail}">
                        <p class="verify-error text-center">${err.updateActivationCodeFail}</p>
                    </c:if>

                    <p class="verify-again-title">Not received any activation mail?</p>
                    <c:url var="resendEmail" value="DispatchController">
                        <c:param name="btnAction" value="ResendCode" />
                    </c:url>
                    <a class="verify-again" href="${resendEmail}">Send me another one</a>
                    <c:if test="${sendSuccess != null}">
                        <p class="title">${sendSuccess}</p>
                    </c:if>

                </div>
                <div class="col-sm-2"></div>
            </div>
        </div>

                                    
        <!--Bootstrap JS-->
        <script src="assets/js/jquery-3.5.1.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
    </body>
</html>
