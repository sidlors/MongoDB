package mx.com.sidlors.mongo.core;


import java.util.Arrays;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		try {

			/**** Connect to MongoDB ****/
			MongoCredential  credential = MongoCredential.createCredential("sidlors", "mongosidlors", "Semeolvid0".toCharArray());
			MongoClient mongo = new MongoClient(new ServerAddress("ds023088.mlab.com:23088"), Arrays.asList(credential));
			/**** Get database ****/
			// si la base no existe MongoDb la creara
			DB db = mongo.getDB("mongosidlors");
			
			/**** Get collection / table from 'testdb' ****/
			// Si la collection no existe MongoDB la creara por nosotros
			DBCollection table = db.getCollection("user");

			/**** Insert ****/
			// crea un documento para almacenar la 'Llave Valor'
			BasicDBObject document = new BasicDBObject();
			document.put("name", "Juan");
			document.put("age", 30);
			document.put("createdDate", new Date());
			table.insert(document);

			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("name", "Juan");

			DBCursor cursor = table.find(searchQuery);

			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}

			/**** Update ****/
			// busca el documento donde name="Juan" y actuliza los nuevos valores
			BasicDBObject query = new BasicDBObject();
			query.put("name", "Juan");

			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("name", "Juan-updated");

			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);

			table.update(query, updateObj);

			/**** Find and display ****/
			BasicDBObject searchQuery2 = new BasicDBObject().append("name", "Juan-updated");

			DBCursor cursor2 = table.find(searchQuery2);

			while (cursor2.hasNext()) {
				System.out.println(cursor2.next());
			}

			/**** Done ****/
			System.out.println("Hecho");

		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
}
