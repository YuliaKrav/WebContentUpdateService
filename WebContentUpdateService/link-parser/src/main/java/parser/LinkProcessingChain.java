package parser;

public class LinkProcessingChain {
    LinkProcessor linkProcessorChain;

    public LinkProcessingChain() {
        buildLinkProcessorChain();
    }

    private void buildLinkProcessorChain() {
        linkProcessorChain = new GitHubLinkParser(new StackOverflowLinkParser(new NullLinkParser(null)));
    }

    public void process(String url) {
        linkProcessorChain.process(url);
    }
}
