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
        return postFactory.createPost();
    }

    public Post createQuestion() {
        return QuestionFactory.getInstance().createPost();
    }

    public Post createAnswer() {
        return AnswerFactory.getInstance().createPost();
    }

    public Post createComment() {
        return CommentFactory.getInstance().createPost();
    }

    public void upvote(Post post) {
        post.upvote();
    }

    public void downvote(Post post) {
        post.downvote();
    }

}

abstract class PostFactory {
    public abstract Post createPost();
}

class QuestionFactory extends PostFactory {
    private static QuestionFactory instance;
    private QuestionFactory() {
    }

    public static QuestionFactory getInstance() {
        if (instance == null) instance = new QuestionFactory();
        return instance;
    }

    @Override
    public Post createPost() {
        return new Question();
    }
}

class AnswerFactory extends PostFactory {
    private static AnswerFactory instance;
    private AnswerFactory() {
    }

    public static AnswerFactory getInstance() {
        if (instance == null) instance = new AnswerFactory();
        return instance;
    }

    @Override
    public Post createPost() {
        return new Answer();
    }
}

class CommentFactory extends PostFactory  {
    private static CommentFactory instance;
    private CommentFactory() {
    }

    public static CommentFactory getInstance() {
        if (instance == null) instance = new CommentFactory();
        return instance;
    }

    public Post createPost() {
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
        question = user.createQuestion();
        user.setPostFactory(AnswerFactory.getInstance());
        Post answer = user.createPost();
        answer = user.createAnswer();
        user.setPostFactory(CommentFactory.getInstance());
        Post comment = user.createPost();
        comment = user.createComment();
        user.upvote(question);
        user.upvote(answer);
        user.upvote(comment);
        user.downvote(question);
        user.downvote(answer);
        user.downvote(comment);
    }
}