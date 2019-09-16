/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIs;

import Database.DAO;
import Database.DBConnector;
import Objects.InStockSlab;
import Objects.SoldSlab;
import Objects.Species;
import Utilities.FileHandler;
import Utilities.MeasurementsParser;
import Utilities.SampleData;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Computebot
 */
public class SlabTracker{
    
    private TableView inStockSlabsWindow;
    private TableView soldSlabsWindow;
    private TableView speciesWindow;    
    
    private Stage primaryStage;
    private Stage slabTrackerWindow;
    
    private FilteredList<InStockSlab> inStockSlabList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllInStockSlabs()));
    private FilteredList<SoldSlab> soldSlabList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllSoldSlabs()));
    private FilteredList<Species> speciesList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllSpecies()));
    private ArrayList<String> speciesWoodCodeList = new ArrayList<>(DAO.getAllAvailableWoodCodes());        
    private ArrayList<String> dryList = new ArrayList<>(Arrays.asList("None","Yes","No"));        
    private ArrayList<String> featuresList = new ArrayList<>(Arrays.asList("None","Kilned","Curly","Burled","Spalted","Birds Eye","Pecky","Ambrosia","Sinker","Axe Sinker","Dressed"));        
    private ArrayList<String> thicknessFilterList = new ArrayList<>(Arrays.asList("None","0\"-1\"","1\"-2\"","2\"-3\"","3\"-4\"","4\"-5\"","5\"-6\"","6\"-7\"","7\"-8\"","8\"-9\"","9\"-10\"","10\"-11\"","11\"-12\""));        
    private ArrayList<String> widthFilterList = new ArrayList<>(Arrays.asList("None","8\"-10\"","10\"-12\"","12\"-14\"","14\"-16\"","16\"-18\"","18\"-20\"","20\"-22\"","22\"-24\"","24\"-26\"","26\"-28\"","28\"-30\""));        
    private ArrayList<String> lengthFilterList = new ArrayList<>(Arrays.asList("None","2\'-4\'","4\'-6\'","6\'-8\'","8\'-10\'","10\'-12\'","12\'-14\'","14\'-16\'","16\'-18\'","18\'-20\'","20\'-22\'","22\'-24\'","24\'-26\'","26\'-28\'","28\'-30\'","30\'-32\'","32\'-34\'","34\'-36\'","36\'-38\'"));        
    FilteredList<InStockSlab> filteredInStockSlabs;//filled in in the filtering section of code (section starts at line 743)
    FilteredList<SoldSlab> filteredSoldSlabs;//filled in in the filtering section of code (section starts at line 743)
    
    //create tab names for tableviews
    private ArrayList<String> tabNameList = new ArrayList<>(Arrays.asList("In-Stock","Sold","Species","Reports","Utilities"));

    //name columns for In-Stock Slabs tab        
    private ArrayList<String> inStockColumnHeaderText = new ArrayList<>(Arrays.asList("Slab Id","Wood Code","Milled Date","Dry?","Thick(in)","Width(in)","Length(ft)","TtlBdFt","Features","TtlFeat$","WidthCost","Ttl $/BdFt","Ttl Slab $","Area->Sub-Area","Row->Column"));
    private ArrayList<String> inStockColumnIdentifier = new ArrayList<>(Arrays.asList("slabIdFormatted","woodCode","milledDateFormatted","isDry","thicknessInInchesFormatted","widthInInchesFormatted", "lengthInFeetFormatted","totalBoardFeetFormatted","featureString","totalFeaturePriceFormatted","widthCostFormatted","totalPricePerBoardFootFormatted","totalSlabPriceFormatted","area","rowColumn"));        

    //name columns for Sold Slabs tab        
    private ArrayList<String> soldColumnHeaderText = new ArrayList<>(Arrays.asList("Slab Id","Wood Code","Milled Date","Dry?","Thick(in)","Width(in)","Length(ft)","TtlBdFt","Features","TtlFeat$","WidthCost","Ttl $/BdFt","Ttl Slab $","Area->Sub-Area","Row->Column","Sold Date","Purchaser"));
    private ArrayList<String> soldColumnIdentifier = new ArrayList<>(Arrays.asList("slabIdFormatted","woodCode","milledDateFormatted","isDry","thicknessInInchesFormatted","widthInInchesFormatted", "lengthInFeetFormatted","totalBoardFeetFormatted","featureString","totalFeaturePriceFormatted","widthCostFormatted","totalPricePerBoardFootFormatted","totalSlabPriceFormatted","area","rowColumn","dateSoldFormatted","soldToCustomer"));        

    //name columns for Species tab        
    private ArrayList<String> speciesColumnHeaderText = new ArrayList<>(Arrays.asList("Wood Code","Species","8in-11in","12in-20in","21in-23in","24in-30in","Sinker $","Axe Sinker $","Ambrosia $","Birds Eye $","Burled $","Curly $","Pecky $","Spalted $","Kilned $","Dressed $"));
    private ArrayList<String> speciesColumnIdentifier = new ArrayList<>(Arrays.asList("speciesWoodCode","speciesName","price8InTo11InCurrencyFormatted","price12InTo20InCurrencyFormatted","price21InTo23InCurrencyFormatted","price24InTo30InCurrencyFormatted","sinkerPriceCurrencyFormatted","axeSinkerPriceCurrencyFormatted","ambrosiaPriceCurrencyFormatted","birdsEyePriceCurrencyFormatted","burledPriceCurrencyFormatted","curlyPriceCurrencyFormatted","peckyPriceCurrencyFormatted","spaltedPriceCurrencyFormatted","kilnedPriceCurrencyFormatted","dressedPriceCurrencyFormatted"));        

    private static InStockSlab currentInStockSlab;
    //private static SoldSlab currentSoldSlab;//not used but left for future implementation of update sold slabs
    private static Species currentSpecies;
    
    private boolean newInStockSlab = true;
    //private boolean newSoldSlab = true;//not used but left for future implementation of adding new sold slabs
    private boolean newSpecies = true;
    
    private TabPane tabPane = new TabPane();
    
    //side bar controls------------------------------------------------------------------------------------------------------------------------------------------->
    private Label woodCodeFilterLabel = new Label("Filter By:\nWood Type:");
    private ComboBox woodCodeFilterComboBox = new ComboBox(FXCollections.observableArrayList(speciesWoodCodeList));
    private Label dryFilter1Label = new Label("Dry:\n");
    private ComboBox dryFilterComboBox = new ComboBox(FXCollections.observableArrayList(dryList));
    private Label featureFilter1Label = new Label("Features:\nFeature(1):\n");
    private ComboBox featureFilter1ComboBox = new ComboBox(FXCollections.observableArrayList(featuresList));
    private Label featureFilter2Label = new Label("Feature(2):");
    private ComboBox featureFilter2ComboBox = new ComboBox(FXCollections.observableArrayList(featuresList));
    private Label featureFilter3Label = new Label("Feature(3):");
    private ComboBox featureFilter3ComboBox = new ComboBox(FXCollections.observableArrayList(featuresList));
    private Label thicknessFilterLabel = new Label("Dimensions:\nThick(in):");
    private ComboBox thicknessFilterComboBox = new ComboBox(FXCollections.observableArrayList(thicknessFilterList));
    private Label widthFilterLabel = new Label("Width(in):");
    private ComboBox widthFilterComboBox = new ComboBox(FXCollections.observableArrayList(widthFilterList));
    private Label lengthFilterLabel = new Label("Length(ft):");
    private ComboBox lengthFilterComboBox = new ComboBox(FXCollections.observableArrayList(lengthFilterList));   
    
    private Label FilterToggleLabel = new Label("Filter:");
    private Label btnSeparator1 = new Label("-------");
    private Button filterOnBtn = new Button("Filter On");
    private Label btnSeparator2 = new Label("-------");
    private Button filterOffBtn = new Button("Filter Off");
    private Label btnSeparator3 = new Label("-------");
    
    //In-Stock Slabs controls------------------------------------------------------------------------------------------------------------------------------------->
    private ToggleButton editInStockSlabsTglBtn = new ToggleButton("Edit In-Stock Slabs");
    private Button newInStockSlabBtn = new Button("New In-Stock Slab");
    private Button saveInStockSlabBtn = new Button("Save In-Stock Slab");
    private Button deleteInStockSlabBtn = new Button("Delete In-Stock Slab");
    private Button printInStockSlabBtn = new Button("Print Current Table Data");
    private Button sellInStockSlabBtn = new Button("Sell This Slab>>");

    private Label inStockIdLabel = new Label("Id Tag:");
    private Label inStockWoodCodeLabel = new Label("Wood Code:");
    private Label inStockMilledDateLabel = new Label("Milled Date:");
    private Label inStockThickLabel = new Label("Thick(in):");
    private Label inStockWidthLabel = new Label("Width(in):");
    private Label inStockLengthLabel = new Label("Length(ft):");
    private Label inStockMainAreaLabel = new Label("Main Area:");
    private Label inStockSubAreaLabel = new Label("Sub Area:");
    private Label inStockRowLabel = new Label("Row:");
    private Label inStockColumnLabel = new Label("Column:");
    private Label inStockSinkerLabel = new Label("Sinker:");
    private Label inStockAmbrosiaLabel = new Label("Ambrosia:");
    private Label inStockAxeSinkerLabel = new Label("Axe Sinker:");
    private Label inStockBirdEyeLabel = new Label("Birds Eye:");
    private Label inStockBurledLabel = new Label("Burled:");
    private Label inStockCurlyLabel = new Label("Curly:");
    private Label inStockPeckyLabel = new Label("Pecky:");
    private Label inStockSpaltedLabel = new Label("Spalted:");
    private Label inStockKilnedLabel = new Label("Kilned:");
    private Label inStockDressedLabel = new Label("Dressed:");
    private Label inStockSellSlabLabel = new Label("Sell Slab");
    private Label inStockDateSoldLabel = new Label("Date Sold:");
    private Label inStockSoldToCustomerLabel = new Label("Customer Name:");

    private TextField inStockIdTextField = new TextField();
    private ComboBox inStockWoodCodeComboBox = new ComboBox(FXCollections.observableArrayList(speciesWoodCodeList));
    private DatePicker inStockMilledDateDatePicker = new DatePicker();
    private TextField inStockThickTextField = new TextField("");
    private TextField inStockWidthTextField = new TextField("");
    private TextField inStockLengthTextField = new TextField("");
    private TextField inStockMainAreaTextField = new TextField("");
    private TextField inStockSubAreaTextField = new TextField("");
    private TextField inStockRowTextField = new TextField("");
    private TextField inStockColumnTextField = new TextField("");
    private CheckBox inStockSinkerCheckBox = new CheckBox("");
    private CheckBox inStockAmbrosiaCheckBox = new CheckBox("");
    private CheckBox inStockAxeSinkerCheckBox = new CheckBox("");
    private CheckBox inStockBirdsEyeCheckBox = new CheckBox("");
    private CheckBox inStockBurledCheckBox = new CheckBox("");
    private CheckBox inStockCurlyCheckBox = new CheckBox("");
    private CheckBox inStockPeckyCheckBox = new CheckBox("");
    private CheckBox inStockSpaltedCheckBox = new CheckBox("");
    private CheckBox inStockKilnedCheckBox = new CheckBox("");
    private CheckBox inStockDressedCheckBox = new CheckBox("");
    private DatePicker inStockDateSoldDatePicker = new DatePicker();
    private TextField inStockSoldToCustomerTextField = new TextField("");


    //Sold Slabs controls----------------------------------------------------------------------------------------------------------------------------------------->
    private Button deleteSoldSlabBtn = new Button("Delete Sold Slab");
    private Button printSoldSlabBtn = new Button("Print Current Table Data");
    private Button returnSoldSlabToInventoryBtn = new Button("Return Selected Slab To Stock>>");

    private Label soldSlabsIdLabel = new Label("Id Tag:");
    private Label soldSlabsWoodCodeLabel = new Label("Wood Code:");
    private Label soldSlabsMilledDateLabel = new Label("Milled Date:");
    private Label soldSlabsThickLabel = new Label("Thick(in):");
    private Label soldSlabsWidthLabel = new Label("Width(in):");
    private Label soldSlabsLengthLabel = new Label("Length(ft):");
    private Label soldSlabsMainAreaLabel = new Label("Main Area:");
    private Label soldSlabsSubAreaLabel = new Label("Sub Area:");
    private Label soldSlabsRowLabel = new Label("Row:");
    private Label soldSlabsColumnLabel = new Label("Column:");
    private Label soldSlabsSinkerLabel = new Label("Sinker:");
    private Label soldSlabsAmbrosiaLabel = new Label("Ambrosia:");
    private Label soldSlabsAxeSinkerLabel = new Label("Axe Sinker:");
    private Label soldSlabsBirdEyeLabel = new Label("Birds Eye:");
    private Label soldSlabsBurledLabel = new Label("Burled:");
    private Label soldSlabsCurlyLabel = new Label("Curly:");
    private Label soldSlabsPeckyLabel = new Label("Pecky:");
    private Label soldSlabsSpaltedLabel = new Label("Spalted:");
    private Label soldSlabsKilnedLabel = new Label("Kilned:");
    private Label soldSlabsDressedLabel = new Label("Dressed:");
    private Label soldSlabsDateSoldLabel = new Label("Date Sold:");
    private Label soldSlabsSoldToCustomerLabel = new Label("Customer Name:");

    private TextField soldSlabsIdTextField = new TextField("");
    private ComboBox soldSlabsWoodCodeComboBox = new ComboBox(FXCollections.observableArrayList(speciesWoodCodeList));
    private DatePicker soldSlabsMilledDateDatePicker = new DatePicker();
    private TextField soldSlabsThickTextField = new TextField("");
    private TextField soldSlabsWidthTextField = new TextField("");
    private TextField soldSlabsLengthTextField = new TextField("");
    private TextField soldSlabsMainAreaTextField = new TextField("");
    private TextField soldSlabsSubAreaTextField = new TextField("");
    private TextField soldSlabsRowTextField = new TextField("");
    private TextField soldSlabsColumnTextField = new TextField("");
    private TextField soldSlabsSinkerTextField = new TextField("");
    private TextField soldSlabsAmbrosiaTextField = new TextField("");
    private TextField soldSlabsAxeSinkerTextField = new TextField("");
    private TextField soldSlabsBirdEyeTextField = new TextField("");
    private TextField soldSlabsBurledTextField = new TextField("");
    private TextField soldSlabsCurlyTextField = new TextField("");
    private TextField soldSlabsPeckyTextField = new TextField("");
    private TextField soldSlabsSpaltedTextField = new TextField("");
    private TextField soldSlabsKilnedTextField = new TextField("");
    private TextField soldSlabsDressedTextField = new TextField("");
    private DatePicker soldSlabsDateSoldTextField = new DatePicker();
    private TextField soldSlabsSoldToCustomerTextField = new TextField("");

    //Species controls------------------------------------------------------------------------------------------------------------------------------------->
    private ToggleButton editSpeciesTglBtn = new ToggleButton("Edit Species");
    private Button printSpeciesListBtn = new Button("Print Species List");
    private Button newSpeciesBtn = new Button("New Species");
    private Button saveSpeciesBtn = new Button("Save Species");
    private Button deleteSpeciesBtn = new Button("Delete Species");

    private Label speciesWoodCodeLabel = new Label("Wood Code:");        
    private Label speciesNameLabel = new Label("Species Name:");
    private Label speciesName8inTo11inLabel = new Label("8in-11in Charge:");        
    private Label speciesName12inTo20inLabel = new Label("12in-20in Charge:");
    private Label speciesName21inTo23inLabel = new Label("21in-23in Charge:");        
    private Label speciesName24inTo30inLabel = new Label("24in-30in Charge:");   
    private Label speciesSinkerLabel = new Label("Sinker Per BoardFoot Charge:");
    private Label speciesAmbrosiaLabel = new Label("Ambrosia Per BoardFoot Charge:");
    private Label speciesAxeSinkerLabel = new Label("Axe Per BoardFoot Sinker Charge:");
    private Label speciesBirdEyeLabel = new Label("Birds Per BoardFoot Eye Charge:");
    private Label speciesBurledLabel = new Label("Burled Per BoardFoot Charge:");
    private Label speciesCurlyLabel = new Label("Curly Per BoardFoot Charge:");
    private Label speciesPeckyLabel = new Label("Pecky Per BoardFoot Charge:");
    private Label speciesSpaltedLabel = new Label("Spalted Per BoardFoot Charge:");
    private Label speciesKilnedLabel = new Label("Kilned Per BoardFoot Charge:");
    private Label speciesDressedLabel = new Label("Dressed Per BoardFoot Charge:");

    private TextField speciesWoodCodeTextField  = new TextField("");
    private TextField speciesNameTextField = new TextField("");
    private TextField species8inTo11inTextField  = new TextField("");
    private TextField species12inTo20inTextField = new TextField("");
    private TextField species21inTo23inTextField  = new TextField("");
    private TextField species24inTo30inTextField = new TextField("");
    private TextField speciesSinkerTextField = new TextField("");
    private TextField speciesAxeSinkerTextField = new TextField("");
    private TextField speciesAmbrosiaTextField = new TextField("");
    private TextField speciesBirdEyeTextField = new TextField("");
    private TextField speciesBurledTextField = new TextField("");
    private TextField speciesCurlyTextField = new TextField("");
    private TextField speciesPeckyTextField = new TextField("");
    private TextField speciesSpaltedTextField = new TextField("");
    private TextField speciesKilnedTextField = new TextField("");
    private TextField speciesDressedTextField = new TextField("");
    
    //Reports Controls----------------------------------------------------------->
    private Button InStockAllReportBtn = new Button("In-Stock Totals All");
    private Button InStockDryReportBtn = new Button("In-Stock Totals Dry");
    private Button InStockNotDryReportBtn = new Button("In-Stock Totals Not Dry");
    private Button soldAllReportBtn = new Button("Sold Totals All");
    private Button soldDryReportBtn = new Button("Sold Totals Dry");
    private Button soldNotDryReportBtn = new Button("Sold Totals Not Dry");
    private Button PrintCurrentReportBtn = new Button("Print Current Report");
    private TextArea reportZone = new TextArea("Press any button above to run a report!");
    
    //Utilities Controls--------------------------------------------------------->
    private Label passwordBoxTitleLabel = new Label("Change Password");
    private Label newPasswordLabel1 = new Label("New Password: ");
    private Label newPasswordLabel2 = new Label("Repeat New Password:");
    private Label exportBoxTitleLabel = new Label("Export Sold Slabs To Another Database & Remove From This Database");
    private Label exportPathLabel = new Label("Export Path: ");
    private Label exportDatePickerLabel = new Label("Export Slabs Sold Before: ");
    private Label importBoxTitleLabel = new Label("Import Entire Slab Tables From Another Database - Other Database Tables Not Deleted");
    private Label importPathLabel = new Label("Import Path: ");
    private Label databasePathTitle = new Label("Path To This Database");
    private Label databasePathLabel = new Label("Database Path: ");
    
    private PasswordField newPasswordField1 = new PasswordField();
    private PasswordField newPasswordField2 = new PasswordField();
    private Button changePasswordBtn = new Button("Change Password");
    
    private TextField exportPathField = new TextField();
    private Button exportBrowseBtn = new Button("Browse");
    private Button exportStartBtn = new Button("Start Export");
    private DatePicker exportDatePicker = new DatePicker();
    
    private TextField importPathField = new TextField(); 
    private Button importBrowseBtn = new Button("Browse");
    private Button importStartBtn = new Button("Start Import");
    private RadioButton importInStockRB = new RadioButton("In-Stock Only");
    private RadioButton importSoldRB = new RadioButton("Sold Only");
    private RadioButton importBothRB = new RadioButton("Both");
    private ToggleGroup importOptionsGrp = new ToggleGroup();
    
    private TextField databasePathField = new TextField(); 
    private Button setDatabaseBrowseBtn = new Button("Browse");
    private Button setDatabaseStartBtn = new Button("Set New Database Location");
    
    
    public SlabTracker(Stage passedInPrimaryStage,String connectionString) {
        
        primaryStage = passedInPrimaryStage;
        slabTrackerWindow = new Stage();
        inStockSlabsWindow = new TableView<>(FXCollections.observableArrayList(inStockSlabList));
        soldSlabsWindow = new TableView<>(FXCollections.observableArrayList(soldSlabList));
        speciesWindow = new TableView<>(FXCollections.observableArrayList(speciesList));

//        SampleData.createAndInsertNewInStockSlabs(100);
//        SampleData.createAndInsertNewSoldSlabs(100);
        //Fill Appointment Tab with data
        for (int i=0; i<inStockColumnHeaderText.size(); i++){
            TableColumn<String, String> newColumn = new TableColumn<>(inStockColumnHeaderText.get(i));
            newColumn.setCellValueFactory(new PropertyValueFactory<>(inStockColumnIdentifier.get(i)));
            inStockSlabsWindow.getColumns().add(newColumn);
            if(inStockColumnHeaderText.get(i).equals("Features")){
                newColumn.prefWidthProperty().set(180);
            }
        }

        //Fill Appointment Tab with data
        for (int i=0; i<soldColumnHeaderText.size(); i++){
            TableColumn<String, String> newColumn = new TableColumn<>(soldColumnHeaderText.get(i));
            newColumn.setCellValueFactory(new PropertyValueFactory<>(soldColumnIdentifier.get(i)));
            soldSlabsWindow.getColumns().add(newColumn);
            if(soldColumnHeaderText.get(i).equals("Features")){
                newColumn.prefWidthProperty().set(180);
            }
        }         

        //Fill Appointment Tab with data
        for (int i=0; i<speciesColumnHeaderText.size(); i++){
            TableColumn<String, String> newColumn = new TableColumn<>(speciesColumnHeaderText.get(i));
            newColumn.setCellValueFactory(new PropertyValueFactory<>(speciesColumnIdentifier.get(i)));
            speciesWindow.getColumns().add(newColumn);
        }         
        //Set Filter ComboBoxes and add None to Wood Code Filter list--------------------------------------------------------------------------------------------->
        speciesWoodCodeList.add(0, "None");
        woodCodeFilterComboBox = new ComboBox(FXCollections.observableArrayList(speciesWoodCodeList));
        resetFilters();
        //Putting the interface pieces in there place! That should tech them!------------------------------------------------------------------------------------->
        HBox root = new HBox();
        root.setId("root");
        Group tabGroup = new Group();
        VBox vBoxButtons = new VBox();
        vBoxButtons.setId("vBoxButtons");
        tabPane.setId("tabPane");
        HBox logoBox = new HBox();
        logoBox.setId("logoBox");
        logoBox.setPrefSize(130,130);
        HBox logoTopSpacer = new HBox();
        logoTopSpacer.setPrefHeight(90);
        
        inStockSlabsWindow.setId("inStockSlabsWindow");
        soldSlabsWindow.setId("soldSlabsWindow");
        speciesWindow.setId("speciesWindow"); 
        
        root.getChildren().addAll(vBoxButtons,tabGroup);
        vBoxButtons.getChildren().addAll(woodCodeFilterLabel,woodCodeFilterComboBox,dryFilter1Label,dryFilterComboBox,featureFilter1Label,featureFilter1ComboBox,featureFilter2Label,
                featureFilter2ComboBox,featureFilter3Label,featureFilter3ComboBox,thicknessFilterLabel,thicknessFilterComboBox,
                widthFilterLabel,widthFilterComboBox,lengthFilterLabel,lengthFilterComboBox,FilterToggleLabel,btnSeparator1,filterOnBtn,btnSeparator2,filterOffBtn,btnSeparator3,logoTopSpacer,logoBox);
        tabGroup.getChildren().add(tabPane);
        
        VBox vboxInStockRoot = new VBox();
        vboxInStockRoot.setId("vboxInStockRoot");
        VBox vboxSoldRoot = new VBox();
        vboxSoldRoot.setId("vboxSoldRoot");
        VBox vboxSpeciesRoot = new VBox();
        vboxSpeciesRoot.setId("vboxSpeciesRoot");
        VBox vboxReportsRoot = new VBox();
        vboxReportsRoot.setId("vboxReportsRoot");        
        VBox vboxUtilitiesRoot = new VBox();
        vboxUtilitiesRoot.setId("vboxUtilitiesRoot");        
        
        for (int i = 0; i < 5; i++) {            
            Tab tab = new Tab();
            HBox hBoxTabButtons = new HBox();
            hBoxTabButtons.setId("hBoxTabButtons");
            HBox spaceContainer = new HBox();//outer container for tab roots & right side spacer that allows the scroll bar to be seen if the window width is decreased
            VBox innerRightSpacer = new VBox();//buffer space so right scroll bar can be seen if window width is made smaller
            HBox dataInputOuterContainer = new HBox();
            dataInputOuterContainer.setId("dataInputOuterContainer");            
            HBox dataInputInnerContainer = new HBox();
            dataInputInnerContainer.setId("dataInputInnerContainer");   
            GridPane dataInputColumn1 = new GridPane();
            dataInputColumn1.setId("dataInputColumn");
            GridPane dataInputColumn2 = new GridPane();
            dataInputColumn2.setId("dataInputColumn");
            GridPane dataInputColumn3 = new GridPane();
            dataInputColumn3.setId("dataInputColumn");
            GridPane dataInputColumn4 = new GridPane();
            dataInputColumn4.setId("dataInputColumn");
            GridPane dataInputColumn5 = new GridPane();
            dataInputColumn5.setId("dataInputColumn");
            tab.setText(tabNameList.get(i));
            switch(i){
                case 0:
                    hBoxTabButtons.getChildren().addAll(editInStockSlabsTglBtn,printInStockSlabBtn,newInStockSlabBtn,saveInStockSlabBtn,deleteInStockSlabBtn,sellInStockSlabBtn);
                    vboxInStockRoot.getChildren().addAll(hBoxTabButtons,inStockSlabsWindow,dataInputOuterContainer);
                    spaceContainer.getChildren().addAll(vboxInStockRoot,innerRightSpacer);
                    
                    dataInputColumn1.add(inStockIdLabel,0,0);
                    dataInputColumn1.add(inStockIdTextField,1,0);
                    dataInputColumn1.add(inStockWoodCodeLabel,0,1);
                    dataInputColumn1.add(inStockWoodCodeComboBox,1,1);
                    dataInputColumn1.add(inStockMilledDateLabel,0,2);
                    dataInputColumn1.add(inStockMilledDateDatePicker,1,2);
                    dataInputColumn1.add(inStockThickLabel,0,3);
                    dataInputColumn1.add(inStockThickTextField,1,3);
                    dataInputColumn1.add(inStockWidthLabel,0,4);
                    dataInputColumn1.add(inStockWidthTextField,1,4);
                    dataInputColumn1.add(inStockLengthLabel,0,5);
                    dataInputColumn1.add(inStockLengthTextField,1,5);
                    
                    dataInputColumn2.add(inStockMainAreaLabel,0,0);
                    dataInputColumn2.add(inStockMainAreaTextField,1,0);
                    dataInputColumn2.add(inStockSubAreaLabel,0,1);
                    dataInputColumn2.add(inStockSubAreaTextField,1,1);
                    dataInputColumn2.add(inStockRowLabel,0,2);
                    dataInputColumn2.add(inStockRowTextField,1,2);
                    dataInputColumn2.add(inStockColumnLabel,0,3);
                    dataInputColumn2.add(inStockColumnTextField,1,3);
                    
                    dataInputColumn3.add(inStockSinkerLabel,0,0);
                    dataInputColumn3.add(inStockSinkerCheckBox,1,0);
                    dataInputColumn3.add(inStockAxeSinkerLabel,0,1);
                    dataInputColumn3.add(inStockAxeSinkerCheckBox,1,1);
                    dataInputColumn3.add(inStockAmbrosiaLabel,0,2);
                    dataInputColumn3.add(inStockAmbrosiaCheckBox,1,2);
                    dataInputColumn3.add(inStockBirdEyeLabel,0,3);
                    dataInputColumn3.add(inStockBirdsEyeCheckBox,1,3);
                    dataInputColumn3.add(inStockBurledLabel,0,4);
                    dataInputColumn3.add(inStockBurledCheckBox,1,4);
                    
                    dataInputColumn4.add(inStockCurlyLabel,0,0);
                    dataInputColumn4.add(inStockCurlyCheckBox,1,0);
                    dataInputColumn4.add(inStockPeckyLabel,0,1);
                    dataInputColumn4.add(inStockPeckyCheckBox,1,1);
                    dataInputColumn4.add(inStockSpaltedLabel,0,2);
                    dataInputColumn4.add(inStockSpaltedCheckBox,1,2);
                    dataInputColumn4.add(inStockKilnedLabel,0,3);
                    dataInputColumn4.add(inStockKilnedCheckBox,1,3);
                    dataInputColumn4.add(inStockDressedLabel,0,4);
                    dataInputColumn4.add(inStockDressedCheckBox,1,4);
                    
                    dataInputColumn5.add(inStockSellSlabLabel,0,0,2,1);
                    dataInputColumn5.add(inStockDateSoldLabel,0,1);
                    dataInputColumn5.add(inStockDateSoldDatePicker,1,1);
                    dataInputColumn5.add(inStockSoldToCustomerLabel,0,2);
                    dataInputColumn5.add(inStockSoldToCustomerTextField,1,2);
                    
                    editInStockSlabsTglBtn.setSelected(true);
                    deleteInStockSlabBtn.setVisible(false);
                    sellInStockSlabBtn.setVisible(false);
                    inStockAxeSinkerCheckBox.setDisable(true);
                    
                    HBox inStockFirstDataInputBox = new HBox();

                    inStockFirstDataInputBox.getChildren().addAll(dataInputColumn1,dataInputColumn2,dataInputColumn3,dataInputColumn4);                    
                    dataInputOuterContainer.getChildren().remove(dataInputColumn5);
                    dataInputOuterContainer.getChildren().addAll(inStockFirstDataInputBox,dataInputColumn5);
                    dataInputOuterContainer.setMaxWidth(1050);
                    dataInputOuterContainer.setMinWidth(700);
                    
                    dataInputColumn5.setId("InStockSellSlabColumn");
                    inStockFirstDataInputBox.setId("inStockFirstDataInputBox");     

                    innerRightSpacer.setMinWidth(175);
                    tab.setContent(spaceContainer);
                  
                    break;
                case 1:
                    dataInputOuterContainer.getChildren().addAll(dataInputInnerContainer);
                    dataInputInnerContainer.getChildren().addAll(dataInputColumn1,dataInputColumn2,dataInputColumn3,dataInputColumn4);            
                    hBoxTabButtons.getChildren().addAll(deleteSoldSlabBtn,printSoldSlabBtn,returnSoldSlabToInventoryBtn);
                    vboxSoldRoot.getChildren().addAll(hBoxTabButtons,soldSlabsWindow,dataInputOuterContainer);
                    spaceContainer.getChildren().addAll(vboxSoldRoot,innerRightSpacer);
                    
                    dataInputOuterContainer.setMaxWidth(1080);
                    dataInputOuterContainer.setMinWidth(700);
                    
                    dataInputColumn1.add(soldSlabsIdLabel,0,0);
                    dataInputColumn1.add(soldSlabsIdTextField,1,0);
                    dataInputColumn1.add(soldSlabsWoodCodeLabel,0,1);
                    dataInputColumn1.add(soldSlabsWoodCodeComboBox,1,1);
                    dataInputColumn1.add(soldSlabsMilledDateLabel,0,2);
                    dataInputColumn1.add(soldSlabsMilledDateDatePicker,1,2);
                    dataInputColumn1.add(soldSlabsThickLabel,0,3);
                    dataInputColumn1.add(soldSlabsThickTextField,1,3);
                    dataInputColumn1.add(soldSlabsWidthLabel,0,4);
                    dataInputColumn1.add(soldSlabsWidthTextField,1,4);
                    dataInputColumn1.add(soldSlabsLengthLabel,0,5);
                    dataInputColumn1.add(soldSlabsLengthTextField,1,5);
                    
                    dataInputColumn2.add(soldSlabsMainAreaLabel,0,0);
                    dataInputColumn2.add(soldSlabsMainAreaTextField,1,0);
                    dataInputColumn2.add(soldSlabsSubAreaLabel,0,1);
                    dataInputColumn2.add(soldSlabsSubAreaTextField,1,1);
                    dataInputColumn2.add(soldSlabsRowLabel,0,2);
                    dataInputColumn2.add(soldSlabsRowTextField,1,2);
                    dataInputColumn2.add(soldSlabsColumnLabel,0,3);
                    dataInputColumn2.add(soldSlabsColumnTextField,1,3);                                        
                    dataInputColumn2.add(soldSlabsDateSoldLabel,0,4);
                    dataInputColumn2.add(soldSlabsDateSoldTextField,1,4);
                    dataInputColumn2.add(soldSlabsSoldToCustomerLabel,0,5);
                    dataInputColumn2.add(soldSlabsSoldToCustomerTextField,1,5);
                    
                    dataInputColumn3.add(soldSlabsSinkerLabel,0,0);
                    dataInputColumn3.add(soldSlabsSinkerTextField,1,0);
                    dataInputColumn3.add(soldSlabsAxeSinkerLabel,0,1);
                    dataInputColumn3.add(soldSlabsAxeSinkerTextField,1,1);
                    dataInputColumn3.add(soldSlabsAmbrosiaLabel,0,2);
                    dataInputColumn3.add(soldSlabsAmbrosiaTextField,1,2);
                    dataInputColumn3.add(soldSlabsBirdEyeLabel,0,3);
                    dataInputColumn3.add(soldSlabsBirdEyeTextField,1,3);
                    dataInputColumn3.add(soldSlabsBurledLabel,0,4);
                    dataInputColumn3.add(soldSlabsBurledTextField,1,4);
                    
                    dataInputColumn4.add(soldSlabsCurlyLabel,0,0);
                    dataInputColumn4.add(soldSlabsCurlyTextField,1,0);
                    dataInputColumn4.add(soldSlabsPeckyLabel,0,1);
                    dataInputColumn4.add(soldSlabsPeckyTextField,1,1);
                    dataInputColumn4.add(soldSlabsSpaltedLabel,0,2);
                    dataInputColumn4.add(soldSlabsSpaltedTextField,1,2);
                    dataInputColumn4.add(soldSlabsKilnedLabel,0,3);
                    dataInputColumn4.add(soldSlabsKilnedTextField,1,3);
                    dataInputColumn4.add(soldSlabsDressedLabel,0,4);
                    dataInputColumn4.add(soldSlabsDressedTextField,1,4);
                    
                    soldSlabsWindow.getSelectionModel().select(0);
                    if(soldSlabList.size()>0){
                        setSelectedSoldSlabDetails();
                    }
                    soldSlabsIdTextField.setEditable(false);
                    soldSlabsWoodCodeComboBox.setDisable(true);
                    soldSlabsMilledDateDatePicker.setEditable(false);
                    soldSlabsThickTextField.setEditable(false);
                    soldSlabsWidthTextField.setEditable(false);
                    soldSlabsLengthTextField.setEditable(false);
                    soldSlabsMainAreaTextField.setEditable(false);
                    soldSlabsSubAreaTextField.setEditable(false);
                    soldSlabsRowTextField.setEditable(false);
                    soldSlabsColumnTextField.setEditable(false);
                    soldSlabsSinkerTextField.setEditable(false);
                    soldSlabsAxeSinkerTextField.setEditable(false);
                    soldSlabsAmbrosiaTextField.setEditable(false);
                    soldSlabsBirdEyeTextField.setEditable(false);
                    soldSlabsBurledTextField.setEditable(false);
                    soldSlabsCurlyTextField.setEditable(false);
                    soldSlabsPeckyTextField.setEditable(false);
                    soldSlabsSpaltedTextField.setEditable(false);
                    soldSlabsKilnedTextField.setEditable(false);
                    soldSlabsDressedTextField.setEditable(false);     
                    soldSlabsDateSoldTextField.setEditable(false);
                    soldSlabsSoldToCustomerTextField.setEditable(false);
                    
                    tab.setContent(spaceContainer);
                    innerRightSpacer.setMinWidth(175);
                    break;
                case 2:
                    dataInputOuterContainer.getChildren().addAll(dataInputInnerContainer);
                    dataInputInnerContainer.getChildren().addAll(dataInputColumn1,dataInputColumn2,dataInputColumn3);            
                    hBoxTabButtons.getChildren().addAll(editSpeciesTglBtn,printSpeciesListBtn,newSpeciesBtn,saveSpeciesBtn,deleteSpeciesBtn);
                    vboxSpeciesRoot.getChildren().addAll(hBoxTabButtons,speciesWindow,dataInputOuterContainer);
                    spaceContainer.getChildren().addAll(vboxSpeciesRoot,innerRightSpacer);
                    
                    dataInputOuterContainer.setMaxWidth(1020);
                    dataInputOuterContainer.setMinWidth(700);
                                       
                    dataInputColumn1.add(speciesWoodCodeLabel,0,0);
                    dataInputColumn1.add(speciesWoodCodeTextField,1,0);
                    dataInputColumn1.add(speciesNameLabel,0,1);
                    dataInputColumn1.add(speciesNameTextField,1,1);
                    dataInputColumn1.add(speciesName8inTo11inLabel,0,2);
                    dataInputColumn1.add(species8inTo11inTextField,1,2);
                    dataInputColumn1.add(speciesName12inTo20inLabel,0,3);
                    dataInputColumn1.add(species12inTo20inTextField,1,3);
                    dataInputColumn1.add(speciesName21inTo23inLabel,0,4);
                    dataInputColumn1.add(species21inTo23inTextField,1,4);
                    dataInputColumn1.add(speciesName24inTo30inLabel,0,5);
                    dataInputColumn1.add(species24inTo30inTextField,1,5);
                    
                    dataInputColumn2.add(speciesSinkerLabel,0,0);
                    dataInputColumn2.add(speciesSinkerTextField,1,0);
                    dataInputColumn2.add(speciesAxeSinkerLabel,0,1);
                    dataInputColumn2.add(speciesAxeSinkerTextField,1,1);
                    dataInputColumn2.add(speciesAmbrosiaLabel,0,2);
                    dataInputColumn2.add(speciesAmbrosiaTextField,1,2);
                    dataInputColumn2.add(speciesBirdEyeLabel,0,3);
                    dataInputColumn2.add(speciesBirdEyeTextField,1,3);                    
                    dataInputColumn2.add(speciesBurledLabel,0,4);
                    dataInputColumn2.add(speciesBurledTextField,1,4);
                    
                    dataInputColumn3.add(speciesCurlyLabel,0,0);
                    dataInputColumn3.add(speciesCurlyTextField,1,0);
                    dataInputColumn3.add(speciesPeckyLabel,0,1);
                    dataInputColumn3.add(speciesPeckyTextField,1,1);
                    dataInputColumn3.add(speciesSpaltedLabel,0,2);
                    dataInputColumn3.add(speciesSpaltedTextField,1,2);
                    dataInputColumn3.add(speciesKilnedLabel,0,3);
                    dataInputColumn3.add(speciesKilnedTextField,1,3);                    
                    dataInputColumn3.add(speciesDressedLabel,0,4);
                    dataInputColumn3.add(speciesDressedTextField,1,4);                    
                    
                    newSpeciesBtn.setVisible(false);
                    saveSpeciesBtn.setVisible(false);
                    deleteSpeciesBtn.setVisible(false);                    
                    speciesWoodCodeTextField.setEditable(false);
                    speciesNameTextField.setEditable(false);
                    species8inTo11inTextField.setEditable(false);
                    species12inTo20inTextField.setEditable(false);
                    species21inTo23inTextField.setEditable(false);
                    species24inTo30inTextField.setEditable(false);
                    speciesSinkerTextField.setEditable(false);
                    speciesAxeSinkerTextField.setEditable(false);
                    speciesAmbrosiaTextField.setEditable(false);
                    speciesBirdEyeTextField.setEditable(false);
                    speciesBurledTextField.setEditable(false);
                    speciesCurlyTextField.setEditable(false);
                    speciesPeckyTextField.setEditable(false);  
                    speciesSpaltedTextField.setEditable(false);
                    speciesKilnedTextField.setEditable(false);     
                    speciesDressedTextField.setEditable(false);
                    
                    tab.setContent(spaceContainer);
                    innerRightSpacer.setMinWidth(175);
                    break;
                case 3:
                    reportZone.setMaxSize(800, 600);
                    reportZone.setMinSize(800, 600);
                    reportZone.setEditable(false);
                    hBoxTabButtons.getChildren().addAll(InStockAllReportBtn,InStockDryReportBtn,InStockNotDryReportBtn,soldAllReportBtn,soldDryReportBtn,soldNotDryReportBtn,PrintCurrentReportBtn);
                    vboxReportsRoot.getChildren().addAll(hBoxTabButtons,reportZone);                    
                    tab.setContent(vboxReportsRoot);

                    break;
                case 4:
                    GridPane passwordGridPane = new GridPane();
                    passwordGridPane.setId("passwordGridPane");
                    GridPane exportGridPane = new GridPane();
                    exportGridPane.setId("exportGridPane");
                    GridPane importGridPane = new GridPane();
                    importGridPane.setId("importGridPane");
                    GridPane setDatabaseGridPane = new GridPane();
                    setDatabaseGridPane.setId("setDatabaseGridPane");
                    
                    passwordGridPane.add(passwordBoxTitleLabel, 0, 0);
                    passwordGridPane.add(newPasswordLabel1, 0, 1);
                    passwordGridPane.add(newPasswordField1, 1, 1);
                    passwordGridPane.add(newPasswordLabel2, 0, 2);
                    passwordGridPane.add(newPasswordField2, 1, 2);
                    passwordGridPane.add(changePasswordBtn, 0, 3);
                    
                    exportGridPane.add(exportBoxTitleLabel, 0, 0, 7, 1);
                    exportGridPane.add(exportPathLabel, 0, 1);
                    exportGridPane.add(exportPathField, 1, 1, 5, 1);
                    exportGridPane.add(exportBrowseBtn, 8, 1);
                    exportGridPane.add(exportDatePickerLabel, 0, 2, 2, 1);
                    exportGridPane.add(exportDatePicker, 2, 2);
                    exportGridPane.add(exportStartBtn, 0, 3);
                  
                    importGridPane.add(importBoxTitleLabel, 0, 0, 7, 1);
                    importGridPane.add(importPathLabel, 0, 1);
                    importGridPane.add(importPathField, 1, 1, 4, 1);
                    importGridPane.add(importBrowseBtn, 7, 1);
                    importGridPane.add(importInStockRB, 0, 2);
                    importGridPane.add(importSoldRB, 1, 2);
                    importGridPane.add(importBothRB, 2, 2);
                    importGridPane.add(importStartBtn, 0, 3);
                    importInStockRB.setToggleGroup(importOptionsGrp);
                    importSoldRB.setToggleGroup(importOptionsGrp);
                    importBothRB.setToggleGroup(importOptionsGrp);
                    importBothRB.setSelected(true);
                    
                    setDatabaseGridPane.add(databasePathTitle, 0, 0, 7, 1);
                    setDatabaseGridPane.add(databasePathLabel, 0, 1);
                    setDatabaseGridPane.add(databasePathField, 1, 1, 5, 1);
                    setDatabaseGridPane.add(setDatabaseBrowseBtn, 8, 1);
                    setDatabaseGridPane.add(setDatabaseStartBtn, 0, 3, 2, 1);
                    
                    passwordGridPane.setMaxSize(380, 160);
                    passwordGridPane.setMinSize(380, 160);
                    exportGridPane.setMaxSize(500, 150);
                    exportGridPane.setMinSize(500, 150);
                    exportPathField.setMinWidth(300);
                    importPathField.setMinWidth(300);
                    databasePathField.setMinWidth(300);
                    importGridPane.setMaxSize(500, 140);
                    importGridPane.setMinSize(500, 140);
                    setDatabaseGridPane.setMaxSize(500, 120);
                    setDatabaseGridPane.setMinSize(500, 120);
                    vboxUtilitiesRoot.setMaxSize(520, 590);
                    vboxUtilitiesRoot.setMinSize(520, 590);
                    
                    vboxUtilitiesRoot.getChildren().addAll(passwordGridPane,exportGridPane,importGridPane,setDatabaseGridPane);
                    tab.setContent(vboxUtilitiesRoot);                    
                    break;
                            
            }            
            tabPane.getTabs().add(tab); 
        }
        //Set Tableview actions----------------------------------------------------------------------------------------------------------------------------------->
        inStockSlabsWindow.setOnKeyReleased((KeyEvent me) -> {
            try{
                inStockSlabsWindow.getSelectionModel().getSelectedCells().get(0);
            }catch(IndexOutOfBoundsException ex){
                inStockSlabsWindow.getSelectionModel().selectFirst(); 
            }
            setSelectedInStockSlabDetails();
        });        
        
        inStockSlabsWindow.setOnMouseClicked((MouseEvent me) -> {
            if(me.getButton().equals(MouseButton.PRIMARY)){
                setSelectedInStockSlabDetails();
            }
        });
        
        soldSlabsWindow.setOnKeyReleased((KeyEvent me) -> {
            try{
                soldSlabsWindow.getSelectionModel().getSelectedCells().get(0);
            }catch(IndexOutOfBoundsException ex){
                soldSlabsWindow.getSelectionModel().selectFirst();
            }
            setSelectedSoldSlabDetails();
        });
        
        soldSlabsWindow.setOnMouseClicked((MouseEvent me) -> {
            if(me.getButton().equals(MouseButton.PRIMARY)){
                setSelectedSoldSlabDetails();
            }
        });
        
        speciesWindow.setOnKeyReleased((KeyEvent me) -> {
            try{
                speciesWindow.getSelectionModel().getSelectedCells().get(0);
            }catch(IndexOutOfBoundsException ex){
                speciesWindow.getSelectionModel().selectFirst();
            }
            setSelectedSpeciesDetails();
        });  
        
        speciesWindow.setOnMouseClicked((MouseEvent me) -> {
            if(me.getButton().equals(MouseButton.PRIMARY)){
                setSelectedSpeciesDetails();
            }
        });          
        
        //set button actions-------------------------------------------------------------------------------------------------------------------------------------->
        //Filter Toggle Button--------------------------------------------------->
        filterOnBtn.setOnAction((ActionEvent event) -> {
            turnFilterOn();
        });
        
        filterOffBtn.setOnAction((ActionEvent event) -> {
            turnFilterOff();
        });

        //In-Stock Buttons------------------------------------------------------->
        newInStockSlabBtn.setOnAction((ActionEvent event) -> {
            clearSelectedInStockSlabDetails();
        });   
        
        saveInStockSlabBtn.setOnAction((ActionEvent event) -> {
            saveSelectedInStockSlab();            
        });
        
        deleteInStockSlabBtn.setOnAction((ActionEvent event) -> {
            deleteSelectedInStockSlab(false);
        });
        printInStockSlabBtn.setOnAction((ActionEvent event) -> {
            FileHandler.printTableToNotePad(inStockSlabsWindow,"In-Stock");
        });
        sellInStockSlabBtn.setOnAction((ActionEvent event)->{
            sellInStockSlab();
        });
        
        editInStockSlabsTglBtn.setOnAction((ActionEvent event)->{
            editInStockSlabsToggle();        
        });
        
        inStockSinkerCheckBox.setOnAction((ActionEvent event)->{
            if(inStockSinkerCheckBox.isSelected()){
                inStockAxeSinkerCheckBox.setDisable(false);
            }else{
                inStockAxeSinkerCheckBox.setDisable(true);
                inStockAxeSinkerCheckBox.setSelected(false);                        
            }
        });
        
        inStockWoodCodeComboBox.setOnMouseClicked((MouseEvent me) -> {
            speciesWoodCodeList = DAO.getAllAvailableWoodCodes();
            inStockWoodCodeComboBox.setItems(FXCollections.observableArrayList(speciesWoodCodeList));
        });
        
        //Sold Slabs Buttons----------------------------------------------------->        
        deleteSoldSlabBtn.setOnAction((ActionEvent event) -> {
            deleteSelectedSoldSlab(false);
        });         
        
        printSoldSlabBtn.setOnAction((ActionEvent event) -> {
            FileHandler.printTableToNotePad(soldSlabsWindow,"Sold");
        });  
                
        returnSoldSlabToInventoryBtn.setOnAction((ActionEvent event) -> {
            returnSoldSlabToStock();
        });  
        
        //Species Buttons-------------------------------------------------------->
        editSpeciesTglBtn.setOnAction((ActionEvent event) -> {
            editSpeciesToggle();
        });
        
        printSpeciesListBtn.setOnAction((ActionEvent event) -> {
           FileHandler.printTableToNotePad(speciesWindow,"Species"); 
        });

        newSpeciesBtn.setOnAction((ActionEvent event) -> {
            clearSelectedSpeciesDetails();
        });
        
        saveSpeciesBtn.setOnAction((ActionEvent event) -> {
            saveSelectedSpecies();
        });
        
        deleteSpeciesBtn.setOnAction((ActionEvent event) -> {
            deleteSelectedSpecies();
        }); 
        
        //Reports Buttons-------------------------------------------------------->
        InStockAllReportBtn.setOnAction((ActionEvent event) -> {
            reportZone.setText(DAO.getInStockSlabsReport(1));
        });
        
        InStockDryReportBtn.setOnAction((ActionEvent event) -> {
            reportZone.setText(DAO.getInStockSlabsReport(2));
        });
        
        InStockNotDryReportBtn.setOnAction((ActionEvent event) -> {
            reportZone.setText(DAO.getInStockSlabsReport(3));
        });
        
        soldAllReportBtn.setOnAction((ActionEvent event) -> {
            reportZone.setText(DAO.getSoldSlabsReport(1));
        });
        
        soldDryReportBtn.setOnAction((ActionEvent event) -> {
            reportZone.setText(DAO.getSoldSlabsReport(2));
        });
        
        soldNotDryReportBtn.setOnAction((ActionEvent event) -> {
            reportZone.setText(DAO.getSoldSlabsReport(3));
        });
        
        PrintCurrentReportBtn.setOnAction((ActionEvent event) -> {
            FileHandler.printTextAreaToNotePad(reportZone.getText(), "Report");
        });
        
        //Utililities Buttons---------------------------------------------------->
        changePasswordBtn.setOnAction((ActionEvent event) -> {
            changeAdminPassword();
        });
        
        exportBrowseBtn.setOnAction((ActionEvent event) -> {
            FileHandler.setFilePath(slabTrackerWindow,exportPathField);
        });
        
        exportStartBtn.setOnAction((ActionEvent event) -> {
            if(FileHandler.checkFilePath(exportPathField.getText().trim())){
                if(exportDatePicker.getValue()!=null){
                    exportSlabsBeforeDate(exportPathField.getText().trim(),exportDatePicker.getValue());
                }else{
                    JOptionPane.showMessageDialog(null,"You must a date to denote which slabs will be exported!","Ooopsie!",JOptionPane.ERROR_MESSAGE); 
                }
            }else{
                JOptionPane.showMessageDialog(null,"This database file doesn't exist!","Ooopsie!",JOptionPane.ERROR_MESSAGE); 
            
            }
        });

        importStartBtn.setOnAction((ActionEvent event) -> {
            if(FileHandler.checkFilePath(importPathField.getText().trim())){
                importSlabsFromAnotherDatabase(importPathField.getText().trim());
            }else{
                JOptionPane.showMessageDialog(null,"This database file doesn't exist!","Ooopsie!",JOptionPane.ERROR_MESSAGE); 
            
            }
        });        
        importBrowseBtn.setOnAction((ActionEvent event) -> {
            FileHandler.setFilePath(slabTrackerWindow,importPathField);
        });
        
        setDatabaseBrowseBtn.setOnAction((ActionEvent event) -> {
            FileHandler.setFilePath(slabTrackerWindow,databasePathField);
        });
        
        setDatabaseStartBtn.setOnAction((ActionEvent event) -> {
            changeDatabaseConnection(databasePathField.getText().trim());
        });
        //Final Stage and Scene Settings----------------------------------------->
        Scene scene = new Scene(root, 1425, 750);
        
        scene.getStylesheets().add("/GUIs/GUIsStyleSheet.css");        
        tabPane.tabClosingPolicyProperty().set(UNAVAILABLE); 
        slabTrackerWindow.setTitle("Slab Trackin! DB File: "+DBConnector.getDBPath());
        slabTrackerWindow.getIcons().add(new Image("/GUIs/ARLogo.png"));

        vBoxButtons.setMinSize(150,775);       
        vBoxButtons.setMaxSize(150,775);       
                
        tabPane.prefWidthProperty().bind(slabTrackerWindow.widthProperty());   
        tabPane.maxWidthProperty().bind(slabTrackerWindow.widthProperty());   
        tabPane.minWidthProperty().bind(slabTrackerWindow.widthProperty()); 
        
        inStockSlabsWindow.setMaxSize(1250, 600);
        soldSlabsWindow.setMaxSize(1390, 600);
        speciesWindow.setMaxSize(1160, 600);

        slabTrackerWindow.setScene(scene);
        slabTrackerWindow.show();
        FileHandler.overwriteDatabaseConfig(connectionString);
    }

    //Filter Box Methods------------------------------------------------------------------------------------------------------------------------------------------>
    public final void turnFilterOn(){
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if(selectedTab.getText().equals("In-Stock")||selectedTab.getText().equals("Sold")){
            
            HashMap<String,String> filterMap = new HashMap<>();
            filteredInStockSlabs = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllInStockSlabs()));
            filteredSoldSlabs = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllSoldSlabs()));
            inStockSlabsWindow.setItems(FXCollections.observableArrayList(filteredInStockSlabs));
            soldSlabsWindow.setItems(FXCollections.observableArrayList(filteredSoldSlabs));
            boolean atLeastOneFilterSelected = false;
            
            filterMap.put("woodCode",woodCodeFilterComboBox.getValue().toString().trim());
            filterMap.put("dry",dryFilterComboBox.getValue().toString().trim());
            filterMap.put("feature1",featureFilter1ComboBox.getValue().toString().trim());
            filterMap.put("feature2",featureFilter2ComboBox.getValue().toString().trim());
            filterMap.put("feature3",featureFilter3ComboBox.getValue().toString().trim());
            filterMap.put("thick",thicknessFilterComboBox.getValue().toString().trim());
            filterMap.put("width",widthFilterComboBox.getValue().toString().trim());
            filterMap.put("length",lengthFilterComboBox.getValue().toString().trim());
            
            for (HashMap.Entry<String, String> entry : filterMap.entrySet()) {
                if(!entry.getValue().equals("None")){
                    atLeastOneFilterSelected = true;
                    break;
                }
            }
            
            if(atLeastOneFilterSelected){
                if(!filterMap.get("woodCode").equals("None")){
                    
                    filteredInStockSlabs = new FilteredList<>(inStockSlabsWindow.getItems());
                    filteredSoldSlabs = new FilteredList<>(soldSlabsWindow.getItems());
                    
                    String selectedWoodCode = filterMap.get("woodCode");
                    filterInStockSlabsByWoodCode(selectedWoodCode);
                    filterSoldSlabsByWoodCode(selectedWoodCode);
                    
                    inStockSlabsWindow.setItems(FXCollections.observableArrayList(filteredInStockSlabs));
                    soldSlabsWindow.setItems(FXCollections.observableArrayList(filteredSoldSlabs));
                }
                if(!filterMap.get("dry").equals("None")){
                    
                    filteredInStockSlabs = new FilteredList<>(inStockSlabsWindow.getItems());
                    filteredSoldSlabs = new FilteredList<>(soldSlabsWindow.getItems());
                    
                    String selectedDryness = filterMap.get("dry");
                    filterInStockSlabsByDryness(selectedDryness);
                    filterSoldSlabsByByDryness(selectedDryness);
                    
                    inStockSlabsWindow.setItems(FXCollections.observableArrayList(filteredInStockSlabs));
                    soldSlabsWindow.setItems(FXCollections.observableArrayList(filteredSoldSlabs));
                }
                if(!filterMap.get("feature1").equals("None")){
                    
                    filteredInStockSlabs = new FilteredList<>(inStockSlabsWindow.getItems());
                    filteredSoldSlabs = new FilteredList<>(soldSlabsWindow.getItems());
                    
                    String feature = filterMap.get("feature1");
                    filterInStockSlabsByFeature(feature);
                    filterSoldSlabsByFeature(feature);
                    
                    inStockSlabsWindow.setItems(FXCollections.observableArrayList(filteredInStockSlabs));
                    soldSlabsWindow.setItems(FXCollections.observableArrayList(filteredSoldSlabs));
                }
                if(!filterMap.get("feature2").equals("None")){
                    filteredInStockSlabs = new FilteredList<>(inStockSlabsWindow.getItems());
                    filteredSoldSlabs = new FilteredList<>(soldSlabsWindow.getItems());
                    
                    String feature = filterMap.get("feature2");
                    filterInStockSlabsByFeature(feature);
                    filterSoldSlabsByFeature(feature);
                    
                    inStockSlabsWindow.setItems(FXCollections.observableArrayList(filteredInStockSlabs));
                    soldSlabsWindow.setItems(FXCollections.observableArrayList(filteredSoldSlabs));
                }
                if(!filterMap.get("feature3").equals("None")){
                    filteredInStockSlabs = new FilteredList<>(inStockSlabsWindow.getItems());
                    filteredSoldSlabs = new FilteredList<>(soldSlabsWindow.getItems());                    
                    
                    String feature = filterMap.get("feature3");
                    filterInStockSlabsByFeature(feature);
                    filterSoldSlabsByFeature(feature);
                    
                    inStockSlabsWindow.setItems(FXCollections.observableArrayList(filteredInStockSlabs));
                    soldSlabsWindow.setItems(FXCollections.observableArrayList(filteredSoldSlabs));                    
                }
                if(!filterMap.get("thick").equals("None")){
                    filteredInStockSlabs = new FilteredList<>(inStockSlabsWindow.getItems());
                    filteredSoldSlabs = new FilteredList<>(soldSlabsWindow.getItems());                    
                    
                    String thickness = filterMap.get("thick");
                    filterInStockSlabsByThickness(thickness);
                    filterSoldSlabsByThickness(thickness);
                    
                    inStockSlabsWindow.setItems(FXCollections.observableArrayList(filteredInStockSlabs));
                    soldSlabsWindow.setItems(FXCollections.observableArrayList(filteredSoldSlabs));                
                }
                if(!filterMap.get("width").equals("None")){
                    filteredInStockSlabs = new FilteredList<>(inStockSlabsWindow.getItems());
                    filteredSoldSlabs = new FilteredList<>(soldSlabsWindow.getItems());                    
                    
                    String width = filterMap.get("width");
                    filterInStockSlabsByWidth(width);
                    filterSoldSlabsByWidth(width);
                    
                    inStockSlabsWindow.setItems(FXCollections.observableArrayList(filteredInStockSlabs));
                    soldSlabsWindow.setItems(FXCollections.observableArrayList(filteredSoldSlabs));                 
                }
                if(!filterMap.get("length").equals("None")){
                    filteredInStockSlabs = new FilteredList<>(inStockSlabsWindow.getItems());
                    filteredSoldSlabs = new FilteredList<>(soldSlabsWindow.getItems());                    
                    
                    String length = filterMap.get("length");
                    filterInStockSlabsByLength(length);
                    filterSoldSlabsByLength(length);
                    
                    inStockSlabsWindow.setItems(FXCollections.observableArrayList(filteredInStockSlabs));
                    soldSlabsWindow.setItems(FXCollections.observableArrayList(filteredSoldSlabs));   
                }
            }else{
                JOptionPane.showMessageDialog(null,"You must select at least 1 filter!","Denied!",JOptionPane.ERROR_MESSAGE); 
            }
        }else{
            JOptionPane.showMessageDialog(null,"You can't filter the data on this page!","Denied!",JOptionPane.ERROR_MESSAGE); 
        }
    }

    private void turnFilterOff(){
        inStockSlabList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllInStockSlabs()));
        inStockSlabsWindow.setItems(FXCollections.observableArrayList(inStockSlabList));
        soldSlabList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllSoldSlabs()));
        soldSlabsWindow.setItems(FXCollections.observableArrayList(soldSlabList));
        resetFilters();
    }
    public void filterInStockSlabsByWoodCode(String selectedWoodCode){
        filteredInStockSlabs.setPredicate(row -> {
            String rowWoodCode = row.getWoodCode();
            return rowWoodCode.equals(selectedWoodCode);
        });    
    }
    
    public void filterSoldSlabsByWoodCode(String selectedWoodCode){
        filteredSoldSlabs.setPredicate(row -> {
            String rowWoodCode = row.getWoodCode();
            return rowWoodCode.equals(selectedWoodCode);
        });    
    }
    
    public void filterInStockSlabsByDryness(String selectedDryness){
        filteredInStockSlabs.setPredicate(row -> {
            String rowDryness = row.getIsDry();
            return rowDryness.equals(selectedDryness);
        });    
    }
    
    public void filterSoldSlabsByByDryness(String selectedDryness){
        filteredSoldSlabs.setPredicate(row -> {
            String rowDryness = row.getIsDry();
            return rowDryness.equals(selectedDryness);
        });      
    }
    
    public void filterInStockSlabsByFeature(String feature){
        
        filteredInStockSlabs.setPredicate(row -> {
            switch(feature){
                case"Kilned":
                    return row.isKilned();
                case"Curly":
                    return row.isCurly();
                case"Burled":
                    return row.isBurled();
                case"Spalted":
                    return row.isSpalted();
                case"Birds Eye":
                    return row.isBirdsEye();
                case"Pecky":
                    return row.isPecky();
                case"Ambrosia":
                    return row.isAmbrosia();
                case"Sinker":
                    return row.isSinker();
                case"Axe Sinker":
                    return row.isAxeSinker();
                case"Dressed":
                    return row.isDressed();
                default: return false;
            }                        
        });
    }
    
    public void filterSoldSlabsByFeature(String feature){
        filteredSoldSlabs.setPredicate(row -> {
            switch(feature){
                case"Kilned":
                    return row.getKilnedCharge()>0;
                case"Curly":
                    return row.getCurlyCharge()>0;
                case"Burled":
                    return row.getBurledCharge()>0;
                case"Spalted":
                    return row.getSpaltedCharge()>0;
                case"Birds Eye":
                    return row.getBirdsEyeCharge()>0;
                case"Pecky":
                    return row.getPeckyCharge()>0;
                case"Ambrosia":
                    return row.getAmbrosiaCharge()>0;
                case"Sinker":
                    return row.getSinkerCharge()>0;
                case"Axe Sinker":
                    return row.getAxeSinkerCharge()>0;
                case"Dressed":
                    return row.getDressedCharge()>0;
                default: return false;
            }                        
        });
    }
    public void filterInStockSlabsByThickness(String selectedThickness){
        ArrayList<Integer> lowHighRange = MeasurementsParser.getFirstTwoNumbersFromMixedString(selectedThickness);
        filteredInStockSlabs.setPredicate(row -> {
            Integer lowRange = lowHighRange.get(0);
            Integer highRange = lowHighRange.get(1);
            Double rowThickness = row.getThicknessInInches();
            return (rowThickness >= lowRange&&rowThickness<highRange);
        }); 
    }
    public void filterSoldSlabsByThickness(String selectedThickness){
        ArrayList<Integer> lowHighRange = MeasurementsParser.getFirstTwoNumbersFromMixedString(selectedThickness);
        filteredSoldSlabs.setPredicate(row -> {
            Integer lowRange = lowHighRange.get(0);
            Integer highRange = lowHighRange.get(1);
            Double rowThickness = row.getThicknessInInches();
            return (rowThickness >= lowRange&&rowThickness<highRange);
        });         
    }
    public void filterInStockSlabsByWidth(String selectedWidth){
        ArrayList<Integer> lowHighRange = MeasurementsParser.getFirstTwoNumbersFromMixedString(selectedWidth);
        filteredInStockSlabs.setPredicate(row -> {
            Integer lowRange = lowHighRange.get(0);
            Integer highRange = lowHighRange.get(1);
            Double rowWidth = row.getWidthInInches();
            return (rowWidth >= lowRange&&rowWidth<highRange);
        });     
    }
    public void filterSoldSlabsByWidth(String selectedWidth){
        ArrayList<Integer> lowHighRange = MeasurementsParser.getFirstTwoNumbersFromMixedString(selectedWidth);
        filteredSoldSlabs.setPredicate(row -> {
            Integer lowRange = lowHighRange.get(0);
            Integer highRange = lowHighRange.get(1);
            Double rowWidth = row.getWidthInInches();
            return (rowWidth >= lowRange&&rowWidth<highRange);
        });  
    }
    public void filterInStockSlabsByLength(String selectedLength){
        ArrayList<Integer> lowHighRange = MeasurementsParser.getFirstTwoNumbersFromMixedString(selectedLength);
        filteredInStockSlabs.setPredicate(row -> {
            Integer lowRange = lowHighRange.get(0);
            Integer highRange = lowHighRange.get(1);
            Double rowLength = row.getLengthInFeet();
            return (rowLength >= lowRange&&rowLength<highRange);
        });    
    }
    public void filterSoldSlabsByLength(String selectedLength){
        ArrayList<Integer> lowHighRange = MeasurementsParser.getFirstTwoNumbersFromMixedString(selectedLength);
        filteredSoldSlabs.setPredicate(row -> {
            Integer lowRange = lowHighRange.get(0);
            Integer highRange = lowHighRange.get(1);
            Double rowLength = row.getLengthInFeet();
            return (rowLength >= lowRange&&rowLength<highRange);
        });    
    }
    
    public final void resetFilters(){
        woodCodeFilterComboBox.getSelectionModel().select(0);
        dryFilterComboBox.getSelectionModel().select(0);
        featureFilter1ComboBox.getSelectionModel().select(0);
        featureFilter2ComboBox.getSelectionModel().select(0);
        featureFilter3ComboBox.getSelectionModel().select(0);
        thicknessFilterComboBox.getSelectionModel().select(0);
        widthFilterComboBox.getSelectionModel().select(0);
        lengthFilterComboBox.getSelectionModel().select(0);    
    }     
    
    
    //In-Stock Window Methods------------------------------------------------------------------------------------------------------------------------------------->
    private void setSelectedInStockSlabDetails(){
        try{
            TablePosition pos = (TablePosition) inStockSlabsWindow.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            InStockSlab selectedSlab = (InStockSlab)inStockSlabsWindow.getItems().get(row);
            currentInStockSlab = selectedSlab;
            inStockIdTextField.setText(selectedSlab.getSlabIdFormatted());
            inStockWoodCodeComboBox.getSelectionModel().select(selectedSlab.getWoodCode());
            inStockMilledDateDatePicker.setValue(selectedSlab.getMilledDate());
            inStockThickTextField.setText(selectedSlab.getThicknessInInchesFormatted());
            inStockWidthTextField.setText(selectedSlab.getWidthInInchesFormatted());
            inStockLengthTextField.setText(selectedSlab.getLengthInFeetFormatted());
            inStockMainAreaTextField.setText(selectedSlab.getLocationMain());
            inStockSubAreaTextField.setText(selectedSlab.getLocationSubArea());
            inStockRowTextField.setText(""+selectedSlab.getLocationRow());
            inStockColumnTextField.setText(""+selectedSlab.getLocationColumn());
            inStockSinkerCheckBox.setSelected(selectedSlab.isSinker());
            inStockAxeSinkerCheckBox.setSelected(selectedSlab.isAxeSinker());
            inStockAmbrosiaCheckBox.setSelected(selectedSlab.isAmbrosia());
            inStockBirdsEyeCheckBox.setSelected(selectedSlab.isBirdsEye()); 
            inStockBurledCheckBox.setSelected(selectedSlab.isBurled());
            inStockCurlyCheckBox.setSelected(selectedSlab.isCurly());
            inStockPeckyCheckBox.setSelected(selectedSlab.isPecky());    
            inStockSpaltedCheckBox.setSelected(selectedSlab.isSpalted());
            inStockKilnedCheckBox.setSelected(selectedSlab.isKilned());        
            inStockDressedCheckBox.setSelected(selectedSlab.isDressed());        
            try{
                inStockDateSoldDatePicker.setValue(selectedSlab.getDateSold());
                inStockSoldToCustomerTextField.setText(selectedSlab.getSoldToCustomer()); 
            }catch(NullPointerException ex){/*This may not be set. These variables exists to reprice already sold slabs*/}

            if(inStockSinkerCheckBox.isSelected()){
                if(editInStockSlabsTglBtn.isSelected()){
                    inStockAxeSinkerCheckBox.setDisable(false);
                }
            }else{
                inStockAxeSinkerCheckBox.setDisable(true);
                inStockAxeSinkerCheckBox.setSelected(false);                        
            } 
            newInStockSlab = false;
            if(editInStockSlabsTglBtn.isSelected()){
                deleteInStockSlabBtn.setVisible(true);
                sellInStockSlabBtn.setVisible(true);
            }
        }catch(IndexOutOfBoundsException ex){
            //nothing is there to select so do nothing
        }

    }
    
    public final void clearSelectedInStockSlabDetails(){
        newInStockSlab = true;
        deleteInStockSlabBtn.setVisible(false);
        sellInStockSlabBtn.setVisible(false);
        inStockSlabsWindow.getSelectionModel().clearSelection();
        currentInStockSlab = null;
        inStockIdTextField.setText("");
        inStockWoodCodeComboBox.getSelectionModel().select("");
        inStockMilledDateDatePicker.setValue(null);
        inStockThickTextField.setText("");
        inStockWidthTextField.setText("");
        inStockLengthTextField.setText("");
        inStockMainAreaTextField.setText("");
        inStockSubAreaTextField.setText("");
        inStockRowTextField.setText("");
        inStockColumnTextField.setText("");
        inStockSinkerCheckBox.setSelected(false);
        inStockAxeSinkerCheckBox.setSelected(false);
        inStockAxeSinkerCheckBox.setDisable(true);
        inStockAmbrosiaCheckBox.setSelected(false);
        inStockBirdsEyeCheckBox.setSelected(false); 
        inStockBurledCheckBox.setSelected(false);
        inStockCurlyCheckBox.setSelected(false);
        inStockPeckyCheckBox.setSelected(false);    
        inStockSpaltedCheckBox.setSelected(false);
        inStockKilnedCheckBox.setSelected(false);        
        inStockDressedCheckBox.setSelected(false); 
        inStockDateSoldDatePicker.setValue(null);
        inStockSoldToCustomerTextField.setText("");   
    }
    
    private void editInStockSlabsToggle(){
        if (editInStockSlabsTglBtn.isSelected()){
            newInStockSlabBtn.setVisible(true);
            saveInStockSlabBtn.setVisible(true);
            if(!newInStockSlab){
                deleteInStockSlabBtn.setVisible(true);
                sellInStockSlabBtn.setVisible(true);
            }
            inStockIdTextField.setEditable(true);
            inStockMilledDateDatePicker.setEditable(true);
            inStockThickTextField.setEditable(true);
            inStockWidthTextField.setEditable(true);
            inStockLengthTextField.setEditable(true);
            inStockMainAreaTextField.setEditable(true);
            inStockSubAreaTextField.setEditable(true);
            inStockRowTextField.setEditable(true);
            inStockColumnTextField.setEditable(true);
            inStockDateSoldDatePicker.setEditable(true);
            inStockSoldToCustomerTextField.setEditable(true);                    
            inStockSinkerCheckBox.setDisable(false);
            inStockWoodCodeComboBox.setDisable(false);
            if(inStockSinkerCheckBox.isSelected()){
                inStockAxeSinkerCheckBox.setDisable(false);
            }
            inStockAmbrosiaCheckBox.setDisable(false);
            inStockBirdsEyeCheckBox.setDisable(false);
            inStockBurledCheckBox.setDisable(false);
            inStockCurlyCheckBox.setDisable(false);
            inStockPeckyCheckBox.setDisable(false);
            inStockSpaltedCheckBox.setDisable(false);
            inStockKilnedCheckBox.setDisable(false); 
            inStockDressedCheckBox.setDisable(false);         
        }else{
            newInStockSlabBtn.setVisible(false);
            saveInStockSlabBtn.setVisible(false);
            deleteInStockSlabBtn.setVisible(false);
            sellInStockSlabBtn.setVisible(false);
            inStockIdTextField.setEditable(false);
            inStockMilledDateDatePicker.setEditable(false);
            inStockThickTextField.setEditable(false);
            inStockWidthTextField.setEditable(false);
            inStockLengthTextField.setEditable(false);
            inStockMainAreaTextField.setEditable(false);
            inStockSubAreaTextField.setEditable(false);
            inStockRowTextField.setEditable(false);
            inStockColumnTextField.setEditable(false);
            inStockDateSoldDatePicker.setEditable(false);
            inStockSoldToCustomerTextField.setEditable(false);                    
            inStockSinkerCheckBox.setDisable(true);
            inStockWoodCodeComboBox.setDisable(true);
            inStockAxeSinkerCheckBox.setDisable(true);
            inStockAmbrosiaCheckBox.setDisable(true);
            inStockBirdsEyeCheckBox.setDisable(true);
            inStockBurledCheckBox.setDisable(true);
            inStockCurlyCheckBox.setDisable(true);
            inStockPeckyCheckBox.setDisable(true);
            inStockSpaltedCheckBox.setDisable(true);
            inStockKilnedCheckBox.setDisable(true); 
            inStockDressedCheckBox.setDisable(true); 
        }
    }
    
    public final void saveSelectedInStockSlab(){
        Long id = -1L;
        String woodCode = null;
        LocalDate milledDate = null;
        Integer thick = null;
        Integer width = null;
        Integer length = null;
        String mainArea = null;
        String subArea;
        Integer row = null;
        Integer column = null;
        
        ArrayList<String> errorList = new ArrayList<>();
        if(inStockIdTextField.getText().equals("")){
            errorList.add(">>You must enter a Slab Id\n");
        }else{
            try{
                id = Long.parseLong(inStockIdTextField.getText());
                if(id<1){
                    errorList.add(">>Id field must at must be 1 or greater\n");
                }            
            }
            catch(NumberFormatException ex){
                errorList.add(">>Id field must be a number\n");
            }        
        }
        if(inStockIdTextField.getText().length()>10){
            errorList.add(">>Id field must at most 10 digits\n");
        }
        try{
            woodCode = inStockWoodCodeComboBox.getSelectionModel().getSelectedItem().toString();
            if(inStockWoodCodeComboBox.getSelectionModel().getSelectedItem().toString().equals("")){
                errorList.add(">>You must select a Wood Code\n");
            }        
        }catch(NullPointerException ex){
            errorList.add(">>You must select a Wood Code\n");
        }       
        if(inStockMilledDateDatePicker.getValue()==null){
            errorList.add(">>You must select a Milled Date\n");
        }else{
            milledDate=inStockMilledDateDatePicker.getValue();        
        }
        if(inStockThickTextField.getText().equals("")){
            errorList.add(">>You must enter Thick(in)\n");
        }else{
            try{
                thick = Integer.parseInt(inStockThickTextField.getText());
                if(thick<=0||thick>12){
                    errorList.add(">>Thick(in) must be between 0 and 12\n");
                }
            }
            catch(NumberFormatException ex){
                errorList.add(">>Thick(in) field must be a number\n");
            }
            
        }
         if(inStockWidthTextField.getText().equals("")){
            errorList.add(">>You must enter Width(in)\n");
        }else{
            try{
                width = Integer.parseInt(inStockWidthTextField.getText());
                if(width<8||width>30){
                    errorList.add(">>Width(in)must be between 8 and 30\n");
                }
            }
            catch(NumberFormatException ex){
                errorList.add(">>Width(in) field must be a number\n");
            }        
        }
         if(inStockLengthTextField.getText().equals("")){
            errorList.add(">>You must enter Length(in)\n");
        }else{
            try{
                length = Integer.parseInt(inStockLengthTextField.getText());
                if(length<2||length>38){
                    errorList.add(">>Length(ft) must be between 2 and 38\n");
                }
            }
            catch(NumberFormatException ex){
                errorList.add(">>Length(in) field must be a number\n");
            }        
        }
        if(inStockMainAreaTextField.getText().trim().equals("")){
            errorList.add(">>You must enter a Main location\n");
        }else{
            mainArea = inStockMainAreaTextField.getText().trim();
        }
        
        if(inStockSubAreaTextField.getText()==null){
            subArea = "" ;
        }else{
            subArea = inStockSubAreaTextField.getText().trim();
        }        
        
        if(inStockRowTextField.getText().equals("")){
            errorList.add(">>You must enter Row location\n");
        }else{
            try{
                row = Integer.parseInt(inStockRowTextField.getText());
                if(row<1){
                    errorList.add(">>Row must be greater than 0\n");
                }            
            }
            catch(NumberFormatException ex){
                errorList.add(">>Row field must be a number\n");
            }        
        }
        
        if(inStockColumnTextField.getText().equals("")){
            errorList.add(">>You must enter Column location\n");
        }else{
            try{
                column = Integer.parseInt(inStockColumnTextField.getText());
                if(column<1){
                    errorList.add(">>Column must be greater than 0\n");
                }                  
            }
            catch(NumberFormatException ex){
                errorList.add(">>Column field must be a number\n");
            }        
        }
     
        if(errorList.isEmpty()){            
            InStockSlab newOrUpdatedSlab = new InStockSlab(
                id,woodCode,milledDate,thick,width,length,
                inStockKilnedCheckBox.isSelected(),
                inStockCurlyCheckBox.isSelected(),
                inStockBurledCheckBox.isSelected(),
                inStockSpaltedCheckBox.isSelected(),
                inStockBirdsEyeCheckBox.isSelected(),
                inStockPeckyCheckBox.isSelected(),
                inStockAmbrosiaCheckBox.isSelected(),
                inStockSinkerCheckBox.isSelected(),
                inStockAxeSinkerCheckBox.isSelected(),
                inStockDressedCheckBox.isSelected(),                    
                mainArea,subArea,row,column,null,null);            
            if(newInStockSlab){
                for(Long slabId:DAO.getAllUsedIds()){
                    if (id.equals(slabId)){
                        JOptionPane.showMessageDialog(null,"Please pick another Id, that one is already in use.","Denied!",JOptionPane.ERROR_MESSAGE);  
                        return;
                    }
                }
                System.out.println("In-Stock Slab added:"+DAO.insertNewInStockSlab(newOrUpdatedSlab));
                newInStockSlab = false;
                inStockSlabList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllInStockSlabs()));
                inStockSlabsWindow.setItems(FXCollections.observableArrayList(inStockSlabList));
                for(InStockSlab slab:inStockSlabList){
                    if (slab.getSlabId().equals(newOrUpdatedSlab.getSlabId())){
                        newOrUpdatedSlab = slab;
                        break;
                    }
                }                 
                inStockSlabsWindow.getSelectionModel().select(newOrUpdatedSlab);
                inStockSlabsWindow.scrollTo(newOrUpdatedSlab);
                currentInStockSlab = newOrUpdatedSlab;
                setSelectedInStockSlabDetails();
                resetFilters();
            }else{
                if(!newOrUpdatedSlab.getSlabId().equals(currentInStockSlab.getSlabId())){
                    for(Long slabId:DAO.getAllUsedIds()){
                        if (id.equals(slabId)){
                            JOptionPane.showMessageDialog(null,"Please pick another Id, that one is already in use.","Denied!",JOptionPane.ERROR_MESSAGE);  
                            return;
                        }
                    }                
                }
                System.out.println("In-Stock Slab updated:"+DAO.updateInStockSlab(newOrUpdatedSlab,currentInStockSlab.getSlabId()));
                inStockSlabList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllInStockSlabs()));
                inStockSlabsWindow.setItems(FXCollections.observableArrayList(inStockSlabList));
                for(InStockSlab slab:inStockSlabList){
                    if (slab.getSlabId().equals(newOrUpdatedSlab.getSlabId())){
                        newOrUpdatedSlab = slab;
                        break;
                    }
                }                
                inStockSlabsWindow.getSelectionModel().select(newOrUpdatedSlab);
                inStockSlabsWindow.scrollTo(newOrUpdatedSlab);
                currentInStockSlab = newOrUpdatedSlab;
                setSelectedInStockSlabDetails();
                resetFilters();
            }            
        }else{
            StringBuilder sbError = new StringBuilder();
            sbError.append("There were some issues with the data input for this slab.\n"
                    + "You must fix these issues before it can be saved:\n");
            for(String s:errorList){
                sbError.append(s);
            }
            JOptionPane.showMessageDialog(null,sbError.toString(),"Denied!",JOptionPane.ERROR_MESSAGE);  
        }
    }
    
    public final void deleteSelectedInStockSlab(boolean slabIsBeingSold){  
        String returnValue;
        if(!slabIsBeingSold){
            returnValue = JOptionPane.showInputDialog(null, 
            "Are you sure that you want to delete that slab?\n"
            + "It will be gone forever and so will it's revenue record!\n"
            + "Please Enter the Admin Password",
            "Are you serious?!?!",JOptionPane.OK_CANCEL_OPTION); 
        }else{
        returnValue = DAO.getPasswordString();
        }
       
        if(returnValue!=null&&returnValue.equals(DAO.getPasswordString())){
            try{
                TablePosition pos = (TablePosition) inStockSlabsWindow.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                InStockSlab selectedSlab = (InStockSlab)inStockSlabsWindow.getItems().get(row);
                System.out.println("Rows Deleted:"+DAO.deleteInstockSlab(selectedSlab));
                inStockSlabList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllInStockSlabs()));
                inStockSlabsWindow.setItems(FXCollections.observableArrayList(inStockSlabList));
                if(inStockSlabList.isEmpty()){
                    clearSelectedInStockSlabDetails();
                }else{
                    if(row>0){
                        inStockSlabsWindow.getSelectionModel().select(row-1);
                    }else{
                        inStockSlabsWindow.getSelectionModel().select(row);
                    }
                    setSelectedSoldSlabDetails();          
                } 
                resetFilters();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"You must select a slab to delete!","Denied!",JOptionPane.ERROR_MESSAGE);                
            }         
        }else{
            if(returnValue!=null){
                JOptionPane.showMessageDialog(null, "That password is incorrect!","Incorrect Password",JOptionPane.INFORMATION_MESSAGE);
            }       
        }        
    
     }
    
    public final void sellInStockSlab(){
        ArrayList<String> errorList = new ArrayList<>();
                
        if(inStockDateSoldDatePicker.getValue()==null){
            errorList.add(">>You must select a sell date\n");
        }
        try{
            if(inStockSoldToCustomerTextField.getText().equals("")){
                errorList.add(">>You must enter a customer name\n");
            }        
        }catch(NullPointerException ex){
            errorList.add(">>You must enter a customer name\n");
        }        
        if(errorList.isEmpty()){
            TablePosition pos = (TablePosition) inStockSlabsWindow.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();            
            InStockSlab selectedSlab = (InStockSlab)inStockSlabsWindow.getItems().get(row);            
            selectedSlab.setDateSold(inStockDateSoldDatePicker.getValue());
            selectedSlab.setSoldToCustomer(inStockSoldToCustomerTextField.getText().trim());
            deleteSelectedInStockSlab(true);
            System.out.println("Slabs Insert Into Sold: "+DAO.insertNewSoldSlab(selectedSlab.convertInStockSlabToSoldSlab()));
            inStockSlabList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllInStockSlabs()));
            inStockSlabsWindow.setItems(FXCollections.observableArrayList(inStockSlabList));
            resetFilters();
            soldSlabList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllSoldSlabs()));
            soldSlabsWindow.setItems(FXCollections.observableArrayList(soldSlabList));
            
         } else{
            StringBuilder sbError = new StringBuilder();
            sbError.append("There were some issues with the data input for selling this slab.\n"
                    + "You must fix these issues before it can be sold:\n");
            for(String s:errorList){
                sbError.append(s);
            }
            JOptionPane.showMessageDialog(null,sbError.toString(),"Denied!",JOptionPane.ERROR_MESSAGE);          
        } 

    }
    
    //Sold slab window methods------------------------------------------------------------------------------------------------------------------------------------>
    private void setSelectedSoldSlabDetails(){
        try{
            TablePosition pos = (TablePosition) soldSlabsWindow.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            SoldSlab selectedSlab = (SoldSlab)soldSlabsWindow.getItems().get(row);
            //currentSoldSlab = selectedSlab;//not used but left for future implmentations of update sold slabs
            soldSlabsIdTextField.setText(selectedSlab.getSlabIdFormatted());
            soldSlabsWoodCodeComboBox.getSelectionModel().select(selectedSlab.getWoodCode());
            soldSlabsMilledDateDatePicker.setValue(selectedSlab.getMilledDate());
            soldSlabsThickTextField.setText(selectedSlab.getThicknessInInchesFormatted());
            soldSlabsWidthTextField.setText(selectedSlab.getWidthInInchesFormatted());
            soldSlabsLengthTextField.setText(selectedSlab.getLengthInFeetFormatted());
            soldSlabsMainAreaTextField.setText(selectedSlab.getLocationMain());
            soldSlabsSubAreaTextField.setText(selectedSlab.getLocationSubArea());
            soldSlabsRowTextField.setText(""+selectedSlab.getLocationRow());
            soldSlabsColumnTextField.setText(""+selectedSlab.getLocationColumn());
            soldSlabsSinkerTextField.setText(""+selectedSlab.getSinkerChargeFormatted());
            soldSlabsAxeSinkerTextField.setText(""+selectedSlab.getAxeSinkerChargeFormatted());
            soldSlabsAmbrosiaTextField.setText(""+selectedSlab.getAmbrosiaChargeFormatted());
            soldSlabsBirdEyeTextField.setText(""+selectedSlab.getBirdsEyeChargeFormatted()); 
            soldSlabsBurledTextField.setText(""+selectedSlab.getBurledChargeFormatted());
            soldSlabsCurlyTextField.setText(""+selectedSlab.getCurlyChargeFormatted());
            soldSlabsPeckyTextField.setText(""+selectedSlab.getPeckyChargeFormatted());    
            soldSlabsSpaltedTextField.setText(""+selectedSlab.getSpaltedChargeFormatted());
            soldSlabsKilnedTextField.setText(""+selectedSlab.getKilnedChargeFormatted());        
            soldSlabsDressedTextField.setText(""+selectedSlab.getDressedChargeFormatted());        
            soldSlabsDateSoldTextField.setValue(selectedSlab.getDateSold());
            soldSlabsSoldToCustomerTextField.setText(""+selectedSlab.getSoldToCustomer());  
        }catch(IndexOutOfBoundsException ex){
         //nothing is there to select so do nothing
        }
    } 

    public final void clearSelectedSoldSlab(){
            soldSlabsIdTextField.setText("");
            soldSlabsWoodCodeComboBox.getSelectionModel().select("");
            soldSlabsMilledDateDatePicker.setValue(null);
            soldSlabsThickTextField.setText("");
            soldSlabsWidthTextField.setText("");
            soldSlabsLengthTextField.setText("");
            soldSlabsMainAreaTextField.setText("");
            soldSlabsSubAreaTextField.setText("");
            soldSlabsRowTextField.setText("");
            soldSlabsColumnTextField.setText("");
            soldSlabsSinkerTextField.setText("");
            soldSlabsAxeSinkerTextField.setText("");
            soldSlabsAmbrosiaTextField.setText("");
            soldSlabsBirdEyeTextField.setText(""); 
            soldSlabsBurledTextField.setText("");
            soldSlabsCurlyTextField.setText("");
            soldSlabsPeckyTextField.setText("");    
            soldSlabsSpaltedTextField.setText("");
            soldSlabsKilnedTextField.setText("");        
            soldSlabsDressedTextField.setText("");        
            soldSlabsDateSoldTextField.setValue(null);
            soldSlabsSoldToCustomerTextField.setText(""); 
    }
    
    public final void returnSoldSlabToStock(){
        String returnValue = JOptionPane.showInputDialog(null,
            "Are you sure that you want to put this slab back into inventory?\n"
            + "The price value will be recalculated according to current species pricing!\n"
            + "This can make historical revenue records inaccurate.\n"
            + "Please Enter Admin Password.",
            "Are You Serious?!?!?", JOptionPane.WARNING_MESSAGE);        
        if(returnValue!=null&&returnValue.equals(DAO.getPasswordString())){
            TablePosition pos = (TablePosition) soldSlabsWindow.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            SoldSlab selectedSlab = (SoldSlab)soldSlabsWindow.getItems().get(row);
            deleteSelectedSoldSlab(true);
            System.out.println("Slabs inserted into In-Stock: "+DAO.insertNewInStockSlab(selectedSlab.convertSoldSlabToInStockSlab()));
            resetFilters();
            inStockSlabList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllInStockSlabs()));
            inStockSlabsWindow.setItems(FXCollections.observableArrayList(inStockSlabList));
        }else{
            if(returnValue!=null){
                JOptionPane.showMessageDialog(null, "That password is incorrect!","Incorrect Password",JOptionPane.INFORMATION_MESSAGE);
            }       
        }   
    }
    
    public final void deleteSelectedSoldSlab(boolean slabIsBeingReturned){        
        String returnValue;
        if(!slabIsBeingReturned){
            returnValue = JOptionPane.showInputDialog(null, 
            "Are you sure that you want to delete that slab?\n"
            + "It will be gone forever and so will it's revenue record!\n"
            + "Please Enter the Admin Password.",
            "Are you serious?!?!",JOptionPane.OK_CANCEL_OPTION);        
        }else{
            returnValue = DAO.getPasswordString();
        }
        if(returnValue!=null&&returnValue.equals(DAO.getPasswordString())){
            try{
                TablePosition pos = (TablePosition) soldSlabsWindow.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                SoldSlab selectedSlab = (SoldSlab)soldSlabsWindow.getItems().get(row);
                System.out.println("Rows Deleted:"+DAO.deleteSoldSlab(selectedSlab));
                soldSlabList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllSoldSlabs()));
                soldSlabsWindow.setItems(FXCollections.observableArrayList(soldSlabList));
                if(soldSlabList.isEmpty()){
                    clearSelectedSoldSlab();
                }else{
                    if(row>0){
                        soldSlabsWindow.getSelectionModel().select(row-1);
                    }else{
                        soldSlabsWindow.getSelectionModel().select(row);
                    }
                    setSelectedSoldSlabDetails();                
                }
                resetFilters();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"You must select a slab to delete!","Denied!",JOptionPane.ERROR_MESSAGE);                
            }   
        }else{
            if(returnValue!=null){
                JOptionPane.showMessageDialog(null, "That password is incorrect!","Incorrect Password",JOptionPane.INFORMATION_MESSAGE);
            }
        }         
     }

    //Species window methods-------------------------------------------------------------------------------------------------------------------------------------->
    private void setSelectedSpeciesDetails(){
        try{
            newSpecies = false;
            if(editSpeciesTglBtn.isSelected()){
                deleteSpeciesBtn.setVisible(true);
            }        
            TablePosition pos = (TablePosition) speciesWindow.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            Species selectedSpecies = (Species)speciesWindow.getItems().get(row);
            currentSpecies = selectedSpecies;
            speciesWoodCodeTextField.setText(selectedSpecies.getSpeciesWoodCode());
            speciesNameTextField.setText(""+selectedSpecies.getSpeciesName());
            species8inTo11inTextField.setText(""+selectedSpecies.getPrice8InTo11InNumberFormatted());
            species12inTo20inTextField.setText(""+selectedSpecies.getPrice12InTo20InNumberFormatted());
            species21inTo23inTextField.setText(""+selectedSpecies.getPrice21InTo23InNumberFormatted());
            species24inTo30inTextField.setText(""+selectedSpecies.getPrice24InTo30InNumberFormatted());
            speciesSinkerTextField.setText(""+selectedSpecies.getSinkerPriceNumberFormatted());
            speciesAxeSinkerTextField.setText(""+selectedSpecies.getAxeSinkerPriceNumberFormatted());
            speciesAmbrosiaTextField.setText(""+selectedSpecies.getAmbrosiaPriceNumberFormatted());
            speciesBirdEyeTextField.setText(""+selectedSpecies.getBirdsEyePriceNumberFormatted()); 
            speciesBurledTextField.setText(""+selectedSpecies.getBurledPriceNumberFormatted());
            speciesCurlyTextField.setText(""+selectedSpecies.getCurlyPriceNumberFormatted());
            speciesPeckyTextField.setText(""+selectedSpecies.getPeckyPriceNumberFormatted());    
            speciesSpaltedTextField.setText(""+selectedSpecies.getSpaltedPriceNumberFormatted());
            speciesKilnedTextField.setText(""+selectedSpecies.getKilnedPriceNumberFormatted());        
            speciesDressedTextField.setText(""+selectedSpecies.getDressedPriceNumberFormatted());         
        }catch(IndexOutOfBoundsException ex){
         //nothing is there to select so do nothing
        }    
    }
    
    public final void clearSelectedSpeciesDetails(){
        newSpecies = true;
        deleteSpeciesBtn.setVisible(false);
        speciesWindow.getSelectionModel().clearSelection();
        currentSpecies = null;
        speciesWoodCodeTextField.setText("");
        speciesNameTextField.setText("");
        species8inTo11inTextField.setText("");
        species12inTo20inTextField.setText("");
        species21inTo23inTextField.setText("");
        species24inTo30inTextField.setText("");
        speciesSinkerTextField.setText("");
        speciesAxeSinkerTextField.setText("");
        speciesAmbrosiaTextField.setText("");
        speciesBirdEyeTextField.setText(""); 
        speciesBurledTextField.setText("");
        speciesCurlyTextField.setText("");
        speciesPeckyTextField.setText("");    
        speciesSpaltedTextField.setText("");
        speciesKilnedTextField.setText("");        
        speciesDressedTextField.setText("");   
    }
    
    public final void editSpeciesToggle(){
        if(editSpeciesTglBtn.isSelected()){
            newSpeciesBtn.setVisible(true);
            saveSpeciesBtn.setVisible(true);
            deleteSpeciesBtn.setVisible(true);                    
            speciesWoodCodeTextField.setEditable(true);
            speciesNameTextField.setEditable(true);
            species8inTo11inTextField.setEditable(true);
            species12inTo20inTextField.setEditable(true);
            species21inTo23inTextField.setEditable(true);
            species24inTo30inTextField.setEditable(true);
            speciesSinkerTextField.setEditable(true);
            speciesAxeSinkerTextField.setEditable(true);
            speciesAmbrosiaTextField.setEditable(true);
            speciesBirdEyeTextField.setEditable(true);
            speciesBurledTextField.setEditable(true);
            speciesCurlyTextField.setEditable(true);
            speciesPeckyTextField.setEditable(true);  
            speciesSpaltedTextField.setEditable(true);
            speciesKilnedTextField.setEditable(true);     
            speciesDressedTextField.setEditable(true);        
        }else{
            newSpeciesBtn.setVisible(false);
            saveSpeciesBtn.setVisible(false);
            deleteSpeciesBtn.setVisible(false);                    
            speciesWoodCodeTextField.setEditable(false);
            speciesNameTextField.setEditable(false);
            species8inTo11inTextField.setEditable(false);
            species12inTo20inTextField.setEditable(false);
            species21inTo23inTextField.setEditable(false);
            species24inTo30inTextField.setEditable(false);
            speciesSinkerTextField.setEditable(false);
            speciesAxeSinkerTextField.setEditable(false);
            speciesAmbrosiaTextField.setEditable(false);
            speciesBirdEyeTextField.setEditable(false);
            speciesBurledTextField.setEditable(false);
            speciesCurlyTextField.setEditable(false);
            speciesPeckyTextField.setEditable(false);  
            speciesSpaltedTextField.setEditable(false);
            speciesKilnedTextField.setEditable(false);     
            speciesDressedTextField.setEditable(false);        
        }
    }
    
    public final void saveSelectedSpecies(){
        
        String speciesWoodCode=null;
        String speciesName=null;
        double price8InTo11In=0.0;
        double price12InTo20In=0.0;
        double price21InTo23In=0.0;
        double price24InTo30In=0.0;
        double sinkerPrice=0.0;
        double axeSinkerPrice=0.0;
        double ambrosiaPrice=0.0;
        double birdsEyePrice=0.0;
        double burledPrice=0.0;
        double curlyPrice=0.0;
        double peckyPrice=0.0;
        double spaltedPrice=0.0; 
        double dressedPrice=0.0;
        double kilnedPrice=0.0;
        
        ArrayList<String> errorList = new ArrayList<>();
        if(speciesWoodCodeTextField.getText().equals("")||speciesWoodCodeTextField.getText().trim().length()!=4){
            errorList.add(">>You must enter a Wood Code with a length of four letters\n");
        }else{
            speciesWoodCode = speciesWoodCodeTextField.getText().trim().toUpperCase();
        }
        if(speciesNameTextField.getText().equals("")){
            errorList.add(">>You must enter a Species Name\n");
        }else{
            speciesName = speciesNameTextField.getText().trim();
        }
        
        if(species8inTo11inTextField.getText().equals("")){
            errorList.add(">>You must enter \'8 to 12\' price\n");
        }else{
            try{
                price8InTo11In = Double.parseDouble(species8inTo11inTextField.getText());
                if(price8InTo11In<=0){
                    errorList.add(">>\'8 to 12\' price must be greater than 0\n");
                }                  
            }
            catch(NumberFormatException ex){
                errorList.add(">>\'8 to 12\' price must be a number\n");
            }        
        }
         if(species12inTo20inTextField.getText().equals("")){
            errorList.add(">>You must enter \'12 to 20\' price\n");
        }else{
            try{
                price12InTo20In = Double.parseDouble(species12inTo20inTextField.getText());
                if(price12InTo20In<=0){
                    errorList.add(">>\'12 to 20\' price must be greater than 0\n");
                }                  
            }
            catch(NumberFormatException ex){
                errorList.add(">>\'12 to 20\' price must be a number\n");
            }        
        }
         if(species21inTo23inTextField.getText().equals("")){
            errorList.add(">>You must enter \'21 to 23\' price\n");
        }else{
            try{
                price21InTo23In = Double.parseDouble(species21inTo23inTextField.getText());
                if(price21InTo23In<=0){
                    errorList.add(">>\'21 to 23\' price must be greater than 0\n");
                }                  
            }
            catch(NumberFormatException ex){
                errorList.add(">>\'21 to 23\' price must be a number\n");
            }        
        }
         if(species24inTo30inTextField.getText().equals("")){
            errorList.add(">>You must enter \'24 to 30\' price\n");
        }else{
            try{
                price24InTo30In = Double.parseDouble(species24inTo30inTextField.getText());
                if(price24InTo30In<=0){
                    errorList.add(">>\'24 to 30\' price must be greater than 0\n");
                }                  
            }
            catch(NumberFormatException ex){
                errorList.add(">>\'24 to 30\' price must be a number\n");
            }        
        }        
         if(speciesSinkerTextField.getText().equals("")){
            errorList.add(">>You must enter \'Sinker\' price\n");
        }else{
            try{
                sinkerPrice = Double.parseDouble(speciesSinkerTextField.getText());
                if(sinkerPrice<=0){
                    errorList.add(">>\'Sinker\' price must be greater than 0\n");
                }                  
            }
            catch(NumberFormatException ex){
                errorList.add(">>\'Sinker\' price must be a number\n");
            }        
        }        
         if(speciesAxeSinkerTextField.getText().equals("")){
            errorList.add(">>You must enter \'Axe Sinker\' price\n");
        }else{
            try{
                axeSinkerPrice = Double.parseDouble(speciesAxeSinkerTextField.getText());
                if(axeSinkerPrice<=0){
                    errorList.add(">>\'Axe Sinker\' price must be greater than 0\n");
                }                  
            }
            catch(NumberFormatException ex){
                errorList.add(">>\'Axe Sinker\' price must be a number\n");
            }        
        }        
         if(speciesAmbrosiaTextField.getText().equals("")){
            errorList.add(">>You must enter \'Ambrosia\' price\n");
        }else{
            try{
                ambrosiaPrice = Double.parseDouble(speciesAmbrosiaTextField.getText());
                if(ambrosiaPrice<=0){
                    errorList.add(">>\'Ambrosia\' price must be greater than 0\n");
                }                  
            }
            catch(NumberFormatException ex){
                errorList.add(">>\'Ambrosia\' price must be a number\n");
            }        
        }        
         if(speciesBirdEyeTextField.getText().equals("")){
            errorList.add(">>You must enter \'Birds Eye\' price\n");
        }else{
            try{
                birdsEyePrice = Double.parseDouble(speciesBirdEyeTextField.getText());
                if(birdsEyePrice<=0){
                    errorList.add(">>\'Birds Eye\' price must be greater than 0\n");
                }                  
            }
            catch(NumberFormatException ex){
                errorList.add(">>\'Birds Eye\' price must be a number\n");
            }        
        }        
         if(speciesBurledTextField.getText().equals("")){
            errorList.add(">>You must enter \'Burled\' price\n");
        }else{
            try{
                burledPrice = Double.parseDouble(speciesBurledTextField.getText());
                if(burledPrice<=0){
                    errorList.add(">>\'Burled\' price must be greater than 0\n");
                }                  
            }
            catch(NumberFormatException ex){
                errorList.add(">>\'Burled\' price must be a number\n");
            }        
        }        
         if(speciesCurlyTextField.getText().equals("")){
            errorList.add(">>You must enter \'Curly\' price\n");
        }else{
            try{
                curlyPrice = Double.parseDouble(speciesCurlyTextField.getText());
                if(curlyPrice<=0){
                    errorList.add(">>\'Curly\' price must be greater than 0\n");
                }                  
            }
            catch(NumberFormatException ex){
                errorList.add(">>\'Curly\' price must be a number\n");
            }        
        }        
         if(speciesPeckyTextField.getText().equals("")){
            errorList.add(">>You must enter \'Pecky\' price\n");
        }else{
            try{
                peckyPrice = Double.parseDouble(speciesPeckyTextField.getText());
                if(peckyPrice<=0){
                    errorList.add(">>\'Pecky\' price must be greater than 0\n");
                }                  
            }
            catch(NumberFormatException ex){
                errorList.add(">>\'Pecky\' price must be a number\n");
            }        
        }        
         if(speciesSpaltedTextField.getText().equals("")){
            errorList.add(">>You must enter \'Spalted\' price\n");
        }else{
            try{
                spaltedPrice = Double.parseDouble(speciesSpaltedTextField.getText());
                if(spaltedPrice<=0){
                    errorList.add(">>\'Spalted\' price must be greater than 0\n");
                }                  
            }
            catch(NumberFormatException ex){
                errorList.add(">>\'Spalted\' price must be a number\n");
            }        
        }        
         if(speciesKilnedTextField.getText().equals("")){
            errorList.add(">>You must enter \'Kilned\' price\n");
        }else{
            try{
                kilnedPrice = Double.parseDouble(speciesKilnedTextField.getText());
                if(kilnedPrice<=0){
                    errorList.add(">>\'Kilned\' price must be greater than 0\n");
                }                  
            }
            catch(NumberFormatException ex){
                errorList.add(">>\'Kilned\' price must be a number\n");
            }        
        }        
         if(speciesDressedTextField.getText().equals("")){
            errorList.add(">>You must enter \'Dressed\' price\n");
        }else{
            try{
                dressedPrice = Double.parseDouble(speciesDressedTextField.getText());
                if(dressedPrice<=0){
                    errorList.add(">>\'Dressed\' price must be greater than 0\n");
                }                  
            }
            catch(NumberFormatException ex){
                errorList.add(">>\'Dressed\' price must be a number\n");
            }        
        }        
        
        if(errorList.isEmpty()){
            Species speciesToSave = new Species(speciesWoodCode,speciesName,price8InTo11In,price12InTo20In,
                    price21InTo23In,price24InTo30In,ambrosiaPrice,axeSinkerPrice,birdsEyePrice,burledPrice, 
                    curlyPrice,dressedPrice,kilnedPrice,peckyPrice,sinkerPrice,spaltedPrice);
            if(newSpecies){
                for(Species species:DAO.getAllSpecies()){
                    if (species.getSpeciesWoodCode().equals(speciesWoodCode)){
                        JOptionPane.showMessageDialog(null,"Please pick another Wood Code, that one is already in use.","Denied!",JOptionPane.ERROR_MESSAGE);  
                        return;
                    }
                }
                System.out.println("Species added:"+DAO.insertNewSpecies(speciesToSave));
                newSpecies = false;
                speciesList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllSpecies()));
                speciesWindow.setItems(FXCollections.observableArrayList(speciesList));
                for(Species species:speciesList){
                    if (species.getSpeciesWoodCode().equals(speciesToSave.getSpeciesWoodCode())){
                        speciesToSave = species;
                        break;
                    }
                }
                speciesWindow.getSelectionModel().select(speciesToSave);
                speciesWindow.scrollTo(speciesToSave);
                currentSpecies = speciesToSave;
                setSelectedSpeciesDetails();
                inStockSlabList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllInStockSlabs()));
                inStockSlabsWindow.setItems(FXCollections.observableArrayList(inStockSlabList));                
            }else{
                if(!speciesToSave.getSpeciesWoodCode().equals(currentSpecies.getSpeciesWoodCode())){
                for(Species species:DAO.getAllSpecies()){
                    if (species.getSpeciesWoodCode().equals(speciesWoodCode)){
                        JOptionPane.showMessageDialog(null,"Please pick another Wood Code, that one is already in use.","Denied!",JOptionPane.ERROR_MESSAGE);  
                        return;
                    }
                }               
                }
                System.out.println("Species updated:"+DAO.updateSpecies(speciesToSave, currentSpecies.getSpeciesWoodCode()));
                speciesList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllSpecies()));
                speciesWindow.setItems(FXCollections.observableArrayList(speciesList));
                for(Species species:speciesList){
                    if (species.getSpeciesWoodCode().equals(speciesToSave.getSpeciesWoodCode())){
                        speciesToSave = species;
                        break;
                    }
                }                
                speciesWindow.getSelectionModel().select(speciesToSave);
                speciesWindow.scrollTo(speciesToSave);
                currentSpecies = speciesToSave;
                setSelectedSpeciesDetails(); 
                inStockSlabList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllInStockSlabs()));
                inStockSlabsWindow.setItems(FXCollections.observableArrayList(inStockSlabList));                
            }
        }else{
            StringBuilder sbError = new StringBuilder();
            sbError.append("There were some issues with the data input for this species.\n"
                    + "You must fix these issues before it can be saved:\n");
            for(String s:errorList){
                sbError.append(s);
            }
            JOptionPane.showMessageDialog(null,sbError.toString(),"Denied!",JOptionPane.ERROR_MESSAGE);          
        
        }
    }
    
    public final void deleteSelectedSpecies(){
        String returnValue = JOptionPane.showInputDialog(null, 
                "Are you sure that you want to delete that species?\n"
                + "It will be gone forever!! \n"
                + "This DELETE WILL FAIL IF you have ANY SLABS in your database that are OF THIS SPECIES!\n"
                + "Please Enter Admin Password.",
                "Are you serious?!?!",JOptionPane.OK_CANCEL_OPTION);   
        if(returnValue!=null&&returnValue.equals(DAO.getPasswordString())){
            try{
                TablePosition pos = (TablePosition) speciesWindow.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                Species selectedSpecies = (Species)speciesWindow.getItems().get(row);
                System.out.println("Rows Deleted:"+DAO.deleteSpecies(selectedSpecies));
                speciesList = new FilteredList<>(FXCollections.observableArrayList(DAO.getAllSpecies()));
                speciesWindow.setItems(FXCollections.observableArrayList(speciesList));
                if(speciesList.isEmpty()){
                    clearSelectedSpeciesDetails();
                }else{
                    speciesWindow.getSelectionModel().select(row);
                    setSelectedSpeciesDetails();                
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"You must select a species to delete!","Denied!",JOptionPane.ERROR_MESSAGE);                
            }   
        }else{
            if(returnValue!=null){
                JOptionPane.showMessageDialog(null, "That password is incorrect!","Incorrect Password",JOptionPane.INFORMATION_MESSAGE);
            }
        }    
    }
    //Utilities Functions-------------------------------------------------------->
    public final void changeAdminPassword(){
        String passField1 = newPasswordField1.getText();
        String passField2 = newPasswordField2.getText();
        if(passField1.equals(passField2)){
            System.out.println("Passwords Updated:"+DAO.updatePassword(passField1));
            JOptionPane.showMessageDialog(null, "New Password is set!","Woop Woop!",JOptionPane.INFORMATION_MESSAGE);
            newPasswordField1.clear();
            newPasswordField2.clear();
    }else{
            JOptionPane.showMessageDialog(null, "You new passwords do no match! Try again please!","Ooooooopsie!",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public final void changeDatabaseConnection(String newConnection){
        if(FileHandler.checkFilePath(newConnection)){
            DBConnector.setDBPath(newConnection);
            FileHandler.overwriteDatabaseConfig(newConnection);
            SlabTracker newSlabTracker = new SlabTracker(primaryStage,databasePathField.getText().trim());
            slabTrackerWindow.close();
        }
    }
    
    private void exportSlabsBeforeDate(String exportToDB,LocalDate exportBeforeDate){
        String returnValue = JOptionPane.showInputDialog(null, 
                "Are you sure that you want to Export these slabs?\n"
                +"These slabs will be removed from this Database &\n" 
                +"could possibly have their IDs changed!\n"
                +"Please Enter Admin Password.",
                "Are you serious?!?!",JOptionPane.OK_CANCEL_OPTION);   
        if(returnValue!=null&&returnValue.equals(DAO.getPasswordString())){
            String exportFromDB = DBConnector.getDBPath();
            ArrayList<SoldSlab> slabsToExport = DAO.getSoldSlabsByDate(exportBeforeDate);
            int rowsChanged;
            boolean exportSuccessful = true;
            DBConnector.setDBPath(exportToDB);
            rowsChanged = DAO.insertSoldSlabListWithIdCorrection(slabsToExport);
            if(rowsChanged == 0){
                exportSuccessful = false;
            }
            System.out.println("Export Successful: "+exportSuccessful);
            DBConnector.setDBPath(exportFromDB);
            slabsToExport = DAO.getSoldSlabsByDate(exportBeforeDate);
            if(rowsChanged>0){
                for(SoldSlab slab: slabsToExport){
                    DAO.deleteSoldSlab(slab);
                }
                JOptionPane.showMessageDialog(null, "Export Complete!","Success!",JOptionPane.INFORMATION_MESSAGE);
                SlabTracker newSlabTracker = new SlabTracker(primaryStage,databasePathField.getText().trim());
                slabTrackerWindow.close();
            }else{
                JOptionPane.showMessageDialog(null, "Export Aborted!","Nothing Done!",JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            if(returnValue!=null){
                JOptionPane.showMessageDialog(null, "You password is incorrect! Try again please!","Ooooooopsie!",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void importSlabsFromAnotherDatabase(String importFromPath){
        String returnValue = JOptionPane.showInputDialog(null, 
                "Are you sure that you want to Import these slabs?\n"
                +"If you are mistaken you will have to delete them\n" 
                +"each singularly!  Imported slab IDs may change if\n"
                +"they have already been used in this database!"        
                +"Please Enter Admin Password.",
                "Are you serious?!?!",JOptionPane.OK_CANCEL_OPTION);   
        if(returnValue!=null&&returnValue.equals(DAO.getPasswordString())){
            String importToDB = DBConnector.getDBPath();
            ArrayList<InStockSlab> inStockSlabsImportList = new ArrayList<>();
            ArrayList<SoldSlab> soldSlabsImportList = new ArrayList<>();
            int rowsAdded;
            boolean importInStockSlabsSuccessful = false;
            boolean importSoldSlabsSuccessful = false;
            DBConnector.setDBPath(importFromPath);
            if(importInStockRB.isSelected()||importBothRB.isSelected()){
                inStockSlabsImportList = DAO.getAllInStockSlabs();
            }
            if(importSoldRB.isSelected()||importBothRB.isSelected()){
                soldSlabsImportList = DAO.getAllSoldSlabs();
            }
            DBConnector.setDBPath(importToDB);    
            if(importInStockRB.isSelected()||importBothRB.isSelected()){
                if(inStockSlabsImportList.isEmpty()){
                    JOptionPane.showMessageDialog(null, "There are no In-Stock Slabs in that table!","Ooooooopsie!",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    int rowsImported = DAO.insertInStockSlabListWithIdCorrection(inStockSlabsImportList);
                    if(rowsImported>0){
                        importInStockSlabsSuccessful = true;
                    }
                    JOptionPane.showMessageDialog(null, "In-Stock Slabs Imported!","Success!",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if(importSoldRB.isSelected()||importBothRB.isSelected()){
                if(soldSlabsImportList.isEmpty()){
                    JOptionPane.showMessageDialog(null, "There are no Sold Slabs in that table!","Ooooooopsie!",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    int rowsImported = DAO.insertSoldSlabListWithIdCorrection(soldSlabsImportList);
                    if(rowsImported>0){
                        importSoldSlabsSuccessful = true;
                    }
                    JOptionPane.showMessageDialog(null, "Sold Slabs Imported!","Success!",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if(importInStockSlabsSuccessful||importSoldSlabsSuccessful){
                SlabTracker newSlabTracker = new SlabTracker(primaryStage,databasePathField.getText().trim());
                slabTrackerWindow.close();
            }
        }else{
            if(returnValue!=null){
                JOptionPane.showMessageDialog(null, "You password is incorrect! Try again please!","Ooooooopsie!",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
