package qube.qoan.gui.components.workspace.finance.parser;

import info.bliki.wiki.model.WikiModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by rainbird on 12/21/15.
 */
public class WikiArticleRipper extends WikiModel {

    private List<String> linkList;

    /**
     * this class is mainly for rendering the wiki-articles
     * on screen- we need it mainly for being able to display
     * images and rendering the tables in vaadin style
     * so that we can actually drag-n-drop choices from them
     */
    public WikiArticleRipper(String imageBaseURL, String linkBaseURL) {
        super(imageBaseURL, linkBaseURL);
        linkList = new ArrayList<String>();
    }

    @Override
    public void addLink(String topicName) {
        linkList.add(topicName);
        super.addLink(topicName);
    }

    @Override
    public Set<String> getLinks() {
        return super.getLinks();
    }

    @Override
    public void appendInternalLink(String topic, String hashSection, String topicDescription, String cssClass, boolean parseRecursive) {
        super.appendInternalLink(topic, hashSection, topicDescription, cssClass, parseRecursive);
    }

    @Override
    public void parseInternalImageLink(String imageNamespace, String rawImageLink) {
        super.parseInternalImageLink(imageNamespace, rawImageLink);
    }

    public List<String> getLinkList() {
        return linkList;
    }

}
