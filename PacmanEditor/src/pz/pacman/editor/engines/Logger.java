package pz.pacman.editor.engines;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	private static Logger instance;
	
	private String filename;
	private PrintWriter printer;
	
	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
			instance.logInitialInformation();
		}
		
		return instance;
	}
	
	private Logger() {
		this.filename = generateName();

	}
	
	public void newLog() {		
		this.filename = generateName();
	}
	
	protected void logInitialInformation() {
		String date = new SimpleDateFormat("HH:mm:ss, dd/MM/yyyy").format(new Date());
		String initInfo = "[ INIT ] -------- PACMAN LEVEL EDITOR LOGGER STARTED, DATE: "+ date +"--------";
		this.log(initInfo);
	}
	
	public void log(String text) {
		try {
			if (printer == null) {
				printer = new PrintWriter(new FileOutputStream(new File(this.filename),true));
			}
			
			printer.println(text);
			this.close();
		} catch (FileNotFoundException e) {
			// do nothing
		}
	}
	
	public void logInformation(String text) {
		this.log("[ INFO ]: "+ text);
	}
	
	public void logWarning(String text) {
		this.log("[ WARNING ]: "+ text);
	}
	
	public void logError(String text) {
		this.log("[ ERROR ]: "+ text);
	}
	
	public void logException(String exceptionType, String text) {
		this.log("[ EXCEPTION ("+ exceptionType +") ]: "+ text);
	}
	
	private String generateName() {
		DateFormat dateFormat = new SimpleDateFormat("HH_mm_ss____dd_MM_yyyy");
		Date date = new Date();
		return "log_PacmanEditor_"+ dateFormat.format(date) +".log";
	}
	
	private void close() {
		if (printer != null) {
			printer.close();
			printer = null;
		}
	}
}
