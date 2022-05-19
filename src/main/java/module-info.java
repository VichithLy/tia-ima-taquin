module com.tia.ima.taquin.imataquin {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tia to javafx.fxml;
    exports com.tia;
}