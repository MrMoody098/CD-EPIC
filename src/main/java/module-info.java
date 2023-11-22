module com.cd.quizwhiz {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.web;

    requires jdk.xml.dom;
    requires thymeleaf;
    requires java.sql; // dependency of thymeleaf not correctly imported otherwise
    requires org.slf4j;
    requires com.opencsv;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    exports com.cd.quizwhiz;
    exports com.cd.quizwhiz.questions;
    exports com.cd.quizwhiz.userstuff;
    exports com.cd.quizwhiz.stats;
}
