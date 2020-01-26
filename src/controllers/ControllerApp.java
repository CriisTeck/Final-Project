package controllers;
import exceptions.DepartmentNotFoundException;
import exceptions.InvalidConstantException;
import exceptions.MineNotFoundException;
import exceptions.OptionInvalidException;
import models.Colombia;
import models.DepartmentName;
import models.MineType;
import models.OreType;
import views.IOManager;
public class ControllerApp {
	private IOManager view;
	private Colombia model;
	
	public ControllerApp() {
		view = new IOManager();
		model = new Colombia();
		model.autogenerateDepartment();
		init();
	}
	
	public void init() {
		//		addMine();
		try {
			int option;
			do {
				option = view.selectOptionMenu();
				switch (option) {
				case 1:
					showTable();
					break;
				case 2: 
					addMine();
					break;
				case 3: 
					removeMine();
					break;
				case 4: 
					editMine();
					break;
				case 5:
					realizeEconomy();
					break;
				case 6:
					selectReports();
					break;
				case 7:
					System.exit(0);
					break;
				default:
					throw new OptionInvalidException();
				}
			} while (option != 7 );
		} catch(OptionInvalidException e) {
			view.showError(e.getMessage());
			init();
		} catch(NumberFormatException e2) {
			view.showError("Opcion Invalida");
			init();
		} catch (DepartmentNotFoundException e) {
			view.showError(e.getMessage());
			init();
		}
	}
	

	public void showTable() {
		view.showTable(model.getListDepartment());
	}
	
	public void addMine() {
		try {
			model.createMine(view.readDepartment(), view.readNameMine(), view.readMeterMine(), view.readOreType(), view.readKilogram(), view.readMineType(), view.readBudgetMine());
			view.showSucesfull();
		} catch (DepartmentNotFoundException | NumberFormatException e) {
			view.showError(e.getMessage());
		}
	}
	
	public void removeMine() {
		try {
			model.sendRemoveMine(view.readDepartment(), view.readId());
			view.showSucesfull();
		} catch (MineNotFoundException | NumberFormatException e) {
			view.showError(e.getMessage());
		}
	}
	
	public void editMine() throws OptionInvalidException {
		int option = view.selectOptionMenuEdit();
		switch (option) {
		case 1:
			editKilogramPrice();
			break;
		case 2:
			editOreType();
			break;
		case 3:
			break;
		default:
			throw new OptionInvalidException();
		}
	}
	
	public void editKilogramPrice() {
		try {
			model.editKilogramPriceMine(view.readDepartment(), view.readId(), view.readKilogram());
			view.showSucesfull();
		} catch (MineNotFoundException | DepartmentNotFoundException e) {
			view.showError(e.getMessage());
		}
		
	}
	
	public void editOreType() {
		try {
			DepartmentName department = view.readDepartment();
			int id = view.readId();
			MineType mineType = view.readMineType();
			OreType oreType = view.readOreType();
			model.editOreType(department,id,oreType);
			model.editMineType(department, id, mineType);
			view.showSucesfull();
		} catch (MineNotFoundException | DepartmentNotFoundException e) {
			view.showError(e.getMessage());
		}
	}
	
	private void realizeEconomy() {
		try {
			int option = view.readOptionEconomy();
			switch (option) {
			case 1:
				sellOre( view.readDepartment(), view.readId(), view.readKilogram());
				break;
			case 2:
				buyInsume( view.readDepartment(), view.readId(), view.readMount());
			case 3:
				break;
			default:
				throw new OptionInvalidException();
			}
		} catch (OptionInvalidException|NumberFormatException e) {
			view.showError(e.getMessage());
			realizeEconomy();
		}
	}
	
	private void sellOre( DepartmentName department, int idMine, int kilos) {
		try {
			model.sellOre(department, idMine, kilos);
			view.showSucesfull();
		} catch (MineNotFoundException | DepartmentNotFoundException e) {
			view.showError(e.getMessage());
		}
	}
	
