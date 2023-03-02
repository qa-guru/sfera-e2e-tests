package zone.sfera.tests.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public abstract class BaseScreen<T> {

    protected SelenideElement elementBySferaId(String idClasspath) {
        return $(AppiumBy.id("zone.sfera:id/" + idClasspath));
    }
    
    protected ElementsCollection collectionBySferaId(String idClasspath) {
        return $$(AppiumBy.id("zone.sfera:id/" + idClasspath));
    }

    protected SelenideElement elementByAndroidId(String idClasspath) {
        return $(AppiumBy.id("android:id/" + idClasspath));
    }

    protected ElementsCollection collectionByAndroidId(String idClasspath) {
        return $$(AppiumBy.id("android:id/" + idClasspath));
    }

}
