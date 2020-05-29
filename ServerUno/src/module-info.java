module serverUno {
	exports com.b2a.server.uno.game;
	exports com.b2a.server.uno.view;
	opens com.b2a.server.uno.game;
	opens com.b2a.server.uno.view;
	requires org.json;
	requires com.b2a.uno;
	requires java.sql;
	requires java.datatransfer;
	requires java.desktop;
	
}