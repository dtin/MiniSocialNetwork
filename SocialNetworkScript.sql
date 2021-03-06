USE [master]
GO
/****** Object:  Database [SocialNetwork]    Script Date: 9/29/2020 8:32:12 PM ******/
CREATE DATABASE [SocialNetwork]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'SocialNetwork', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS2014\MSSQL\DATA\SocialNetwork.mdf' , SIZE = 4288KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'SocialNetwork_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS2014\MSSQL\DATA\SocialNetwork_log.ldf' , SIZE = 1072KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [SocialNetwork] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [SocialNetwork].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [SocialNetwork] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [SocialNetwork] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [SocialNetwork] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [SocialNetwork] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [SocialNetwork] SET ARITHABORT OFF 
GO
ALTER DATABASE [SocialNetwork] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [SocialNetwork] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [SocialNetwork] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [SocialNetwork] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [SocialNetwork] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [SocialNetwork] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [SocialNetwork] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [SocialNetwork] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [SocialNetwork] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [SocialNetwork] SET  ENABLE_BROKER 
GO
ALTER DATABASE [SocialNetwork] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [SocialNetwork] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [SocialNetwork] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [SocialNetwork] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [SocialNetwork] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [SocialNetwork] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [SocialNetwork] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [SocialNetwork] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [SocialNetwork] SET  MULTI_USER 
GO
ALTER DATABASE [SocialNetwork] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [SocialNetwork] SET DB_CHAINING OFF 
GO
ALTER DATABASE [SocialNetwork] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [SocialNetwork] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [SocialNetwork] SET DELAYED_DURABILITY = DISABLED 
GO
USE [SocialNetwork]
GO
/****** Object:  Table [dbo].[tblComments]    Script Date: 9/29/2020 8:32:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblComments](
	[commentId] [int] IDENTITY(1,1) NOT NULL,
	[postId] [int] NOT NULL,
	[commentUser] [varchar](50) NOT NULL,
	[commentContent] [nvarchar](150) NOT NULL,
	[createdAt] [bigint] NOT NULL,
	[isDeleted] [bit] NOT NULL,
 CONSTRAINT [PK_tblComments] PRIMARY KEY CLUSTERED 
(
	[commentId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblEmotions]    Script Date: 9/29/2020 8:32:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblEmotions](
	[postId] [int] NOT NULL,
	[emotionUserId] [varchar](50) NOT NULL,
	[emotion] [varchar](10) NOT NULL,
	[isDeleted] [bit] NOT NULL,
	[createdAt] [bigint] NOT NULL,
	[updatedAt] [bigint] NULL,
 CONSTRAINT [PK_tblEmotions] PRIMARY KEY CLUSTERED 
(
	[postId] ASC,
	[emotionUserId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblNotifications]    Script Date: 9/29/2020 8:32:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblNotifications](
	[notificationId] [int] IDENTITY(1,1) NOT NULL,
	[postId] [int] NOT NULL,
	[commentId] [int] NULL,
	[notiUserId] [varchar](50) NOT NULL,
	[fromUserId] [varchar](50) NOT NULL,
	[typeOfNoti] [varchar](10) NOT NULL,
	[contentNoti] [nvarchar](100) NOT NULL,
	[createdAt] [bigint] NOT NULL,
	[updatedAt] [bigint] NULL,
	[isDeleted] [bit] NOT NULL,
 CONSTRAINT [PK_tblNotification] PRIMARY KEY CLUSTERED 
(
	[notificationId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblPosts]    Script Date: 9/29/2020 8:32:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblPosts](
	[postId] [int] IDENTITY(1,1) NOT NULL,
	[userPostId] [varchar](50) NOT NULL,
	[postTitle] [nvarchar](100) NOT NULL,
	[postContent] [nvarchar](1000) NOT NULL,
	[image] [nvarchar](200) NULL,
	[isDeleted] [bit] NOT NULL,
	[createdAt] [bigint] NOT NULL,
 CONSTRAINT [PK_tblPost] PRIMARY KEY CLUSTERED 
(
	[postId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblStatus]    Script Date: 9/29/2020 8:32:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblStatus](
	[statusId] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](10) NOT NULL,
 CONSTRAINT [PK_tblStatus] PRIMARY KEY CLUSTERED 
(
	[statusId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tblUsers]    Script Date: 9/29/2020 8:32:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tblUsers](
	[email] [varchar](50) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[password] [varchar](100) NOT NULL,
	[role] [varchar](10) NOT NULL,
	[statusId] [int] NOT NULL,
	[activationCode] [int] NOT NULL,
 CONSTRAINT [PK_tblAccounts] PRIMARY KEY CLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[tblComments] ON 

INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (1, 26, N'tin@gmail.com', N'Testing comment for post 26....', 1601039621151, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (2, 26, N'tin@gmail.com', N'Test thÃªm láº§n ná»¯a', 1601041871596, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (3, 26, N'tin@gmail.com', N'Gogoogo', 1601042680125, 1)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (4, 26, N'long@gmail.com', N'Gogo go
', 1601050914487, 1)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (5, 27, N'tin@gmail.com', N'Hello test thử comment', 1601117810567, 1)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (6, 27, N'tin@gmail.com', N'Test test test', 1601118460734, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (7, 29, N'long@gmail.com', N'Gogogog
', 1601121192447, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (8, 28, N'Tin@gmail.com', N'Test thử comment', 123456789, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (9, 30, N'tin@gmail.com', N'Alo alo', 1601133900725, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (10, 30, N'long@gmail.com', N'Test lần nữa', 1601133987138, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (11, 12, N'Tin@gmail.com', N'HAWEHA', 12391873, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (12, 12, N'Tin@gmail.com', N'HAWEHA', 12391873, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (13, 12, N'Tin@gmail.com', N'HAWEHA', 12391873, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (14, 30, N'tin@gmail.com', N'ralwhel', 1601134487031, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (15, 30, N'tin@gmail.com', N'Aloawea wroea', 1601134676064, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (16, 30, N'tin@gmail.com', N'testing again
', 1601134831086, 1)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (17, 30, N'tin@gmail.com', N'Heawerhai', 1601134991619, 1)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (18, 30, N'long@gmail.com', N'Test new', 1601135560283, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (19, 30, N'tin@gmail.com', N'Alo', 1601135574418, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (20, 136, N'long@gmail.com', N'Alo', 1601224939481, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (21, 123, N'long@gmail.com', N'Alo', 1601225135256, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (22, 138, N'long@gmail.com', N'Alo laoo', 1601225204316, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (23, 139, N'tin@gmail.com', N'Alol alo', 1601225317640, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (24, 142, N'long@gmail.com', N'Alo alo', 1601384452774, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (25, 143, N'tin@gmail.com', N'Hay đấy bạn', 1601385966162, 0)
INSERT [dbo].[tblComments] ([commentId], [postId], [commentUser], [commentContent], [createdAt], [isDeleted]) VALUES (26, 143, N'long@gmail.com', N'Ok bạn luôn', 1601386005679, 0)
SET IDENTITY_INSERT [dbo].[tblComments] OFF
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (1, N'tin@gmail.com', N'Like', 0, 1600695114817, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (4, N'tin@gmail.com', N'Like', 0, 1600661199660, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (6, N'tin@gmail.com', N'Like', 0, 1600694386645, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (10, N'long@gmail.com', N'Like', 0, 1600695436434, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (10, N'tin@gmail.com', N'Like', 0, 1600694301339, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (11, N'long@gmail.com', N'Like', 0, 1600697002602, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (11, N'tin@gmail.com', N'Like', 1, 1600658024827, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (17, N'long@gmail.com', N'Like', 0, 1600695449757, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (17, N'tin@gmail.com', N'Dislike', 1, 1600695472718, 1600969759426)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (18, N'long@gmail.com', N'Like', 0, 1600696533724, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (18, N'tin@gmail.com', N'Like', 1, 1600697635333, 1600702790025)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (19, N'long@gmail.com', N'Like', 0, 1600696998453, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (19, N'tin@gmail.com', N'Like', 1, 1600697019075, 1600781654823)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (20, N'long@gmail.com', N'Like', 0, 1600697058780, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (20, N'tin@gmail.com', N'Like', 0, 1600697613660, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (21, N'tin@gmail.com', N'Dislike', 0, 1600782198202, 1600782408107)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (22, N'tin@gmail.com', N'Like', 0, 1600782444018, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (23, N'long@gmail.com', N'Like', 0, 1600871265258, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (23, N'tin@gmail.com', N'Dislike', 0, 1600782558301, 1600962117071)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (24, N'long@gmail.com', N'Dislike', 0, 1600782649299, 1600871325430)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (24, N'tin@gmail.com', N'Like', 0, 1600782622939, 1600870410997)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (25, N'long@gmail.com', N'Like', 0, 1600871323047, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (25, N'tin@gmail.com', N'Like', 0, 1600871296643, 1601121301143)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (26, N'long@gmail.com', N'Dislike', 0, 1601121501187, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (26, N'tin@gmail.com', N'Like', 0, 1601038162589, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (27, N'tin@gmail.com', N'Like', 1, 1601117784048, 1601117795971)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (30, N'long@gmail.com', N'Dislike', 1, 1601135618684, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (30, N'tin@gmail.com', N'Dislike', 1, 1601121483651, 1601135625487)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (120, N'tin@gmail.com', N'Dislike', 0, 1601189020776, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (125, N'long@gmail.com', N'Like', 0, 1601222422931, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (136, N'long@gmail.com', N'Like', 0, 1601224399668, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (137, N'long@gmail.com', N'Like', 0, 1601224080195, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (141, N'tin@gmail.com', N'Like', 0, 1601365370769, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (142, N'long@gmail.com', N'Like', 0, 1601384454088, NULL)
INSERT [dbo].[tblEmotions] ([postId], [emotionUserId], [emotion], [isDeleted], [createdAt], [updatedAt]) VALUES (143, N'tin@gmail.com', N'Like', 0, 1601385969806, 1601385986245)
SET IDENTITY_INSERT [dbo].[tblNotifications] ON 

INSERT [dbo].[tblNotifications] ([notificationId], [postId], [commentId], [notiUserId], [fromUserId], [typeOfNoti], [contentNoti], [createdAt], [updatedAt], [isDeleted]) VALUES (15, 30, NULL, N'long@gmail.com', N'tin@gmail.com', N'Emotion', N'Tin disliked your post', 1601121483720, 1601225418399, 1)
INSERT [dbo].[tblNotifications] ([notificationId], [postId], [commentId], [notiUserId], [fromUserId], [typeOfNoti], [contentNoti], [createdAt], [updatedAt], [isDeleted]) VALUES (16, 26, NULL, N'tin@gmail.com', N'long@gmail.com', N'Emotion', N'Long disliked your post', 1601121501197, NULL, 0)
INSERT [dbo].[tblNotifications] ([notificationId], [postId], [commentId], [notiUserId], [fromUserId], [typeOfNoti], [contentNoti], [createdAt], [updatedAt], [isDeleted]) VALUES (18, 30, 17, N'long@gmail.com', N'tin@gmail.com', N'Comment', N'Tin commented in your post.', 1601134991690, NULL, 1)
INSERT [dbo].[tblNotifications] ([notificationId], [postId], [commentId], [notiUserId], [fromUserId], [typeOfNoti], [contentNoti], [createdAt], [updatedAt], [isDeleted]) VALUES (19, 30, 19, N'long@gmail.com', N'tin@gmail.com', N'Comment', N'Tin commented on your post.', 1601135574493, NULL, 0)
INSERT [dbo].[tblNotifications] ([notificationId], [postId], [commentId], [notiUserId], [fromUserId], [typeOfNoti], [contentNoti], [createdAt], [updatedAt], [isDeleted]) VALUES (20, 125, NULL, N'tin@gmail.com', N'long@gmail.com', N'Emotion', N'Long liked your post', 1601222423013, NULL, 0)
INSERT [dbo].[tblNotifications] ([notificationId], [postId], [commentId], [notiUserId], [fromUserId], [typeOfNoti], [contentNoti], [createdAt], [updatedAt], [isDeleted]) VALUES (21, 137, NULL, N'tin@gmail.com', N'long@gmail.com', N'Emotion', N'Long liked your post', 1601224080231, NULL, 0)
INSERT [dbo].[tblNotifications] ([notificationId], [postId], [commentId], [notiUserId], [fromUserId], [typeOfNoti], [contentNoti], [createdAt], [updatedAt], [isDeleted]) VALUES (22, 136, NULL, N'tin@gmail.com', N'long@gmail.com', N'Emotion', N'Long liked your post', 1601224399709, NULL, 1)
INSERT [dbo].[tblNotifications] ([notificationId], [postId], [commentId], [notiUserId], [fromUserId], [typeOfNoti], [contentNoti], [createdAt], [updatedAt], [isDeleted]) VALUES (23, 136, 20, N'tin@gmail.com', N'long@gmail.com', N'Comment', N'Long commented on your post', 1601224939552, NULL, 0)
INSERT [dbo].[tblNotifications] ([notificationId], [postId], [commentId], [notiUserId], [fromUserId], [typeOfNoti], [contentNoti], [createdAt], [updatedAt], [isDeleted]) VALUES (24, 123, 21, N'tin@gmail.com', N'long@gmail.com', N'Comment', N'Long commented on your post', 1601225135320, NULL, 0)
INSERT [dbo].[tblNotifications] ([notificationId], [postId], [commentId], [notiUserId], [fromUserId], [typeOfNoti], [contentNoti], [createdAt], [updatedAt], [isDeleted]) VALUES (25, 138, 22, N'tin@gmail.com', N'long@gmail.com', N'Comment', N'Long commented on your post', 1601225204335, NULL, 0)
INSERT [dbo].[tblNotifications] ([notificationId], [postId], [commentId], [notiUserId], [fromUserId], [typeOfNoti], [contentNoti], [createdAt], [updatedAt], [isDeleted]) VALUES (26, 139, 23, N'long@gmail.com', N'tin@gmail.com', N'Comment', N'Tin commented on your post', 1601225317701, 1601225327489, 1)
INSERT [dbo].[tblNotifications] ([notificationId], [postId], [commentId], [notiUserId], [fromUserId], [typeOfNoti], [contentNoti], [createdAt], [updatedAt], [isDeleted]) VALUES (27, 141, NULL, N'damdongtin@gmail.com', N'tin@gmail.com', N'Emotion', N'Tin liked your post', 1601365370803, 1601365393197, 1)
INSERT [dbo].[tblNotifications] ([notificationId], [postId], [commentId], [notiUserId], [fromUserId], [typeOfNoti], [contentNoti], [createdAt], [updatedAt], [isDeleted]) VALUES (28, 142, 24, N'tin@gmail.com', N'long@gmail.com', N'Comment', N'Long commented on your post', 1601384452859, 1601384479463, 1)
INSERT [dbo].[tblNotifications] ([notificationId], [postId], [commentId], [notiUserId], [fromUserId], [typeOfNoti], [contentNoti], [createdAt], [updatedAt], [isDeleted]) VALUES (29, 142, NULL, N'tin@gmail.com', N'long@gmail.com', N'Emotion', N'Long liked your post', 1601384454110, 1601384479463, 1)
INSERT [dbo].[tblNotifications] ([notificationId], [postId], [commentId], [notiUserId], [fromUserId], [typeOfNoti], [contentNoti], [createdAt], [updatedAt], [isDeleted]) VALUES (30, 143, 25, N'long@gmail.com', N'tin@gmail.com', N'Comment', N'Tin commented on your post', 1601385966183, 1601386044636, 1)
INSERT [dbo].[tblNotifications] ([notificationId], [postId], [commentId], [notiUserId], [fromUserId], [typeOfNoti], [contentNoti], [createdAt], [updatedAt], [isDeleted]) VALUES (31, 143, NULL, N'long@gmail.com', N'tin@gmail.com', N'Emotion', N'Tin liked your post', 1601385969821, 1601386044636, 1)
SET IDENTITY_INSERT [dbo].[tblNotifications] OFF
SET IDENTITY_INSERT [dbo].[tblPosts] ON 

INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (1, N'tin@gmail.com', N'', N'Hello everybody. 
Yo yo yo yo!', NULL, 0, 1600519923570)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (2, N'tin@gmail.com', N'', N'Yo yo yo 
Hello My friend and everybody!', NULL, 1, 1600520383555)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (3, N'tin@gmail.com', N'', N'ABc
', NULL, 1, 1600522363070)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (4, N'tin@gmail.com', N'', N'Abc', NULL, 0, 1600522371237)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (5, N'tin@gmail.com', N'', N'Abc', NULL, 1, 1600522378537)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (6, N'tin@gmail.com', N'', N'Abc
', NULL, 1, 1600522491356)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (7, N'tin@gmail.com', N'', N'aWERHAW', NULL, 1, 1600522510183)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (8, N'tin@gmail.com', N'', N'Hello', NULL, 1, 1600523160224)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (9, N'tin@gmail.com', N'', N'Test New Post :) 
Yo yo hello', NULL, 1, 1600575182893)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (10, N'long@gmail.com', N'', N'Hello', NULL, 0, 1600583242787)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (11, N'tin@gmail.com', N'', N'Hello lolololol :)', NULL, 0, 1600587992254)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (12, N'tin@gmail.com', N'', N'Hello llolololololol', NULL, 1, 1600588005196)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (13, N'tin@gmail.com', N'', N'Helllooowleawoheoirha', NULL, 1, 1600588078137)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (14, N'tin@gmail.com', N'Xin chÃ o', N'Helololololo', NULL, 1, 1600588089980)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (15, N'tin@gmail.com', N'Xin chÃ o', N' Hello xinc hÃ o', NULL, 1, 1600588205333)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (16, N'tin@gmail.com', N'', N'Nthing', NULL, 1, 1600588238467)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (17, N'long@gmail.com', N'', N'Another test! For emotion add', NULL, 0, 1600695417348)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (18, N'tin@gmail.com', N'', N'Hello lololo', NULL, 0, 1600696521713)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (19, N'long@gmail.com', N'', N'Gog og og ogooogo gog go', NULL, 0, 1600696724893)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (20, N'tin@gmail.com', N'', N'ALOOOO', NULL, 1, 1600697025987)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (21, N'tin@gmail.com', N'', N'Aloolololo', NULL, 0, 1600782195356)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (22, N'tin@gmail.com', N'', N'waiorheaohwroa', NULL, 1, 1600782441618)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (23, N'tin@gmail.com', N'', N'awerhaowhorha', NULL, 1, 1600782555008)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (24, N'tin@gmail.com', N'', N'waoerhoawh
', NULL, 1, 1600782619213)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (25, N'long@gmail.com', N'', N'Alo', NULL, 0, 1600871280063)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (26, N'tin@gmail.com', N'', N'Nothing is here!
Gogogoea oea', NULL, 0, 1601038153418)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (27, N'tin@gmail.com', N'', N'Test thử coi có upload được chưa', NULL, 1, 1601051154493)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (28, N'tin@gmail.com', N'', N'Test thử', NULL, 1, 1601096657506)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (29, N'long@gmail.com', N'', N'Testing herer', NULL, 1, 1601121182081)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (30, N'long@gmail.com', N'', N'Thử test notification', NULL, 0, 1601121462724)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (31, N'tin@gmail.com', N'Test thứ 0', N'0: Test', NULL, 0, 1601187415906)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (32, N'tin@gmail.com', N'Test thứ 1', N'1: Test', NULL, 0, 1601187415925)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (33, N'tin@gmail.com', N'Test thứ 2', N'2: Test', NULL, 0, 1601187415932)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (34, N'tin@gmail.com', N'Test thứ 3', N'3: Test', NULL, 0, 1601187415937)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (35, N'tin@gmail.com', N'Test thứ 4', N'4: Test', NULL, 0, 1601187415942)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (36, N'tin@gmail.com', N'Test thứ 5', N'5: Test', NULL, 0, 1601187415947)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (37, N'tin@gmail.com', N'Test thứ 6', N'6: Test', NULL, 0, 1601187415952)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (38, N'tin@gmail.com', N'Test thứ 7', N'7: Test', NULL, 0, 1601187415958)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (39, N'tin@gmail.com', N'Test thứ 8', N'8: Test', NULL, 0, 1601187415962)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (40, N'tin@gmail.com', N'Test thứ 9', N'9: Test', NULL, 0, 1601187415968)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (41, N'tin@gmail.com', N'Test thứ 10', N'10: Test', NULL, 0, 1601187415973)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (42, N'tin@gmail.com', N'Test thứ 11', N'11: Test', NULL, 0, 1601187415977)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (43, N'tin@gmail.com', N'Test thứ 12', N'12: Test', NULL, 0, 1601187415980)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (44, N'tin@gmail.com', N'Test thứ 13', N'13: Test', NULL, 0, 1601187415982)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (45, N'tin@gmail.com', N'Test thứ 14', N'14: Test', NULL, 0, 1601187415984)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (46, N'tin@gmail.com', N'Test thứ 15', N'15: Test', NULL, 0, 1601187415986)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (47, N'tin@gmail.com', N'Test thứ 16', N'16: Test', NULL, 0, 1601187415990)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (48, N'tin@gmail.com', N'Test thứ 17', N'17: Test', NULL, 0, 1601187415992)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (49, N'tin@gmail.com', N'Test thứ 18', N'18: Test', NULL, 0, 1601187415994)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (50, N'tin@gmail.com', N'Test thứ 19', N'19: Test', NULL, 0, 1601187415997)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (51, N'tin@gmail.com', N'Test thứ 20', N'20: Test', NULL, 0, 1601187415999)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (52, N'tin@gmail.com', N'Test thứ 21', N'21: Test', NULL, 0, 1601187416001)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (53, N'tin@gmail.com', N'Test thứ 22', N'22: Test', NULL, 0, 1601187416003)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (54, N'tin@gmail.com', N'Test thứ 23', N'23: Test', NULL, 0, 1601187416005)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (55, N'tin@gmail.com', N'Test thứ 24', N'24: Test', NULL, 0, 1601187416007)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (56, N'tin@gmail.com', N'Test thứ 25', N'25: Test', NULL, 0, 1601187416009)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (57, N'tin@gmail.com', N'Test thứ 26', N'26: Test', NULL, 0, 1601187416011)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (58, N'tin@gmail.com', N'Test thứ 27', N'27: Test', NULL, 0, 1601187416013)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (59, N'tin@gmail.com', N'Test thứ 28', N'28: Test', NULL, 0, 1601187416015)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (60, N'tin@gmail.com', N'Test thứ 29', N'29: Test', NULL, 0, 1601187416017)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (61, N'tin@gmail.com', N'Test thứ 0', N'0: Test', NULL, 0, 1601187420700)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (62, N'tin@gmail.com', N'Test thứ 1', N'1: Test', NULL, 0, 1601187420705)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (63, N'tin@gmail.com', N'Test thứ 2', N'2: Test', NULL, 0, 1601187420708)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (64, N'tin@gmail.com', N'Test thứ 3', N'3: Test', NULL, 0, 1601187420712)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (65, N'tin@gmail.com', N'Test thứ 4', N'4: Test', NULL, 0, 1601187420715)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (66, N'tin@gmail.com', N'Test thứ 5', N'5: Test', NULL, 0, 1601187420719)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (67, N'tin@gmail.com', N'Test thứ 6', N'6: Test', NULL, 0, 1601187420722)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (68, N'tin@gmail.com', N'Test thứ 7', N'7: Test', NULL, 0, 1601187420725)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (69, N'tin@gmail.com', N'Test thứ 8', N'8: Test', NULL, 0, 1601187420730)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (70, N'tin@gmail.com', N'Test thứ 9', N'9: Test', NULL, 0, 1601187420733)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (71, N'tin@gmail.com', N'Test thứ 10', N'10: Test', NULL, 0, 1601187420737)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (72, N'tin@gmail.com', N'Test thứ 11', N'11: Test', NULL, 0, 1601187420740)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (73, N'tin@gmail.com', N'Test thứ 12', N'12: Test', NULL, 0, 1601187420744)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (74, N'tin@gmail.com', N'Test thứ 13', N'13: Test', NULL, 0, 1601187420747)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (75, N'tin@gmail.com', N'Test thứ 14', N'14: Test', NULL, 0, 1601187420751)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (76, N'tin@gmail.com', N'Test thứ 15', N'15: Test', NULL, 0, 1601187420754)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (77, N'tin@gmail.com', N'Test thứ 16', N'16: Test', NULL, 0, 1601187420758)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (78, N'tin@gmail.com', N'Test thứ 17', N'17: Test', NULL, 0, 1601187420762)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (79, N'tin@gmail.com', N'Test thứ 18', N'18: Test', NULL, 0, 1601187420766)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (80, N'tin@gmail.com', N'Test thứ 19', N'19: Test', NULL, 0, 1601187420770)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (81, N'tin@gmail.com', N'Test thứ 20', N'20: Test', NULL, 0, 1601187420773)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (82, N'tin@gmail.com', N'Test thứ 21', N'21: Test', NULL, 0, 1601187420777)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (83, N'tin@gmail.com', N'Test thứ 22', N'22: Test', NULL, 0, 1601187420781)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (84, N'tin@gmail.com', N'Test thứ 23', N'23: Test', NULL, 0, 1601187420784)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (85, N'tin@gmail.com', N'Test thứ 24', N'24: Test', NULL, 0, 1601187420788)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (86, N'tin@gmail.com', N'Test thứ 25', N'25: Test', NULL, 0, 1601187420792)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (87, N'tin@gmail.com', N'Test thứ 26', N'26: Test', NULL, 0, 1601187420794)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (88, N'tin@gmail.com', N'Test thứ 27', N'27: Test', NULL, 0, 1601187420798)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (89, N'tin@gmail.com', N'Test thứ 28', N'28: Test', NULL, 0, 1601187420802)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (90, N'tin@gmail.com', N'Test thứ 29', N'29: Test', NULL, 0, 1601187420808)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (91, N'tin@gmail.com', N'Test thứ 0', N'0: Test', NULL, 0, 1601187429735)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (92, N'tin@gmail.com', N'Test thứ 1', N'1: Test', NULL, 0, 1601187429739)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (93, N'tin@gmail.com', N'Test thứ 2', N'2: Test', NULL, 0, 1601187429742)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (94, N'tin@gmail.com', N'Test thứ 3', N'3: Test', NULL, 0, 1601187429745)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (95, N'tin@gmail.com', N'Test thứ 4', N'4: Test', NULL, 0, 1601187429748)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (96, N'tin@gmail.com', N'Test thứ 5', N'5: Test', NULL, 0, 1601187429751)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (97, N'tin@gmail.com', N'Test thứ 6', N'6: Test', NULL, 0, 1601187429754)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (98, N'tin@gmail.com', N'Test thứ 7', N'7: Test', NULL, 0, 1601187429757)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (99, N'tin@gmail.com', N'Test thứ 8', N'8: Test', NULL, 0, 1601187429761)
GO
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (100, N'tin@gmail.com', N'Test thứ 9', N'9: Test', NULL, 0, 1601187429766)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (101, N'tin@gmail.com', N'Test thứ 10', N'10: Test', NULL, 0, 1601187429770)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (102, N'tin@gmail.com', N'Test thứ 11', N'11: Test', NULL, 0, 1601187429775)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (103, N'tin@gmail.com', N'Test thứ 12', N'12: Test', NULL, 0, 1601187429780)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (104, N'tin@gmail.com', N'Test thứ 13', N'13: Test', NULL, 0, 1601187429785)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (105, N'tin@gmail.com', N'Test thứ 14', N'14: Test', NULL, 0, 1601187429790)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (106, N'tin@gmail.com', N'Test thứ 15', N'15: Test', NULL, 0, 1601187429795)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (107, N'tin@gmail.com', N'Test thứ 16', N'16: Test', NULL, 0, 1601187429801)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (108, N'tin@gmail.com', N'Test thứ 17', N'17: Test', NULL, 0, 1601187429805)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (109, N'tin@gmail.com', N'Test thứ 18', N'18: Test', NULL, 0, 1601187429811)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (110, N'tin@gmail.com', N'Test thứ 19', N'19: Test', NULL, 0, 1601187429816)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (111, N'tin@gmail.com', N'Test thứ 20', N'20: Test', NULL, 0, 1601187429820)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (112, N'tin@gmail.com', N'Test thứ 21', N'21: Test', NULL, 0, 1601187429825)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (113, N'tin@gmail.com', N'Test thứ 22', N'22: Test', NULL, 0, 1601187429830)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (114, N'tin@gmail.com', N'Test thứ 23', N'23: Test', NULL, 0, 1601187429835)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (115, N'tin@gmail.com', N'Test thứ 24', N'24: Test', NULL, 0, 1601187429841)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (116, N'tin@gmail.com', N'Test thứ 25', N'25: Test', NULL, 0, 1601187429845)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (117, N'tin@gmail.com', N'Test thứ 26', N'26: Test', NULL, 0, 1601187429852)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (118, N'tin@gmail.com', N'Test thứ 27', N'27: Test', NULL, 0, 1601187429857)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (119, N'tin@gmail.com', N'Test thứ 28', N'28: Test', NULL, 1, 1601187429862)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (120, N'tin@gmail.com', N'Test thứ 29', N'29: Test', NULL, 1, 1601187429868)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (121, N'tin@gmail.com', N'', N'dalew', NULL, 0, 1601213033382)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (122, N'tin@gmail.com', N'', N'awerawera', NULL, 0, 1601213204376)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (123, N'tin@gmail.com', N'', N'alehroiaw', NULL, 0, 1601214141440)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (124, N'tin@gmail.com', N'', N'haoewhoia', N'1601220961861_Picture1.jpg', 1, 1601220963581)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (125, N'tin@gmail.com', N'', N'Một ngày code lab web sẽ thật tuyệt vời
cho tới khi gặp cặp ae 404 500', N'1601222218369_58486a72849cf46a2a931338.png', 1, 1601222221807)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (126, N'tin@gmail.com', N'', N'Trời xanh xanh ngát xanh', N'1601223187624_Picture1.jpg', 1, 1601223190706)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (127, N'tin@gmail.com', N'', N'Xanh', N'1601223332746_', 1, 1601223332761)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (128, N'tin@gmail.com', N'', N'Trời hơi xanh', N'1601223349419_', 1, 1601223349428)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (129, N'tin@gmail.com', N'', N'reawe', N'1601223463172_', 1, 1601223463182)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (130, N'tin@gmail.com', N'', N'ahfoweahrj', N'1601223570952_', 1, 1601223570963)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (131, N'tin@gmail.com', N'', N'earwearw', N'1601223582037_58486a72849cf46a2a931338.png', 1, 1601223584631)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (132, N'tin@gmail.com', N'', N'aweawe', N'1601223587737_', 1, 1601223587739)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (133, N'tin@gmail.com', N'', N'aweawe', N'1601223587988_', 1, 1601223587991)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (134, N'tin@gmail.com', N'', N'earwera', N'1601223607982_', 1, 1601223608150)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (135, N'tin@gmail.com', N'', N'erawea', N'1601223759632_', 1, 1601223759642)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (136, N'tin@gmail.com', N'', N'aeworoaioehr', NULL, 1, 1601223908204)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (137, N'tin@gmail.com', N'', N'enraweor', N'1601223915321_Picture1.jpg', 1, 1601223916726)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (138, N'tin@gmail.com', N'', N'Tesating
', NULL, 1, 1601225193111)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (139, N'long@gmail.com', N'', N'aohewoirahow', NULL, 1, 1601225307552)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (140, N'tin@gmail.com', N'waelal', N'awerhaowher
', NULL, 1, 1601273701613)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (141, N'damdongtin@gmail.com', N'ahwreohao', N'weoahorhao', N'1601365349349_58486a72849cf46a2a931338.png', 1, 1601365352257)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (142, N'tin@gmail.com', N'werhoah', N'hoihioreh', NULL, 1, 1601374294676)
INSERT [dbo].[tblPosts] ([postId], [userPostId], [postTitle], [postContent], [image], [isDeleted], [createdAt]) VALUES (143, N'long@gmail.com', N'Test lần cuối', N'Test lại lần cuối', N'1601385931057_58486a72849cf46a2a931338.png', 1, 1601385935722)
SET IDENTITY_INSERT [dbo].[tblPosts] OFF
SET IDENTITY_INSERT [dbo].[tblStatus] ON 

INSERT [dbo].[tblStatus] ([statusId], [name]) VALUES (1, N'New')
INSERT [dbo].[tblStatus] ([statusId], [name]) VALUES (2, N'Active')
SET IDENTITY_INSERT [dbo].[tblStatus] OFF
INSERT [dbo].[tblUsers] ([email], [name], [password], [role], [statusId], [activationCode]) VALUES (N'damdongtin@gmail.com', N'Tín', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'Member', 2, 870616)
INSERT [dbo].[tblUsers] ([email], [name], [password], [role], [statusId], [activationCode]) VALUES (N'long@gmail.com', N'Long', N'8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', N'Member', 2, 123456)
INSERT [dbo].[tblUsers] ([email], [name], [password], [role], [statusId], [activationCode]) VALUES (N'tin@gmail.com', N'Tin', N'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', N'Member', 2, 999999)
ALTER TABLE [dbo].[tblComments]  WITH CHECK ADD  CONSTRAINT [FK_tblComments_tblPosts] FOREIGN KEY([postId])
REFERENCES [dbo].[tblPosts] ([postId])
GO
ALTER TABLE [dbo].[tblComments] CHECK CONSTRAINT [FK_tblComments_tblPosts]
GO
ALTER TABLE [dbo].[tblComments]  WITH CHECK ADD  CONSTRAINT [FK_tblComments_tblUsers] FOREIGN KEY([commentUser])
REFERENCES [dbo].[tblUsers] ([email])
GO
ALTER TABLE [dbo].[tblComments] CHECK CONSTRAINT [FK_tblComments_tblUsers]
GO
ALTER TABLE [dbo].[tblEmotions]  WITH CHECK ADD  CONSTRAINT [FK_tblEmotions_tblAccounts] FOREIGN KEY([emotionUserId])
REFERENCES [dbo].[tblUsers] ([email])
GO
ALTER TABLE [dbo].[tblEmotions] CHECK CONSTRAINT [FK_tblEmotions_tblAccounts]
GO
ALTER TABLE [dbo].[tblEmotions]  WITH CHECK ADD  CONSTRAINT [FK_tblEmotions_tblPosts] FOREIGN KEY([postId])
REFERENCES [dbo].[tblPosts] ([postId])
GO
ALTER TABLE [dbo].[tblEmotions] CHECK CONSTRAINT [FK_tblEmotions_tblPosts]
GO
ALTER TABLE [dbo].[tblNotifications]  WITH CHECK ADD  CONSTRAINT [FK_tblNotifications_tblComments] FOREIGN KEY([commentId])
REFERENCES [dbo].[tblComments] ([commentId])
GO
ALTER TABLE [dbo].[tblNotifications] CHECK CONSTRAINT [FK_tblNotifications_tblComments]
GO
ALTER TABLE [dbo].[tblNotifications]  WITH CHECK ADD  CONSTRAINT [FK_tblNotifications_tblPosts] FOREIGN KEY([postId])
REFERENCES [dbo].[tblPosts] ([postId])
GO
ALTER TABLE [dbo].[tblNotifications] CHECK CONSTRAINT [FK_tblNotifications_tblPosts]
GO
ALTER TABLE [dbo].[tblNotifications]  WITH CHECK ADD  CONSTRAINT [FK_tblNotifications_tblUsers] FOREIGN KEY([notiUserId])
REFERENCES [dbo].[tblUsers] ([email])
GO
ALTER TABLE [dbo].[tblNotifications] CHECK CONSTRAINT [FK_tblNotifications_tblUsers]
GO
ALTER TABLE [dbo].[tblNotifications]  WITH CHECK ADD  CONSTRAINT [FK_tblNotifications_tblUsers1] FOREIGN KEY([fromUserId])
REFERENCES [dbo].[tblUsers] ([email])
GO
ALTER TABLE [dbo].[tblNotifications] CHECK CONSTRAINT [FK_tblNotifications_tblUsers1]
GO
ALTER TABLE [dbo].[tblPosts]  WITH CHECK ADD  CONSTRAINT [FK_tblPosts_tblAccounts] FOREIGN KEY([userPostId])
REFERENCES [dbo].[tblUsers] ([email])
GO
ALTER TABLE [dbo].[tblPosts] CHECK CONSTRAINT [FK_tblPosts_tblAccounts]
GO
ALTER TABLE [dbo].[tblUsers]  WITH CHECK ADD  CONSTRAINT [FK_tblAccounts_tblStatus] FOREIGN KEY([statusId])
REFERENCES [dbo].[tblStatus] ([statusId])
GO
ALTER TABLE [dbo].[tblUsers] CHECK CONSTRAINT [FK_tblAccounts_tblStatus]
GO
USE [master]
GO
ALTER DATABASE [SocialNetwork] SET  READ_WRITE 
GO
