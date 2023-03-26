package parser.replies;

public record GitHubReply(String url, String user, String repository) implements LinkParserReplies {
}
