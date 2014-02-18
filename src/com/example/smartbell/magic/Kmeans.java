package com.example.smartbell.magic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Kmeans {

	String exercise;
	BodyParser bp;
	ArrayList<Body> bodies;
	
	public Kmeans(String exercise, BodyParser bp) {
		this.exercise = exercise;
		this.bp = bp;
		this.bodies = bp.getBodyList();
//		for(Body b : this.bodies){
//			for(String s : b.getParts().keySet()){
//				System.out.println(s);
//			}
//		}
	}
	
	ArrayList<Body> selectInitialCentroids(ArrayList<Body> dataset, int k, int method, int distType){
		ArrayList<Body> d = new ArrayList<Body>(dataset);
		
		Body c = calcCentroid(d, method);
		ArrayList<Body> m = new ArrayList<Body>();
		
		for(int i = 0; i < k; i++){
			HashMap<Double, Body> max = new HashMap<Double, Body>();
			for(Body body : d){
				if(distType > 0){
					max.put(dist(c, body), body);
				}
				else{
					max.put(dist(c, body), body);
				}
			}
			m.add(i, max.get(findMax(max.keySet())));
			c = m.get(i);
			d.remove(m.get(i)); // didn't have this before
		}
		
		return m;
	}
	
	Double findMax(Iterable<Double> iter){
		Double max = 0.0;
		
		for(Double d : iter){
			if(d > max)
				max = d;
		}
		
		return max;
	}
	
	Double findMin(Iterable<Double> iter){
		Double min = 9999999999.9;
		
		for(Double d : iter){
			if(d < min)
				min = d;
		}
		
		return min;
	}
	
	double dist(Body a, Body b){
		ArrayList<Double> diff = new ArrayList<Double>();
		HashMap<String, String> partsA = a.getParts();
		HashMap<String, String> partsB = b.getParts();
		for(String key : a.getPartNames()){
//			System.out.println("partsA = "+partsA);
//			System.out.println("partsB = "+partsB);
			if(partsB.isEmpty())
				diff.add(Double.valueOf(partsA.get(key)));
			else
				diff.add(Double.valueOf(partsA.get(key)) - Double.valueOf(partsB.get(key)));
		}
		
		return mag(diff);
	}

	
	ArrayList<ArrayList<Body>> cluster(int k, int method, double sse, int distType){
		ArrayList<Body> dataset = new ArrayList<Body>(bodies);
		
		ArrayList<Body> m = selectInitialCentroids(dataset, k, method, distType);

		ArrayList<ArrayList<Body>> cl;
		double error = 99999999;
		
		do{
			cl = new ArrayList<ArrayList<Body>>();
			
			for(int i = 0; i < k; i++){
				cl.add(i, new ArrayList<Body>());
			}
			
			for(Body b : dataset){
				HashMap<Double, Integer> distances = new HashMap<Double, Integer>();
				for(int j = 0; j < k; j++){
					if(distType == 0){
						distances.put(dist(b, m.get(j)), j);
					}
					else{
						distances.put(dist(b, m.get(j)), j);
					}
				}
				int clusterNum = distances.get(findMin(distances.keySet()));
				cl.get(clusterNum).add(b);
			}
			for(int j = 0; j < k; j++){
//				System.out.println("cl.get("+j+"): "+cl.get(j));
				m.add(j, calcCentroid(cl.get(j), method));
			}
			
		}while (!((error - (error = calcsse(cl, m, k, distType))) <= (error * sse)));
		
		return cl;
	}

	Body calcCentroid(ArrayList<Body> dataset, int method){
		Body centroid = new Body();
/*		System.out.println("dataset: "+dataset);
		System.out.println("dataset.get(0): "+dataset.get(0));
		System.out.println("dataset.get(0).getPartNames(): "+dataset.get(0).getPartNames());
		System.out.println("dataset.get(0).getPartNames().length: "+dataset.get(0).getPartNames().length);*/
		for(int i = 0; !dataset.isEmpty() && i < dataset.get(0).getPartNames().length; i++){
//			System.out.println("i: "+i);
			if(method == 1){
				centroid.getParts().put(dataset.get(0).getPartNames()[i], mean(getCol(dataset, i)).toString());
			}
			else{
				centroid.getParts().put(dataset.get(0).getPartNames()[i], mean(getCol(dataset, i)).toString());
			}
		}
		
		return centroid;
	}
	
	ArrayList<Double> getCol(ArrayList<Body> dataset, int col){
		ArrayList<Double> arr = new ArrayList<Double>();
		for(Body b : dataset){
/*			System.out.println("b.getPartNames()"+b.getPartNames());
			for(int i = 0; i < b.getPartNames().length; i++){
				System.out.println("b.getPartNames()["+i+"] = "+b.getPartNames()[i]);
			}
			System.out.println("b.getParts().get(b.getPartNames()[col])): "+b.getParts().get(b.getPartNames()[col]));*/
			arr.add(Double.valueOf(b.getParts().get(b.getPartNames()[col])));
		}
		return arr;
	}
	
	Double mean(ArrayList<Double> arr){
		double sum = 0;
		for(Double d : arr){
			sum += d;
		}
		return sum / arr.size();
	}
	
	double manDist(Body a, Body b){
		double z = 0;
		
		return z;
	}
	
	double mag(ArrayList<Double> vector){
		double sqSum = 0;
		
		for(Double d : vector){
			sqSum += d * d;
		}
		
		return Math.sqrt(sqSum);
	}
	
	double calcsse(ArrayList<ArrayList<Body>> c, ArrayList<Body> m, int k, int distType){
		double error = 0.0;
		for(int j = 0; j < k; j++){
			for(int x = 0; x < c.get(j).size(); x++){
				if(distType > 0){
					error += dist(c.get(j).get(x), m.get(j));
				}
				else{
					error += dist(c.get(j).get(x), m.get(j));
				}
			}
		}
		return error;
	}
}
