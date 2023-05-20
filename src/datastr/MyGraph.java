package datastr;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class MyGraph<T> {
	private MyVerticeNode[] graphElements;
	private final int DEFAULT_ARRAY_SIZE = 5;
	private int arraySize = DEFAULT_ARRAY_SIZE;
	private int elementCount = 0;
	
	//TODO konstruktori, isEmpty, isFull, howManyElements, increase
	public MyGraph()
	{
		graphElements = new MyVerticeNode[arraySize];
	}
	
	public MyGraph(int inputArraySize) {
		if(inputArraySize > 0) {
			arraySize = inputArraySize;
		}
		
		graphElements = new MyVerticeNode[arraySize];
	}
	
	public boolean isEmpty()
	{
		return (elementCount==0);
	}
	public boolean isFull()
	{
		return (elementCount==arraySize);
	}
	
	public int howManyElements() {
		return elementCount;
	}
	//5. increaseHeap
	private void increaseGraph() {
		//1. noskaidrot, cik reizes lielāks būs jaunaisnumbers masīvs
		
		int newArraySize = (elementCount>100)? (int)(arraySize*1.5) : arraySize*2;
		arraySize = newArraySize;
		//2. izveidot jauno masīvu
		MyVerticeNode[] newArrayForElements = new MyVerticeNode[arraySize];
		//3. veikt visu elementu pāŗkopēšanu jaunajā masīvā (sakot no 0.šūnas)
		for(int i = 0; i < elementCount; i++)
		{
			newArrayForElements[i] = graphElements[i];
		}
		//4. nomainīt referenci uz jauno masīvu
		graphElements = newArrayForElements;
	}
	
	//TODO pārbaudīt, vai tāds elements jau neeksistē
	public void addVertice(T inputElement) {
		if(isFull()) {
			increaseGraph();
		}
		
		MyVerticeNode newNode = new MyVerticeNode(inputElement);
		
		graphElements[elementCount] = newNode;
		elementCount++;

	}
	
	
	public void addEdge(T fromVertice, T toVertice, float weight) throws Exception {
		
		int fromVerticeIndex = searchVertice(fromVertice);
		int toVerticeIndex = searchVertice(toVertice);
		
		//abi elementi ir atrasti grafa masīvā
		if(fromVerticeIndex != -1 && toVerticeIndex != -1) {
			
			MyVerticeNode fromVerticeNode = graphElements[fromVerticeIndex];
			
			MyEdgeNode newEdgeNode = new MyEdgeNode(toVerticeIndex, weight);
			
			//ja šī ir pirmā saite šai pilsētai (from)
			if(fromVerticeNode.getFirstEdge()==null) {
				fromVerticeNode.setFirstEdge(newEdgeNode);
			}
			else
			{
				MyEdgeNode temp = fromVerticeNode.getFirstEdge();
				while(temp.getNext()!=null) {
					temp = temp.getNext();
				}
				temp.setNext(newEdgeNode);
			}
			
			
		}
		//TODO izveidot iespēju, ja kāda no virsotnēm vēl nav grafā, tad to izveidot
		else
		{
			throw (new Exception("Kāda vai abas virsotnes nav atrastas grafā, līdz ar to nevar izveidot saiti"));
		}
		
		
	}
    public int indexIs(T item) {
        for (int i = 0; i < elementCount; i++) {
            if (graphElements[i].getElement().equals(item)) {
                return i;
            }
        }
        return -1;
    }
    
    public int weightIs(T fromV, T toV) throws Exception {
        int fromIndex = searchVertice(fromV);
        int toIndex = searchVertice(toV);

        if (fromIndex == -1 || toIndex == -1) {
            throw new Exception("One or both vertices not found in the graph.");
        }

        MyEdgeNode edge = graphElements[fromIndex].getFirstEdge();
        while (edge != null) {
            if (edge.getIndexOfElement() == toIndex) {
                return (int) edge.getWeight();
            }
            edge = edge.getNext();
        }

        throw new Exception("There is no edge between the specified vertices.");
    }
	
	private int searchVertice(T vertice) {
		for(int i = 0; i < elementCount; i++) {
			if(graphElements[i].getElement().equals(vertice)) {
				return i;
			}
		}
		return -1;
	}
	
	public void printNeighbourVertices(T fromV) throws Exception {
        int fromIndex = searchVertice(fromV);

        if (fromIndex == -1) {
            throw new Exception("The specified vertex is not found in the graph.");
        }

        MyVerticeNode<T> vertice = graphElements[fromIndex];
        MyEdgeNode edge = vertice.getFirstEdge();

        if (edge == null) {
            System.out.println("No neighbor vertices found for " + vertice.getElement());
        } else {
            System.out.print("Neighbor vertices of " + vertice.getElement() + ": ");
            while (edge != null) {
                int toIndex = edge.getIndexOfElement();
                T toVertice = (T) graphElements[toIndex].getElement();
                System.out.print(toVertice + " ");
                edge = edge.getNext();
            }
            System.out.println();
        }
    }
	
	
	
	
	public void print() throws Exception {
		if(isEmpty()) {
			throw (new Exception("Grafs ir tukss un to nevar izprintēt"));
		}
		
		for(int i = 0; i < elementCount; i++) {
			System.out.print(graphElements[i].getElement() + "--> ");
			MyEdgeNode temp = graphElements[i].getFirstEdge();
			while(temp!=null) {
				int toVerticeIndex = temp.getIndexOfElement();
				float weight = temp.getWeight();
				System.out.print(graphElements[toVerticeIndex].getElement() + " " + weight + "km, ");
				temp = temp.getNext();
			}
			System.out.println();
		}
		
	}
	public void makeEmpty() {
		arraySize = DEFAULT_ARRAY_SIZE;
		graphElements = new MyVerticeNode[arraySize];
		elementCount = 0;
		System.gc();
	
	}
	
	
	public T getShortCut(T element) throws Exception {
		//TODO pārbaudīt, vai grafs nav tukšs
		
		int fromVerticeIndex = searchVertice(element);
		if(fromVerticeIndex != -1) { //padotais elements ir atrasts mūsu grafā
			MyEdgeNode temp = graphElements[fromVerticeIndex].getFirstEdge();
			
			MyEdgeNode myShortEdge = temp;
			
			while(temp!=null) {
				if(temp.getWeight() < myShortEdge.getWeight()) {
					myShortEdge = temp;
				}
				temp = temp.getNext();
			}
			int toShortVerticeIndex = myShortEdge.getIndexOfElement();
			return (T)graphElements[toShortVerticeIndex].getElement();
		}
		else
		{
			throw (new Exception(element+ " nav atrasta grafā" ));
		}
		
	}
	
	//(20 + 5)/2 = 12.5 *0.7 = 
	//TODO
	//apstaigāšana dziļumā
	 public void printDepthFirst(T fromV, T toV) throws Exception {
	        int startIndex = searchVertice(fromV);
	        int endIndex = searchVertice(toV);

	        if (startIndex == -1 || endIndex == -1) {
	            throw new Exception("Nav atrasta vertice");
	        }

	        Set<Integer> visited = new HashSet<>();
	        Stack<Integer> stack = new Stack<>();
	        boolean found = false;

	        stack.push(startIndex);
	        visited.add(startIndex);

	        StringBuilder output = new StringBuilder();

	        while (!stack.isEmpty()) {
	            int currentIndex = stack.pop();

	            if (currentIndex == endIndex) {
	                found = true;
	                break;
	            }

	            output.append(graphElements[currentIndex].getElement()).append(" ");

	            MyEdgeNode currentEdge = graphElements[currentIndex].getFirstEdge();
	            while (currentEdge != null) {
	                int neighborIndex = currentEdge.getIndexOfElement();
	                if (!visited.contains(neighborIndex)) {
	                    stack.push(neighborIndex);
	                    visited.add(neighborIndex);
	                }
	                currentEdge = currentEdge.getNext();
	            }
	        }

	        System.out.println(output.toString());

	        if (found) {
	            System.out.println("No "+fromV+" uz "+toV+" ir iespejams aiziet");
	        } else {
	        	System.out.println("No "+fromV+" uz "+toV+" NAV iespejams aiziet");
	        }
	    }
	//apstaigāšana platumā
	 
	 public void printBreadthFirst(T fromV, T toV) throws Exception {
		    int startIndex = searchVertice(fromV);
		    int endIndex = searchVertice(toV);

		    if (startIndex == -1 || endIndex == -1) {
		        throw new Exception("Nav atrasts mainigais");
		    }

		    Set<Integer> visited = new HashSet<>();
		    Queue<Integer> queue = new LinkedList<>();
		    boolean found = false;

		    queue.offer(startIndex);
		    visited.add(startIndex);

		    StringBuilder output = new StringBuilder();

		    while (!queue.isEmpty()) {
		        int currentIndex = queue.poll();

		        if (currentIndex == endIndex) {
		            found = true;
		            break;
		        }

		        output.append(graphElements[currentIndex].getElement()).append(" ");

		        MyEdgeNode currentEdge = graphElements[currentIndex].getFirstEdge();
		        while (currentEdge != null) {
		            int neighborIndex = currentEdge.getIndexOfElement();
		            if (!visited.contains(neighborIndex)) {
		                queue.offer(neighborIndex);
		                visited.add(neighborIndex);
		            }
		            currentEdge = currentEdge.getNext();
		        }
		    }

		    System.out.println(output.toString());

		    if (found) {
		    	System.out.println("No "+fromV+" uz "+toV+" ir iespejams aiziet");
		    } else {
		    	System.out.println("No "+fromV+" uz "+toV+" NAV iespejams aiziet");
		    }
		}

	//minimalais pārklāšanās koka izveide (Minimum Spanning TRee)
	//Deikstra algoritms
	 
	  private void printPath(int[] predecessors, int vertexIndex) {
	        if (vertexIndex != -1) {
	            printPath(predecessors, predecessors[vertexIndex]);
	            System.out.print(graphElements[vertexIndex].getElement() + " ");
	        }
	    }
	 
	 public void printShortestPath(T fromV, T toV) throws Exception {
		    int startIndex = searchVertice(fromV);
		    int endIndex = searchVertice(toV);

		    if (startIndex == -1 || endIndex == -1) {
		        throw new Exception("One or both vertices not found in the graph.");
		    }

		    float[] distances = new float[elementCount];
		    int[] predecessors = new int[elementCount];

		    for (int i = 0; i < elementCount; i++) {
		        distances[i] = Float.MAX_VALUE;
		        predecessors[i] = -1;
		    }

		    distances[startIndex] = 0;

		    Queue<Integer> queue = new LinkedList<>();
		    queue.offer(startIndex);

		    while (!queue.isEmpty()) {
		        int currentIndex = queue.poll();

		        MyEdgeNode currentEdge = graphElements[currentIndex].getFirstEdge();
		        while (currentEdge != null) {
		            int neighborIndex = currentEdge.getIndexOfElement();
		            float weight = currentEdge.getWeight();
		            float newDistance = distances[currentIndex] + weight;

		            if (newDistance < distances[neighborIndex]) {
		                distances[neighborIndex] = newDistance;
		                predecessors[neighborIndex] = currentIndex;
		                queue.offer(neighborIndex);
		            }

		            currentEdge = currentEdge.getNext();
		        }
		    }

		    if (distances[endIndex] == Float.MAX_VALUE) {
		        System.out.println("Nav tāda ceļa no " + fromV + " uz  " + toV + ".");
		    } else {
		        System.out.print("Īsākais ceļš no " + fromV + " uz " + toV + " ir: \n");
		        printPath(predecessors, endIndex);
		        System.out.println("\nKopeja distance: " + distances[endIndex] + " km");
		    }
		}

	
	

}
