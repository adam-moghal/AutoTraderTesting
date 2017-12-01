import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Homepage {

    @FindBy(css = "#postcode")
    private WebElement postcode;

    @FindBy(css = "#searchVehicles > fieldset > fieldset > label:nth-child(2)")
    private WebElement usedCarCheckbox;
    @FindBy(css = "#searchVehicles > fieldset > fieldset > label:nth-child(3)")
    private WebElement nearlyNewCheckbox;
    @FindBy(css = "#js-search-button")
    private WebElement searchButton;
    @FindBy(css = "body > main > section.search-page__left > div.search-form > form > div > div > h1")
    private WebElement searchResult;
    @FindBy(css = "#js-header-nav > ul > li.header__nav-listing.header__nav-my-at > div > a")
    private WebElement signInButton;

    @FindBy(css = "#js-header-nav > ul > li:nth-child(3) > a")
    private WebElement carReviewHeader;

    @FindBy(css = "#car-reviews > li.header__sub-nav-listing.header__sub-nav-listing--2 > a")
    private WebElement carReviewOption;



    public void setPostcode(String pCode) {

        postcode.sendKeys(pCode);

    }

    public void setNewCarsOnly() {
        usedCarCheckbox.click();
        nearlyNewCheckbox.click();
    }

    public void searchCar() {
        searchButton.click();
    }

    public String getSearchResult() {

        return searchResult.getText();
    }

    public void signinButton() {
        signInButton.click();
    }
    public void carOwnerReview() {
        carReviewHeader.click();
        carReviewOption.click();

    }
}
