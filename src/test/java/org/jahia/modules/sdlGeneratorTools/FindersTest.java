package org.jahia.modules.sdlGeneratorTools;


import org.testng.Assert;
import org.testng.annotations.Test;

public class FindersTest extends GeneratorToolsRepository{

    @Test(alwaysRun = true)
    public void findersListTest() {

        goToGeneratorTools();
        clickClear();

        addType("jnt:news", "news");

        addProperty("jcr:title", "title");
        addProperty("desc", "description");
        addProperty("date", "created");

        clickNext();

        verifyElementClickable(findByXpath(xpathAddNewFinder));
        clickOn(findByXpath(xpathAddNewFinder));

        checkAddFinderDialog();

        clickOn(findByXpath("//input[@id='finder-name']/parent::div/div"));

        Assert.assertEquals(findElementsByXpath("//ul[@role='listbox']/li").size(), 8,
                "Number of finders in finders list is incorrect");
    }

    @Test(alwaysRun = true)
    public void addFindersTest() {
        goToGeneratorTools();
        clickClear();

        addType("jnt:news", "NewsEntry");

        addProperty("jcr:title", "title");

        clickNext();

        addFinder("all", "news");

        Assert.assertTrue(findByXpath("//div[@class='ace_content'][contains(.,'type NewsEntry @mapping(node: \"jnt:news\") " +
                "{    metadata: Metadata     title: String @mapping(property: \"jcr:title\")}extend type Query {    allNews: [NewsEntry]}')]").isDisplayed(),
                "Failed to create schema");
    }

    @Test(alwaysRun = true)
    public void editFindersTest() {
        goToGeneratorTools();
        clickClear();

        addType("jnt:news", "NewsEntry");

        addProperty("jcr:title", "title");

        clickNext();

        addFinder("all", "news");

        verifyElementClickable(findByXpath("//span[contains(.,'allNews')]/parent::div/parent::div[@role='button']"));
        clickOn(findByXpath("//span[contains(.,'allNews')]/parent::div/parent::div[@role='button']"));

        checkEditFinderDialog();

        clickOn(findByXpath("//input[@id='finder-name']/parent::div/div"));
        verifyElementClickable(findByXpath("//li[@data-value='byTitle']"));

        clickOn(findByXpath("//li[@data-value='byTitle']"));
        Assert.assertEquals(findByXpath("//p[contains(@class,'FinderPreviewComp')]/span").getText(), "ByTitle",
                "finder preview failed to reflect the newly chosen finder");

        findByXpath("//input[@id='propertyName']").clear();
        findByXpath("//input[@id='propertyName']").sendKeys("myNews");

        Assert.assertEquals(findByXpath("//p[contains(@class,'FinderPreviewComp')]/em").getText(), "myNews",
                "finder preview failed to reflect the new custom name");

        clickUpdate();

        Assert.assertTrue(findByXpath("//div[@class='ace_content'][contains(.,'type NewsEntry @mapping(node: \"jnt:news\") {    metadata: Metadata     title: String @mapping(property: \"jcr:title\")}extend type Query {    myNewsByTitle: [NewsEntry]}')]").isDisplayed(),
                "the added finder did not appear in GraphQL Schema view");
    }

    private void checkEditFinderDialog() {
        Assert.assertTrue(findByXpath("//h2[contains(.,'Edit a finder')]").isDisplayed(),
                "Edit finder dialog failed to open");
        Assert.assertTrue(findByXpath("//p[contains(.,'Select a finder')]").isDisplayed(),
                "Edit finder dialog failed to open");
        Assert.assertTrue(findByXpath("//input[@id='finder-name']/parent::div").isDisplayed(),
                "Edit finder dialog failed to open");
        Assert.assertTrue(findByXpath("//p[contains(.,'Custom name')]").isDisplayed(),
                "Edit finder dialog failed to open");
        Assert.assertTrue(findByXpath("//input[@id='propertyName']").isDisplayed(),
                "Edit finder dialog failed to open");
        Assert.assertTrue(findByXpath("//span[text()='Delete']/parent::button").isDisplayed(),
                "Edit finder dialog failed to open");
        Assert.assertTrue(findByXpath("//span[text()='Cancel']/parent::button").isDisplayed(),
                "Edit finder dialog failed to open");
        Assert.assertTrue(findByXpath("//span[text()='Update']/parent::button").isDisplayed(),
                "Edit finder dialog failed to open");
    }


    //findByXpath("//p[contains(.,'Node type')]/parent::ul/li[3]");  ---start of list of created types
}
