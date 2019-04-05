package org.jahia.modules.sdlGeneratorTools;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PropertiesTest extends GeneratorToolsRepository {

    @Test(dataProvider = "propertiesList", alwaysRun = true)
    public void checkPropertiesListTest(String nodeType, String typeName, int listSize) {

        goToGeneratorTools();
        clickClear();

        addType(nodeType, typeName);

        clickOn(findByXpath(xpathAddNewProperty));

        Assert.assertTrue(findByXpath(xpathSelectMapProperty).isDisplayed(), "Failed to open property dialog box");
        Assert.assertTrue(findByXpath(xpathSelectProperty).isDisplayed(), "Failed to open property dialog box");
        clickOn(findByXpath(xpathSelectProperty));

        checkAddPropertyDialog();

        clickOn(findByXpath("//div[contains(@aria-haspopup,'true')]"));
        waitForElementToStopMoving(findByXpath("//ul[@role='listbox']"));
        //--check properties dropdown list
        Assert.assertEquals(findElementsByXpath("//ul[@role='listbox']/li").size(), listSize, "properties dropdown list is incorrect");
    }

    @Test(alwaysRun = true)
    public void addPropertyTest() {
        goToGeneratorTools();
        clickClear();

        addType("jnt:article", "article");

        addProperty("jcr:description", "description");

        Assert.assertTrue(findByXpath("//span[contains(.,'description')]").isDisplayed(), "Failed to add property");

    }

    @Test(alwaysRun = true)
    public void mapPropertyToType(){
        goToGeneratorTools();
        clickClear();

        addType("jdnt:allStories", "allNews");

        addMapPropertyToType("Asset", "startPage", "page");

        clickAdd();
        Assert.assertTrue(findByXpath("//span[contains(.,'page')]").isDisplayed(), "Failed to add property");

    }

    @Test(alwaysRun = true)
    public void editPropertiesTest(){
        goToGeneratorTools();
        clickClear();
        addType("jnt:news", "newsEntry");

        Assert.assertTrue(findByXpath("//span[contains(@class,'Mui')][contains(.,'metadata')]").isDisplayed(), "Failed to add metadata property by default");

        clickOn(findByXpath(xpathAddNewProperty));

        waitForElementToBeClickable(findByXpath(xpathSelectProperty));
        clickOn(findByXpath(xpathSelectProperty));

        clickOn(findByXpath("//div[contains(@aria-haspopup,'true')]"));

        clickOn(findByXpath("//span[contains(@class,'Mui')][contains(.,'description')]"));
        Assert.assertTrue(findByXpath("//span[contains(.,'description')]/parent::div").isDisplayed(), "Failed to select property type");
        clickAdd();

        Assert.assertTrue(findByXpath("//span[contains(@class,'Mui')][contains(.,'description')]").isDisplayed(), "Failed to add property");
        clickOn(findByXpath("//span[contains(@class,'Mui')][contains(.,'description')]"));

        WebElement propertyInput = findByXpath("//input[@id='propertyName']");
        propertyInput.clear();
        propertyInput.sendKeys("descEdited");

        clickUpdate();

        Assert.assertTrue(waitForElementToBeVisible(findByXpath("//span[contains(@class,'Mui')][contains(.,'descEdited')]")).isDisplayed(), "Failed to edit/update property");


    }


}
