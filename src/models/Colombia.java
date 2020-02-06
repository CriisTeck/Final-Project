package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.DepartmentNotFoundException;
import exceptions.MineNotFoundException;
import models.utils.MatrixReport;
/**
 * Clase que se encarga de administrar la creacion de departamento,minas,reportes.
 * @author Cristian and Felipe
 */
public class Colombia {
	
	private Department[] listDepartment;
	
	/**
	 * Constructor que se encarga de inicializar el vector
	 * correspondiente para almacenar los departamentos.
	 */
	public Colombia() {
		listDepartment = new Department[10]; 
	}
	
	/**
	 * Metodo encargado de la generación automatica de los departamentos.
	 */
	public void autogenerateDepartment() {
		for (int i = 0; i < listDepartment.length; i++) {
			try {
				listDepartment[i] = new Department(DepartmentName.values()[i],25000000);
			} catch (MineNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Metodo que se encarga de buscar la posicion de in departamento, sabiendo el nombre.
	 * @param name
	 * Entra por parametro el nombre del departamento a buscar.
	 * @return
	 * Retorna la posicion en el vector de departamentos del departamento a buscar.
	 * @throws DepartmentNotFoundException
	 */
	private int searchDepartment(DepartmentName name) throws DepartmentNotFoundException {
		for (int i = 0; i < listDepartment.length; i++) {
			if(listDepartment[i].getName().equals(name.getName()))
				return i;
		}
		throw new DepartmentNotFoundException();
	}
	
	/**
	 * Agrega una nueva mina a un departamento.
	 * @param department
	 * Nombre de departamento en el cual crear la mina.
	 * @param nameMine
	 * Nombre de la nueva mina.
	 * @param meter
	 * Numero de metros cuadrados de la nueva mina.
	 * @param oreType
	 * Tipo de mineral que va a explotar la mina.
	 * @param kilogramPrice
	 * Valor del kilo de mineral a explotar.
	 * @param mineType
	 * Tipo de la mina nueva
	 * @param budgetMine
	 * Presupuesto de la nueva mina.
	 * @throws DepartmentNotFoundException
	 * Al no encontrarse un departamento por el nombre indicado, lanzara la exepcion.
	 */
	public void createMine(DepartmentName department, String nameMine,int meter, OreType oreType, int kilogramPrice, MineType mineType,int budgetMine) throws DepartmentNotFoundException {
		int position = searchDepartment(department);
		listDepartment[position].addMine(new Mine(nameMine,meter,oreType, kilogramPrice,listDepartment[position].getListMine().size(),mineType,budgetMine));
	}
	
	/**
	 * Envia peticion al departamento ingresado para remover una mina.
	 * @param department
	 * Nombre de departamento ingresado.
	 * @param id
	 * Numero de identificacion de la mina a remover. 
	 * @throws MineNotFoundException
	 * Lanza la excepcion cuando no se encuentra la mina.
	 */
	public void sendRemoveMine(DepartmentName department, int id) throws MineNotFoundException {
		for (Department department2 : listDepartment) {
			if(department2.getName().equals(department.getName()))
				department2.removeMine(id);
		}
	}
	
	/**
	 * Retorna el vector de departamentos.
	 * @return
	 * Retorna el vector de departamentos almacenado en la clase.
	 */
	public Department[] getListDepartment() {
		return listDepartment;
	}
	
	/**
	 * Edita el precio por kilogramo de una determinada mina.
	 * @param department
	 * Nombre del departamento donde se desea editar la mina.
	 * @param id
	 * Numero de identificacion de la mina a editar.
	 * @param newKilogramPrice
	 * Valor nuevo que se le asignara al mineral explotado en la mina.
	 * @throws MineNotFoundException
	 * La excepcion es lanzada al no encontrar la mina con el numero de identificacion suministrado.
	 * @throws DepartmentNotFoundException
	 * La Excepcion es lanzada al no encontrar el departamento suministrado.
	 */
	public void editKilogramPriceMine(DepartmentName department, int id, int newKilogramPrice) throws MineNotFoundException, DepartmentNotFoundException {
		listDepartment[searchDepartment(department)].editKilogramPrice(newKilogramPrice, id);
	}
	
	/**
	 * Edita el tipo de mineral que explota determinada mina.
	 * @param department
	 * Nombre del departamento donde se desea editar los datos de la mina.
	 * @param idMine
	 * Numero de identificacion de la mina a editar.
	 * @param oreType
	 * Tipo de mineral nuevo que va a empezar a explotar la mina.
	 * @throws MineNotFoundException
	 * Lanza esta excepcion cuando no se encuetra la mina ingresada.
	 * @throws DepartmentNotFoundException
	 * Lanza esta excepcion cuando no se encuentra el departamento especificado.
	 */
	public void editOreType(DepartmentName department, int idMine, OreType oreType) throws MineNotFoundException, DepartmentNotFoundException {
		listDepartment[searchDepartment(department)].editOreType(oreType, idMine);
	}
	
	
	/**
	 * Edita el tipo de mina.
	 * @param department
	 * Nombre del departamento donde se desea editar la mina
	 * @param idMine
	 * Numero de identificacion de la mina a editar,
	 * @param type
	 * Nuevo tipo de mina.
	 * @throws MineNotFoundException
	 * la Excepcion es lanzada cuando no se encuentra la mina ingresada.
	 * @throws DepartmentNotFoundException
	 * La excepcion es lanzada al no encontrar el departamento especificado.
	 */
	public void editMineType(DepartmentName department, int idMine, MineType type) throws MineNotFoundException, DepartmentNotFoundException {
		listDepartment[searchDepartment(department)].editMineType(type, idMine);
	}
	
	/**
	 * Realiza la transaccion de vender mineral de una mina.
	 * @param department
	 * Nombre del departamento en el que se encuentra la mina con la cual realizar la transaccion.
	 * @param idMine
	 * Numero de identificacion de la mina a realizar la transaccion.
	 * @param kilos
	 * Cantidad de kilogramos a vender.
	 * @throws MineNotFoundException
	 * La excepcion es lanzada al no encontrar la mina especificada.
	 * @throws DepartmentNotFoundException
	 * La excepcion es lanzada al no encontrar el departamento especificado.
	 */
	public void sellOre(DepartmentName department, int idMine, int kilos) throws MineNotFoundException, DepartmentNotFoundException {
		listDepartment[searchDepartment(department)].sellOre(idMine, kilos);
	}
	
	/**
	 * Realiza la transaccion de comprar insumos para una mina.
	 * @param department
	 * Nombre del departamento donde se encuentra la mina con la cual realizar la transaccion.
	 * @param idMine
	 * Numero de identificacion de la mina a realizar la transaccion.
	 * @param mount
	 * Cantidad monetaria de insumos a comprar.
	 * @throws MineNotFoundException
	 * La excepcion es lanzada al no encontrar la mina especificada.
	 * @throws DepartmentNotFoundException
	 * La excepcion es lanzada al no encontrar el departamento especificado.
	 */
	public void buyInsumes(DepartmentName department, int idMine, int mount) throws MineNotFoundException, DepartmentNotFoundException {
		listDepartment[searchDepartment(department)].buyInsumes(idMine, mount);
	}
	
	/**
	 * Genera reporte de ganancias que tiene cada mina en un departamento especificado.
	 * @param dep
	 * Nombre de departamento al cual se le hara el reporte.
	 * @return
	 * Retorna un vector de objetos que contiene una matriz con el grafico de barras y las convenciones.
	 * @throws DepartmentNotFoundException
	 * La excepcion es lanzada al no encontrar el departamento especificada.
	 */
	public Object[] generateReportGainsPerMine(DepartmentName dep) throws DepartmentNotFoundException {
		Map<String,Integer> data = new HashMap<String,Integer>();
		List<Mine> list = listDepartment[searchDepartment(dep)].getListMine();
		for (int i = 0; i < list.size(); i++) {
			data.put(list.get(i).getName(),list.get(i).getGain());
		}
		return MatrixReport.generateMatrixReport(data, MatrixReport.DEPART, 5000);
	}
		
	/**
	 * Genera reporte de cantidad de minas por  cada departamento.
	 * @return
	 * Retorna un vector de objetos que contiene una matriz con el grafico de barras y las convenciones.
	 */
	public Object[] generateReportMinesPerDepartment() {
		Map<String,Integer> data = new HashMap<String,Integer>();
		for (int i = 0; i < listDepartment.length; i++) {
			data.put(listDepartment[i].getName(),listDepartment[i].getListMine().size());
		}
		return MatrixReport.generateMatrixReport(data, MatrixReport.DEPART,1);
	}
	
	/**
	 * Genera reporte de cantidad de minas en total.
	 * @return
	 * Retorna un vector de objetos que contiene una matriz con el grafico de barras y las convenciones.
	 */
	public Object[] generateReportQuantityMinesInColombia() {
		Map<String, Integer> data = new HashMap<String, Integer>();
		int count = 0;
		for (int i = 0; i < listDepartment.length; i++) {
			count  +=  listDepartment[i].getListMine().size();
 		}
		data.put("Colombia", count);
		return MatrixReport.generateMatrixReport(data, MatrixReport.DEPART, 1);
	}
	
	/**
	 * Genera reporte de cantidad de metros cuadrados que cada departamento explota en total.
	 * @return
	 * Retorna un vector de objetos que contiene una matriz con el grafico de barras y las convenciones.
	 */
	public 	Object[] generateReportM2perDepartment() {
		Map<String,Integer> data = new HashMap<String,Integer>();
		for (int i = 0; i < listDepartment.length; i++) {
			data.put(listDepartment[i].getName(),searchQuantityM2PerDepartment(i));
		}
		return MatrixReport.generateMatrixReport(data, MatrixReport.DEPART,200);
		
	}

	/**
	 * Obtiene la cantidad de metros cuadrados de un departamento especifico.
	 * @param count
	 * Posicion que ocupa el departamento al cual se le va a obtener la cantidad de metros explotados.
	 * @return
	 * Retorna la cantidad de metros explotados del departamento que esta en la posicion suministrada.
	 */
	private int searchQuantityM2PerDepartment(int count) {
		int counter = 0;
			for (int j = 0; j < listDepartment[count].getListMine().size(); j++) {
			
					counter += listDepartment[count].getListMine().get(j).getMeter() ;
			}
		
		return counter;
	}
	
	/**
	 * Genera reporte de cantidad de minas que explotan cada uno de los minerales.
	 * @return
	 * Retorna un vector de objetos que contiene una matriz con el grafico de barras y las convenciones.
	 */
	public Object[] generateReportMinesPerOre() {
		Map<String, Integer> data = new HashMap<String, Integer>();
		for (int i = 0; i < OreType.values().length; i++) {
			OreType ore = OreType.values()[i];
			data.put(ore.getOreType(), searchQuantityMines(ore));
		}
		return MatrixReport.generateMatrixReport(data, MatrixReport.ORE,1);
	}
	
	/**
	 * Obtiene la cantidad de minas que explotan un mineral en especifico.
	 * @param ore
	 * Tipo de mineral con el que se van a contar las minas.
	 * @return
	 * Retorna la cantidad de minas que explotan el mineral especificado.
	 */
	private int searchQuantityMines(OreType ore) {
		int counter = 0;
		for (int i = 0; i < listDepartment.length; i++) {
			for (int j = 0; j < listDepartment[i].getListMine().size(); j++) {
				if(listDepartment[i].getListMine().get(j).getOreType() == ore )
					counter++;
			}
		}
		return counter;
	}
	
	/**
	 * Genera reporte de ganancias de cada departamento
	 * @return
	 * Retorna un vector de objetos que contiene una matriz con el grafico de barras y las convenciones.
	 */	
	public Object[] generateReportGain() {
		Map<String,Integer> data = new HashMap<String, Integer>();
		for (int i = 0; i < listDepartment.length; i++) {
			data.put(listDepartment[i].getName(),listDepartment[i].getGain());
		}
		return MatrixReport.generateMatrixReport(data, MatrixReport.DEPART, 10000);
	}
	
	/**
	 * Genera reporte de cantidad de minas por su tipo.
	 * @return
	 * Retorna un vector de objetos que contiene una matriz con el grafico de barras y las convenciones.
	 */
	public Object[] generateReportQuantityTypeMines(){
		Map<String,Integer> data = new HashMap<String,Integer>();
		for (int i = 0; i < MineType.values().length; i++) {
			MineType mine = MineType.values()[i];
			data.put(mine.getMineType(), searchQuantityTypeMines(mine));
		}
		return MatrixReport.generateMatrixReport(data, MatrixReport.TYPE_MINE,4);
	}
	
	/**
	 * Obtiene la cantidad de minas que tienen como propiedad un tipo especifico.
	 * @param mine
	 * Tipo de mina con el cual se van a buscar las minas.
	 * @return
	 * Retorna la cantidad de minas del tipo especificado.
	 */
	private int searchQuantityTypeMines(MineType mine) {
		int counter = 0;
		for (int i = 0; i < listDepartment.length; i++) {
			for (int j = 0; j < listDepartment[i].getListMine().size(); j++) {
				if(listDepartment[i].getListMine().get(j).getMineType() == mine)
					counter++;
			}
		}
		return counter;
	}
	
	/**
	 * Genera reporte de metros cuadrados explotados por cada mina en un departamento especificado.
	 * @param department
	 * Nombre del departamento en el cual se va a realizar el reporte.
	 * @return
	 * Retorna un vector de objetos que contiene una matriz con el grafico de barras y las convenciones.
	 * @throws DepartmentNotFoundException
	 * La Excepcion es lanzada cuando el departamento especificado no es encontrado.
	 */
	public Object[] generateReportm2PerDepartment(DepartmentName department) throws DepartmentNotFoundException {
		Map<String,Integer> data = new HashMap<String,Integer>();
		List<Mine> mine = listDepartment[searchDepartment(department)].getListMine();
		for (int i = 0; i < mine.size(); i++) {
			data.put(mine.get(i).getName(),mine.get(i).getMeter());
		}
		return MatrixReport.generateMatrixReport(data, MatrixReport.TYPE_MINE, 40);
	}
	
	/**
	 * Genera reporte del valor del kilogramo de cada mineral.
	 * @return
	 * Retorna un vector de objetos que contiene una matriz con el grafico de barras y las convenciones.
	 */
	public Object[] generateReportPricePerKilogram() {
		Map<String, Integer> data = new HashMap<String, Integer>();
		for (int i = 0; i < OreType.values().length; i++) {
			OreType ore = OreType.values()[i];
			data.put(ore.getOreType(),searchAveragePrice(ore));
		}
		return MatrixReport.generateMatrixReport(data, MatrixReport.ORE, 1000);
	}
	
	/**
	 * Obtiene el precio por kilogramo promedio del mineral especificado.
	 * @param ore
	 * Tipo de mineral al cual se le buscara el precio por kilogramo promedio entre todas las minas.
	 * @return
	 * Retorna el precio por kilogramo promedio del mineral especificado.
	 */
	private int searchAveragePrice(OreType ore) {
		int priceTotalOre = 0, counter = 0;
		for (int i = 0; i < listDepartment.length; i++) {
			for (int j = 0; j < listDepartment[i].getListMine().size(); j++) {
				if(listDepartment[i].getListMine().get(j).getOreType() == ore ) {
					priceTotalOre += listDepartment[i].getListMine().get(j).getKilogramPrice();
					counter++;
				}
			}
		}
		if(counter == 0)
			return 0;
		else
			return priceTotalOre / counter;
	}
	
	/**
	 * Genera reporte del valor total por kilogramo de todos los minerales en cada departamento.
	 * @return
	 * Retorna un vector de objetos que contiene una matriz con el grafico de barras y las convenciones.
	 */
	public Object[] generateReportPricePerDepartment() {
		Map<String,Integer> data = new HashMap<String,Integer>();
		for (int i = 0; i < listDepartment.length; i++) {
			int totalPrice = 0;
			for (int j = 0; j < listDepartment[i].getListMine().size(); j++) {
				totalPrice += listDepartment[i].getListMine().get(j).getKilogramPrice();
			}
			data.put(listDepartment[i].getName(), totalPrice);
		}
		return MatrixReport.generateMatrixReport(data, MatrixReport.DEPART, 1000);
	}
}