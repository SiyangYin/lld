package org.example.lld.stackoverflow;

import java.util.ArrayList;
import java.util.List;

public class StackOverflow {
}

abstract class Post {
    private int id;
    private String title;
    private String content;
    private User author;
    private int upvotes;
    private int downvotes;

    public Post(int id, String title, String content, User author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.upvotes = 0;
        this.downvotes = 0;
    }

    public void upvote() {
        upvotes++;
    }

    public void downvote() {
        downvotes++;
    }
}

class Question extends Post {
    private String tags;

    public Question(int id, String title, String content, User author, String tags) {
        super(id, title, content, author);
        this.tags = tags;
    }

    public String getTags() {
        return tags;
    }
}

class Answer extends Post {
    private int questionId;

    public Answer(int id, String title, String content, User author, int questionId) {
        super(id, title, content, author);
        this.questionId = questionId;
    }

    public int getQuestionId() {
        return questionId;
    }
}

class Comment {
    private int id;
    private String content;
    private User author;
    private int postId;

    public Comment(int id, String content, User author, int postId) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.postId = postId;
    }

    public int getPostId() {
        return postId;
    }
}

class User {
    private int id;
    private String username;
    private String email;

    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}

class Tag {
    private String name;
    private String description;

    public Tag(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

class StackOverflowPlatform {
    private List<Question> questions;
    private List<Answer> answers;
    private List<Comment> comments;
    private List<User> users;
    private List<Tag> tags;

    public StackOverflowPlatform() {
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.users = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    // Methods to add, retrieve, and manage posts, users, comments, and tags would go here
}

abstract class PostFactory {
    public abstract Post createPost(int id, String title, String content, User author);
}

class QuestionFactory extends PostFactory {
    private String tags;

    public QuestionFactory(String tags) {
        this.tags = tags;
    }

    @Override
    public Post createPost(int id, String title, String content, User author) {
        return new Question(id, title, content, author, tags);
    }
}

class AnswerFactory extends PostFactory {
    private int questionId;

    public AnswerFactory(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public Post createPost(int id, String title, String content, User author) {
        return new Answer(id, title, content, author, questionId);
    }
}

class CommentFactory {
    private int postId;

    public CommentFactory(int postId) {
        this.postId = postId;
    }

    public Comment createComment(int id, String content, User author) {
        return new Comment(id, content, author, postId);
    }
}

class UserFactory {
    public User createUser(int id, String username, String email) {
        return new User(id, username, email);
    }
}

class TagFactory {
    public Tag createTag(String name, String description) {
        return new Tag(name, description);
    }
}

class StackOverflowFactory {
    private StackOverflowPlatform platform;

    public StackOverflowFactory() {
        this.platform = new StackOverflowPlatform();
    }

    public PostFactory getPostFactory(String type, String tagsOrQuestionId) {
        if ("question".equalsIgnoreCase(type)) {
            return new QuestionFactory(tagsOrQuestionId);
        } else if ("answer".equalsIgnoreCase(type)) {
            return new AnswerFactory(Integer.parseInt(tagsOrQuestionId));
        }
        return null;
    }

    public CommentFactory getCommentFactory(int postId) {
        return new CommentFactory(postId);
    }

    public UserFactory getUserFactory() {
        return new UserFactory();
    }

    public TagFactory getTagFactory() {
        return new TagFactory();
    }
}

