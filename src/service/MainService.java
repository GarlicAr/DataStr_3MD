package service;

import datastr.MyGraph;

public class MainService {

	public static void main(String[] args) {
		MyGraph<String> myMap = new MyGraph<>();
		
		try
		{
		myMap.addVertice("Rīga");
		myMap.addVertice("Ventspils");
		myMap.addVertice("Kuldīga");
		myMap.addVertice("Liepāja");
		myMap.addVertice("Jelgava");
		myMap.addVertice("Talsi");
		myMap.addVertice("Cēsis");
		
		
		myMap.addEdge("Rīga", "Ventspils", 12);
		myMap.addEdge("Ventspils", "Rīga", 189);
		myMap.addEdge("Ventspils", "Kuldīga", 60);
		myMap.addEdge("Kuldīga", "Ventspils", 60);
		myMap.addEdge("Liepāja", "Rīga", 100);
		myMap.addEdge("Rīga", "Liepāja", 100);
		myMap.addEdge("Rīga", "Cēsis", 103);
		myMap.addEdge("Cēsis", "Talsi", 12);
		myMap.addEdge("Ventspils", "Talsi", 1);
		
		
		
		myMap.printShortestPath("Rīga", "Talsi");
		
		}
		catch (Exception e) {
			System.out.println(e);
		}
		

	}

}
