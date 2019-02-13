package DatabaseApp.viewExtended;

import DatabaseApp.models.Warehouse;
import DatabaseApp.models.Worker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditWorkerLayoutController {

    @FXML private TextField indexField;

    @FXML private TextField nameField;

    @FXML private TextField surnameField;

    @FXML private TextField addressField;

    @FXML private TextField telNumField;

    @FXML private TextField mailField;

    @FXML private TextField PESELField;

    @FXML private ChoiceBox<Warehouse> warehouseIndexBox;

    private Stage dialogStage;

    private Worker worker;

    private boolean acceptClicked;

    @FXML public void initialize() {

    }

    @FXML private void handleAccept() {
        if(isInputValid()) {
            worker.setIndex(indexField.getText());
            worker.setName(nameField.getText());
            worker.setSurname(surnameField.getText());
            worker.setAddress(addressField.getText());
            worker.setTelNum(telNumField.getText());
            worker.setMail(mailField.getText());
            worker.setPESEL(PESELField.getText());

            ObservableList<Warehouse> warehouses = warehouseIndexBox.getItems();
            Warehouse newWarehouse = warehouseIndexBox.getSelectionModel().getSelectedItem();
            if(worker.getWarehouseIndex() == newWarehouse.getIndex()) {
                worker.setWarehouseIndex(newWarehouse.getIndex());
            } else {
                for (Warehouse oldWarehouse : warehouses) {
                    if(worker.getWarehouseIndex() == oldWarehouse.getIndex()) {
                        oldWarehouse.deleteWorker(worker);
                    }
                }
                worker.setWarehouseIndex(newWarehouse.getIndex());
                newWarehouse.addWorker(worker);
            }

            this.acceptClicked = true;
            dialogStage.close();
        }
    }

    @FXML private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String content = "";
        if(indexField.getText() == null || indexField.getText().length() == 0) {
            content += "No valid index\n";
        }
        if(nameField.getText() == null || nameField.getText().length() == 0) {
            content += "No valid name\n";
        }
        if(surnameField.getText() == null || surnameField.getText().length() == 0) {
            content += "No valid surname\n";
        }
        if(addressField.getText() == null || addressField.getText().length() == 0) {
            content += "No valid address";
        }
        if(telNumField.getText() == null || telNumField.getText().length() == 0) {
            content += "No valid telephone number\n";
        }
        if(mailField.getText() == null || mailField.getText().length() == 0) {
            content += "No valid email address\n";
        }
        if(PESELField.getText() == null || PESELField.getText().length() == 0) {
            content += "No valid PESEL\n";
        }
        if(warehouseIndexBox.getSelectionModel().getSelectedItem() == null) {
            content += "No warehouse index selected\n";
        }

        if(content.length() == 0) {
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields:");
            alert.setContentText(content);
            alert.showAndWait();
            return false;
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setWorker(Worker worker, ObservableList<Warehouse> warehouses) {
        this.worker = worker;

        this.indexField.setText(worker.getIndexString());
        this.nameField.setText(worker.getName());
        this.surnameField.setText(worker.getSurname());
        this.addressField.setText(worker.getAddress());
        this.telNumField.setText(worker.getTelNum());
        this.mailField.setText(worker.getMail());
        this.PESELField.setText(worker.getPESEL());

        this.warehouseIndexBox.setItems(warehouses);
        for (Warehouse warehouse : warehouses) {
            if(warehouse.getIndex() == worker.getWarehouseIndex()) {
                this.warehouseIndexBox.setValue(warehouse);
            }
        }
    }

    public boolean isAcceptClicked() {
        return this.acceptClicked;
    }
}