	private void buyInsume( DepartmentName department, int idMine, int mount ) {
		try {
			model.buyInsumes(department, idMine, mount);
			view.showSucesfull();
		} catch (MineNotFoundException | DepartmentNotFoundException e) {
			view.showError(e.getMessage());
		}
	}
	
	private void selectReports() throws OptionInvalidException, DepartmentNotFoundException {
		int option = view.showMenuGeneralReports();
		switch (option) {
		case 1:
			selectTypeReportQuantityMines();
			break;
		case 2:
			selectTypeReportGain();
			break;
		case 3:
			selectTypeReportExtention();
			break;
		case 4:
			selectReportPriceMines();
			break;
		default:
			throw new OptionInvalidException();
		}
	}
	/**
	 * 
	 * @throws OptionInvalidException
	 */
	private void selectReportPriceMines() throws OptionInvalidException {
		int option = view.showMenuPriceMines();
		switch(option) {
		case 1:
			createReportPriceMines();
			break;
		case 2:
			createReportPricePerDepartment();
			break;
		case 3:
			break;
		default:
			throw new OptionInvalidException();
		}
	}
	
	private void createReportPricePerDepartment() {
		view.showReportOne(model.generateReportPricePerDepartment());
	}

	private void createReportPriceMines() {
		view.showReportOne(model.generateReportPricePerKilogram());
	}

	private void selectTypeReportQuantityMines() throws DepartmentNotFoundException, OptionInvalidException {
		int option = view.showMenuQuantityMines();
		switch (option) {
		case 1:
			createReportMinesPerDepartment();
			break;
		case 2:
			createReportMinesPerOre();
			break;
		case 3:
			createReportTypeMines();
			break;
		case 4:
			createReportMinesInColombia();
			break;
		case 5:
			break;
		default:
			throw new OptionInvalidException();
			
		}
	}

	private void selectTypeReportGain() throws DepartmentNotFoundException, OptionInvalidException {
		int option = view.showMenuGains();
		switch (option) {
		case 1:
			createReportGainPerMine();
			break;
		case 2:
			createReportGainPerDepartment();
			break;
		case 3:
			break;
		default:
			throw new OptionInvalidException();
		}
	}
	
	private void selectTypeReportExtention() throws DepartmentNotFoundException, OptionInvalidException {
		int option = view.showMenuExtention();
		switch (option) {
		case 1:
			createReportExtentionMinePerDepartment();
			break;
		case 2:
			createReportExtention();
			break;
		case 3:
			break;
		default:
			throw new OptionInvalidException();
		}
	}
	
	private void createReportExtentionMinePerDepartment() throws DepartmentNotFoundException {
		view.showReportOne(model.generateReportm2PerDepartment(view.readDepartment()));
	}
	
	private void createReportGainPerMine() throws DepartmentNotFoundException {
		view.showReportQuantityMines(model.generateReportGainsPerMine(view.readDepartment()));
	}
	
	private void createReportMinesPerDepartment() {
		try {
			view.showReportOne(model.generateReportMinesPerDepartment());
		} catch (InvalidConstantException e) {
			view.showError(e.getMessage());
		}
	}
	
	private void createReportMinesPerOre() {
		try {
			view.showReportOne(model.generateReportMinesPerOre());
		} catch (InvalidConstantException e) {
			view.showError(e.getMessage());
		}
	}
	
	private void createReportGainPerDepartment() {
		view.showReportGains(model.generateReportGain());
	}
	
	private void createReportTypeMines() {
		view.showReportOne(model.generateReportQuantityTypeMines());
	}
	
	private void createReportExtention(){
		view.showReportOne(model.generateReportM2perDepartment());
	}
	private void createReportMinesInColombia() {
		try {
			view.showReportOne(model.generateReportQuantityMinesInColombia());
		} catch (InvalidConstantException e) {
			view.showError(e.getMessage());
		}
	}
}










