package parser;

import parser.replies.LinkParserReplies;

public class LinkProcessingChain {
    /**
     * This variable stores the linkProcessorChain.
     */
    LinkProcessor linkProcessorChain;

    public LinkProcessingChain() {
        buildLinkProcessorChain();
    }

    private void buildLinkProcessorChain() {
        linkProcessorChain = new GitHubLinkParser(new StackOverflowLinkParser(new NullLinkParser(null)));
    }

    public LinkParserReplies process(String url) {
        return linkProcessorChain.process(url);
    }
}
