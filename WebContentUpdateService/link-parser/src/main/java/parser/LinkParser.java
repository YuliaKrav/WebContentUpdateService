package parser;

public sealed interface LinkParser
    permits GitHubLinkParser, StackOverflowLinkParser, NullLinkParser {
}
