package de.truschin.helloworldpaas;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.vz.mongodb.jackson.JacksonDBCollection;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.MongoURI;
import com.google.gson.Gson;
import com.mongodb.Bytes;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

@Path("/directory")
public class PersonResource { 

	public static final String DB_NAME = "CloudFoundry_j9h6loa9_nb109vup";
	public static final String PERSON_COLLECTION = "Person";
	public static final String MONGO_HOST = "ds029051.mongolab.com";
	public static final int MONGO_PORT = 29051;

	@GET
	@Path("/test") 
	public Response test(){
		StringBuilder sb = new StringBuilder("unknown");
		try {

			InetAddress ip = InetAddress.getLocalHost();

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
			}

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e){

			e.printStackTrace();

		}

		ServiceResponse<List<Person>> resp = new ServiceResponse<List<Person>>(null, sb.toString());

		System.err.println("JSON" + new Gson().toJson(resp));

		return Response.status(200).entity(new Gson().toJson(resp)).build();
	}

	@GET
	@Path("/names") 
	public Response getNames(){

		MongoClient mongo = null;

		try {
			mongo = new MongoClient(new MongoClientURI( "mongodb://truschin:fishbone@ds029051.mongolab.com:29051/CloudFoundry_j9h6loa9_nb109vup" ));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MongoOperations mongoOps = new MongoTemplate(mongo, DB_NAME);

		DBCollection collection = mongoOps.getCollection("Person");

		DBCursor list = collection.find();

		List<Person> listPersons = new ArrayList<Person>();

		while (list.hasNext()){

			DBObject obj = list.next();
			String firstName = (String) obj.get("firstName");
			String secondName = (String) obj.get("secondName");

			Person per = new Person(firstName, secondName);

			listPersons.add(per);
		}

		StringBuilder sb = new StringBuilder("unknown");
		try {

			InetAddress ip = InetAddress.getLocalHost();

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
			}

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e){

			e.printStackTrace();

		}

		ServiceResponse<List<Person>> resp = new ServiceResponse<List<Person>>(listPersons, sb.toString());

		System.err.println("JSON" + new Gson().toJson(resp));

		mongo.close();

		return Response.status(200).entity(new Gson().toJson(resp)).build();
	}

	@POST
	@Path("/names") 
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveName(String req){

		MongoClient mongo = null;
		try {
			mongo = new MongoClient(new MongoClientURI( "mongodb://truschin:fishbone@ds029051.mongolab.com:29051/CloudFoundry_j9h6loa9_nb109vup" ));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MongoOperations mongoOps = new MongoTemplate(mongo, DB_NAME);

		Person p = new Gson().fromJson(req, Person.class);

		mongoOps.insert(p, "Person");

		mongo.close();

		SendGrid sendgrid = new SendGrid("kvQuo6B8BE", "gWk1fObAbt");

		SendGrid.Email email = new SendGrid.Email();

		email.addTo("truschin@gmail.com");
		email.setFrom("your@youremail.com");
		email.setSubject(p.firstName + " " + p.secondName + " added.");
		email.setHtml(req);

		try {
			SendGrid.Response response = sendgrid.send(email);
		} catch (SendGridException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuilder sb = new StringBuilder("unknown");
		try {

			InetAddress ip = InetAddress.getLocalHost();

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
			}

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e){

			e.printStackTrace();

		}

		ServiceResponse<List<Person>> resp = new ServiceResponse<List<Person>>(null, sb.toString());
		
		return Response.status(200).entity(new Gson().toJson(resp)).build();

	}
}