package org.example.lld.stackoverflow;

public class StackOverflow {
}

abstract class Post {
    private int id;
    private String title;
    private String content;
    private User author;
    private int upvotes;
    private int downvotes;

    public Post() {
    }

    public void upvote() {
        upvotes++;
    }

    public void downvote() {
        downvotes++;
    }
}

class Question extends Post {
    public Question() {
        super();
    }
}

class Answer extends Post {
    public Answer() {
        super();
    }
}

class Comment extends Post {
    public Comment() {
        super();
    }
}

class User {
    private PostFactory postFactory;
    public User() {
    }

    public void setPostFactory(PostFactory postFactory) {
        this.postFactory = postFactory;
    }

    public Post createPost() {
        return postFactory.create();
    }

    public void upvote(Post post) {
        post.upvote();
    }

    public void downvote(Post post) {
        post.downvote();
    }

}

interface PostFactory {
    Post create();
}

class QuestionFactory implements PostFactory {
    private static QuestionFactory instance;
    private QuestionFactory() {
    }

    public static QuestionFactory getInstance() {
        if (instance == null) instance = new QuestionFactory();
        return instance;
    }

    @Override
    public Post create() {
        return new Question();
    }
}

class AnswerFactory implements PostFactory {
    private static AnswerFactory instance;
    private AnswerFactory() {
    }

    public static AnswerFactory getInstance() {
        if (instance == null) instance = new AnswerFactory();
        return instance;
    }

    @Override
    public Post create() {
        return new Answer();
    }
}

class CommentFactory implements PostFactory  {
    private static CommentFactory instance;
    private CommentFactory() {
    }

    public static CommentFactory getInstance() {
        if (instance == null) instance = new CommentFactory();
        return instance;
    }

    public Post create() {
        return new Comment();
    }
}

class UserFactory {
    public User createUser() {
        return new User();
    }
}

class Client {
    public static void main(String[] args) {
        UserFactory userFactory = new UserFactory();
        User user = userFactory.createUser();
        user.setPostFactory(QuestionFactory.getInstance());
        Post question = user.createPost();
        user.setPostFactory(AnswerFactory.getInstance());
        Post answer = user.createPost();
        user.setPostFactory(CommentFactory.getInstance());
        Post comment = user.createPost();
        user.upvote(question);
        user.upvote(answer);
        user.upvote(comment);
        user.downvote(question);
        user.downvote(answer);
        user.downvote(comment);
    }
}